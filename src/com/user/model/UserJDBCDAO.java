package com.user.model;

import java.util.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class UserJDBCDAO implements UserDAO_interface {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String USERID = "David";
	private static final String PASSWORD = "123456";

	private static final String INSERT_STMT = "INSERT INTO user(userAccount, userName, userPassword, userMobile, userSex, userBirthday, userRegisterDate, userReportedTimes) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_STMT = "UPDATE user SET userAccount=?, userName=?, userPassword=?, userMobile=?, userSex=?, userBirthday=?, userReportedTimes=? WHERE userID=?";
	private static final String DELETE_STMT = "DELETE FROM user WHERE userID=?";
	private static final String FIND_BY_USERID = "SELECT * FROM user WHERE userID=?";
	private static final String FIND_BY_USERACCOUNT = "SELECT * FROM user WHERE userAccount=?";
	private static final String GET_ALL = "SELECT * FROM user";
	private static final String CHANGEPASSWORD = "UPDATE user SET userPassword=? WHERE userID=?";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void insert(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

//			pstmt.setInt(1, userVO.getUserID());
			pstmt.setString(1, userVO.getUserAccount());
			pstmt.setString(2, userVO.getUserName());
			pstmt.setString(3, userVO.getUserPassword());
			pstmt.setString(4, userVO.getUserMobile());
			pstmt.setString(5, userVO.getUserSex());
			pstmt.setDate(6, userVO.getUserBirthday());
			pstmt.setTimestamp(7, userVO.getUserRegisterDate());
			pstmt.setInt(8, userVO.getUserReportedTimes());

			pstmt.executeUpdate();
		} catch (SQLException se) {
//			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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
	}

	@Override
	public void update(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, userVO.getUserAccount());
			pstmt.setString(2, userVO.getUserName());
			pstmt.setString(3, userVO.getUserPassword());
			pstmt.setString(4, userVO.getUserMobile());
			pstmt.setString(5, userVO.getUserSex());
			pstmt.setDate(6, userVO.getUserBirthday());
//			pstmt.setTimestamp(7, userVO.getUserRegisterDate());
			pstmt.setInt(7, userVO.getUserReportedTimes());
			pstmt.setInt(8, userVO.getUserID());
			pstmt.executeUpdate();
		} catch (SQLException se) {
//			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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

	}

	@Override
	public void delete(Integer userID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, userID);
			pstmt.executeUpdate();

		} catch (SQLException se) {
//			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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

	}

	@Override
	public UserVO findByUserId(Integer userID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO userVO = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_USERID);
			pstmt.setInt(1, userID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userVO = new UserVO();
				userVO.setUserID(rs.getInt("UserID"));
				userVO.setUserAccount(rs.getString("UserAccount"));
				userVO.setUserName(rs.getString("UserName"));
				userVO.setUserPassword(rs.getString("UserPassword"));
				userVO.setUserMobile(rs.getString("UserMobile"));
				userVO.setUserSex(rs.getString("UserSex"));
				userVO.setUserBirthday(rs.getDate("UserBirthday"));
				userVO.setUserRegisterDate(rs.getTimestamp("UserRegisterDate"));
				userVO.setUserReportedTimes(rs.getInt("UserReportedTimes"));
			}

		} catch (SQLException se) {
//			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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

		return userVO;
	}

	@Override
	public UserVO findByUserAccount(String userAccount) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO userVO = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_USERACCOUNT);
			pstmt.setString(1, userAccount);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userVO = new UserVO();
				userVO.setUserID(rs.getInt("UserID"));
				userVO.setUserAccount(rs.getString("UserAccount"));
				userVO.setUserName(rs.getString("UserName"));
				userVO.setUserPassword(rs.getString("UserPassword"));
				userVO.setUserMobile(rs.getString("UserMobile"));
				userVO.setUserSex(rs.getString("UserSex"));
				userVO.setUserBirthday(rs.getDate("UserBirthday"));
				userVO.setUserRegisterDate(rs.getTimestamp("UserRegisterDate"));
				userVO.setUserReportedTimes(rs.getInt("UserReportedTimes"));
			}

		} catch (SQLException se) {
//			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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

		return userVO;
	}

	@Override
	public List<UserVO> getAll() {
		List<UserVO> userList = new ArrayList<UserVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO userVO = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				userVO = new UserVO();
				userVO.setUserID(rs.getInt("UserID"));
				userVO.setUserAccount(rs.getString("UserAccount"));
				userVO.setUserName(rs.getString("UserName"));
				userVO.setUserPassword(rs.getString("UserPassword"));
				userVO.setUserMobile(rs.getString("UserMobile"));
				userVO.setUserSex(rs.getString("UserSex"));
				userVO.setUserBirthday(rs.getDate("UserBirthday"));
				userVO.setUserRegisterDate(rs.getTimestamp("UserRegisterDate"));
				userVO.setUserReportedTimes(rs.getInt("UserReportedTimes"));
				userList.add(userVO);
			}

		} catch (SQLException se) {
//			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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
		return userList;
	}
	
	@Override
	public void changePassword(UserVO userVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(CHANGEPASSWORD);
			
			pstmt.setString(1, userVO.getUserPassword());
			pstmt.setInt(2, userVO.getUserID());
			
			pstmt.executeUpdate();


		} catch (SQLException se) {
//			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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
	}

	public static void main(String[] args) {

		UserJDBCDAO dao = new UserJDBCDAO();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sd.format(new Date(System.currentTimeMillis()));
		Timestamp ts = Timestamp.valueOf(str);

		// INSERT 新增
		UserVO userVO1 = new UserVO();
//		userVO1.setUserID(1021);
		userVO1.setUserAccount("1021@cloudgym.com");
		userVO1.setUserName("吳一男");
		userVO1.setUserPassword("654321");
		userVO1.setUserMobile("0921123456");
		userVO1.setUserSex("男");
		userVO1.setUserBirthday(java.sql.Date.valueOf("1941-01-01"));
		userVO1.setUserRegisterDate(ts);
		userVO1.setUserReportedTimes(0);
		dao.insert(userVO1);
		System.out.println("新增成功");		

		// UPDATE 修改
//		UserVO userVO2 = new UserVO();
//		userVO2.setUserID(1021);
//		userVO2.setUserAccount("x021@cloudgym.com");
//		userVO2.setUserName("吳二男");
//		userVO2.setUserPassword("754321");
//		userVO2.setUserMobile("0921123456");
//		userVO2.setUserSex("男");
//		userVO2.setUserBirthday(java.sql.Date.valueOf("1941-01-01"));
//		userVO2.setUserReportedTimes(0);
//		dao.update(userVO2);
//		System.out.println("更新成功");

		// DELETE 刪除
//		dao.delete(1021);
//		System.out.println("刪除成功");

		// FIND_BY_USERID 單筆查詢
//		UserVO userVO3 = dao.findByUserId(1001);
//		System.out.print(userVO3.getUserID() + ", ");
//		System.out.print(userVO3.getUserAccount() + ", ");
//		System.out.print(userVO3.getUserName() + ", ");
//		System.out.print(userVO3.getUserPassword() + ", ");
//		System.out.print(userVO3.getUserMobile() + ", ");
//		System.out.print(userVO3.getUserSex() + ", ");
//		System.out.print(userVO3.getUserBirthday() + ", ");
//		System.out.print(userVO3.getUserRegisterDate() + ", ");
//		System.out.print(userVO3.getUserReportedTimes());
//		System.out.println();
//		System.out.println("單筆查詢完成");

		// FIND_ALL 查詢全部
//		List<UserVO> userList1 = dao.getAll();
//		for (UserVO userVO4 : userList1) {
//			System.out.print(userVO4.getUserID() + ", ");
//			System.out.print(userVO4.getUserAccount() + ", ");
//			System.out.print(userVO4.getUserName() + ", ");
//			System.out.print(userVO4.getUserPassword() + ", ");
//			System.out.print(userVO4.getUserMobile() + ", ");
//			System.out.print(userVO4.getUserSex() + ", ");
//			System.out.print(userVO4.getUserBirthday() + ", ");
//			System.out.print(userVO4.getUserRegisterDate() + ", ");
//			System.out.print(userVO4.getUserReportedTimes());
//			System.out.println();
//		}
//		System.out.println("全部查詢完成");			
		
//		UserVO userVO = new UserVO();
//		String userSex = userVO.getUserSex();
//		System.out.println(userSex);
		
		
	}

}
