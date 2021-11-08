package com.subscription.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subscription.model.SubscriptionService;
import com.subscription.model.SubscriptionVO;

public class SubscriptionServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
	
	if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String str = req.getParameter("subNo");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入訂閱編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subscription/select_page.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			
			Integer subNo = null;
			try {
				subNo = new Integer(str);
			} catch (Exception e) {
				errorMsgs.add("訂閱編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subscription/select_page.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			
			/***************************2.開始查詢資料*****************************************/
			SubscriptionService subscriptionSvc = new SubscriptionService();
			SubscriptionVO subscriptionVO = subscriptionSvc.getBySubNo(subNo);
			if (subscriptionVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subscription/select_page.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			
			/***************************3.查詢完成,準備轉交(Send the Success view)*************/
			req.setAttribute("subscriptionVO", subscriptionVO); // 資料庫取出的empVO物件,存入req
			String url = "/subscription/listOneSubscription.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/subscription/select_page.jsp");
			failureView.forward(req, res);
			}
		}
	
	if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		
		try {
			/***************************1.接收請求參數****************************************/
			Integer subNo = new Integer(req.getParameter("subNo"));
			
			/***************************2.開始查詢資料****************************************/
			SubscriptionService subscriptionSvc = new SubscriptionService();
			SubscriptionVO subscriptionVO = subscriptionSvc.getBySubNo(subNo);
							
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("subscriptionVO", subscriptionVO);         // 資料庫取出的empVO物件,存入req
			String url = "/subscription/update_subscription_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理**********************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/subscription/listAllSubscription.jsp");
			failureView.forward(req, res);
			}
		}
	
	if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
		
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
	
		try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			
			Integer subNo = new Integer(req.getParameter("subNo").trim());

			Integer subID = null;
			try {
				subID = new Integer(req.getParameter("subID").trim());
			} catch (NumberFormatException e) {
				subID = null;
				errorMsgs.add("方案編號請填數字.");
			}
			
			Integer userID = null;
			try {
				userID = new Integer(req.getParameter("userID").trim());
			} catch (NumberFormatException e) {
				userID = null;
				errorMsgs.add("會員編號請填數字.");
			}

			SubscriptionVO subscriptionVO = new SubscriptionVO();
			subscriptionVO.setSubNo(subNo);
			subscriptionVO.setSubID(subID);
			subscriptionVO.setUserID(userID);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("subscriptionVO", subscriptionVO); // 含有輸入格式錯誤的subscriptionVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subscription/update_subscription_input.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			
			/***************************2.開始修改資料*****************************************/
			SubscriptionService subscriptionSvc = new SubscriptionService();
			subscriptionVO = subscriptionSvc.updateSubscription(subNo, subID, userID);
			
			/***************************3.修改完成,準備轉交(Send the Success view)*************/
			req.setAttribute("subscriptionVO", subscriptionVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/subscription/listOneSubscription.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/subscription/update_subscription_input.jsp");
			failureView.forward(req, res);
				}
			}
	if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
		
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		
		try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			
			Integer subNo = null;
			try {
				subNo = new Integer(req.getParameter("subNo").trim());
			} catch (NumberFormatException e) {
				subNo = null;
				errorMsgs.add("訂閱編號請填數字.");
			}
			
			Integer subID = null;
			try {
				subID = new Integer(req.getParameter("subID").trim());
			} catch (NumberFormatException e) {
				subID = null;
				errorMsgs.add("方案編號請填數字.");
			}
			
			Integer userID = null;
			try {
				userID = new Integer(req.getParameter("userID").trim());
			} catch (NumberFormatException e) {
				userID = null;
				errorMsgs.add("教練編號請填數字.");
			}
			
			SubscriptionVO subscriptionVO = new SubscriptionVO();
			subscriptionVO.setSubNo(subNo);
			subscriptionVO.setSubID(subID);
			subscriptionVO.setUserID(userID);
			
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("subscriptionVO", subscriptionVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subscription/addSubscription.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.開始新增資料*****************************************/
			SubscriptionService subscriptionSvc = new SubscriptionService();
			subscriptionVO = subscriptionSvc.addSubscription(subNo, subID, userID);
			
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			String url = "/subscription/listAllSubscription.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);				
			
			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/subscription/addSubscription.jsp");
			failureView.forward(req, res);
			}
		}
	if ("delete".equals(action)) { // 來自listAllEmp.jsp

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/***************************1.接收請求參數***************************************/
			Integer subNo = new Integer(req.getParameter("subNo"));
			
			/***************************2.開始刪除資料***************************************/
			SubscriptionService subscriptionSvc = new SubscriptionService();
			subscriptionSvc.deleteSubscription(subNo);
			
			/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
			String url = "/subscription/listAllSubscription.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理**********************************/
		} catch (Exception e) {
			errorMsgs.add("刪除資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/subscription/listAllSubscription.jsp");
			failureView.forward(req, res);
			}
		}
	}
}