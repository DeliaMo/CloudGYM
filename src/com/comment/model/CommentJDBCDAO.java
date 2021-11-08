package com.comment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommentJDBCDAO implements CommentDAO_interface {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String USER = "David";
	private static final String PASSWORD = "123456";
	private static final String INSERT = "INSERT INTO COMMENT(postsID, userID, commentContent, commentPublishDate) VALUES(?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE COMMENT SET commentContent=?, commentPublishDate=?, commentShow=? WHERE commentID=?";
	private static final String DELETE = "update comment set commentshow = 0 where commentid = ?";
	private static final String FIND_PK = "SELECT * FROM COMMENT WHERE COMMENTID = ?";
	private static final String FIND_ALL = "SELECT * FROM COMMENT where commentShow = 1";
	private static final String FIND_COMMENT = "SELECT COUNT(*) FROM comment where postsid = ? and commentShow = 1";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public Integer insert(CommentVO commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer primaryKey = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT, 1);

			pstmt.setInt(1, commentVO.getPostsID());
			pstmt.setInt(2, commentVO.getUserID());
			pstmt.setString(3, commentVO.getCommentContent());
			pstmt.setTimestamp(4, commentVO.getCommentPublishDate());
			pstmt.executeUpdate();

			// 取得自增主鍵
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				primaryKey = rs.getInt(1);
			}
			return primaryKey;

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(CommentVO commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, commentVO.getCommentContent());
			pstmt.setTimestamp(2, commentVO.getCommentPublishDate());
			pstmt.setBoolean(3, commentVO.isCommentShow());
			pstmt.setInt(4, commentVO.getCommentID());
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
	public void delete(Integer commentID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, commentID);
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
	public CommentVO findByPrimaryKey(Integer commentID) {
		CommentVO commentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_PK);
			pstmt.setInt(1, commentID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				commentVO = new CommentVO();
				commentVO.setCommentID(rs.getInt("commentid"));
				commentVO.setPostsID(rs.getInt("postsid"));
				commentVO.setUserID(rs.getInt("userid"));
				commentVO.setCommentContent(rs.getString("commentcontent"));
				commentVO.setCommentPublishDate(rs.getTimestamp("commentpublishdate"));
				commentVO.setCommentShow(rs.getBoolean("commentshow"));
				commentVO.setCommentReportedTimes(rs.getInt("commentReportedTimes"));
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
		return commentVO;
	}

	@Override
	public Integer countByComment(Integer postsID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = 0;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_COMMENT);

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
	public List<CommentVO> findAll() {
		List<CommentVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CommentVO commentVO = new CommentVO();
				commentVO.setCommentID(rs.getInt("commentid"));
				commentVO.setPostsID(rs.getInt("postsid"));
				commentVO.setUserID(rs.getInt("userid"));
				commentVO.setCommentContent(rs.getString("commentcontent"));
				commentVO.setCommentPublishDate(rs.getTimestamp("commentpublishdate"));
				commentVO.setCommentShow(rs.getBoolean("commentshow"));
				commentVO.setCommentReportedTimes(rs.getInt("commentreportedtimes"));
				list.add(commentVO);
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
		CommentJDBCDAO dao = new CommentJDBCDAO();

//		新增
//		CommentVO commentVO = new CommentVO();
//		commentVO.setPostsID(40002);
//		commentVO.setUserID(1002);
//		commentVO.setCommentContent("AAAAAAAAAAAA");
//		commentVO.setCommentPublishDate(java.sql.Timestamp.valueOf("2021-01-01 11:11:11"));
//		commentVO.setCommentShow(true);
//		dao.insert(commentVO);
//		System.out.println("新增成功");

//		修改
//		CommentVO commentVO = new CommentVO();
//		commentVO.setCommentContent("BBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//		commentVO.setCommentPublishDate(java.sql.Timestamp.valueOf("2021-02-02 22:22:22"));
//		commentVO.setCommentShow(false);
//		commentVO.setCommentID(50011);
//		dao.update(commentVO);
//		System.out.println("修改成功");

//		刪除
//		dao.delete(50011);
//		System.out.println("刪除成功");

//		查詢PK
//		CommentVO PK = dao.findByPrimaryKey(50002);
//		System.out.println(PK);

//		查詢All
//		List<CommentVO> list = dao.findAll();
//		for(CommentVO all : list) {
//			System.out.println(all);
//		}

//		文章留言數查詢
//		Scanner sc = new Scanner(System.in);
//		System.out.println("請輸入postID");
//		Integer postsID = sc.nextInt();
//		Integer count = dao.countByComment(postsID);
//		System.out.println("postsID = " + postsID);
//		System.out.println("回應總數 " + count);
//		sc.close();

	}

}
