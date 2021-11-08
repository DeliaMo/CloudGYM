package com.orders.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.coach.model.*;
import com.coachMenu.model.*;
import com.coachMenuList.model.*;
import com.google.gson.Gson;
import com.orderList.model.*;
import com.orders.model.*;
import com.subList.model.*;
import com.user.model.*;
import com.userRights.model.*;
import com.video.model.*;

import others.CardInfo;
import redis.clients.jedis.Jedis;
import javax.naming.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrdersServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("addCart".equals(action)) {

			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Jedis jedis = new Jedis("localhost", 6379);

			try {
				HttpSession session = req.getSession();
				String userID = session.getAttribute("userID").toString();
				String uri = (String) session.getAttribute("uri");

				if (userID == null) { // 判斷是否有登入
					errorMsgs.add("請先登入會員");
					res.sendRedirect(uri);
					return;
				}

				// 取得商品id和商品名稱並存到redis裡
				String menuID = req.getParameter("menuID");
				String menuPrice = req.getParameter("menuPrice");
				String subID = req.getParameter("subID");
				String coachID = req.getParameter("coachID");
				String videoID = req.getParameter("videoID");
				String videoPrice = req.getParameter("videoPrice");

				VideoService videoSvc = new VideoService();
				CoachMenuService coachmenuSvc = new CoachMenuService();
				Set<String> keys = jedis.hkeys(userID);
				
				if (menuID != null && menuPrice != null) {
					jedis.hset(userID, menuID, menuPrice);
					CoachMenuVO coachMenuVO = coachmenuSvc.getByMenuID(new Integer(menuID));
					String id = coachMenuVO.getUserID().toString();
					
					CoachMenuListService coachmenulistSvc = new CoachMenuListService();
					List<CoachMenuListVO> list = coachmenulistSvc.getByMenuID(Integer.parseInt(menuID));
					List<String> videoIDs = new ArrayList<String>();
					for(CoachMenuListVO coachmenulist : list) {
						videoIDs.add(coachmenulist.getVideoID().toString());
					}
					System.out.println(videoIDs);
					if(keys != null) {
						for(String key : keys) {
							if(id.equals(key)) {
								jedis.hdel(userID, menuID);
							}
							if(key.charAt(0) == '3') {
								VideoVO videoVO = videoSvc.findByPrimaryKey(Integer.parseInt(key));
								if(videoIDs.contains(key)) {
									jedis.hdel(userID, key);
								}
							}
						}
					}
				}
				
				
				if (subID != null && coachID != null) {
					jedis.hset(userID, coachID, subID);
					for(String key : keys) {
						if(key.charAt(0) == '3') {
							VideoVO videoVO = videoSvc.findByPrimaryKey(Integer.parseInt(key));
							String id = videoVO.getUserID().toString();
							if(coachID.equals(id)) {
								jedis.hdel(userID, key);
							}
						}
						if(key.charAt(0) == '6') {
							CoachMenuVO coachMenuVO = coachmenuSvc.getByMenuID(Integer.parseInt(key));
							String id = coachMenuVO.getUserID().toString();
							if(coachID.equals(id)) {
								jedis.hdel(userID, key);
							}
						}
					}
				}

				if (videoID != null && videoPrice != null) {
					jedis.hset(userID, videoID, videoPrice);
					VideoVO videoVO = videoSvc.findByPrimaryKey(new Integer(videoID));
					String id = videoVO.getUserID().toString();
					if(keys != null) {
						for(String key : keys) {
							if(id.equals(key)) {
								jedis.hdel(userID, videoID);
							}
						}
					}
				}
				
				// 把要傳回ajax的資料用list包起來
				List<Long> list = new ArrayList<>();
				long hlen = jedis.hlen(userID);
				list.add(hlen);
				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);

				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jedis.close();
				out.close();
//				req.getRequestDispatcher(servletPath).forward(req, res);
			}
		}

		if ("deleteProduct".equals(action)) {
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();

			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			Jedis jedis = new Jedis("localhost", 6379);
			try {
				// 取得當前登入的人
				HttpSession session = req.getSession();
				String userID = session.getAttribute("userID").toString();
				// 取得要刪除的menuID或userID
				String menuID = req.getParameter("menuID");
				String coachID = req.getParameter("coachID");
				String videoID = req.getParameter("videoID");
				Set<String> keys = jedis.hkeys(userID);
				int deletedPrice = 0;

				// 判斷要用menuID還是userID刪除資料
				if (menuID != null) {
					// 透過menuID取得價格
//					CoachMenuService coachMenuSvc = new CoachMenuService();
//					CoachMenuVO coachMenuVO = coachMenuSvc.getByMenuID(Integer.parseInt(menuID));
//					deletedPrice = coachMenuVO.getPrice();
					deletedPrice = Integer.parseInt(jedis.hget(userID, menuID));
					
					
					// 透過menuID去刪redis裡的資料 = 刪除購物車裡的東西
					jedis.hdel(userID, menuID);
				}
				
				if (coachID != null) {
					// 透過redis取得subID再去取得價格
					Integer subID = Integer.parseInt(jedis.hget(userID, coachID));
					SubListService sublistSvc = new SubListService();
					SubListVO sublistVO = sublistSvc.getBySubID(subID);
					deletedPrice = sublistVO.getPrice();

					// 刪除redis裡的資料
					jedis.hdel(userID, coachID);
				}

				if (videoID != null) {
					// 透過videoID取得價格
//					VideoService videoSvc = new VideoService();
//					VideoVO videoVO = videoSvc.findByPrimaryKey(Integer.parseInt(videoID));
//					deletedPrice = videoVO.getPrice();
					deletedPrice = Integer.parseInt(jedis.hget(userID, videoID));

					// 刪除redis裡的資料
					jedis.hdel(userID, videoID);
				}

				// 把要傳回ajax的資料用list包起來
				List list = new ArrayList();
				Long hlen = jedis.hlen(userID);
				list.add(deletedPrice);
				list.add(hlen);

				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);
				req.setAttribute("hlen", jedis.hlen(userID));

				

				// 刪除完成，返回原頁面
//				res.sendRedirect(req.getContextPath() + "/html/pay_page.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (jedis != null) {
					jedis.close();
				}
				if (out != null) {
					out.close();
				}
			}
		}
		
		if("pay".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			Jedis jedis = null;
			
			try {
				/**************************1.錯誤處理*************************/
				String cardNumber = req.getParameter("cardNumber");
				if(cardNumber == null || cardNumber.trim().length() == 0) {
					errorMsgs.add("卡號不得為空");
				}
				
				String cardName = req.getParameter("cardName");
				if(cardName == null || cardName.trim().length() == 0) {
					errorMsgs.add("請輸入持卡人姓名");
				}
				
				String expire = req.getParameter("expire");
				if(expire == null || expire.trim().length() == 0) {
					errorMsgs.add("請輸入卡片有效期限");
				}
				
				String ccv = req.getParameter("ccv");
				if(ccv == null || ccv.trim().length() == 0) {
					errorMsgs.add("請輸入卡片安全碼");
				}
				
				CardInfo cardinfo = new CardInfo();
				cardinfo.setCardNumber(cardNumber);
				cardinfo.setCardName(cardName);
				cardinfo.setExpire(expire);
				cardinfo.setCcv(ccv);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("cardinfo", cardinfo);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/order/pay_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/**************************2.開始新增訂單*************************/
				jedis = new Jedis("localhost", 6379);
				HttpSession session = req.getSession();
				String userID = session.getAttribute("userID").toString();
				SubListService sublistSvc = new SubListService();
				OrdersService ordersSvc = new OrdersService();
				OrderListService orderlistSvc = new OrderListService();
				
				Integer totalPrice = 0;
				List<Integer> items = new ArrayList<Integer>();
				List<Integer> coachIDs = new ArrayList<Integer>();
				List<Integer> subIDs = new ArrayList<Integer>();
				
				
				Set<String> set = jedis.hkeys(userID);
				for(String keys : set) {
					if(keys.charAt(0) != '2') {
						items.add(Integer.parseInt(keys));
						Integer price = Integer.parseInt(jedis.hget(userID, keys));
						totalPrice += price;
					}else {
						Integer coachID = Integer.parseInt(keys);
						coachIDs.add(coachID);
						
						Integer subID = Integer.parseInt(jedis.hget(userID, keys));
						subIDs.add(subID);
						items.add(subID);

						SubListVO sublist = sublistSvc.getBySubID(subID);
						Integer price = sublist.getPrice();
						totalPrice += price;
					}
				}
				
//				OrdersVO ordersVO = ordersSvc.addOrders(Integer.parseInt(userID), totalPrice);
//				Integer orderNo = ordersVO.getOrderNo();
//				req.setAttribute("orderNo", orderNo);
//				req.setAttribute("coachIDs", coachIDs);
//				req.setAttribute("subIDs", subIDs);
//				
//				for(Integer itemID : items) {
//					orderlistSvc.addOrderList(orderNo, itemID);
//				}
				
				OrdersVO ordersVO = ordersSvc.addOrders2(Integer.parseInt(userID), totalPrice, items);
				Integer orderNo = ordersVO.getOrderNo();
				System.out.println(orderNo);
				req.setAttribute("orderNo", orderNo);
				req.setAttribute("coachIDs", coachIDs);
				req.setAttribute("subIDs", subIDs);
				
				
				/*********************3.將訂單明細的東西加到使用者可觀看的資料庫裡*******************/
				VideoService videoSvc = new VideoService();
				CoachMenuListService coachmenulistSvc = new CoachMenuListService();
				UserRightsService userrightsSvc = new UserRightsService();
				
				// 取得使用者已經有的影片權限
				List<Integer> videoRight = new ArrayList<>();
				List<UserRightsVO> userRightsList = userrightsSvc.getAll(Integer.parseInt(userID));
				for(UserRightsVO vo : userRightsList) {
					Integer id = vo.getVideoID();
					videoRight.add(id);
				}
				//---------------------------------------------------------------------
				
				for(String key : set) {
					if(key.startsWith("2")) {
						Integer duration = 0;
						Integer subID = Integer.parseInt(jedis.hget(userID, key));
						SubListVO sublistVO = sublistSvc.getBySubID(subID);
						if(sublistVO.getDuration().startsWith("一")) {
							duration = 30;
						}
						if(sublistVO.getDuration().startsWith("三")) {
							duration = 90;
						}
						if(sublistVO.getDuration().startsWith("十")) {
							duration = 365;
						}
						List<VideoVO> list = videoSvc.getByUserID(Integer.parseInt(key));
						for(VideoVO videoVO : list) {
							Integer videoID = videoVO.getVideoID();
							if(videoRight.contains(videoID)) {
								continue;
							}else {
								userrightsSvc.add(Integer.parseInt(userID), videoID, duration);
							}
						}
					}
					if(key.startsWith("6")) {
						List<CoachMenuListVO> list = coachmenulistSvc.getByMenuID(Integer.parseInt(key));
						for(CoachMenuListVO coachMenuListVO : list) {
							Integer videoID = coachMenuListVO.getVideoID();
							if(videoRight.contains(videoID)) {
								continue;
							}else {
								userrightsSvc.add(Integer.parseInt(userID), videoID, 0);
							}
						}
					}
					if(key.startsWith("3")) {
						if(!videoRight.contains(Integer.parseInt(key))) {
							userrightsSvc.add(Integer.parseInt(userID), Integer.parseInt(key), 0);
						}
					}
				}
				
				//----------------------------寄出購買成功通知信件---------------------------//
				UserService userSvc = new UserService();
				UserVO userVO = userSvc.findByUserId(Integer.parseInt(userID));
				String usermail = userVO.getUserAccount();
				String subject = "CloudGYM購買成功通知";
				String mailtext = "您在CloudGYM的購買已完成，趕快一起動起來吧";
				
				sendMail(usermail, subject, mailtext);
				
				jedis.del(userID);
				/************************4.新增訂單資料完成，轉交至成功頁面***********************/
				
				RequestDispatcher successView = req.getRequestDispatcher("/html/order/thanks_page.jsp");
				successView.forward(req, res);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(jedis != null) {
					jedis.close();
				}
			}
		}

	}
	
	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
		public void sendMail(String to, String subject, String messageText) {
				
		   try {
			   // 設定使用SSL連線至 Gmail smtp Server
			   Properties props = new Properties();
			   props.put("mail.smtp.host", "smtp.gmail.com");
			   props.put("mail.smtp.socketFactory.port", "465");
			   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			   props.put("mail.smtp.auth", "true");
			   props.put("mail.smtp.port", "465");

	       // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
	       // ●須將myGmail的【安全性較低的應用程式存取權】打開
		     final String myGmail = "tfa103cloudgym@gmail.com";
		     final String myGmail_password = "tfa103group3";
			   Session session = Session.getInstance(props, new Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
					   return new PasswordAuthentication(myGmail, myGmail_password);
				   }
			   });

			   Message message = new MimeMessage(session);
			   message.setFrom(new InternetAddress(myGmail));
			   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			  
			   //設定信中的主旨  
			   message.setSubject(subject);
			   //設定信中的內容 
			   message.setText(messageText);

			   Transport.send(message);
			   System.out.println("傳送成功!");
	     }catch (MessagingException e){
		     System.out.println("傳送失敗!");
		     e.printStackTrace();
	     }
	   }
}
