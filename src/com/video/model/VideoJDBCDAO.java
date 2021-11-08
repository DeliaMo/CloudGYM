package com.video.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;
import com.userRights.model.UserRightsVO;

public class VideoJDBCDAO implements VideoDAO_interface {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	public static final String USER = "David";
	public static final String PASSWRD = "123456";

	private static final String INSERT_STMT = "INSERT INTO video(userID, title, price, intro, content, level, thePosition, publishTime) VALUES(?,?,?,?,?,?,?, CURRENT_TIMESTAMP)";
	private static final String DELETE = "DELETE FROM video WHERE videoID=?";
	private static final String UPDATE_STMT = "UPDATE video set title =?, price = ?, intro=?, img=?, content=?, level=?, listed = ?, reportedTimes=?, thePosition=? where videoID =?";
	private static final String GET_ONE_STMT = "SELECT * FROM video where videoID=?";
	private static final String GET_ALL_STMT = "SELECT * FROM video";
	private static final String GET_ALL_NOVIDEO = "SELECT videoID, userID, title, price, intro, img, review, level, duration, listed, reportedTimes, publishTime, thePosition FROM video";
	private static final String GET_BY_USERID = "SELECT * FROM video WHERE userID = ?";
	private static final String FIND_BY_POSITIONNO = "SELECT videoID, userID, title, price, intro, img, review, level, duration, listed, reportedTimes, publishTime, thePosition FROM video WHERE thePosition=?";
	private static final String GET_ONE_STMT_NOVIDEO = "SELECT userID, title, price, intro, img, review, level, duration, listed, reportedTimes, publishTime, thePosition FROM video where videoID=?";
	private static final String RECOMMENDED_VIDEOS = "select * from video order by rand() limit 3";
	private static final String UPDATE_REPORTEDTIMES = "UPDATE video SET reportedTimes=? WHERE videoID=?";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public Integer add(VideoVO videoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer primaryKey = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(INSERT_STMT, 1);

			pstmt.setInt(1, videoVO.getUserID());
			pstmt.setString(2, videoVO.getTitle());
			pstmt.setInt(3, videoVO.getPrice());
			pstmt.setString(4, videoVO.getIntro());
//			pstmt.setBytes(5, videoVO.getImg());
			pstmt.setBytes(5, videoVO.getContent());
			pstmt.setString(6, videoVO.getLevel());
			pstmt.setInt(7, videoVO.getThePosition());
//			pstmt.setInt(7, videoVO.getDuration());
//			pstmt.setBoolean(9, videoVO.getListed());
//			pstmt.setInt(10, videoVO.getReportedTimes());

			pstmt.executeUpdate();

			// ���憓蜓�
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				primaryKey = rs.getInt(1);
			}
			return primaryKey;

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
		return primaryKey;
	}

	@Override
	public void update(VideoVO videoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, videoVO.getTitle());
			pstmt.setInt(2, videoVO.getPrice());
			pstmt.setString(3, videoVO.getIntro());
			pstmt.setBytes(4, videoVO.getImg());
			pstmt.setBytes(5, videoVO.getContent());
			pstmt.setString(6, videoVO.getLevel());
			pstmt.setBoolean(7, videoVO.getListed());
			pstmt.setInt(8, videoVO.getReportedTimes());
			pstmt.setInt(9, videoVO.getThePosition());
			pstmt.setInt(10, videoVO.getVideoID());

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
	public void delete(Integer videoID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(DELETE);

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
	public VideoVO findByPrimaryKey(Integer videoID) {
		VideoVO videoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, videoID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoID(rs.getInt("videoID"));
				videoVO.setUserID(rs.getInt("userID"));
				videoVO.setTitle(rs.getString("title"));
				videoVO.setDuration(rs.getInt("duration"));
				videoVO.setPrice(rs.getInt("price"));
				videoVO.setIntro(rs.getString("intro"));
				videoVO.setImg(rs.getBytes("img"));
				videoVO.setContent(rs.getBytes("content"));
				videoVO.setReview(rs.getInt("review"));
				videoVO.setPublishTime(rs.getTimestamp("publishTime"));
				videoVO.setLevel(rs.getString("level"));
				videoVO.setListed(rs.getBoolean("listed"));
				videoVO.setReportedTimes(rs.getInt("reportedTimes"));
				videoVO.setThePosition(rs.getInt("thePosition"));
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

		return videoVO;

	}

	@Override
	public List<VideoVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VideoVO> list = new ArrayList<VideoVO>();
		VideoVO videoVO = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoID(rs.getInt("videoID"));
				videoVO.setUserID(rs.getInt("userID"));
				videoVO.setTitle(rs.getString("title"));
				videoVO.setDuration(rs.getInt("duration"));
				videoVO.setPrice(rs.getInt("price"));
				videoVO.setIntro(rs.getString("intro"));
				videoVO.setImg(rs.getBytes("img"));
				videoVO.setContent(rs.getBytes("content"));
				videoVO.setReview(rs.getInt("review"));
				videoVO.setPublishTime(rs.getTimestamp("publishTime"));
				videoVO.setLevel(rs.getString("level"));
				videoVO.setListed(rs.getBoolean("listed"));
				videoVO.setReportedTimes(rs.getInt("reportedTimes"));
				videoVO.setThePosition(rs.getInt("thePosition"));

				list.add(videoVO);
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

	@Override
	public List<VideoVO> findByUserID(Integer userID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VideoVO> list = new ArrayList<VideoVO>();
		VideoVO videoVO = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_BY_USERID);
			pstmt.setInt(1, userID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoID(rs.getInt("videoID"));
				videoVO.setUserID(rs.getInt("userID"));
				videoVO.setTitle(rs.getString("title"));
				videoVO.setDuration(rs.getInt("duration"));
				videoVO.setPrice(rs.getInt("price"));
				videoVO.setIntro(rs.getString("intro"));
				videoVO.setImg(rs.getBytes("img"));
//				videoVO.setContent(rs.getBinaryStream("content"));
				videoVO.setReview(rs.getInt("review"));
				videoVO.setPublishTime(rs.getTimestamp("publishTime"));
				videoVO.setLevel(rs.getString("level"));
				videoVO.setListed(rs.getBoolean("listed"));
				videoVO.setReportedTimes(rs.getInt("reportedTimes"));
				videoVO.setThePosition(rs.getInt("thePosition"));

				list.add(videoVO);
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

	@Override
	public List<VideoVO> getAll2() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VideoVO> list = new ArrayList<VideoVO>();
		VideoVO videoVO = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ALL_NOVIDEO);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoID(rs.getInt("videoID"));
				videoVO.setUserID(rs.getInt("userID"));
				videoVO.setTitle(rs.getString("title"));
				videoVO.setDuration(rs.getInt("duration"));
				videoVO.setPrice(rs.getInt("price"));
				videoVO.setIntro(rs.getString("intro"));
				videoVO.setImg(rs.getBytes("img"));
//				videoVO.setContent(rs.getBinaryStream("content"));
				videoVO.setReview(rs.getInt("review"));
				videoVO.setPublishTime(rs.getTimestamp("publishTime"));
				videoVO.setLevel(rs.getString("level"));
				videoVO.setListed(rs.getBoolean("listed"));
				videoVO.setReportedTimes(rs.getInt("reportedTimes"));
				videoVO.setThePosition(rs.getInt("thePosition"));

				list.add(videoVO);
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
	
	@Override
	public List<VideoVO> findByPositionNo(Integer positionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VideoVO> list = new ArrayList<VideoVO>();
		VideoVO videoVO = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(FIND_BY_POSITIONNO);
			pstmt.setInt(1, positionNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoID(rs.getInt("videoID"));
				videoVO.setUserID(rs.getInt("userID"));
				videoVO.setTitle(rs.getString("title"));
				videoVO.setDuration(rs.getInt("duration"));
				videoVO.setPrice(rs.getInt("price"));
				videoVO.setIntro(rs.getString("intro"));
				videoVO.setImg(rs.getBytes("img"));
//				videoVO.setContent(rs.getBinaryStream("content"));
				videoVO.setReview(rs.getInt("review"));
				videoVO.setPublishTime(rs.getTimestamp("publishTime"));
				videoVO.setLevel(rs.getString("level"));
				videoVO.setListed(rs.getBoolean("listed"));
				videoVO.setReportedTimes(rs.getInt("reportedTimes"));
				videoVO.setThePosition(rs.getInt("thePosition"));

				list.add(videoVO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	@Override
	public VideoVO findByPrimaryKeyNoVideo(Integer videoID) {
		VideoVO videoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, videoID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoID(rs.getInt("videoID"));
				videoVO.setUserID(rs.getInt("userID"));
				videoVO.setTitle(rs.getString("title"));
				videoVO.setDuration(rs.getInt("duration"));
				videoVO.setPrice(rs.getInt("price"));
				videoVO.setIntro(rs.getString("intro"));
//				videoVO.setImg(rs.getBytes("img"));
//				videoVO.setContent(rs.getBytes("content"));
				videoVO.setReview(rs.getInt("review"));
				videoVO.setPublishTime(rs.getTimestamp("publishTime"));
				videoVO.setLevel(rs.getString("level"));
				videoVO.setListed(rs.getBoolean("listed"));
				videoVO.setReportedTimes(rs.getInt("reportedTimes"));
				videoVO.setThePosition(rs.getInt("thePosition"));
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
		return videoVO;
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

	public static InputStream getPictureStream(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		return fis;
	}

	@Override
	public List<VideoVO> recommendedVideos() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VideoVO> list3 = new ArrayList<VideoVO>();
		VideoVO videoVO = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(RECOMMENDED_VIDEOS);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoVO = new VideoVO();
				videoVO.setVideoID(rs.getInt("videoID"));
				videoVO.setUserID(rs.getInt("userID"));
				videoVO.setTitle(rs.getString("title"));
				videoVO.setDuration(rs.getInt("duration"));
				videoVO.setPrice(rs.getInt("price"));
				videoVO.setIntro(rs.getString("intro"));
				videoVO.setImg(rs.getBytes("img"));
				videoVO.setContent(rs.getBytes("content"));
				videoVO.setReview(rs.getInt("review"));
				videoVO.setPublishTime(rs.getTimestamp("publishTime"));
				videoVO.setLevel(rs.getString("level"));
				videoVO.setListed(rs.getBoolean("listed"));
				videoVO.setReportedTimes(rs.getInt("reportedTimes"));
				videoVO.setThePosition(rs.getInt("thePosition"));

				list3.add(videoVO);
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
		return list3;
	}

	public static void main(String[] args) throws IOException {
		VideoJDBCDAO dao = new VideoJDBCDAO();

		// add listed, reportedtimes��身true, 0 銋��蝞∠�靽格
//		VideoVO video = new VideoVO();
//		video.setUserID(2005);
//		video.setTitle("撅振�頨�");
//		video.setPrice(20);
//		video.setIntro("撅振�������銋���頨怒�頨怨��部皝臭�嚗����摰嗡��������頨怨�嚗�");
//		byte[] pic = getPictureByteArray("items/example6.jpg");
//		video.setImg(pic);
//		InputStream is = getPictureStream("items/test.mp4");
//		video.setContent(is);
//		video.setLevel("銝�");
//		video.setDuration(20);
//		video.setListed(true);
//		video.setReportedTimes(0);
//		dao.add(video);
//		is.close();

		// delete
//		dao.delete(3016);

		// update
//		VideoVO video = new VideoVO();
//		video.setTitle("牙白");
//		byte[] pic = getPictureByteArray("items/example6.jpg");
//		video.setImg(pic);
//		video.setPrice(77);
//		video.setIntro("起來運動");
//		InputStream is = getPictureStream("items/test2.mp4");
//		video.setContent(is);
//		video.setLevel("強");
//		video.setVideoID(300025);
//		dao.update(video);
//		is.close();

		// findone
//		VideoVO video = dao.findByPrimaryKey(2006);
//		System.out.println(video.getVideoID());
//		System.out.println(video.getUserID());
//		System.out.println(video.getTitle());
//		System.out.println(video.getDuration());
//		System.out.println(video.getPrice());
//		System.out.println(video.getImg());
//		System.out.println(video.getIntro());
//		System.out.println(video.getContent());
//		System.out.println(video.getPublishTime());
//		System.out.println(video.getReview());
//		System.out.println(video.getLevel());

		// findall
//		List<VideoVO> list = dao.getAll();
//		for(VideoVO video1 : list) {
//			System.out.println(video1.getVideoID());
//			System.out.println(video1.getUserID());
//			System.out.println(video1.getTitle());
//			System.out.println(video1.getDuration());
//			System.out.println(video1.getPrice());
//			System.out.println(video1.getImg());
//			System.out.println(video1.getIntro());
//			System.out.println(video1.getContent());
//			System.out.println(video1.getPublishTime());
//			System.out.println(video1.getReview());
//			System.out.println(video1.getLevel());
//		}
		
		// find by positionNo
		List<VideoVO> list = dao.findByPositionNo(1);
		for(VideoVO video1 : list) {
			System.out.println(video1.getVideoID());
			System.out.println(video1.getUserID());
			System.out.println(video1.getTitle());
			System.out.println(video1.getDuration());
			System.out.println(video1.getPrice());
//			System.out.println(video1.getImg());
			System.out.println(video1.getIntro());
//			System.out.println(video1.getContent());
			System.out.println(video1.getPublishTime());
			System.out.println(video1.getReview());
			System.out.println(video1.getLevel());
			System.out.println(video1.getThePosition());
		}
	}

	@Override
	public void updateReportedTimes(Integer reportedTimes, Integer videoID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(UPDATE_REPORTEDTIMES);
			
			pstmt.setInt(1, reportedTimes);
			pstmt.setInt(2, videoID);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
}
