package com.review.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewJDBCDAO implements ReviewDAO_interface {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	public static final String USER = "David";
	public static final String PASSWRD = "123456";

	private static final String INSERT_STMT = "INSERT INTO review(videoID, star) VALUES(?, ?)";
	private static final String DELETE = "DELETE FROM review WHERE reviewNo = ?";
	private static final String DELETE_BY_VIDEO = "DELETE FROM review WHERE videoID = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM review where videoID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM review order by videoID";
	private static final ReviewVO reviewVO = null;

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void add(ReviewVO reviewVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, reviewVO.getVideoID());
			pstmt.setInt(2, reviewVO.getStar());

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
	public void delete(Integer reviewNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, reviewNo);
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
	public void deleteByVideoID(Integer videoID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(DELETE_BY_VIDEO);

			pstmt.setInt(1, videoID);
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
	public ReviewVO findByPrimaryKey(Integer videoID) {
		ReviewVO reviewVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, videoID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reviewVO = new ReviewVO();
				reviewVO.setReviewNo(rs.getInt("reviewNo"));
				reviewVO.setVideoID(rs.getInt("videoID"));
				reviewVO.setStar(rs.getInt("star"));

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

		return reviewVO;
	}

	@Override
	public List<ReviewVO> getAll() {
		List<ReviewVO> list = new ArrayList<ReviewVO>();
		ReviewVO reviewVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				reviewVO = new ReviewVO();
				reviewVO.setReviewNo(rs.getInt("reviewNo"));
				reviewVO.setVideoID(rs.getInt("videoID"));
				reviewVO.setStar(rs.getInt("star"));
				list.add(reviewVO);
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
		ReviewJDBCDAO dao = new ReviewJDBCDAO();

		// add
//		ReviewVO reviewVO1 = new ReviewVO();
//		reviewVO1.setVideoID(3002);
//		reviewVO1.setStar(5);
//		dao.add(reviewVO1);

		// delete
//		dao.delete(10);
//		dao.deleteByVideoID(3001);

		// findone
//		ReviewVO reviewVO2 = dao.findByPrimaryKey(3005);
//		System.out.println(reviewVO2.getReviewNo());
//		System.out.println(reviewVO2.getVideoID());
//		System.out.println(reviewVO2.getStar());

		// findall
		List<ReviewVO> list = dao.getAll();
		for (ReviewVO review : list) {
			System.out.println(review.getReviewNo());
			System.out.println(review.getVideoID());
			System.out.println(review.getStar());
		}

	}

}
