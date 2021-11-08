package com.posts.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostsJDBCDAO implements PostsDAO_interface {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String USER = "David";
	private static final String PASSWORD = "123456";
	private static final String INSERT = "insert into posts(userID, postsTitle, postsContent, postsImg, postsPublishDate, tagID) values(?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update posts set postsTitle=?, postsContent=?, postsImg=?, tagID=? where postsID=?";
	private static final String DELETE = "update posts set postsshow = 0 where postsid = ?";
	private static final String FIND_PK = "select * from posts where postsid = ?";
	private static final String FIND_TOP = "select * from posts where postsshow = 1 order by postsLikes desc";
	private static final String FIND_MORE = "SELECT * FROM posts ORDER BY rand() LIMIT 15";
	private static final String FIND_ALL = "select * from posts where postsshow = 1 order by postsPublishDate desc";
	private static final String FIND_ALL2 = "select * from posts order by postsPublishDate desc";
	private static final String FIND_KEYWORD = "select * from posts as p1 join (select userid, UserName from user union select userid, coachname from coach) as p2 on p1.userID = p2.userID where postsTitle like CONCAT('%',?,'%') or username like CONCAT('%',?,'%') or postsContent like CONCAT('%',?,'%')";
	private static final String UPDATE_POSTSREPORTEDTIMES = "UPDATE posts SET postsReportedTimes=? WHERE postsID=?";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void insert(PostsVO postsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setInt(1, postsVO.getUserID());
			pstmt.setString(2, postsVO.getPostsTitle());
			pstmt.setString(3, postsVO.getPostsContent());
			pstmt.setBytes(4, postsVO.getPostsImg());
			pstmt.setTimestamp(5, postsVO.getPostsPublishDate());
			pstmt.setInt(6, postsVO.getTagID());
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
	public void update(PostsVO postsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, postsVO.getPostsTitle());
			pstmt.setString(2, postsVO.getPostsContent());
			pstmt.setBytes(3, postsVO.getPostsImg());
			pstmt.setInt(4, postsVO.getTagID());
			pstmt.setInt(5, postsVO.getPostsID());
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
	public void delete(Integer postsID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, postsID);
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
	public PostsVO findByPrimaryKey(Integer postsID) {
		PostsVO postsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_PK);
			pstmt.setInt(1, postsID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				postsVO = new PostsVO();
				postsVO.setPostsID(rs.getInt("postsid"));
				postsVO.setUserID(rs.getInt("userid"));
				postsVO.setPostsTitle(rs.getString("poststitle"));
				postsVO.setPostsContent(rs.getString("postscontent"));
				postsVO.setPostsImg(rs.getBytes("postsimg"));
				postsVO.setPostsPublishDate(rs.getTimestamp("postspublishdate"));
				postsVO.setTagID(rs.getInt("tagid"));
				postsVO.setPostsLikes(rs.getInt("postslikes"));
				postsVO.setPostsReportedTimes(rs.getInt("postsreportedtimes"));
				postsVO.setPostsShow(rs.getBoolean("postsshow"));
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
		return postsVO;
	}

	@Override
	public List<PostsVO> findByTopPost() {
		List<PostsVO> top = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_TOP);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostsVO postsVO = new PostsVO();
				postsVO.setPostsID(rs.getInt("postsid"));
				postsVO.setUserID(rs.getInt("userid"));
				postsVO.setPostsTitle(rs.getString("poststitle"));
				postsVO.setPostsContent(rs.getString("postscontent"));
				postsVO.setPostsImg(rs.getBytes("postsimg"));
				postsVO.setPostsPublishDate(rs.getTimestamp("postspublishdate"));
				postsVO.setTagID(rs.getInt("tagid"));
				postsVO.setPostsLikes(rs.getInt("postslikes"));
				postsVO.setPostsReportedTimes(rs.getInt("postsreportedtimes"));
				postsVO.setPostsShow(rs.getBoolean("postsshow"));
				top.add(postsVO);
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
		return top;
	}

	@Override
	public List<PostsVO> findMore() {
		List<PostsVO> more = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_MORE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostsVO postsVO = new PostsVO();
				postsVO.setPostsID(rs.getInt("postsid"));
				postsVO.setUserID(rs.getInt("userid"));
				postsVO.setPostsTitle(rs.getString("poststitle"));
				postsVO.setPostsContent(rs.getString("postscontent"));
				postsVO.setPostsImg(rs.getBytes("postsimg"));
				postsVO.setPostsPublishDate(rs.getTimestamp("postspublishdate"));
				postsVO.setTagID(rs.getInt("tagid"));
				postsVO.setPostsLikes(rs.getInt("postslikes"));
				postsVO.setPostsReportedTimes(rs.getInt("postsreportedtimes"));
				postsVO.setPostsShow(rs.getBoolean("postsshow"));
				more.add(postsVO);
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
		return more;
	}

	@Override
	public List<PostsVO> findAll() {
		List<PostsVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostsVO postsVO = new PostsVO();
				postsVO.setPostsID(rs.getInt("postsid"));
				postsVO.setUserID(rs.getInt("userid"));
				postsVO.setPostsTitle(rs.getString("poststitle"));
				postsVO.setPostsContent(rs.getString("postscontent"));
				postsVO.setPostsImg(rs.getBytes("postsimg"));
				postsVO.setPostsPublishDate(rs.getTimestamp("postspublishdate"));
				postsVO.setTagID(rs.getInt("tagid"));
				postsVO.setPostsLikes(rs.getInt("postslikes"));
				postsVO.setPostsReportedTimes(rs.getInt("postsreportedtimes"));
				postsVO.setPostsShow(rs.getBoolean("postsshow"));
				list.add(postsVO);
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

	@Override
	public List<PostsVO> findAll2() {

		List<PostsVO> list2 = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_ALL2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostsVO postsVO = new PostsVO();
				postsVO.setPostsID(rs.getInt("postsid"));
				postsVO.setUserID(rs.getInt("userid"));
				postsVO.setPostsTitle(rs.getString("poststitle"));
				postsVO.setPostsContent(rs.getString("postscontent"));
				postsVO.setPostsImg(rs.getBytes("postsimg"));
				postsVO.setPostsPublishDate(rs.getTimestamp("postspublishdate"));
				postsVO.setTagID(rs.getInt("tagid"));
				postsVO.setPostsLikes(rs.getInt("postslikes"));
				postsVO.setPostsReportedTimes(rs.getInt("postsreportedtimes"));
				postsVO.setPostsShow(rs.getBoolean("postsshow"));
				list2.add(postsVO);
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
		return list2;
	}

	@Override
	public List<PostsVO> findKeyword(String str) {
		List<PostsVO> keyword = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_KEYWORD);
			pstmt.setString(1, str);
			pstmt.setString(2, str);
			pstmt.setString(3, str);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostsVO postsVO = new PostsVO();
				postsVO.setPostsID(rs.getInt("postsid"));
				postsVO.setUserID(rs.getInt("userid"));
				postsVO.setPostsTitle(rs.getString("poststitle"));
				postsVO.setPostsContent(rs.getString("postscontent"));
				postsVO.setPostsImg(rs.getBytes("postsimg"));
				postsVO.setPostsPublishDate(rs.getTimestamp("postspublishdate"));
				postsVO.setTagID(rs.getInt("tagid"));
				postsVO.setPostsLikes(rs.getInt("postslikes"));
				postsVO.setPostsReportedTimes(rs.getInt("postsreportedtimes"));
				postsVO.setPostsShow(rs.getBoolean("postsshow"));
				keyword.add(postsVO);
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
		return keyword;
	}

	@Override
	public void updatePostsReportedTimes(Integer postsReportedTimes, Integer postsID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_POSTSREPORTEDTIMES);
			
			pstmt.setInt(1, postsReportedTimes);
			pstmt.setInt(2, postsID);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

//	@Override
//	public List<PostsVO> findMyPost() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	public static void main(String[] args) throws IOException {
//		PostsJDBCDAO dao = new PostsJDBCDAO();
//
//		嚙踐��蕭
//		PostsVO postsVO = new PostsVO();
//		postsVO.setPostsID(40011);
//		postsVO.setUserID(1002);
//		postsVO.setPostsTitle("ABCD");
//		postsVO.setPostsContent("DCBA");
//		postsVO.setPostsImg(getPictureByteArray("items/img1.png"));
//		postsVO.setPostsPublishDate(java.sql.Timestamp.valueOf("2021-01-01 11:11:11"));
//		postsVO.setTagID(20);
//		postsVO.setPostsShow(true);
//		dao.insert(postsVO);
//		System.out.println("嚙踐��等嚙踐�蕭嚙�");
//
//		�����
//		PostsVO postsVO = new PostsVO();
//		postsVO.setPostsTitle("ABCDAAAAA");
//		postsVO.setPostsContent("DCBAAAAA");
//		postsVO.setPostsImg(getPictureByteArray("items/img2.jpg"));
//		postsVO.setPostsPublishDate(java.sql.Timestamp.valueOf("2021-02-02 22:22:22"));
//		postsVO.setTagID(30);
//		postsVO.setPostsShow(false);
//		postsVO.setPostsID(40011);
//		dao.update(postsVO);
//		System.out.println("����蕭嚙踐�蕭嚙�");
//
//		嚙踝�蕭謒�
//		dao.delete(40011);
//		System.out.println("嚙踝�蕭謒蕭嚙踐�蕭嚙�");
//
//		嚙踐���
//		PostsVO PK = dao.findByPrimaryKey(40003);
//		System.out.println(PK);
//
//		嚙踐���l
//		List<PostsVO> list = dao.findAll();
//		for (PostsVO all : list) {
//			System.out.println(all);
//		}
//
//		 ��摮�
//		String str = "蝢�";
//		List<PostsVO> keyword = dao.findKeyword(str);
//		for (PostsVO Kw : keyword) {
//			System.out.println(Kw);
//		}
//}
//	
//	public static byte[] getPictureByteArray(String path) throws IOException {
//		FileInputStream fis = new FileInputStream(path);
//		byte[] buffer = new byte[fis.available()];
//		fis.read(buffer);
//		fis.close();
//		return buffer;
//	}
//	

}