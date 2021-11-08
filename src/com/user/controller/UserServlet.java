package com.user.controller;

//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Servlet implementation class UserServelet
// */
//@WebServlet("/UserServelet")

import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.user.model.*;

public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) { // 來自sign_up_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = sd.format(new Date(System.currentTimeMillis()));
			Timestamp ts = Timestamp.valueOf(str);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				UserService userSvc = new UserService();
				UserVO userVO1 = null;

//				Integer userID = new Integer(req.getParameter("userID").trim());

				String userName = req.getParameter("userName");
//				String userNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (userName == null || userName.trim().length() == 0) {
//					errorMsgs.add("姓名: 請勿空白");
//				} else if (!userName.trim().matches(userNameReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//				}

				String userAccount = req.getParameter("userAccount");
				String accountReg = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
				if (userAccount == null || userAccount.trim().length() == 0) {
					errorMsgs.add("帳號: 請勿空白!");
				}else if (!userAccount.trim().matches(accountReg)) {
					errorMsgs.add("email格式錯誤");
				}else if(userAccount != null && userSvc.findByUserAccount(userAccount) != null){ 
					errorMsgs.add("帳號: 該用戶已註冊!");
				}

				String userPassword = req.getParameter("userPassword").trim();
				String passwordReg = "^[(a-zA-Z0-9_)]{6,40}$";
				if (userPassword == null || userPassword.trim().length() == 0) {
					errorMsgs.add("密碼: 請勿空白!");
				} else if (!userPassword.trim().matches(passwordReg) && userPassword.trim().length() <= 40) {
					errorMsgs.add("密碼只能是英文字母,數字,且長度不能小於6碼!");
				} else if (!userPassword.trim().matches(passwordReg) && userPassword.trim().length() > 40) {
					errorMsgs.add("密碼長度過長,請勿大於40碼");
				}

				String passwordConfirm = req.getParameter("passwordConfirm").trim();
				if (!passwordConfirm.equals(userPassword)) {
					errorMsgs.add("兩次密碼輸入不一致!");
				} else if (passwordConfirm.trim().length() == 0) {
					errorMsgs.add("確認密碼: 請勿空白!");
				}

				String userMobile = req.getParameter("userMobile");
//				String userMobileReg = "^[0-9]{10}$";
//				if (userMobile == null || userMobile.trim().length() == 0) {
//					errorMsgs.add("手機: 請勿空白");
//				} else if (!userMobile.trim().matches(userMobileReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("手機: 只能10碼數字");
//				}

				java.sql.Date userBirthday = null;
//				try {
//					userBirthday = java.sql.Date.valueOf(req.getParameter("userBirthday").trim());
//				} catch (IllegalArgumentException e) {
//					userBirthday = new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}

				String userSex = req.getParameter("userSex");
//				if (userSex == null || userSex.trim().length() == 0) {
//					errorMsgs.add("性別: 請勿空白");
//				}

				Timestamp userRegisterDate = ts;

				Integer userReportedTimes = 0;

				UserVO userVO = new UserVO();
//				userVO.setUserID(userID);
				userVO.setUserName(userName);
				userVO.setUserAccount(userAccount);
				userVO.setUserPassword(userPassword);
				userVO.setUserMobile(userMobile);
				userVO.setUserSex(userSex);
				userVO.setUserBirthday(userBirthday);
				userVO.setUserRegisterDate(ts);
				userVO.setUserReportedTimes(userReportedTimes);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("userVO", userVO); // 含有輸入格式錯誤的userVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/html/login/sign_up_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				userVO = userSvc.addUser(userAccount, userName, userPassword, userMobile, userSex, userBirthday,
						userRegisterDate, userReportedTimes);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/html/login/login_user.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交login_user.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/login/sign_up_page.jsp");
				failureView.forward(req, res);
			}
		}

//		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//				Integer userID = new Integer(req.getParameter("userID").trim());
//
//				String userName = req.getParameter("userName");
//				String userNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (userName == null || userName.trim().length() == 0) {
//					errorMsgs.add("姓名: 請勿空白");
//				} else if (!userName.trim().matches(userNameReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//				}
//
//				String userAccount = req.getParameter("userAccount");
//				if (userAccount == null || userAccount.trim().length() == 0) {
//					errorMsgs.add("帳號: 請勿空白");
//				}
//
//				String userPassword = req.getParameter("userPassword").trim();
//				if (userPassword == null || userPassword.trim().length() == 0) {
//					errorMsgs.add("密碼: 請勿空白");
//				}
//
////				String passwordConfirm = req.getParameter("passwordConfirm").trim();
////				if (passwordConfirm != userPassword) {
////					errorMsgs.add("兩次密碼輸入不一致");
////				} else if (passwordConfirm.trim().length() == 0) {
////					errorMsgs.add("確認密碼: 請勿空白");
////				}
//
//				String userMobile = req.getParameter("userMobile");
//				String userMobileReg = "^[0-9]{10}$";
//				if (userMobile == null || userMobile.trim().length() == 0) {
//					errorMsgs.add("手機: 請勿空白");
//				} else if (!userMobile.trim().matches(userMobileReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("手機: 只能10碼數字");
//				}
//
//				java.sql.Date userBirthday = null;
//				try {
//					userBirthday = java.sql.Date.valueOf(req.getParameter("userBirthday").trim());
//				} catch (IllegalArgumentException e) {
//					userBirthday = new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
//
//				String userSex = req.getParameter("userSex");
//				if (userSex == null || userSex.trim().length() == 0) {
//					errorMsgs.add("性別: 請勿空白");
//				}
//
////				Timestamp userRegisterDate = userVO.getUserRegisterDate();
//
//				Integer userReportedTimes = 0;
//
//				UserVO userVO = new UserVO();
//				userVO.setUserID(userID);
//				userVO.setUserName(userName);
//				userVO.setUserAccount(userAccount);
//				userVO.setUserPassword(userPassword);
//				userVO.setUserMobile(userMobile);
//				userVO.setUserSex(userSex);
//				userVO.setUserBirthday(userBirthday);
////				userVO.setUserRegisterDate(userRegisterDate);
//				userVO.setUserReportedTimes(userReportedTimes);
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("userVO", userVO); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req.getRequestDispatcher("/html/update_user_input.jsp");
//					failureView.forward(req, res);
//					return; // 程式中斷
//				}
//
//				/*************************** 2.開始修改資料 *****************************************/
//				UserService userSvc = new UserService();
//				userVO = userSvc.updateUser(userAccount, userName, userPassword, userMobile, userSex, userBirthday, userReportedTimes, userID);
//
//				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
//				req.setAttribute("userVO", userVO); // 資料庫update成功後,正確的的userVO物件,存入req
//				String url = "/html/listOneUser.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneUser.jsp
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 *************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/html/update_user_input.jsp");
//				failureView.forward(req, res);
//			}
//		}
//		
//		if ("delete".equals(action)) { // 來自listAllEmp.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 ***************************************/
//				Integer userID = new Integer(req.getParameter("userID"));
//
//				/*************************** 2.開始刪除資料 ***************************************/
//				UserService userSvc = new UserService();
//				userSvc.deleteUser(userID);
//
//				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//				String url = "/html/listAllUser.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/html/listAllUser.jsp");
//				failureView.forward(req, res);
//			}
//		}
//
//		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//				String str = req.getParameter("userID");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入會員編號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/html/select_page.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//				Integer userID = null;
//				try {
//					userID = new Integer(str);
//				} catch (Exception e) {
//					errorMsgs.add("會員編號格式不正確");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/html/select_page.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//				/*************************** 2.開始查詢資料 *****************************************/
//				UserService userSvc = new UserService();
//				UserVO userVO = userSvc.findByUserId(userID);
//				if (userVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/html/select_page.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
//				req.setAttribute("userVO", userVO); // 資料庫取出的userVO物件,存入req
//				String url = "/html/listOneUser.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 *************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/html/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}
//
//		if ("getAccount_For_Display".equals(action)) { // 來自select_page.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//				String str = req.getParameter("userAccount");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入會員帳號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/html/select_page.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//				String userAccount = null;
//				try {
//					userAccount = new String(str);
//				} catch (Exception e) {
//					errorMsgs.add("會員帳號格式不正確");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/html/select_page.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//				/*************************** 2.開始查詢資料 *****************************************/
//				UserService userSvc = new UserService();
//				UserVO userVO = userSvc.findByUserAccount(userAccount);
//				if (userVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/html/select_page.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//
//				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
//				req.setAttribute("userVO", userVO); // 資料庫取出的userVO物件,存入req
//				String url = "/html/listOneUser.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneUser.jsp
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 *************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/html/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}
//
//		if ("getOne_For_Update".equals(action)) { // 來自listAllUser.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 ****************************************/
//				Integer userID = new Integer(req.getParameter("userID"));
//
//				/*************************** 2.開始查詢資料 ****************************************/
//				UserService userSvc = new UserService();
//				UserVO userVO = userSvc.findByUserId(userID);
//
//				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
//				req.setAttribute("userVO", userVO); // 資料庫取出的userVO物件,存入req
//				String url = "/html/update_user_input.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_user_input.jsp
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/html/listAllUser.jsp");
//				failureView.forward(req, res);
//			}
//		}

	}
}
