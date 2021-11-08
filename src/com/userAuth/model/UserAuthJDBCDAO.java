package com.userAuth.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class UserAuthJDBCDAO implements UserAuthDAO_interface {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	public static final String USER = "David";
	public static final String PASSWORD = "123456";
	              
	private static final String INSERT_STMT = "INSERT INTO userAuth (userID,banComment,banShopping,banVideo,banUsers,startTime) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE userAuth SET banComment = ?, banShopping = ?, banVideo = ?, banUsers = ?, startTime = ? WHERE userID = ?";
	private static final String FIND_BY_USERID_STMT = "SELECT * FROM userAuth WHERE userID = ?";
	private static final String FIND_ALL = "SELECT * FROM userAuth"; 
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void insert(UserAuthVO userAuthVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, userAuthVO.getUserID());
			pstmt.setInt(2, userAuthVO.getBanComment());
			pstmt.setInt(3, userAuthVO.getBanShopping());			
			pstmt.setInt(4, userAuthVO.getBanVideo());			
			pstmt.setInt(5, userAuthVO.getBanUsers());			
			pstmt.setTimestamp(6, userAuthVO.getStartTime());

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
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
	public void update(UserAuthVO userAuthVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, userAuthVO.getBanComment());
			pstmt.setInt(2, userAuthVO.getBanShopping());
			pstmt.setInt(3, userAuthVO.getBanVideo());
			pstmt.setInt(4, userAuthVO.getBanUsers());
			pstmt.setTimestamp(5, userAuthVO.getStartTime());
			pstmt.setInt(6, userAuthVO.getUserID());
			
			pstmt.executeUpdate();			
			
		} catch (SQLException e) {		
			e.printStackTrace();
		}finally{
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
	public UserAuthVO findbyuserID(Integer userID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserAuthVO userAuthVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_USERID_STMT);
			
			pstmt.setInt(1, userID);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				userAuthVO = new UserAuthVO();
				userAuthVO.setUserID(rs.getInt("userID"));
				userAuthVO.setBanComment(rs.getInt("banComment"));
				userAuthVO.setBanShopping(rs.getInt("banShopping"));
				userAuthVO.setBanVideo(rs.getInt("banVideo"));
				userAuthVO.setBanUsers(rs.getInt("banUsers"));
				userAuthVO.setStartTime(rs.getTimestamp("startTime"));				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
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
		
		return userAuthVO;
	}

	@Override
	public List<UserAuthVO> findAll() {
		List<UserAuthVO> allList = new ArrayList<UserAuthVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserAuthVO UserAuthVO = null;
		
		try {
			con = DriverManager.getConnection(URL,USER,PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				UserAuthVO = new UserAuthVO();
				UserAuthVO.setUserAuthID(rs.getInt("userAuthID"));
				UserAuthVO.setUserID(rs.getInt("userID"));
				UserAuthVO.setBanComment(rs.getInt("banComment"));
				UserAuthVO.setBanShopping(rs.getInt("banShopping"));
				UserAuthVO.setBanVideo(rs.getInt("banVideo"));
				UserAuthVO.setBanUsers(rs.getInt("banUsers"));
				UserAuthVO.setStartTime(rs.getTimestamp("startTime"));		
				allList.add(UserAuthVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
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
		
		return allList;
	}

	public static void main(String[] args) {
		
		UserAuthJDBCDAO dao = new UserAuthJDBCDAO();		
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sd.format(new Date(System.currentTimeMillis()));
		Timestamp ts = Timestamp.valueOf(str);
		
		//新增
		UserAuthVO us1 = new UserAuthVO();
		us1.setUserID(2006);
//		us1.setBanComment(0);
//		us1.setBanShopping(0);
//		us1.setBanVideo(1);
//		us1.setBanUsers(1);
		us1.setStartTime(ts);
		dao.insert(us1);
		
		//修改
//		UserAuthVO us2 = new UserAuthVO();
//		us2.setUserAuthID(2);
//		us2.setUserID(1004);
//		us2.setBanComment(1);
//		us2.setBanShopping(0);
//		us2.setBanVideo(0);
//		us2.setBanUsers(0);
//		us2.setStartTime(ts);
//		dao.update(us2);
		
		//單筆修改		
		UserAuthVO us3 = dao.findbyuserID(1005);
//		us3.setUserAuthID(2);
		us3.setBanComment(1);
		us3.setBanShopping(1);
//		us3.setBanVideo(0);
//		us3.setBanUsers(0);
//		us3.setStartTime(ts);
		dao.update(us3);
		
		//單筆查詢
		UserAuthVO us4 =dao.findbyuserID(1004);
		System.out.print(us4.getUserID()+",");
		System.out.print(us4.getBanComment()+",");
		System.out.print(us4.getBanVideo()+",");
		System.out.print(us4.getBanShopping()+",");
		System.out.print(us4.getBanUsers()+",");
		System.out.println(us4.getStartTime());
		
		//查詢		
//		List<UserAuthVO> us4 = dao.findAll();
//		for (UserAuthVO Rvo : us4) {			
//			System.out.print(Rvo.getUserAuthID()+",");
//			System.out.print(Rvo.getUserID()+",");
//			System.out.print(Rvo.getBanComment()+",");
//			System.out.print(Rvo.getBanShopping()+",");
//			System.out.print(Rvo.getBanVideo()+",");
//			System.out.print(Rvo.getBanUsers()+",");
//			System.out.println(Rvo.getStartTime());	
//		}

	}

}
