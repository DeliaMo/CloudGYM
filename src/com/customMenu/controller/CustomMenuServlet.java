package com.customMenu.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.customMenu.model.*;
import com.customMenuList.model.*;
import com.google.gson.Gson;

import java.util.*;

public class CustomMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CustomMenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		
		
		BufferedReader br = req.getReader();
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		
		
		if("putmenu".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			PrintWriter out = res.getWriter();
			try {
				Integer userID = Integer.parseInt(req.getParameter("userID"));
				Integer videoID = Integer.parseInt(req.getParameter("videoID"));
				Integer menuID = Integer.parseInt(req.getParameter("menuID"));
				
				CustomMenuListService custommenulistSvc = new CustomMenuListService();
				custommenulistSvc.add(menuID, videoID);
				
				List<String> list = new ArrayList<>();
				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(out != null) {
					out.close();
				}
			}
			
		}
		
		if("addmenu".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			try {
//				String str = req.getParameter("userID");
//				System.out.println(str);
				Integer userID = Integer.parseInt(req.getParameter("userID"));
				String location = (String) req.getSession().getAttribute("oneVideoPage");
				
				String title = req.getParameter("title");
				if(title.trim().length() == 0) {
					errorMsgs.add("請輸入菜單名稱");
				}
				System.out.println(title);
				String content = req.getParameter("content");
				if(content.trim().length() == 0) {
					errorMsgs.add("請輸入菜單簡介");
				}
				System.out.println(content);
				
				CustomMenuVO customMenuVO = new CustomMenuVO();
				customMenuVO.setUserID(userID);
				customMenuVO.setTitle(title);
				customMenuVO.setContent(content);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("customMenuVO", customMenuVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/video/addmenu.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*******************************************************************************/
				
				CustomMenuService custommenuSvc = new CustomMenuService();
				custommenuSvc.add(userID, content, title);
				res.sendRedirect(location);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}

}
