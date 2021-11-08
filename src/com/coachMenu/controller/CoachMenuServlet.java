package com.coachMenu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coachMenu.model.*;
import com.coachMenuList.model.CoachMenuListJDBCDAO;
import com.coachMenuList.model.CoachMenuListService;
import com.coachMenuList.model.CoachMenuListVO;
import com.posts.model.PostsService;
import com.posts.model.PostsVO;

public class CoachMenuServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    public CoachMenuServlet() {
        super();
    }


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		if ("addmenu".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.��隢�� - 頛詨�撘�隤方��� *************************/
				Integer userID = Integer.parseInt(req.getParameter("userID"));
//				try {
//					userID = Integer.parseInt(req.getParameter("userID"));
//				} catch (NumberFormatException ne) {
//					
//				}
				System.out.println(userID);
				
				String menuName = req.getParameter("menuName");
				if (menuName.trim().length() == 0) {
					errorMsgs.add("請輸入菜單名稱");
				}
				System.out.println(menuName);
				
				String priceStr = req.getParameter("price");
				Integer price = null;
				if (priceStr.trim().length() == 0) {
					errorMsgs.add("請輸入價格");
				}else {
					try {
						price = Integer.parseInt(priceStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("請輸入正確的價格格式");
					}
				}
				System.out.println(price);
				
				Boolean isPublic = true;
				if(price != null) {
					if (price != 0) {
						isPublic = false;
					}
				}
				
				System.out.println(isPublic);
				
				Integer positionNo = null;
				try {
					positionNo = Integer.parseInt(req.getParameter("positionNo"));
				} catch (Exception e) {
					errorMsgs.add("請選擇部位");
				}
				System.out.println(positionNo);

				Set<Integer> refVideos = new HashSet<Integer>();
				Integer refVideo1 = Integer.parseInt(req.getParameter("refVideo1"));
				refVideos.add(refVideo1);
				Integer refVideo2 = Integer.parseInt(req.getParameter("refVideo2"));
				refVideos.add(refVideo2);
				Integer refVideo3 = Integer.parseInt(req.getParameter("refVideo3"));
				refVideos.add(refVideo3);

				CoachMenuVO coachMenuVO = new CoachMenuVO();
				coachMenuVO.setMenuName(menuName);
				coachMenuVO.setUserID(userID);
				coachMenuVO.setPrice(price);
				coachMenuVO.setIsPublic(isPublic);
				coachMenuVO.setPositionNo(positionNo);
				
//				System.out.println("ok");
				
				if(!errorMsgs.isEmpty()) {
					for(String message : errorMsgs) {
						System.out.println(message);
					}
					req.setAttribute("coachMenuVO", coachMenuVO);
					RequestDispatcher failureView  = req.getRequestDispatcher("/html/coach/protected_coach/buildMenu.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.���憓��� ***************************************/
				
				CoachMenuService coachmenuSvc = new CoachMenuService();
				CoachMenuVO coachmenu = coachmenuSvc.addCoachMenu(userID, menuName, price, isPublic, positionNo);
				Integer menuID = coachmenu.getMenuID();
				CoachMenuListService coachmenulistSvc = new CoachMenuListService();
				for(Integer videoID : refVideos) {
					coachmenulistSvc.addCoachMenuList(menuID, videoID);
				}
				
//				System.out.println("ok2");

				/*************************** 3.�憓���,皞��漱(Send the Success view) ***********/
				
				RequestDispatcher successView = req.getRequestDispatcher("/html/coach/protected_coach/coach_overview.jsp");
				successView.forward(req, res);
				
//				System.out.println("ok3");
				
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/protected_coach/buildMenu.jsp");
				failureView.forward(req, res);
				System.out.println("fail");
			}
		}
		
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.��隢�� - 頛詨�撘�隤方��� **********************/
				Integer userID = new Integer(req.getParameter("userID"));
				System.out.println(userID);
				
				Integer menuID = new Integer(req.getParameter("menuID"));
				System.out.println(menuID);
				
				String menuName = req.getParameter("menuName");
				if (menuName.trim().length() == 0) {
					errorMsgs.add("請輸入菜單名稱");
				}
				System.out.println(menuName);
				
				String priceStr = req.getParameter("price");
				Integer price = null;
				if (priceStr.trim().length() == 0) {
					errorMsgs.add("請輸入價格");
				}else {
					try {
						price = Integer.parseInt(priceStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("請輸入正確的價格格式");
					}
				}
				System.out.println(price);
				
//				Boolean isPublic = true;
//				if(price != null) {
//					if (price != 0) {
//						isPublic = false;
//					}
//				}
//				System.out.println(isPublic);

				Integer positionNo = null;
				try {
					positionNo = Integer.parseInt(req.getParameter("positionNo"));
				} catch (Exception e) {
					errorMsgs.add("請選擇部位");
				}
				System.out.println(positionNo);
				
				List<Integer> refVideos = new ArrayList<Integer>();
				Integer refVideo1 = Integer.parseInt(req.getParameter("refVideo1"));
				System.out.println(refVideo1);
				refVideos.add(refVideo1);
				Integer refVideo2 = Integer.parseInt(req.getParameter("refVideo2"));
				System.out.println(refVideo2);
				refVideos.add(refVideo2);
				Integer refVideo3 = Integer.parseInt(req.getParameter("refVideo3"));
				System.out.println(refVideo3);
				refVideos.add(refVideo3);
				System.out.println(refVideos);

				CoachMenuVO coachMenuVO = new CoachMenuVO();
				coachMenuVO.setMenuID(userID);
				coachMenuVO.setMenuID(menuID);
				coachMenuVO.setMenuName(menuName);
				coachMenuVO.setPrice(price);
//				coachMenuVO.setIsPublic(isPublic);
				coachMenuVO.setPositionNo(positionNo);
				System.out.println("ok");

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachMenuVO", coachMenuVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/updateMenu.jsp");
					failureView.forward(req, res);
					System.out.println("fail");
					return;
				}

				/*************************** 2.���耨�鞈�� *****************************************/
				CoachMenuService coachmenuSvc = new CoachMenuService();
				CoachMenuVO coachmenu = coachmenuSvc.updateCoachMenu(coachMenuVO);
				System.out.println("ok2");
				
//				Integer menuNo = coachlistmenu.getMenuNo();
				CoachMenuListService coachmenulistSvc = new CoachMenuListService();
				List<CoachMenuListVO> coachmenulist = coachmenulistSvc.getByMenuID(menuID);
				List<Integer> menuNos = new LinkedList<Integer>();
				
				for(CoachMenuListVO coachMenuListVO : coachmenulist) {
					menuNos.add(coachMenuListVO.getMenuNo());
				}
				System.out.println(menuNos);
				for(int i = 0; i < refVideos.size(); i++) {
					try {
						coachmenulistSvc.updateCoachMenuList(((LinkedList<Integer>) menuNos).poll(), refVideos.get(i));
					} catch (Exception e) {
						coachmenulistSvc.addCoachMenuList(menuID, refVideos.get(i));
					}
				}
				System.out.println(menuNos);
				/*************************** 3.靽格摰��,皞��漱(Send the Success view) *************/
				req.setAttribute("coachMenuVO", coachMenuVO);
				String url = "/html/coach/protected_coach/coach_overview.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 靽格�����,頧漱listOneEmp.jsp
				successView.forward(req, res);
				/*************************** �隞���隤方��� *************************************/
			} catch (Exception e) {
				errorMsgs.add("靽格鞈�仃���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/updateMenu.jsp");
				failureView.forward(req, res);
				System.out.println("fail2");
				e.printStackTrace();
			}
		}
		
		if ("delete".equals(action)) {
			/*************************** 1.��隢�� ***************************************/
			Integer menuID = new Integer(req.getParameter("menuID"));

			/*************************** 2.����鞈�� ***************************************/
			CoachMenuService coachMenuSvc = new CoachMenuService();
			coachMenuSvc.deleteCoachMenu(menuID);

			/*************************** 3.��摰��,皞��漱(Send the Success view) ***********/
//				String url = "/html/ArticleList.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

			RequestDispatcher successView = null;
			String url = req.getParameter("page");
			if ("APG".equals(url)) {
				successView = req.getRequestDispatcher("/html/coach/protected_coach/coach_overview.jsp");
				successView.forward(req, res);
			} else {
				successView = req.getRequestDispatcher("/html/coach/updateMenu.jsp" );
				successView.forward(req, res);
			}

		}
		
	}
}
