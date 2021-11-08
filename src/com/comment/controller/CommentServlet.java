package com.comment.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comment.model.CommentService;
import com.comment.model.CommentVO;
import com.google.gson.Gson;
import com.posts.model.PostsService;
import com.posts.model.PostsVO;

public class CommentServlet extends HttpServlet {

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
			String commentcontent = req.getParameter("commentcontent");

			SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = ds.format(new Date(System.currentTimeMillis()));
			Timestamp commentpublishdate = Timestamp.valueOf(str);

			CommentVO commentVO = new CommentVO();
			commentVO.setPostsID(postsid);
			commentVO.setUserID(userid);
			commentVO.setCommentContent(commentcontent);
			commentVO.setCommentPublishDate(commentpublishdate);

			CommentService commentSvc = new CommentService();
			commentVO = commentSvc.addComment(postsid, userid, commentcontent, commentpublishdate);
			Integer commentID = commentVO.getCommentID();
			req.setAttribute("timestamp", commentpublishdate);

			// ajax摮��
			List<String> list = new ArrayList<String>();
			list.add(userid.toString());
			list.add(commentcontent);
			list.add(str);
			list.add(commentID.toString());
			String json = new Gson().toJson(list);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			out.write(json);
			out.close();

		}
		
		
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/*************************** 1.��隢�� ***************************************/
				Integer commentid = new Integer(req.getParameter("commentid"));

				/*************************** 2.����鞈�� ***************************************/
				CommentService commentSvc = new CommentService();
				Integer postID = commentSvc.getByCommtntID(commentid).getPostsID();
				System.out.println(postID);
				commentSvc.deleteComment(commentid);

				/*************************** 3.��摰��,皞��漱(Send the Success view) ***********/
				
//				String url = "/Forum/ArticleList.jsp";
//				String url = "/html/back_end_post_page.jsp?postID="+postID;
				RequestDispatcher successView = null;
				String url = req.getParameter("page");
				if("APG".equals(url)) {
					successView = req.getRequestDispatcher("/html/article/ArticlePage.jsp?postsID=" + postID);
					successView.forward(req, res);
					return;
				}else
					successView = req.getRequestDispatcher("/html/back_end/back_end_post_page.jsp?postID="+ postID);
					successView.forward(req, res);

				/*************************** �隞���隤方��� **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("��鞈�仃���:" + e.getMessage());
////				RequestDispatcher failureView = req.getRequestDispatcher("/Forum/ArticleList.jsp");
//				RequestDispatcher failureView = req.getRequestDispatcher("/html/back_end_post.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
	}

}
