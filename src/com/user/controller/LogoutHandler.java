package com.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LogoutHandler")
public class LogoutHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);//防止建立Session
		if(session == null){
			res.sendRedirect(req.getContextPath() + "/html/login/login_user.jsp");
			return;
		}
		
		/****移除登入的session****/
		session.removeAttribute("account");    // 會員與教練的account
		session.removeAttribute("name");    // 會員與教練的name
		session.removeAttribute("userID");    // 會員與教練的id
		session.removeAttribute("adminNo");    // 管理者的adminNo
		session.removeAttribute("userVO");    // 會員的userVO
		session.removeAttribute("coachVO");    // 教練的coachVO
		session.removeAttribute("adminVO");    // 管理者的adminVO
		
		res.sendRedirect(req.getContextPath() + "/main_page.jsp");

	}

}
