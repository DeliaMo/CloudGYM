package com.coach.controller;

import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CoachImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;

	public CoachImgServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/jpeg"); // 圖片=image/jpeg 影片=audio/mpeg
		ServletOutputStream out = res.getOutputStream();
		String userID = req.getParameter("userID");
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select coachImg from coach where userID=" + userID);
			// 指令記得改

			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("coachImg"));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei",
					"David", "123456");
			// 帳號密碼要改
		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Couldn't load JdbcOdbcDriver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
