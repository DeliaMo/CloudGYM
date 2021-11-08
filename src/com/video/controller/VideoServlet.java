package com.video.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.video.model.*;
import com.videoAction.model.*;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 100 * 1024 * 1024, maxRequestSize = 500 * 1024 * 1024)

public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		if("updatelist".equals(action)) {//接收來自back_end_video.jsp資料
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
//			res.setCharacterEncoding("UTF-8");
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer videoID = new Integer(req.getParameter("videoID").trim());
				
				VideoService videoSvc = new VideoService();
				VideoVO vo = videoSvc.findByPrimaryKey(videoID);
				
				Boolean Listed = new Boolean(req.getParameter("videoshow"+videoID));
				if (vo.getListed()==true) {
					vo.setListed(false);
				}else {
					vo.setListed(true);												
					}
				
				/***************************2.開始查詢資料*****************************************/
				vo = videoSvc.updateVideo(vo);
				System.out.println("2."+vo.getListed());
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
//				HttpSession session = req.getSession();
//				Object whichPage = session.getAttribute("whichPage");
//				String url = "/html/back_end/back_end_video.jsp?whichPage="+whichPage;
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
				
			//ajax 傳送更新
				List<String> list = new ArrayList<String>();
				list.add("updateSuccess");
				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/html/back_end/back_end_video.jsp");
//				failureView.forward(req, res);
			}finally {
					if (out != null) {
						out.close();
					}
				}
		}
		
		if("upload".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer userID = Integer.parseInt(req.getParameter("userID"));
				
				String title = req.getParameter("title");
				if(title.trim().length() == 0) {
					errorMsgs.add("請輸入影片標題");
				}
				
				String priceStr = req.getParameter("price");
				Integer price = null;
				if (priceStr.trim().length() == 0) {
					errorMsgs.add("請輸入價格");
				}else {
					try {
						price = Integer.parseInt(priceStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("價格格式錯誤");
					}
				}
				
				String intro = req.getParameter("intro");
				if(intro.trim().length() == 0) {
					errorMsgs.add("請輸入簡介");
				}
				
				String level = req.getParameter("level");
				
				Integer thePosition = Integer.parseInt(req.getParameter("thePosition"));
				
				String actionname1 = req.getParameter("actionname1");
				String times1 = req.getParameter("times1");
				String set1 = req.getParameter("set1");
				String actionname2 = req.getParameter("actionname2");
				String times2 = req.getParameter("times2");
				String set2 = req.getParameter("set2");
				String actionname3 = req.getParameter("actionname3");
				String times3 = req.getParameter("times3");
				String set3 = req.getParameter("set3");
				String[] action1 = {actionname1, times1, set1};
				String[] action2 = {actionname2, times2, set2};
				String[] action3 = {actionname3, times3, set3};
				
				if(action1[0].trim().equals("") && action1[1].trim().equals("") && action1[2].trim().equals("")) {
					action1 = null;
				}
				if(action2[0].trim().equals("") && action2[1].trim().equals("") && action2[2].trim().equals("")) {
					action2 = null;
				}
				if(action3[0].trim().equals("") && action3[1].trim().equals("") && action3[2].trim().equals("")) {
					action3 = null;
				}
				if(action1 != null && action2 != null) {
					if(action2[0].trim().equals(action1[0])) {
						action2 = null;
					}
				}
				if(action1 != null && action2 != null && action3!= null) {
					if(action3[0].trim().equals(action1[0]) || action3[0].trim().equals(action2[0])) {
						action3 = null;
					}
				}
				
				if(action1 != null) {
					for(int i = 0; i < action1.length; i++) {
						if(action1[i].trim().equals("")) {
							errorMsgs.add("請輸入動作名稱與相應的次數與組數");
							break;
						}
					}
				}
				if(action2 != null) {
					for(int i = 0; i < action2.length; i++) {
						if(action2[i].trim().equals("")) {
							errorMsgs.add("請輸入動作名稱與相應的次數與組數");
							break;
						}
					}
				}
				if(action3 != null) {
					for(int i = 0; i < action3.length; i++) {
						if(action3[i].trim().equals("")) {
							errorMsgs.add("請輸入動作名稱與相應的次數與組數");
							break;
						}
					}
				}
				if(action1 == null && action2 == null && action3 == null) {
					errorMsgs.add("請至少輸入一組動作名稱和次數");
				}
				
				
				Part part = req.getPart("video");
//				System.out.println(part.getSize());
				String filename = part.getSubmittedFileName();
//				if(filename.equals("")) {
//					System.out.println("true");
//				}
//				System.out.println("filename = " + filename);
				byte[] content = null;
				if(filename.length() != 0 && part.getSize() != 0) {
					InputStream in = part.getInputStream();
					content = new byte[in.available()];
					in.read(content);
					in.close();
				}else {
					errorMsgs.add("請上傳影片");
				}
				
				VideoVO videoVO = new VideoVO();
				videoVO.setUserID(userID);
				videoVO.setTitle(title);
				videoVO.setPrice(price);
				videoVO.setIntro(intro);
				videoVO.setContent(content);
				videoVO.setLevel(level);
				
				if(!errorMsgs.isEmpty()) {
					for(String message : errorMsgs) {
						System.out.println(message);
					}
					req.setAttribute("videoVO", videoVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/protected_coach/buildVideo.jsp");
					failureView.forward(req, res);
					return;
				}
				
				System.out.println(userID);
				/***************************2.開始查詢資料*****************************************/
				VideoService videoSvc = new VideoService();
				VideoVO video = videoSvc.add(userID, title, price, intro, content, level, thePosition);
				Integer videoID = video.getVideoID();
				
				VideoActionService videoactionSvc = new VideoActionService();
				if(action1 != null) {
					Integer times = Integer.parseInt(action1[1]);
					Integer set = Integer.parseInt(action1[2]);
					videoactionSvc.addVideoAction(videoID, actionname1, times, set);
				}
				if(action2 != null) {
					Integer times = Integer.parseInt(action2[1]);
					Integer set = Integer.parseInt(action2[2]);
					videoactionSvc.addVideoAction(videoID, actionname2, times, set);
				}
				if(action3 != null) {
					Integer times = Integer.parseInt(action3[1]);
					Integer set = Integer.parseInt(action3[2]);
					videoactionSvc.addVideoAction(videoID, actionname3, times, set);
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				
				RequestDispatcher successView = req.getRequestDispatcher("/html/coach/protected_coach/coach_overview.jsp");
				successView.forward(req, res);
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		
		
		
//=====================================================================//
		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("start");
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
//				pstmt.setBytes(4, videoVO.getContent());
				
				Integer userID = Integer.parseInt(req.getParameter("userID"));
				System.out.println(userID);
				
				Integer videoID = Integer.parseInt(req.getParameter("videoID"));
				System.out.println(videoID);
				
				String title = req.getParameter("title");
				if(title.trim().length() == 0) {
					errorMsgs.add("請輸入影片標題");
				}
				System.out.println(title);
				
				
				String priceStr = req.getParameter("price");
				Integer price = null;
				if (priceStr.trim().length() == 0) {
					errorMsgs.add("請輸入價格");
				}else {
					try {
						price = Integer.parseInt(priceStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("價格格式錯誤");
					}
				}
				System.out.println(price);
				
				String intro = req.getParameter("intro");
				if(intro.trim().length() == 0) {
					errorMsgs.add("請輸入影片介紹");
				}
				System.out.println(intro);
				
				String level = req.getParameter("level");
				if(intro.trim().length() == 0) {
					errorMsgs.add("請選擇影片強度");
				}
				System.out.println(level);
				
				Integer thePosition = null;
				try {
					thePosition = Integer.parseInt(req.getParameter("thePosition"));
				} catch (Exception e) {
					errorMsgs.add("取得position 失敗");
				}
				
				String actionname1 = req.getParameter("actionname1");
				String times1 = req.getParameter("times1");
				String set1 = req.getParameter("set1");
				String actionname2 = req.getParameter("actionname2");
				String times2 = req.getParameter("times2");
				String set2 = req.getParameter("set2");
				String actionname3 = req.getParameter("actionname3");
				String times3 = req.getParameter("times3");
				String set3 = req.getParameter("set3");
				String[] action1 = {actionname1, times1, set1};
				String[] action2 = {actionname2, times2, set2};
				String[] action3 = {actionname3, times3, set3};
				System.out.println(action1);
				System.out.println(action2);
				System.out.println(action3);
				System.out.println("pass1");
				
				if(action1[0].trim().equals("") && action1[1].trim().equals("") && action1[2].trim().equals("")) {
					action1 = null;
				}
				if(action2[0].trim().equals("") && action2[1].trim().equals("") && action2[2].trim().equals("")) {
					action2 = null;
				}
				if(action3[0].trim().equals("") && action3[1].trim().equals("") && action3[2].trim().equals("")) {
					action3 = null;
				}
				if(action1 != null && action2 != null) {
					if(action2[0].trim().equals(action1[0])) {
						action2 = null;
					}
				}
				if(action1 != null && action2 != null && action3!= null) {
					if(action3[0].trim().equals(action1[0]) || action3[0].trim().equals(action2[0])) {
						action3 = null;
					}
				}
				
				if(action1 != null) {
					for(int i = 0; i < action1.length; i++) {
						if(action1[i].trim().equals("")) {
							errorMsgs.add("xxxx1");
							return;
						}
					}
				}
				if(action2 != null) {
					for(int i = 0; i < action2.length; i++) {
						if(action2[i].trim().equals("")) {
							errorMsgs.add("xxx2");
							return;
						}
					}
				}
				if(action3 != null) {
					for(int i = 0; i < action3.length; i++) {
						if(action3[i].trim().equals("")) {
							errorMsgs.add("xxx3");
							return;
						}
					}
				}
				if(action1 == null && action2 == null && action3 == null) {
					errorMsgs.add("xxx4");
				}
				
				
				Part part = req.getPart("video");
//				System.out.println(part.getSize());
				String filename = part.getSubmittedFileName();
//				if(filename.equals("")) {
//					System.out.println("true");
//				}
//				System.out.println("filename = " + filename);
				byte[] content = null;
				if(filename.length() != 0 && part.getSize() != 0) {
					InputStream in = part.getInputStream();
					content = new byte[in.available()];
					in.read(content);
					in.close();
				}else {
					errorMsgs.add("請上傳影片");
				}
				System.out.println("pass2");
				
				VideoVO videoVO = new VideoVO();
				videoVO.setUserID(userID);
				System.out.println("passA");
				videoVO.setVideoID(videoID);
				System.out.println("passB");
				videoVO.setTitle(title);
				System.out.println("passC");
				videoVO.setPrice(price);
				System.out.println("passD");
				videoVO.setIntro(intro);
				System.out.println("passE");
				videoVO.setContent(content);
				System.out.println("passF");
				videoVO.setLevel(level);
				System.out.println("pass3");
				videoVO.setThePosition(thePosition);
				
				if(!errorMsgs.isEmpty()) {
					for(String message : errorMsgs) {
						System.out.println(message);
					}
					req.setAttribute("videoVO", videoVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/protected_coach/buildVideo.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料*****************************************/
				VideoService videoSvc = new VideoService();
				VideoVO video = videoSvc.updateVideo(videoVO);
				System.out.println("pass4");
				
				VideoActionService videoactionSvc = new VideoActionService();
				if(action1 != null) {
					Integer times = Integer.parseInt(action1[1]);
					Integer set = Integer.parseInt(action1[2]);
					videoactionSvc.addVideoAction(videoID, actionname1, times, set);
				}
				if(action2 != null) {
					Integer times = Integer.parseInt(action2[1]);
					Integer set = Integer.parseInt(action2[2]);
					videoactionSvc.addVideoAction(videoID, actionname2, times, set);
				}
				if(action3 != null) {
					Integer times = Integer.parseInt(action3[1]);
					Integer set = Integer.parseInt(action3[2]);
					videoactionSvc.addVideoAction(videoID, actionname3, times, set);
				}
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				
				RequestDispatcher successView = req.getRequestDispatcher("/html/coach/protected_coach/coach_overview.jsp");
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/coach/updateVideo.jsp");
				failureView.forward(req, res);
				System.out.println("fail2");
				e.printStackTrace();
			}
		}
	}
}
