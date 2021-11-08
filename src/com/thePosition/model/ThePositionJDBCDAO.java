package com.thePosition.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThePositionJDBCDAO  implements ThePositionDAO_interface{
	
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String userid = "David";
	private static final String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO thePosition(positionName) VALUES(?)";
	private static final String UPDATE_STMT = "UPDATE thePosition SET positionName=? WHERE positionNo=?";
	private static final String DELETE_STMT = "DELETE FROM thePosition WHERE positionNo=?";
	private static final String GET_ONE_STMT = "SELECT * FROM thePosition WHERE positionNo=?";
	private static final String GET_ALL_STMT = "SELECT * FROM thePosition";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(ThePositionVO thePositionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, thePositionVO.getPositionName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public void update(ThePositionVO thePositionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, thePositionVO.getPositionName());
			pstmt.setInt(2, thePositionVO.getPositionNo());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public void delete(Integer positionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, positionNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	public ThePositionVO getOnePosition(Integer positionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ThePositionVO thePositionVO = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, positionNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				thePositionVO = new ThePositionVO();
				thePositionVO.setPositionNo(rs.getInt("positionNo"));
				thePositionVO.setPositionName(rs.getString("positionName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thePositionVO;
	}

	@Override
	public List<ThePositionVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ThePositionVO thePositionVO = null;
		ResultSet rs = null;
		List<ThePositionVO> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				thePositionVO = new ThePositionVO();
				thePositionVO.setPositionNo(rs.getInt("positionNo"));
				thePositionVO.setPositionName(rs.getString("positionName"));
				list.add(thePositionVO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		ThePositionJDBCDAO dao = new ThePositionJDBCDAO();
		
		// insert
//		ThePositionVO obj1 = new ThePositionVO();
//		obj1.setPostionName("頭");
//		dao.insert(obj1);
		
		// update
//		ThePositionVO obj1 = new ThePositionVO();
//		obj1.setPostionName("頭12");
//		obj1.setPositionNo(6);
//		dao.update(obj1);
		
		// delete
//		dao.delete(7);
		
		// getOne
//		ThePositionVO obj = dao.getOnePosition(6);
//		System.out.print(obj.getPositionNo() + ", ");
//		System.out.print(obj.getPostionName());
//		System.out.println();
		
		// getAll
		List<ThePositionVO> list = dao.getAll();
		for(ThePositionVO pvo : list) {
			System.out.print(pvo.getPositionNo() + ", ");
			System.out.print(pvo.getPositionName());
			System.out.println();
		}
	}
}
