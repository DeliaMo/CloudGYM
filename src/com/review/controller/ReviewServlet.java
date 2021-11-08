package com.review.controller;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.review.model.*;

public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewServlet() {
        super();
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		req.setCharacterEncoding("UTF-8");
		System.out.println(action);
		
		if("addReview".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			PrintWriter out = res.getWriter();
			try {
				Integer userID = (Integer) req.getSession().getAttribute("userID");
				
				Integer stars = Integer.parseInt(req.getParameter("stars"));
				Integer videoID = Integer.parseInt(req.getParameter("videoID"));
				
				ReviewService reviewSvc = new ReviewService();
				reviewSvc.add(videoID, stars);
				
				List<String> list = new ArrayList<String>();
//				list.add(stars.toString());
				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(out != null) {
					out.close();
				}
			}
		}
	}

}
