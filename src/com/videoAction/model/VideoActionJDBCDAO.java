package com.videoAction.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Driver;

public class VideoActionJDBCDAO implements VideoActionDAO_interface {

	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String userid = "David";
	private static final String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO videoAction(videoID, action, times, sets) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE videoAction SET videoID=?, action=?, times=?, sets=? WHERE actNo=?";
	private static final String DELETE_STMT = "DELETE FROM videoAction WHERE actNo=?";
	private static final String DELETE_BY_VIDEOID = "DELETE FROM videoAction WHERE videoID=?";
	private static final String FIND_BY_ACTIONNO = "SELECT * FROM videoAction WHERE actNo=?";
	private static final String FIND_BY_VIDEOID = "SELECT * FROM videoAction WHERE videoID=?";
	private static final String GET_ALL = "SELECT * FROM videoAction";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(VideoActionVO videoActionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, videoActionVO.getVideoID());
			pstmt.setString(2, videoActionVO.getAction());
			pstmt.setInt(3, videoActionVO.getTimes());
			pstmt.setInt(4, videoActionVO.getSets());

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
	public void update(VideoActionVO videoActionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, videoActionVO.getVideoID());
			pstmt.setString(2, videoActionVO.getAction());
			pstmt.setInt(3, videoActionVO.getTimes());
			pstmt.setInt(4, videoActionVO.getSets());
			pstmt.setInt(5, videoActionVO.getActNo());
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
	public void delete(Integer actionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, actionNo);
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
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_BY_VIDEOID);
			pstmt.setInt(1, videoID);
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

	@Override
	public VideoActionVO findByActionNo(Integer actionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		VideoActionVO videoActionVO = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_ACTIONNO);
			pstmt.setInt(1, actionNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				videoActionVO = new VideoActionVO();
				videoActionVO.setActNo(rs.getInt("actNo"));
				videoActionVO.setVideoID(rs.getInt("videoID"));
				videoActionVO.setAction(rs.getString("action"));
				videoActionVO.setTimes(rs.getInt("times"));
				videoActionVO.setSets(rs.getInt("sets"));
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
		return videoActionVO;
	}

	@Override
	public List<VideoActionVO> findByVideoID(Integer videoID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<VideoActionVO> list = new ArrayList<>();
		VideoActionVO videoActionVO = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_VIDEOID);
			pstmt.setInt(1, videoID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoActionVO = new VideoActionVO();
				videoActionVO.setActNo(rs.getInt("actNo"));
				videoActionVO.setVideoID(rs.getInt("videoID"));
				videoActionVO.setAction(rs.getString("action"));
				videoActionVO.setTimes(rs.getInt("times"));
				videoActionVO.setSets(rs.getInt("sets"));
				list.add(videoActionVO);
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
	public List<VideoActionVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<VideoActionVO> list = new ArrayList<>();
		VideoActionVO videoActionVO = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				videoActionVO = new VideoActionVO();
				videoActionVO.setActNo(rs.getInt("actNo"));
				videoActionVO.setVideoID(rs.getInt("videoID"));
				videoActionVO.setAction(rs.getString("action"));
				videoActionVO.setTimes(rs.getInt("times"));
				videoActionVO.setSets(rs.getInt("sets"));
				list.add(videoActionVO);
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
		VideoActionJDBCDAO dao  = new VideoActionJDBCDAO();
		
		// insert
		VideoActionVO videoActionVO = new VideoActionVO();
		videoActionVO.setVideoID(30010);
		videoActionVO.setAction("動作二十");
		videoActionVO.setTimes(30);
		videoActionVO.setSets(10);
		dao.insert(videoActionVO);
		
		// update
//		VideoActionVO videoActionVO = new VideoActionVO();
//		videoActionVO.setActionNo(17);
//		videoActionVO.setVideoID(30002);
//		videoActionVO.setAction("yyyyy");
//		videoActionVO.setTimes(20);
//		videoActionVO.setSets(10);
//		dao.update(videoActionVO);
		
		// delete by actionNo
//		dao.delete(17);
		
		// delete by videoID
//		dao.deleteByVideoID(30010);
		
		// find by actionNo
//		VideoActionVO videoActionVO = dao.findByActionNo(1);
//		System.out.print(videoActionVO.getActionNo() + ", ");
//		System.out.print(videoActionVO.getVideoID() + ", ");
//		System.out.print(videoActionVO.getAction() + ", ");
//		System.out.print(videoActionVO.getTimes() + ", ");
//		System.out.print(videoActionVO.getSets());
//		System.out.println();
		
		// find by videoID
//		List<VideoActionVO> list = dao.findByVideoID(30004);
//		for(VideoActionVO vo : list) {
//			System.out.print(vo.getActionNo() + ", ");
//			System.out.print(vo.getVideoID() + ", ");
//			System.out.print(vo.getAction() + ", ");
//			System.out.print(vo.getTimes() + ", ");
//			System.out.print(vo.getSets());
//			System.out.println();
//		}
		
		// get all
		List<VideoActionVO> list = dao.getAll();
		for(VideoActionVO vo : list) {
			System.out.print(vo.getActNo() + ", ");
			System.out.print(vo.getVideoID() + ", ");
			System.out.print(vo.getAction() + ", ");
			System.out.print(vo.getTimes() + ", ");
			System.out.print(vo.getSets());
			System.out.println();
		}
	}
}
