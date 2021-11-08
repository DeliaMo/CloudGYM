package com.userMenuListPage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.customMenu.model.CustomMenuService;
import com.customMenuList.model.*;
import com.google.gson.Gson;
import com.video.model.*;
import com.videoAction.model.*;
import com.process.model.*;
import com.coachMenuList.model.*;

public class UserMenuListPageServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		List<CustomMenuListVO> list1 = new ArrayList<>();
		try {
			list1 = (ArrayList<CustomMenuListVO>) req.getAttribute("menulist");
			req.setAttribute("menulist", list1);
		} catch (Exception e) {

		}

		if ("getAll".equals(action)) {
			try {
				Integer menuID = new Integer(req.getParameter("menuID"));

				CustomMenuListService cmlSvc = new CustomMenuListService();
				list1 = cmlSvc.getAll(menuID);

				CustomMenuService cmSvc = new CustomMenuService();
				Integer percent = cmSvc.findByPrimaryKey(menuID).getCompleted();

				HttpSession session = req.getSession();
				session.setAttribute("menulist", list1);
				session.setAttribute("percent", percent);
				session.setAttribute("location", menuID);

				String url = "/html/user/protected_user/userMenuListPage1.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {

			}
		}

		if ("getCoach".equals(action)) {
			try {
				Integer itemID = new Integer(req.getParameter("itemID"));
				CoachMenuListService coachSvc = new CoachMenuListService();
				List<CoachMenuListVO> list = coachSvc.getByMenuID(itemID);

				req.setAttribute("coachlist", list);

				String url = "/html/user/protected_user/userMenuListPage2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {

			}
		}

		if ("goto".equals(action)) {
			try {
				Integer menuID = new Integer(req.getParameter("menuID"));
//				System.out.println(menuID);
				CustomMenuListService cmlSvc = new CustomMenuListService();
				list1 = cmlSvc.getAll(menuID);
				CustomMenuService cmSvc = new CustomMenuService();
				String menuTitle = cmSvc.findByPrimaryKey(menuID).getTitle();

//				req.setAttribute("menulist", list1);
				req.setAttribute("menuTitle", menuTitle);
				req.setAttribute("menuID", menuID);

				String url = "/html/user/protected_user/userMenuListPage2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {

			}
		}

		if ("playVideo".equals(action)) {
			try {
				String menuTitle = req.getParameter("menuTitle");
				Integer videoID = new Integer(req.getParameter("id"));
				VideoService videoSvc = new VideoService();
				VideoVO play = videoSvc.findByPrimaryKey(videoID);
				Integer listID = new Integer(req.getParameter("listID"));
//				System.out.println(listID);
				ProcessService processSvc = new ProcessService();
				List<ProcessVO> process = processSvc.getByListID(listID);
				VideoActionService vaSvc = new VideoActionService();
				List<VideoActionVO> actions = vaSvc.getByVideoID(videoID);
				System.out.println(actions.size());

				req.setAttribute("actions", actions);
//				req.setAttribute("menulist", list1);
				req.setAttribute("process", process);
				req.setAttribute("listID", listID);
				req.setAttribute("menuTitle", menuTitle);
				req.setAttribute("play", play);
				String url = "/html/user/protected_user/userMenuListPage2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {

			}
		}
		if ("changeProcess".equals(action)) {
			try {
				Integer processNo = new Integer(req.getParameter("processNo"));
				System.out.println(processNo);
				ProcessService processSvc = new ProcessService();
				ProcessVO reqprocess = processSvc.getByProcessNo(processNo);
//				Integer reqprocessno = reqprocess.getProcessNo();
				Integer reqprocesssets = reqprocess.getSets();
				System.out.println(reqprocesssets);
				System.out.println(reqprocesssets + 1);

				processSvc.updateProcess(reqprocess.getUserID(), reqprocess.getListID(), reqprocess.getAction(),
						reqprocesssets + 1, processNo);

				Integer newsets = processSvc.getByProcessNo(processNo).getSets();

				PrintWriter out = res.getWriter();
				List<Integer> list = new ArrayList<Integer>();
				list.add(newsets);
				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);
				out.close();
			} catch (Exception e) {

			}
		}
		if ("init".equals(action)) {
			try {
				Integer listID = new Integer(req.getParameter("listID"));
				ProcessService processSvc = new ProcessService();
				List<ProcessVO> list = processSvc.getByListID(listID);

				PrintWriter out = res.getWriter();
				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);
				out.close();

			} catch (Exception e) {

			}
		}

	}
}
