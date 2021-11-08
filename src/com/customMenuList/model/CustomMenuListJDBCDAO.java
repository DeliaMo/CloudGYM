package com.customMenuList.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomMenuListJDBCDAO implements CustomMenuListDAO_interface{
	
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	public static final String USER = "David";
	public static final String PASSWRD = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO customMenuList(menuID, videoID) VALUES(?,?)"; //新增一個影片在某個菜單裡
	private static final String DELETE = "DELETE FROM customMenuList WHERE listID=?";	//刪除某個菜單裡的某支影片
	private static final String GET_ONE_STMT = "SELECT * FROM customMenuList where listID=?";
	private static final String GET_ALL_STMT = "SELECT * FROM customMenuList where menuID=?";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	

	@Override
	public void add(CustomMenuListVO customMenuList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, customMenuList.getMenuID());
			pstmt.setInt(2, customMenuList.getVideoID());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void delete(Integer listID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, listID);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public CustomMenuListVO findByPrimaryKey(Integer listID) {
		CustomMenuListVO customMenuList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, listID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				customMenuList = new CustomMenuListVO();
				customMenuList.setListID(rs.getInt("listID"));
				customMenuList.setMenuID(rs.getInt("menuID"));
				customMenuList.setVideoID(rs.getInt("videoID"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return customMenuList;
	}

	@Override
	public List<CustomMenuListVO> getAll(Integer menuID) {
		List<CustomMenuListVO> list = new ArrayList<CustomMenuListVO>();
		CustomMenuListVO customMenuList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setInt(1, menuID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				customMenuList = new CustomMenuListVO();
				customMenuList.setListID(rs.getInt("listID"));
				customMenuList.setMenuID(rs.getInt("menuID"));
				customMenuList.setVideoID(rs.getInt("videoID"));
				list.add(customMenuList);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return list;
	}
	
	public static void main(String[] args) {
		CustomMenuListJDBCDAO dao = new CustomMenuListJDBCDAO();
		
		//add
//		CustomMenuListVO menuList = new CustomMenuListVO();
//		menuList.setMenuID(8);
//		menuList.setVideoID(3012);
//		dao.add(menuList);
		
		//delete
//		dao.delete(15);
		
		//findone
//		CustomMenuListVO menuList = dao.findByPrimaryKey(14);
//		System.out.println(menuList.getListID());
//		System.out.println(menuList.getMenuID());
//		System.out.println(menuList.getVideoID());
		
		//findall
		List<CustomMenuListVO> list = dao.getAll(6);
		for(CustomMenuListVO menuList: list) {
			System.out.println(menuList.getListID());
			System.out.println(menuList.getMenuID());
			System.out.println(menuList.getVideoID());
		}
	}
	
}
