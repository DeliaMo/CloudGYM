package com.coach.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CoachLogoutHandler
 */
@WebServlet("/protected_coach/CoachLogoutHandler")
public class CoachLogoutHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @category 退出登入的Servlet,登出
	 * @author Bird
	 */

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);//防止建立Session
		if(session == null){
			res.sendRedirect(req.getContextPath() + "/html/login/login_coach.jsp");
			return;
		}
		
		session.removeAttribute("coachAccount");
		session.removeAttribute("coachName");
		session.removeAttribute("coachID");
		res.sendRedirect(req.getContextPath() + "/html/login/login_coach.jsp");

	}

}
