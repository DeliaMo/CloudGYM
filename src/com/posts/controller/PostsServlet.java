package com.posts.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.posts.model.*;

@MultipartConfig
public class PostsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
							
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.��隢�� - 頛詨�撘�隤方��� **********************/
			try {
				String str = req.getParameter("postsid");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("頛詨���楊���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/posts/selectPostsPage.jsp");
					failureView.forward(req, res);
					return;
				}
				Integer postsid = null;
				try {
					postsid = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("���楊��撘�迤蝣�");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/posts/selectPostsPage.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.���閰Ｚ��� *****************************************/
				PostsService postsSvc = new PostsService();
				PostsVO postsVO = postsSvc.getByPostsID(postsid);
				if (postsVO == null) {
					errorMsgs.add("��鞈��");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/posts/selectPostsPage.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.�閰Ｗ���,皞��漱(Send the Success view) *************/
				req.setAttribute("postsVO", postsVO);
				String url = "/Forum/ArticlePage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** �隞���隤方��� *************************************/
			} catch (Exception e) {
				errorMsgs.add("�瘜�����:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/posts/selectPostsPage.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.��隢�� ****************************************/
				Integer postsid = new Integer(req.getParameter("postsid"));

				/*************************** 2.���閰Ｚ��� ****************************************/
				PostsService postsSvc = new PostsService();
				PostsVO postsVO = postsSvc.getByPostsID(postsid);

				/*************************** 3.�閰Ｗ���,皞��漱(Send the Success view) ************/
				req.setAttribute("postsVO", postsVO);
				String url = "/html/article/UpdateArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** �隞���隤方��� **********************************/
			} catch (Exception e) {
				errorMsgs.add("�瘜���耨������:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/posts/listAllPosts.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.��隢�� - 頛詨�撘�隤方��� **********************/
				Integer postsid = new Integer(req.getParameter("postsid"));

				String poststitle = req.getParameter("poststitle").trim();
				if (poststitle == null || poststitle.trim().length() == 0) {
					errorMsgs.add("璅��蝛箇");
				}

				String postscontent = req.getParameter("postscontent").trim();
				if (postscontent == null || postscontent.trim().length() == 0) {
					errorMsgs.add("�摰寡�蝛箇");
				}

				InputStream in = req.getPart("postsimg").getInputStream();
				byte[] postsimg = null;
				if (in.available() != 0) {
					postsimg = new byte[in.available()];
					in.read(postsimg);
					in.close();
				} else {
					errorMsgs.add("隢�����");
				}

//				Timestamp postspublishdate = new Timestamp(System.currentTimeMillis());

				Integer tagid = new Integer(req.getParameter("tagid").trim());

				PostsVO postsVO = new PostsVO();
				postsVO.setPostsID(postsid);
				postsVO.setPostsTitle(poststitle);
				postsVO.setPostsContent(postscontent);
				postsVO.setPostsImg(postsimg);
//				postsVO.setPostsPublishDate(postspublishdate);
				postsVO.setTagID(tagid);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("postsVO", postsVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/article/UpdateArticle.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.���耨�鞈�� *****************************************/
				PostsService postsSvc = new PostsService();
				postsVO = postsSvc.updatePosts(poststitle, postscontent, postsimg, tagid, postsid);


				/*************************** 3.靽格摰��,皞��漱(Send the Success view) *************/
				req.setAttribute("postsVO", postsVO);
				String url = "/html/article/ArticleList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 靽格�����,頧漱listOneEmp.jsp
				successView.forward(req, res);

				/*************************** �隞���隤方��� *************************************/
			} catch (Exception e) {
				errorMsgs.add("靽格鞈�仃���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/article/UpdateArticle.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.��隢�� - 頛詨�撘�隤方��� *************************/
				Integer userid = new Integer(req.getParameter("userid"));

				String poststitle = req.getParameter("poststitle").trim();
				if (poststitle == null || poststitle.trim().length() == 0) {
					errorMsgs.add("文章標題請勿空白");
				}

				String postscontent = req.getParameter("postscontent").trim();
				if (postscontent == null || postscontent.trim().length() == 0) {
					errorMsgs.add("文章內容請勿空白");
				}

				InputStream in = req.getPart("postsimg").getInputStream();
				byte[] postsimg = null;
				if (in.available() != 0) {
					postsimg = new byte[in.available()];
					in.read(postsimg);
					in.close();
				} else {
					errorMsgs.add("請上傳文章圖片");
				}

				Timestamp postspublishdate = new Timestamp(System.currentTimeMillis());

				Integer tagid = new Integer(req.getParameter("tagid"));

				PostsVO postsVO = new PostsVO();
				postsVO.setUserID(userid);
				postsVO.setPostsTitle(poststitle);
				postsVO.setPostsContent(postscontent);
				postsVO.setPostsImg(postsimg);
				postsVO.setPostsPublishDate(postspublishdate);
				postsVO.setTagID(tagid);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("postsVO", postsVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/article/AddArticle.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.���憓��� ***************************************/
				PostsService postsSvc = new PostsService();
				postsVO = postsSvc.addPosts(userid, poststitle, postscontent, postsimg, postspublishdate, tagid);

				/*************************** 3.�憓���,皞��漱(Send the Success view) ***********/
				String url = "/html/article/ArticleList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** �隞���隤方��� **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("XXX.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			/*************************** 1.��隢�� ***************************************/
			Integer postsid = new Integer(req.getParameter("postsid"));

			/*************************** 2.����鞈�� ***************************************/
			PostsService postsSvc = new PostsService();
			postsSvc.deletePosts(postsid);

			/*************************** 3.��摰��,皞��漱(Send the Success view) ***********/
//				String url = "/html/ArticleList.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

			RequestDispatcher successView = null;
			String url = req.getParameter("page");
			if ("APG".equals(url)) {
				successView = req.getRequestDispatcher("/html/article/ArticleList.jsp");
				successView.forward(req, res);
			} else {
				successView = req.getRequestDispatcher("/html/back_end/back_end_post_page.jsp?postID=" + postsid);
				successView.forward(req, res);
			}

		}

		if ("search".equals(action)) {
			/*************************** 1.��隢�� ***************************************/
			String str = new String(req.getParameter("str"));
			/*************************** 2.���憓��� ***************************************/
			PostsService postsSvc = new PostsService();
			List<PostsVO> postsVO = postsSvc.search(str);
			/*************************** 3.�閰Ｗ���,皞��漱(Send the Success view) ************/
			req.setAttribute("postsVO", str);
			String url = "/html/article/ArticleList_Search.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

				/*************************** �隞���隤方��� **********************************/
			
		}
	

}