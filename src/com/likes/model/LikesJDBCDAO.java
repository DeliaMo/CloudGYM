package com.likes.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.posts.model.PostsVO;

public class LikesJDBCDAO implements LikesDAO_interface {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String USER = "David";
	private static final String PASSWORD = "123456";

	private static final String INSERT = "INSERT INTO LIKES(postsID, userID) VALUES(?, ?)";
	private static final String DELETE = "DELETE FROM LIKES WHERE LIKESID = ?";
	private static final String FIND_PK = "SELECT * FROM LIKES WHERE LIKESID = ?";
	private static final String FIND_ALL = "SELECT * FROM LIKES";
	private static final String FIND_LIKE = "SELECT COUNT(*) FROM LIKES where postsid = ?";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void insert(LikesVO likesVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, likesVO.getPostsID());
			pstmt.setInt(2, likesVO.getUserID());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
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
				} catch (Exception se) {
					se.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(Integer likesID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, likesID);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
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
				} catch (Exception se) {
					se.printStackTrace();
				}
			}
		}
	}

	@Override
	public LikesVO findByPrimaryKey(Integer likesID) {
		LikesVO likesVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_PK);
			pstmt.setInt(1, likesID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				likesVO = new LikesVO();
				likesVO.setLikesID(rs.getInt("likesid"));
				likesVO.setPostsID(rs.getInt("postsid"));
				likesVO.setUserID(rs.getInt("userid"));
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
		return likesVO;
	}

	@Override
	public Integer countByLike(Integer postsID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = 0;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_LIKE);

			pstmt.setInt(1, postsID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	@Override
	public List<LikesVO> findAll() {
		List<LikesVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				LikesVO likesVO = new LikesVO();
				likesVO.setLikesID(rs.getInt("likesid"));
				likesVO.setPostsID(rs.getInt("postsid"));
				likesVO.setUserID(rs.getInt("userid"));
				list.add(likesVO);
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
		LikesJDBCDAO dao = new LikesJDBCDAO();

//		新增
//		LikesVO likesVO = new LikesVO();
//		likesVO.setPostsID(40005);
//		likesVO.setUserID(1005);
//		dao.insert(likesVO);
//		System.out.println("新增成功");

//		刪除
//		dao.delete(80013);
//		System.out.println("刪除成功");

//		查詢PK
//		LikesVO PK = dao.findByPrimaryKey(80002);
//		System.out.println(PK);

//		查詢All
//		List<LikesVO> list = dao.findAll();
//		for (LikesVO all : list) {
//			System.out.println(all);
//		}

//		文章按讚數查詢
//		Scanner sc = new Scanner(System.in);
//		System.out.println("請輸入postID");
//		Integer postsID = sc.nextInt();
//		Integer count = dao.countByLike(postsID);
//		System.out.println("postsID = " + postsID);
//		System.out.println("回應總數 " + count);
//		sc.close();
	}

}
