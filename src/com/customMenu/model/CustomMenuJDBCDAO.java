package com.customMenu.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomMenuJDBCDAO implements CustomMenuDAO_interface{
	
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei&useunicode=true&characterencoding=utf8";
	public static final String USER = "David";
	public static final String PASSWRD = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO customMenu(userID, content, title, buildTime) VALUES(?,?,?,CURRENT_TIMESTAMP)";
	private static final String DELETE = "DELETE FROM customMenu WHERE menuID=?";
	private static final String UPDATE_STMT = "UPDATE customMenu set content = ? , title=? , buildTime = CURRENT_TIMESTAMP where menuID =?";
	private static final String GET_ONE_STMT = "SELECT * FROM customMenu where menuID=?";
	private static final String GET_ALL_STMT = "SELECT * FROM customMenu where userID=?";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(CustomMenuVO customMenu) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, customMenu.getUserID());
			pstmt.setString(2, customMenu.getContent());
			pstmt.setString(3, customMenu.getTitle());
			
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
	public void delete(Integer menuID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, menuID);
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
	public void update(CustomMenuVO customMenu) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, customMenu.getContent());
			pstmt.setString(2, customMenu.getTitle());
			pstmt.setInt(3, customMenu.getMenuID());
			
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
	public CustomMenuVO findByPrimaryKey(Integer menuID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomMenuVO customMenu = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, menuID);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				customMenu = new CustomMenuVO();
				customMenu.setMenuID(rs.getInt("menuID"));
				customMenu.setUserID(rs.getInt("userID"));
				customMenu.setContent(rs.getString("content"));
				customMenu.setTitle(rs.getString("title"));
				customMenu.setBuildTime(rs.getTimestamp("buildTime"));
				customMenu.setCompleted(rs.getInt("completed"));
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
		
		
		
		return customMenu;
	}

	@Override
	public List<CustomMenuVO> getAll(Integer userID) {
		List<CustomMenuVO> list = new ArrayList<CustomMenuVO>();
		CustomMenuVO customMenu = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setInt(1, userID);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				customMenu = new CustomMenuVO();
				customMenu.setMenuID(rs.getInt("menuID"));
				customMenu.setUserID(rs.getInt("userID"));
				customMenu.setContent(rs.getString("content"));
				customMenu.setTitle(rs.getString("title"));
				customMenu.setBuildTime(rs.getTimestamp("buildTime"));
				customMenu.setCompleted(rs.getInt("completed"));
				list.add(customMenu);
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
		CustomMenuJDBCDAO dao = new CustomMenuJDBCDAO();
		
		//add
//		CustomMenuVO menu = new CustomMenuVO();
//		menu.setUserID(1003);
//		menu.setContent("持之以恆就是成功之道");
//		menu.setTitle("第三週繼續keep fighting");
//		dao.add(menu);
		
		//delete
//		dao.delete(5);
		
		//update
//		CustomMenuVO menu = new CustomMenuVO();
//		menu.setContent("我要成功");
//		menu.setTitle("衝呀");
//		menu.setMenuID(7);
//		dao.update(menu);
		
		//findone
//		CustomMenuVO menu = dao.findByPrimaryKey(7);
//		System.out.println(menu.getMenuID());
//		System.out.println(menu.getUserID());
//		System.out.println(menu.getContent());
//		System.out.println(menu.getTitle());
//		System.out.println(menu.getBuildTime());
		
		//findall
		List<CustomMenuVO> list = dao.getAll(1003);
		for(CustomMenuVO menu : list) {
			System.out.println(menu.getMenuID());
//			System.out.println(menu.getUserID());
//			System.out.println(menu.getContent());
//			System.out.println(menu.getTitle());
//			System.out.println(menu.getBuildTime());
		}
	}
}
