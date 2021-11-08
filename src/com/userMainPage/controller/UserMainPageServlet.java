package com.userMainPage.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.model.*;
import com.orders.model.*;
import com.orderList.model.*;

public class UserMainPageServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	};

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("history_orders".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer userID = new Integer(req.getParameter("userID"));
				OrdersService ordersSvc = new OrdersService();
				List<OrdersVO> orders = ordersSvc.getOrdersByUserID(userID); //1003's all orders
				OrderListService orderlistSvc = new OrderListService();
				
//				List<OrderListVO> orderlists = orderlistSvc.getOrderListByOrderNo()
//				List<Integer> itemids = new ArrayList<Integer>();
//				for(OrdersVO order : orders) {
//					//用orderno把所有的list拿出來
//					orderlists = orderlistSvc.getOrderListByOrderNo(order.getOrderNo());
//					for(OrderListVO orderlist : orderlists) {
//						Integer itemid = orderlist.getItemID();
//						itemids.add(itemid);
//					}
//				}
				
				
				req.setAttribute("orders", orders);

				String url = "/html/user/protected_user/userHistoryOrders.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e){
				errorMsgs.add("無法取得歷史訂單資料:" + e.getMessage());
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/user/protected_user/userMainPage.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		

		if ("update_prepare".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer userID = new Integer(req.getParameter("userID"));
				System.out.println(userID);
				UserService userSvc = new UserService();
				UserVO userVO = userSvc.findByUserId(userID);

				req.setAttribute("userVO", userVO);
				
				String url = "/html/user/protected_user/modifyUserInfo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/html/user/protected_user/userMainPage.jsp");
				failureView.forward(req, res);
			}

		}
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer userID = new Integer(req.getParameter("userID").trim());

				String userName = req.getParameter("userName");
				String userNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (userName == null || userName.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if (!userName.trim().matches(userNameReg)) { 
					errorMsgs.add("姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String userAccount = req.getParameter("userAccount");
				if (userAccount == null || userAccount.trim().length() == 0) {
					errorMsgs.add("帳號: 請勿空白");
				}

				String userPassword = req.getParameter("userPassword").trim();
				if (userPassword == null || userPassword.trim().length() == 0) {
					errorMsgs.add("密碼: 請勿空白");
				}

				String passwordConfirm = req.getParameter("passwordConfirm").trim();
				if (!passwordConfirm.equals(userPassword)) {
					errorMsgs.add("兩次密碼輸入不一致");
				} else if (passwordConfirm.trim().length() == 0) {
					errorMsgs.add("確認密碼: 請勿空白");
				}

				String userMobile = req.getParameter("userMobile");
				String userMobileReg = "^[0-9]{10}$";
				if (userMobile == null || userMobile.trim().length() == 0) {
					errorMsgs.add("手機: 請勿空白");
				} else if (!userMobile.trim().matches(userMobileReg)) { 
					errorMsgs.add("手機: 只能10碼數字");
				}

				java.sql.Date userBirthday = null;
				try {
					userBirthday = java.sql.Date.valueOf(req.getParameter("userBirthday").trim());
				} catch (IllegalArgumentException e) {
					userBirthday = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				String userSex = req.getParameter("userSex");
				if (userSex == null || userSex.trim().length() == 0) {
					errorMsgs.add("性別: 請勿空白");
				}

//							Timestamp userRegisterDate = userVO.getUserRegisterDate();

				Integer userReportedTimes = 0;

				UserVO userVO = new UserVO();
				userVO.setUserID(userID);
				userVO.setUserName(userName);
				userVO.setUserAccount(userAccount);
				userVO.setUserPassword(userPassword);
				userVO.setUserMobile(userMobile);
				userVO.setUserSex(userSex);
				userVO.setUserBirthday(userBirthday);
//							userVO.setUserRegisterDate(userRegisterDate);
				userVO.setUserReportedTimes(userReportedTimes);
				req.getSession().setAttribute("name", userName);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("userVO", userVO); 
					RequestDispatcher failureView = req.getRequestDispatcher("/html/user/protected_user/modifyUserInfo.jsp");
					failureView.forward(req, res);
					return; 
				}

				UserService userSvc = new UserService();
				userVO = userSvc.updateUser(userAccount, userName, userPassword, userMobile, userSex, userBirthday,
						userReportedTimes, userID);

				req.setAttribute("userVO", userVO); 
				String url = "/html/user/protected_user/userMainPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/user/protected_user/modifyUserInfo.jsp");
				failureView.forward(req, res);
			}

		}
	}
}
