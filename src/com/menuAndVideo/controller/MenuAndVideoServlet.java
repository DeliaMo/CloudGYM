package com.menuAndVideo.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.customMenu.model.*;
import com.customMenuList.model.*;

public class MenuAndVideoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	};

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("delete_list".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer listID = new Integer(req.getParameter("listID"));
				
				CustomMenuListService cmlSvc = new CustomMenuListService();
				cmlSvc.delete(listID);
				
				String url = "/html/user/protected_user/userMenuAndVideo2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除項目失敗："+ e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/user/protected_user/userMenuAndVideo2.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete_menu".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer menuID = new Integer(req.getParameter("menuID"));
				
				CustomMenuService cmlSvc = new CustomMenuService();
				cmlSvc.delete(menuID);
				
				String url = "/html/user/protected_user/userMenuAndVideo2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除菜單失敗："+ e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/user/protected_user/userMenuAndVideo2.jsp");
				failureView.forward(req, res);
			}
		}
		if("update_prepare".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer menuID = new Integer(req.getParameter("menuID"));
				System.out.println(menuID);
				CustomMenuService cmlSvc = new CustomMenuService();
				CustomMenuVO customMenuVO = cmlSvc.findByPrimaryKey(menuID);
				
				req.setAttribute("customMenuVO", customMenuVO);
				
				String url = "/html/user/protected_user/updateMenu.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/html/user/protected_user/userMenuAndVideo2.jsp");
				failureView.forward(req, res);
			}
			
		}
		if("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer menuID = new Integer(req.getParameter("menuID").trim());
				String title = req.getParameter("title");
				if(title == null || title.trim().length() == 0) {
					errorMsgs.add("菜單名稱請勿空白");
				}
				String content = req.getParameter("content");
				if(content == null || content.trim().length() == 0) {
					errorMsgs.add("菜單敘述請勿空白");
				}
				
				CustomMenuVO customMenuVO = new CustomMenuVO();
				customMenuVO.setTitle(title);
				customMenuVO.setContent(content);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("customMenuVO", customMenuVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/user/protected_user/updateMenu.jsp");
					failureView.forward(req, res);
					return;
				}
				
				CustomMenuService cmSvc = new CustomMenuService();
				customMenuVO = cmSvc.update(menuID, content, title);
				
				req.setAttribute("customMenuVO", customMenuVO);
				String url = "/html/user/protected_user/userMenuAndVideo2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗："+ e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/html/user/protected_user/updateMenu.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Integer userID = new Integer(req.getParameter("userID").trim());
			
			String title = req.getParameter("title");
			if(title == null || title.trim().length() == 0) {
				errorMsgs.add("菜單名稱請勿空白");
			}
			String content = req.getParameter("content");
			if(content == null || content.trim().length() == 0) {
				errorMsgs.add("菜單敘述請勿空白");
			}
			
			CustomMenuVO customMenuVO = new CustomMenuVO();
			customMenuVO.setTitle(title);
			customMenuVO.setContent(content);
			
			if(!errorMsgs.isEmpty()) {
				req.setAttribute("customMenuVO", customMenuVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/");
				failureView.forward(req, res);
				return;
			}
			
			CustomMenuService cmSvc = new CustomMenuService();
			customMenuVO = cmSvc.add(userID, content, title);
			
			req.setAttribute("customMenuVO", customMenuVO);
			String url = "/";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		}
		
	}
}
