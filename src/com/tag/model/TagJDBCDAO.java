package com.tag.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagJDBCDAO implements TagDAO_interface {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String USER = "David";
	private static final String PASSWORD = "123456";
	private static final String FIND_PK = "SELECT * FROM TAG WHERE TAGID = ?";
	private static final String FIND_ALL = "SELECT * FROM TAG";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public TagVO findByPrimaryKey(Integer tagID) {
		TagVO tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_PK);
			pstmt.setInt(1, tagID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tagVO = new TagVO();
				tagVO.setTagID(tagID);
				tagVO.setTagName(rs.getString("tagname"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return tagVO;
	}

	@Override
	public List<TagVO> findAll() {
		List<TagVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				TagVO tagVO = new TagVO();
				tagVO.setTagID(rs.getInt("tagid"));
				tagVO.setTagName(rs.getString("tagname"));
				list.add(tagVO);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		TagJDBCDAO dao = new TagJDBCDAO();

//		查詢PK
//		TagVO PK = dao.findByPrimaryKey(10);
//		System.out.println(PK);

//		 查詢All
//		List<TagVO> list = dao.findAll();
//		for (TagVO all : list) {
//			System.out.println(all);
//		}

	}

}