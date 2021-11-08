package com.collection.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.collection.model.CollectionService;
import com.collection.model.CollectionVO;
import com.google.gson.Gson;

public class CollectionServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		if ("insert".equals(action)) {
			PrintWriter out = res.getWriter();
			res.setCharacterEncoding("UTF-8");
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer userID = new Integer(req.getParameter("userid"));
			Integer itemID = null;
			if(req.getParameter("itemid") != null) {
				itemID = new Integer(req.getParameter("itemid"));
			}
					
			Integer itemID2 = null;
			if(req.getParameter("videoID") != null) {
				itemID2 = Integer.parseInt(req.getParameter("videoID"));
			}
					
			if(itemID != null) {
				CollectionVO collectionVO = new CollectionVO();
				collectionVO.setUserID(userID);
				collectionVO.setItemID(itemID);
				/*************************** 2.開始新增資料 ***************************************/
				CollectionService collectionSve = new CollectionService();
				collectionVO = collectionSve.addCollection(userID, itemID);
			}
//			
			if(itemID2 != null) {
				CollectionVO collectionVO = new CollectionVO();
				collectionVO.setUserID(userID);
				collectionVO.setItemID(itemID2);
				/*************************** 2.開始新增資料 ***************************************/
				CollectionService collectionSve = new CollectionService();
				collectionVO = collectionSve.addCollection(userID, itemID2);
			}
			
			//AJAX存取
			List<String> list = new ArrayList<String>();
			list.add(userID.toString());
			if(itemID != null) {
				list.add(itemID.toString());
			}
			String json = new Gson().toJson(list);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			out.write(json);
			out.close();
		}
		
		
	}
}
