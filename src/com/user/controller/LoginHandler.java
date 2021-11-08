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
import javax.servlet.http.HttpSession;

import com.user.model.UserService;
import com.user.model.UserVO;
import com.userAuth.model.UserAuthService;
import com.userAuth.model.UserAuthVO;

@WebServlet("/loginhandler")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("Big5");
		res.setContentType("text/html; charset=Big5");

		// 【取得使用者 帳號(account) 密碼(password)】
		String account = req.getParameter("account"); 
		String password = req.getParameter("password");
		
		String action = req.getParameter("action");
		
		/*************************** 會員登入 ***************************/
		if ("login".equals(action)) { // 來自login_user.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				
				if (account == null || (account.trim()).length() == 0) {
					errorMsgs.add("請輸入帳號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/html/login/login_user.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String userAccount = null;
				try {
					userAccount = new String(account);
				} catch (Exception e) {
					errorMsgs.add("帳號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/html/login/login_user.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				UserService userSvc = new UserService();
				UserVO userVO = userSvc.findByUserAccount(userAccount);

				if (userVO == null) {
					errorMsgs.add("查無資料");
				} else {
					if (!password.equals(userVO.getUserPassword())) {
						errorMsgs.add("密碼錯誤");
					} else {
						UserAuthService userAuthSvc = new UserAuthService();
						UserAuthVO userAuthVO = userAuthSvc.getUserID(userVO.getUserID());
						if (userAuthVO != null) {
							Integer banUser = userAuthVO.getBanUsers();
							if (banUser == 1) {
								errorMsgs.add("此帳號已被停權");
							}
						}
					}
				}
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/html/login/login_user.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("userVO", userVO); // 資料庫取出的userVO物件,存入req
				String name = userVO.getUserName();
				Integer id = userVO.getUserID();
				
				// 【帳號 , 密碼有效時, 才做以下工作】
				HttpSession session = req.getSession();
				session.setAttribute("account", account); // *工作1: 才在session內做已經登入過的標識
				session.setAttribute("name", name);
				session.setAttribute("userVO", userVO);
				session.setAttribute("userID", id);
	
				try {
					String location = (String) session.getAttribute("location");
					if (location != null) {
						session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
						res.sendRedirect(location);
						return;
					}
				} catch (Exception ignored) {
				}
	
				res.sendRedirect(req.getContextPath() + "/main_page.jsp"); // *工作3: (-->如無來源網頁:則重導至main_page.jsp)

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/html/login/login_user.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
