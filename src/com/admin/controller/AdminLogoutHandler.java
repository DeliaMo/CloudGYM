package com.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/AdminLogoutHandler")
public class AdminLogoutHandler extends HttpServlet {

	/**
	 * @category 退出登入的Servlet,登出
	 * @author Bird
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);//防止建立Session
		if(session == null){
			res.sendRedirect(req.getContextPath() + "/html/login_admin.jsp");
			return;
		}
		
		/****管理者的session****/
		session.removeAttribute("adminNo");
		
		res.sendRedirect(req.getContextPath() + "/html/login_admin.jsp");

	}

}
