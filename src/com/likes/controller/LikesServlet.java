package com.likes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.likes.model.*;

public class LikesServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);

		if ("insert".equals(action)) {
			PrintWriter out = res.getWriter();
			res.setCharacterEncoding("UTF-8");
			
			Integer postsid = new Integer(req.getParameter("postsid"));
			Integer userid = new Integer(req.getParameter("userid"));

			LikesVO likesVO = new LikesVO();
			likesVO.setPostsID(postsid);
			likesVO.setUserID(userid);
			LikesService likesSvc = new LikesService();
			likesVO = likesSvc.addLikes(postsid, userid);
			
			
			//ajax
			List<String> list = new ArrayList<String>();
			list.add(postsid.toString());
			list.add(userid.toString());
			String json = new Gson().toJson(list);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			out.write(json);
			out.close();
			
//			RequestDispatcher successView = req.getRequestDispatcher("/html/ArticlePage.jsp?postsID="+postsid);
//			successView.forward(req, res);
		}

		
//		if ("delete".equals(action)) {
//			Integer likesid = new Integer(req.getParameter("likesid"));
//
//			LikesService likesSvc = new LikesService();
//			likesSvc.deleteLikes(likesid);
//
//			RequestDispatcher successView = req.getRequestDispatcher("/Forum/ArticleList.jsp");
//			successView.forward(req, res);
//		}

	}
}
