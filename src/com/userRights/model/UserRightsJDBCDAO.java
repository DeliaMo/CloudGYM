package com.userRights.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRightsJDBCDAO implements UserRightsDAO_interface {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	public static final String USER = "David";
	public static final String PASSWRD = "123456";

	private static final String INSERT_STMT = "INSERT INTO userRights(userID, videoID, duration) VALUES(?,?,?)";
	private static final String DELETE = "DELETE FROM userRights WHERE rightsID=?";
	private static final String UPDATE_STMT = "UPDATE userRights set duration = ? where rightsID =?";
	private static final String GET_ONE_STMT = "SELECT * FROM userRights where rightsID=?";
	private static final String GET_ALL_STMT = "SELECT * FROM userRights where userID = ?";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(UserRightsVO userrights) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, userrights.getUserID());
			pstmt.setInt(2, userrights.getVideoID());
			pstmt.setInt(3, userrights.getDuration());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void delete(Integer rightsID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, rightsID);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void update(UserRightsVO userrights) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, userrights.getDuration());
			pstmt.setInt(2, userrights.getRightsID());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public UserRightsVO findByPrimaryKey(Integer rightsID) {
		UserRightsVO userrights = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, rightsID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				userrights = new UserRightsVO();
				userrights.setRightsID(rs.getInt("rightsID"));
				userrights.setUserID(rs.getInt("userID"));
				userrights.setVideoID(rs.getInt("videoID"));
				userrights.setDuration(rs.getInt("duration"));
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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return userrights;

	}

	@Override
	public List<UserRightsVO> getAll(Integer userID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserRightsVO> list = new ArrayList<UserRightsVO>();
		UserRightsVO userrights = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setInt(1, userID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				userrights = new UserRightsVO();
				userrights.setRightsID(rs.getInt("rightsID"));
				userrights.setUserID(rs.getInt("userID"));
				userrights.setVideoID(rs.getInt("videoID"));
				userrights.setDuration(rs.getInt("duration"));
				list.add(userrights);
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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
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
		UserRightsJDBCDAO dao = new UserRightsJDBCDAO();

		for (int i = 0; i < 10; i++) {
			UserRightsVO rights = new UserRightsVO();
			rights.setUserID(1002);
			rights.setVideoID(30000 + i);
			rights.setDuration(30);
			dao.add(rights);
			System.out.println("做了第" + i + "次");
		}

//		UserRightsVO rights = new UserRightsVO();
//		rights.setUserID(1001);
//		rights.setVideoID(30007);
//		rights.setDuration(30);
//		dao.add(rights);

		// delete
//		dao.delete(11);

		// update
//		UserRightsVO rights = new UserRightsVO();
//		rights.setRightsID(10);
//		rights.setDuration(30);
//		dao.update(rights);

		// find
//		UserRightsVO rights = dao.findByPrimaryKey(10);
//		System.out.println(rights.getRightsID());
//		System.out.println(rights.getUserID());
//		System.out.println(rights.getVideoID());
//		System.out.println(rights.getDuration());

		// finduser
		List<UserRightsVO> list = dao.getAll(1001);
		for (UserRightsVO right : list) {
			System.out.println(right.getRightsID());
			System.out.println(right.getUserID());
			System.out.println(right.getVideoID());
			System.out.println(right.getDuration());
		}

	}

}
