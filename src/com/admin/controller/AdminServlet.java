package com.admin.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.admin.model.*;


public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("updatebyAuth".equals(action)) {//接收來自back_end_admin.jsp資料
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer adminID = new Integer(req.getParameter("id").trim());
				System.out.println(adminID);
				
				AdminService adminSvc = new AdminService();
				AdminVO vo = adminSvc.getOneAdmin(adminID);				
								
				Integer commentAuth = null;
				try {
					commentAuth = new Integer(req.getParameter("commentAuth"));
					if (commentAuth != null) {
						vo.setCommentAuth(1);						
					}
					System.out.println("comm=" + commentAuth);					
				}catch(Exception e) {
					vo.setCommentAuth(0);
				}
								
				Integer videoAuth = null;
				try {
					videoAuth = new Integer(req.getParameter("videoAuth"));
					if (videoAuth != null) {
						vo.setVideoAuth(1);
					}
					System.out.println("video="+ videoAuth);					
				}catch(Exception e) {
					vo.setVideoAuth(0);
				}
						
				Integer subAuth = null;
				try {
					subAuth = new Integer(req.getParameter("subAuth"));
					if (subAuth != null) {
						vo.setSubAuth(1);				
					}
					System.out.println("sub="+subAuth);					
				}catch(Exception e) {
					vo.setSubAuth(0);
				}
					
				Integer userAuth = null;
				try {
					userAuth = new Integer(req.getParameter("userAuth"));
					if (userAuth != null) {
						vo.setUserAuth(1);		
					}
					System.out.println("user="+userAuth);					
				}catch(Exception e) {
					vo.setUserAuth(0);
				}

				/***************************2.開始修改資料*****************************************/
//				AdminService adminSvc = new AdminService();
//				adminVO = adminSvc.updatAdmin(adminID, adminName, adminPW, commentAuth, videoAuth, subAuth, userAuth);
				
				vo = adminSvc.updatAdmin(vo);
				System.out.println(vo.getCommentAuth());
				System.out.println(vo.getSubAuth());
				System.out.println(vo.getUserAuth());
				System.out.println(vo.getVideoAuth());
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/html/back_end/back_end_Admin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/back_end/back_end_Admin.jsp");
				failureView.forward(req, res);
			}						
		}
		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/				
				Integer adminID = new Integer(req.getParameter("id").trim());
				
				String adminName = req.getParameter("name");
				String nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{2,10}$";
				if(adminName == null || adminName.trim().length()==0) {
					errorMsgs.add("姓名不能留白");
				}else if(!adminName.trim().matches(nameReg)) {
					errorMsgs.add("姓名只能是中、英文字母,且長度必須在2-10之間");
				}
				
				String adminPW = req.getParameter("pws");
				String pwsReg = "^[(a-zA-Z0-9_)]{2,10}$";
				if(adminPW == null || adminPW.trim().length()==0) {
					errorMsgs.add("密碼不能留白");
				}else if(!adminPW.trim().matches(pwsReg)) {
					errorMsgs.add("密碼只能是英文字母,數字_,且長度必須在2-10之間");
				}				
				
				String confPW = req.getParameter("confpws");
				if(confPW == null || confPW.trim().length()==0) {
					errorMsgs.add("確認密碼不能留白");
				}else if(!confPW.equals(adminPW)) {
					errorMsgs.add("密碼不正確");
				}
				
				AdminService adminSvc = new AdminService();
				AdminVO vo = adminSvc.getOneAdmin(adminID);
				vo.setAdminID(adminID);
				vo.setAdminName(adminName);
				vo.setAdminPW(adminPW);
				
				// 含有輸入格式錯誤的empVO物件,也存入req
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("AdminVO" ,vo);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/back_end/back_end_Admin_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				vo = adminSvc.updatAdmin(vo);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("adminVO", vo); // 資料庫取出的empVO物件,存入req
				String url = "/html/back_end/back_end_Admin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				/***************************其他可能的錯誤處理*************************************/
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/back_end/back_end_Admin_page.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}
