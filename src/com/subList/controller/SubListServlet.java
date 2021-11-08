package com.subList.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subList.model.SubListService;
import com.subList.model.SubListVO;

public class SubListServlet extends HttpServlet {

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
				String str = req.getParameter("subID");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入方案編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/subList/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer subID = null;
				try {
					subID = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("方案編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/subList/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				SubListService subListSvc = new SubListService();
				SubListVO subListVO = subListSvc.getBySubID(subID);
				if (subListVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/subList/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("subListVO", subListVO); // 資料庫取出的empVO物件,存入req
				String url = "/subList/listOneSubList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subList/select_page.jsp");
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
				Integer subID = new Integer(req.getParameter("subID"));
				
				/***************************2.開始查詢資料****************************************/
				SubListService subListSvc = new SubListService();
				SubListVO subListVO = subListSvc.getBySubID(subID);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("subListVO", subListVO);         // 資料庫取出的empVO物件,存入req
				String url = "/subList/update_subList_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subList/listAllSubList.jsp");
				failureView.forward(req, res);
				}
			}
		
		if ("update".equals(action)) { // 來自back_end_sublit_update.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				Integer subID = new Integer(req.getParameter("subID").trim());

				String duration = req.getParameter("duration").trim();
				if (duration == null || duration.trim().length() == 0) {
					errorMsgs.add("方案時長請勿空白");
				}
				
				String subName = req.getParameter("subName").trim();
//				if (subName == null || subName.trim().length() == 0) {
//					errorMsgs.add("方案名稱請勿空白");
//				}
				
				Integer price = null;
				try {
					price = new Integer(req.getParameter("price").trim());
				} catch (NumberFormatException e) {
					price = null;
					errorMsgs.add("價格請填數字.");
				}

				SubListVO subListVO = new SubListVO();
				subListVO.setSubID(subID);
				subListVO.setDuration(duration);
				subListVO.setSubName(subName);
				subListVO.setPrice(price);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("subListVO", subListVO); // 含有輸入格式錯誤的subscriptionVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/html/back_end/back_end_sublist_update.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				SubListService subListSvc = new SubListService();
				subListVO = subListSvc.updateSubList(subID,duration,subName,price);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("subListVO", subListVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/html/back_end/back_end_sublist.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/html/back_end/back_end_sublist_update.jsp");
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
				
				Integer subID = null;
				try {
					subID = new Integer(req.getParameter("subID").trim());
				} catch (NumberFormatException e) {
					subID = null;
					errorMsgs.add("方案編號請填數字.");
				}
				
				String duration = req.getParameter("duration").trim();
				if (duration == null || duration.trim().length() == 0) {
					errorMsgs.add("方案時長請勿空白");
				}
				
				String subName = req.getParameter("subName").trim();
				if (subName == null || subName.trim().length() == 0) {
					errorMsgs.add("方案名稱請勿空白");
				}
				
				Integer price = null;
				try {
					price = new Integer(req.getParameter("price").trim());
				} catch (NumberFormatException e) {
					price = null;
					errorMsgs.add("價格請填數字.");
				}

				SubListVO subListVO = new SubListVO();
				subListVO.setSubID(subID);
				subListVO.setDuration(duration);
				subListVO.setSubName(subName);
				subListVO.setPrice(price);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("subListVO", subListVO); // 含有輸入格式錯誤的subscriptionVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/subList/addSubList.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始新增資料*****************************************/
				SubListService subListSvc = new SubListService();
				subListVO = subListSvc.addSubList(subID,duration,subName,price);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/subList/listAllSubList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subList/addSubList.jsp");
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
				Integer subID = new Integer(req.getParameter("subID"));
				
				/***************************2.開始刪除資料***************************************/
				SubListService subListSvc = new SubListService();
				subListSvc.deleteSubList(subID);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/subList/listAllSubList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/subList/listAllSubList.jsp");
				failureView.forward(req, res);
				}
			}
	}
}	
