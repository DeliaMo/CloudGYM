package com.userAuth.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.userAuth.*;
import com.userAuth.model.UserAuthService;
import com.userAuth.model.UserAuthVO;

public class UserAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("AuthMgt");
		
		if("stopUser".equals(action)) {//接收來自back_end_user.jsp/back_end_coach.jsp(停權按鈕)
			try {
			/***************************1.接收 check-box 請求參數 **********************/
			UserAuthService userAuthSvc = new UserAuthService();			

			String[] user = req.getParameterValues("userid");						
			if(user != null) {
				for(int i = 0; i < user.length; i++) {
					Integer userID = Integer.valueOf(user[i]);
							
					UserAuthVO userAuthVO = new UserAuthVO();
					userAuthVO = userAuthSvc.getUserID(userID);	
					
			/***************************2.開始修改資料*****************************************/
					Integer banComment = null;
					Integer banShopping = null;
					Integer banVideo = null;
					Integer banUsers = null;
					java.sql.Timestamp startTime = new java.sql.Timestamp(System.currentTimeMillis());
					
					if(userAuthVO==null) {				//判斷資料庫是否有值: 無值 走 insert								
						banComment = 0;
						banShopping = 0;
						banVideo = 0;
						banUsers = 1;
																		
						userAuthVO = userAuthSvc.addUserAuth(userID, banComment, banShopping, banVideo, banUsers, startTime);
																		
					}else {	//有值 走update
						userAuthVO.setUserID(userID);
						Integer getBanUsers = userAuthVO.getBanUsers();
						if (getBanUsers == 0) {//判斷按鈕切換功能
							userAuthVO.setBanUsers(1);							
						}else {
							userAuthVO.setBanUsers(0);													
						}
						userAuthVO.setStartTime(startTime);				
						userAuthVO = userAuthSvc.updateUserAuth(userAuthVO);						
					}
											
//					System.out.println(vo.getUserID().getClass().getSimpleName());												

					System.out.println(userAuthVO.getUserID());
					System.out.println(userAuthVO.getBanUsers());
				
				}				
			}
											
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			HttpSession session = req.getSession();
			Integer whichPage = (Integer)session.getAttribute("whichPage");
			RequestDispatcher successView = null;
			String url = req.getParameter("page");
				if("usermanager".equals(url)) {
					successView = req.getRequestDispatcher("/html/back_end/back_end_user.jsp?whichPage="+whichPage);
					successView.forward(req, res);					
				}else {
					successView = req.getRequestDispatcher("/html/back_end/back_end_coach.jsp?whichPage="+whichPage);
					successView.forward(req, res);					
				}
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				System.out.println("修改失敗:"+e.getMessage());
			}
		}
		
		if("stopComment".equals(action)) {//接收來自back_end_user.jsp/back_end_coach.jsp(禁言按鈕)
			try {
			/***************************1.接收 check-box 請求參數 **********************/
			UserAuthService userAuthSvc = new UserAuthService();			

			String[] user = req.getParameterValues("userid");						
			if(user != null) {
				for(int i = 0; i < user.length; i++) {
					Integer userID = Integer.valueOf(user[i]);

					UserAuthVO userAuthVO = new UserAuthVO();
					userAuthVO = userAuthSvc.getUserID(userID);	
					
			/***************************2.開始修改資料*****************************************/
					Integer banComment = null;
					Integer banShopping = null;
					Integer banVideo = null;
					Integer banUsers = null;
					java.sql.Timestamp startTime = new java.sql.Timestamp(System.currentTimeMillis());
					
					if(userAuthVO==null) {				//判斷資料庫是否有值: 無值 走 insert								
						banComment = 1;
						banShopping = 0;
						banVideo = 0;
						banUsers = 0;
																		
						userAuthVO = userAuthSvc.addUserAuth(userID, banComment, banShopping, banVideo, banUsers, startTime);
																		
					}else {	//有值 走update
						userAuthVO.setUserID(userID);
						Integer getBanComm = userAuthVO.getBanComment();
						if (getBanComm == 0) {//判斷按鈕切換功能
							userAuthVO.setBanComment(1);							
						}else {
							userAuthVO.setBanComment(0);													
						}
						userAuthVO.setStartTime(startTime);				
						userAuthVO = userAuthSvc.updateUserAuth(userAuthVO);						
					}
											
					System.out.println(userAuthVO.getUserID());
					System.out.println(userAuthVO.getBanComment());
				
				}				
			}
											
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			HttpSession session = req.getSession();
			Integer whichPage = (Integer)session.getAttribute("whichPage");
			RequestDispatcher successView = null;
			String url = req.getParameter("page");
				if("usermanager".equals(url)) {
					successView = req.getRequestDispatcher("/html/back_end/back_end_user.jsp?whichPage="+whichPage);
					successView.forward(req, res);					
				}else {
					successView = req.getRequestDispatcher("/html/back_end/back_end_coach.jsp?whichPage="+whichPage);
					successView.forward(req, res);					
				}
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				System.out.println("修改失敗:"+e.getMessage());
			}

		}
		
		if("stopBuy".equals(action)) {//接收來自back_end_user.jsp(禁止購買按鈕)
			try {
			/***************************1.接收 check-box 請求參數 **********************/
			UserAuthService userAuthSvc = new UserAuthService();			

			String[] user = req.getParameterValues("userid");						
			if(user != null) {
				for(int i = 0; i < user.length; i++) {
					Integer userID = Integer.valueOf(user[i]);

					UserAuthVO userAuthVO = new UserAuthVO();
					userAuthVO = userAuthSvc.getUserID(userID);	
					
			/***************************2.開始修改資料*****************************************/
					Integer banComment = null;
					Integer banShopping = null;
					Integer banVideo = null;
					Integer banUsers = null;
					java.sql.Timestamp startTime = new java.sql.Timestamp(System.currentTimeMillis());
					
					if(userAuthVO==null) {				//判斷資料庫是否有值: 無值 走 insert								
						banComment = 0;
						banShopping = 1;
						banVideo = 0;
						banUsers = 0;
																		
						userAuthVO = userAuthSvc.addUserAuth(userID, banComment, banShopping, banVideo, banUsers, startTime);
																		
					}else {	//有值 走update
						userAuthVO.setUserID(userID);
						Integer getBanBuy = userAuthVO.getBanShopping();
						if (getBanBuy == 0) {//判斷按鈕切換功能
							userAuthVO.setBanShopping(1);							
						}else {
							userAuthVO.setBanShopping(0);													
						}
						userAuthVO.setStartTime(startTime);				
						userAuthVO = userAuthSvc.updateUserAuth(userAuthVO);						
					}
											
					System.out.println(userAuthVO.getUserID());
					System.out.println(userAuthVO.getBanShopping());
				
				}				
			}
											
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			HttpSession session = req.getSession();
			Integer whichPage = (Integer)session.getAttribute("whichPage");
			RequestDispatcher successView = null;
			String url = req.getParameter("page");
				if("usermanager".equals(url)) {
					successView = req.getRequestDispatcher("/html/back_end/back_end_user.jsp?whichPage="+whichPage);
					successView.forward(req, res);					
				}else {
					successView = req.getRequestDispatcher("/html/back_end/back_end_coach.jsp?whichPage="+whichPage);
					successView.forward(req, res);					
				}
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				System.out.println("修改失敗:"+e.getMessage());
			}

		}
		
		if("stopUpload".equals(action)) {//接收來自back_end_coach.jsp(禁止上傳按鈕)
			try {
			/***************************1.接收 check-box 請求參數 **********************/
			UserAuthService userAuthSvc = new UserAuthService();			

			String[] user = req.getParameterValues("userid");						
			if(user != null) {
				for(int i = 0; i < user.length; i++) {
					Integer userID = Integer.valueOf(user[i]);

					UserAuthVO userAuthVO = new UserAuthVO();
					userAuthVO = userAuthSvc.getUserID(userID);	
					
			/***************************2.開始修改資料*****************************************/
					Integer banComment = null;
					Integer banShopping = null;
					Integer banVideo = null;
					Integer banUsers = null;
					java.sql.Timestamp startTime = new java.sql.Timestamp(System.currentTimeMillis());
					
					if(userAuthVO==null) {				//判斷資料庫是否有值: 無值 走 insert								
						banComment = 0;
						banShopping = 0;
						banVideo = 1;
						banUsers = 0;
																		
						userAuthVO = userAuthSvc.addUserAuth(userID, banComment, banShopping, banVideo, banUsers, startTime);
																		
					}else {	//有值 走update
						userAuthVO.setUserID(userID);
						Integer getBanUpload = userAuthVO.getBanVideo();
						if (getBanUpload == 0) {//判斷按鈕切換功能
							userAuthVO.setBanVideo(1);							
						}else {
							userAuthVO.setBanVideo(0);													
						}
						userAuthVO.setStartTime(startTime);				
						userAuthVO = userAuthSvc.updateUserAuth(userAuthVO);						
					}
											
					System.out.println(userAuthVO.getUserID());
					System.out.println(userAuthVO.getBanVideo());				
				}				
			}
											
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			HttpSession session = req.getSession();
			Integer whichPage = (Integer)session.getAttribute("whichPage");
			RequestDispatcher successView = null;
			String url = req.getParameter("page");
				if("usermanager".equals(url)) {
					successView = req.getRequestDispatcher("/html/back_end/back_end_user.jsp?whichPage="+whichPage);
					successView.forward(req, res);					
				}else {
					successView = req.getRequestDispatcher("/html/back_end/back_end_coach.jsp?whichPage="+whichPage);
					successView.forward(req, res);					
				}
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				System.out.println("修改失敗:"+e.getMessage());
			}

		}
		
	}

}