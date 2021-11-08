package com.user.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.model.UserService;
import com.user.model.UserVO;

@WebServlet("/ResetPassword")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("userForget".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				UserService userSvc = new UserService();
						
				String userAccount = req.getParameter("userAccount");
				String accountReg = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
				if (userAccount == null || userAccount.trim().length() == 0) {
					errorMsgs.add("帳號: 請勿空白!");
				}else if (!userAccount.trim().matches(accountReg)) {
					errorMsgs.add("email格式錯誤");
				}else if(userAccount != null && userSvc.findByUserAccount(userAccount) == null){ 
					errorMsgs.add("帳號: 該用戶尚未註冊!");
				}
						
				UserVO userVO = new UserVO();
				userVO.setUserAccount(userAccount);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("userVO", userVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/html/login/forget_password.jsp");
					failureView.forward(req, res);
					return;
				}	
				/********************************************************/
				
				
				UserService userSvc2 = new UserService();
				userVO = userSvc2.findByUserAccount(userAccount);
				
				
				RandomPassword random = new RandomPassword();
					
				String newpsw = random.getRandom(8);
				
				userSvc.changePassword(newpsw, userVO.getUserID());

				String subject = "新密碼";
				String messageText = newpsw ;
					
			    MailService mailService = new MailService();
			    mailService.sendMail(userAccount, subject, messageText);
				
				req.setAttribute("userVO", userVO);
				String url = "/html/login/forget_email_success.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/html/login/forget_password.jsp");
				failureView.forward(req, res);
			}				
			
			
		}
		
	}

}