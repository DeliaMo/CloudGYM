package com.coach.controller;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coach.model.CoachService;
import com.coach.model.CoachVO;
@MultipartConfig
public class CoachServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("userID");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入教練編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/all_coach_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				Integer userID = null;
				try {
					userID = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("教練編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/all_coach_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				CoachService coachSvc = new CoachService();
				CoachVO coachVO = coachSvc.getByUserID(userID);
				if (coachVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/all_coach_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("coachVO", coachVO); // 資料庫取出的coachVO物件,存入req
				String url = "/html/coach/coach_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneCoach.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/all_coach_page.jsp");
				failureView.forward(req, res);
			}
		}

//		if ("getOne_For_Update".equals(action)) {
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/***************************1.接收請求參數****************************************/
//				Integer userID = new Integer(req.getParameter("userID"));
//				
//				/***************************2.開始查詢資料****************************************/
//				CoachService coachSvc = new CoachService();
//				CoachVO coachVO = coachSvc.getByUserID(userID);
//								
//				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				req.setAttribute("coachVO", coachVO);         // 資料庫取出的CoachVO物件,存入req
//				String url = "/html/changeCoachInfo_page.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_coach_input.jsp
//				successView.forward(req, res);
//
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/html/changeCoachInfo_page.jsp");
//				failureView.forward(req, res);
//				}
//			}

		if ("update".equals(action)) { // 來自update_coach_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

				Integer userID = new Integer(req.getParameter("userID").trim());
		
//				String coachAccount = req.getParameter("coachAccount");
//				String coachAccountReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (coachAccount == null || coachAccount.trim().length() == 0) {
//					errorMsgs.add("教練帳號: 請勿空白");
//				} else if (!coachAccount.trim().matches(coachAccountReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("教練帳號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//				}

				String coachName = req.getParameter("coachName").trim();
//				String coachNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,50}$";
				if (coachName == null || coachName.trim().length() == 0) {
					errorMsgs.add("教練名字: 請勿空白");
					System.out.println("教練名字: 請勿空白");
				} 
//				else if (!coachName.trim().matches(coachNameReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("教練名字: 只能是中、英文字母、數字和_ , 且長度必需在1到50之間");
//					System.out.println("教練名字: 只能是中、英文字母、數字和_ , 且長度必需在1到50之間");
//				}
				System.out.println(coachName);
				
//				String coachPassword = req.getParameter("coachPassword");
//				String coachPasswordReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (coachPassword == null || coachPassword.trim().length() == 0) {
//					errorMsgs.add("教練密碼: 請勿空白");
//				} else if (!coachPassword.trim().matches(coachPasswordReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("教練密碼: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//				}

//				String userMobile = req.getParameter("userMobile").trim();
//				if (userMobile == null || userMobile.trim().length() == 0) {
//					errorMsgs.add("教練電話請勿空白");
//				}

				InputStream in = req.getPart("coachImg").getInputStream();
				byte[] coachImg = null;
				if (in.available() != 0) {
					coachImg = new byte[in.available()];
					in.read(coachImg);
					in.close();
					System.out.println("ok");
				} 
				else {
					errorMsgs.add("請上傳圖片");
					System.out.println("上傳圖片");
				}
				
//				String coachSex = req.getParameter("coachSex").trim();
//				if (coachSex == null || coachSex.trim().length() == 0) {
//					errorMsgs.add("教練性別請勿空白");
//				}

//				java.sql.Date coachBirthday = null;
//				try {
//					coachBirthday = java.sql.Date.valueOf(req.getParameter("coachBirthday").trim());
//				} catch (IllegalArgumentException e) {
//					coachBirthday = new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}

				String coachDescription = req.getParameter("coachDescription").trim();
//				if (coachDescription == null || coachDescription.trim().length() == 0) {
//					errorMsgs.add("自我介紹請勿空白");
//				}
				System.out.println(coachDescription);
				
//				java.sql.Date coachRegisteredDate = null;
//				try {
//					coachRegisteredDate = java.sql.Date.valueOf(req.getParameter("coachRegisteredDate").trim());
//				} catch (IllegalArgumentException e) {
//					coachRegisteredDate = new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}

				String coachCertificate = req.getParameter("coachCertificate").trim();
//				if (coachCertificate == null || coachCertificate.trim().length() == 0) {
//					errorMsgs.add("教練證照請勿空白");
//				}
				System.out.println(coachCertificate);
				
//				Integer reportedTimes = null;
//				try {
//					reportedTimes = new Integer(req.getParameter("reportedTimes").trim());
//				} catch (NumberFormatException e) {
//					reportedTimes = null;
//					errorMsgs.add("被舉報次數請填數字.");
//				}

				

				CoachVO coachVO = new CoachVO();
				coachVO.setUserID(userID);
				coachVO.setCoachName(coachName);
				coachVO.setCoachImg(coachImg);
				coachVO.setCoachDescription(coachDescription);
				coachVO.setCoachCertificate(coachCertificate);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachVO", coachVO); // 含有輸入格式錯誤的coachVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/protected_coach/changeCoachInfo_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				CoachService coachSvc = new CoachService();
				coachVO = coachSvc.updateCoach(coachVO);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("coachVO", coachVO); // 資料庫update成功後,正確的的coachVO物件,存入req
				String url = "/html/coach/coach_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneCoach.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/protected_coach/changeCoachInfo_page.jsp");
				failureView.forward(req, res);
			}
		}

//		if ("insert".equals(action)) { // 來自addCoach.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//				Integer userID = null;
//				try {
//					userID = new Integer(req.getParameter("userID").trim());
//				} catch (NumberFormatException e) {
//					userID = null;
//					errorMsgs.add("教練編號請填數字.");
//				}
//
//				String coachAccount = req.getParameter("coachAccount");
//				String coachAccountReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (coachAccount == null || coachAccount.trim().length() == 0) {
//					errorMsgs.add("教練帳號: 請勿空白");
//				} else if (!coachAccount.trim().matches(coachAccountReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("教練帳號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//				}
//
//				String coachName = req.getParameter("coachName").trim();
//				if (coachName == null || coachName.trim().length() == 0) {
//					errorMsgs.add("教練名字請勿空白");
//				}
//
//				String coachPassword = req.getParameter("coachPassword");
//				String coachPasswordReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//				if (coachPassword == null || coachPassword.trim().length() == 0) {
//					errorMsgs.add("教練密碼: 請勿空白");
//				} else if (!coachPassword.trim().matches(coachPasswordReg)) { // 以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("教練密碼: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//				}
//
//				String userMobile = req.getParameter("userMobile").trim();
//				if (userMobile == null || userMobile.trim().length() == 0) {
//					errorMsgs.add("教練電話請勿空白");
//				}
//
//				InputStream in = req.getPart("coachImg").getInputStream();
//				byte[] coachImg = null;
//				if (in.available() != 0) {
//					coachImg = new byte[in.available()];
//					in.read(coachImg);
//					in.close();
//				} else {
//					errorMsgs.add("請上傳圖片");
//				}
//
//				String coachSex = req.getParameter("coachSex").trim();
//				if (coachSex == null || coachSex.trim().length() == 0) {
//					errorMsgs.add("教練性別請勿空白");
//				}
//
//				java.sql.Date coachBirthday = null;
//				try {
//					coachBirthday = java.sql.Date.valueOf(req.getParameter("coachBirthday").trim());
//				} catch (IllegalArgumentException e) {
//					coachBirthday = new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
//
//				String coachDescription = req.getParameter("coachDescription").trim();
//				if (coachDescription == null || coachDescription.trim().length() == 0) {
//					errorMsgs.add("自我介紹請勿空白");
//				}
//
//				java.sql.Date coachRegisteredDate = null;
//				try {
//					coachRegisteredDate = java.sql.Date.valueOf(req.getParameter("coachRegisteredDate").trim());
//				} catch (IllegalArgumentException e) {
//					coachRegisteredDate = new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
//
//				String coachCertificate = req.getParameter("coachCertificate").trim();
//				if (coachCertificate == null || coachCertificate.trim().length() == 0) {
//					errorMsgs.add("教練證照請勿空白");
//				}
//
//				Integer reportedTimes = null;
//				try {
//					reportedTimes = new Integer(req.getParameter("reportedTimes").trim());
//				} catch (NumberFormatException e) {
//					reportedTimes = null;
//					errorMsgs.add("被舉報次數請填數字.");
//				}
//
//				CoachVO coachVO = new CoachVO();
//				coachVO.setUserID(userID);
//				coachVO.setCoachAccount(coachAccount);
//				coachVO.setCoachName(coachName);
//				coachVO.setCoachPassword(coachPassword);
//				coachVO.setUserMobile(userMobile);
//				byte[] coachImg1 = null;
//				coachVO.setCoachImg(coachImg1);
//				coachVO.setCoachSex(coachSex);
//				coachVO.setCoachBirthday(coachBirthday);
//				coachVO.setCoachDescription(coachDescription);
//				coachVO.setCoachRegisteredDate(coachRegisteredDate);
//				coachVO.setCoachCertificate(coachCertificate);
//				coachVO.setReportedTimes(reportedTimes);
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("coachVO", coachVO); // 含有輸入格式錯誤的coachVO物件,也存入req
//					RequestDispatcher failureView = req.getRequestDispatcher("/coach/addCoach.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				/*************************** 2.開始新增資料 ***************************************/
//				CoachService coachSvc = new CoachService();
//				coachVO = coachSvc.addCoach(userID, coachAccount, coachName, coachPassword, coachImg1, userMobile,
//						coachSex, coachBirthday, coachDescription, coachRegisteredDate, coachCertificate,
//						reportedTimes);
//
//				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//				String url = "/coach/listAllCoach.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllCoach.jsp
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/coach/addCoach.jsp");
//				failureView.forward(req, res);
//			}
//		}

//		if ("delete".equals(action)) { // 來自listAllCoach.jsp
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
//				CoachService coachSvc = new CoachService();
//				coachSvc.deleteCoach(userID);
//
//				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//				String url = "/coach/listAllCoach.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/coach/listAllCoach.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
		if ("gotocoach".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.��隢�� ****************************************/
				Integer userID = new Integer(req.getParameter("userID"));

				/*************************** 2.���閰Ｚ��� ****************************************/
				CoachService coachSvc = new CoachService();
				CoachVO coachVO = coachSvc.getByUserID(userID);

				/*************************** 3.�閰Ｗ���,皞��漱(Send the Success view) *************/
				req.setAttribute("coachVO", coachVO);
				RequestDispatcher successView = req.getRequestDispatcher("/html/coach/coach_page.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
