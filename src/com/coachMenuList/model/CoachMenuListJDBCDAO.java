package com.coachMenuList.model;

import java.sql.*;
import java.util.*;


public class CoachMenuListJDBCDAO implements CoachMenuListDAO_interface{
	
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String userid = "David";
	private static final String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO coachMenuList(menuID, videoID) VALUES(?, ?);";
	private static final String UPDATE_STMT = "UPDATE coachMenuList SET videoID=? WHERE menuNo=?";
	private static final String DELETE_STMT = "DELETE FROM coachMenuList WHERE menuNo=?";
	private static final String FIND_BY_MENUNO = "SELECT * FROM coachMenuList WHERE menuNo=?";
	private static final String FIND_BY_MENUID = "SELECT * FROM coachMenuList WHERE menuID=?";
	private static final String FIND_ALL = "SELECT * FROM coachMenuList";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(CoachMenuListVO coachMenuListVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, coachMenuListVO.getMenuID());
			pstmt.setInt(2, coachMenuListVO.getVideoID());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	public void update(CoachMenuListVO coachMenuListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, coachMenuListVO.getVideoID());
//			pstmt.setInt(2, coachMenuListVO.getMenuID());
			pstmt.setInt(2, coachMenuListVO.getMenuNo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	public void delete(Integer menuNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, menuNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	public CoachMenuListVO findByMenuNo(Integer menuNo) {
		
		CoachMenuListVO coachMenuListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_MENUNO);
			pstmt.setInt(1, menuNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				coachMenuListVO = new CoachMenuListVO();
				coachMenuListVO.setMenuNo(rs.getInt("menuNo"));
				coachMenuListVO.setMenuID(rs.getInt("menuID"));
				coachMenuListVO.setVideoID(rs.getInt("videoID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
		return coachMenuListVO;
	}

	@Override
	public List<CoachMenuListVO> findByMenuID(Integer menuID) {

		List<CoachMenuListVO> list = new ArrayList<CoachMenuListVO>();
		CoachMenuListVO coachMenuListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_MENUID);
			pstmt.setInt(1, menuID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				coachMenuListVO = new CoachMenuListVO();
				coachMenuListVO.setMenuNo(rs.getInt("menuNo"));
				coachMenuListVO.setMenuID(rs.getInt("menuID"));
				coachMenuListVO.setVideoID(rs.getInt("videoID"));
				list.add(coachMenuListVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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

	@Override
	public List<CoachMenuListVO> findAll() {
		// TODO Auto-generated method stub
		List<CoachMenuListVO> list = new ArrayList<CoachMenuListVO>();
		CoachMenuListVO coachMenuListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				coachMenuListVO = new CoachMenuListVO();
				coachMenuListVO.setMenuNo(rs.getInt("menuNo"));
				coachMenuListVO.setMenuID(rs.getInt("menuID"));
				coachMenuListVO.setVideoID(rs.getInt("videoID"));
				list.add(coachMenuListVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] arg) {
		CoachMenuListJDBCDAO dao = new CoachMenuListJDBCDAO();
		
		// �憓�
//		CoachMenuListVO cml = new CoachMenuListVO();
//		cml.setMenuID(60001);
//		cml.setVideoID(30005);
//		dao.insert(cml);
		
		// 靽格
//		CoachMenuListVO cml2 = new CoachMenuListVO();
//		cml2.setMenuNo(21);
//		cml2.setMenuID(60002);
//		cml2.setVideoID(30005);
//		dao.update(cml2);
		
		// ��
		dao.delete(21);

		// �����敦蝺刻�閰�
//		CoachMenuListVO cml = dao.findByMenuNo(01);
//		System.out.print(cml.getMenuNo() + ", ");
//		System.out.print(cml.getMenuID() + ", ");
//		System.out.print(cml.getVideoID());
//		System.out.println();
		
		// ���蝺刻�閰�
//		List<CoachMenuListVO> list1 = dao.findByMenuID(60001);
//		for(CoachMenuListVO cmlvo : list1) {
//			System.out.print(cmlvo.getMenuNo() + ", ");
//			System.out.print(cmlvo.getMenuID() + ", ");
//			System.out.print(cmlvo.getVideoID());
//		}
		
		// �閰Ｗ�
		List<CoachMenuListVO> list2 = dao.findAll();
		for(CoachMenuListVO cmlvo2 : list2) {
			System.out.print(cmlvo2.getMenuNo() + ", ");
			System.out.print(cmlvo2.getMenuID() + ", ");
			System.out.print(cmlvo2.getVideoID());
			System.out.println();
		}
	}
}
