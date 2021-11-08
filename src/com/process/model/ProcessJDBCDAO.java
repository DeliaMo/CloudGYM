package com.process.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessJDBCDAO implements ProcessDAO_interface{
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String USER = "David";
	private static final String PASSWRD = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO process(userID, listID, action) VALUES(?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE process SET userID=?, listID=?, action=?, sets=? WHERE processNo=?";
	private static final String DELETE_STMT = "DELETE FROM process WHERE processNo=?";
	private static final String FIND_BY_PROCESSNO = "SELECT * FROM process WHERE processNo=?";
	private static final String FIND_BY_USERID = "SELECT * FROM process WHERE userID=?";
	private static final String FIND_BY_LISTID = "SELECT * FROM process WHERE listID=?";
	private static final String FIND_BY_ACTNO = "SELECT * FROM process WHERE actNo=?";
	private static final String GET_ALL = "SELECT * FROM process";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	@Override
	public ProcessVO findByActNo(Integer actNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProcessVO processVO = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(FIND_BY_ACTNO);
			pstmt.setInt(1, actNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				processVO = new ProcessVO();
				processVO.setProcessNo(rs.getInt("processNo"));
				processVO.setUserID(rs.getInt("userID"));
				processVO.setListID(rs.getInt("listID"));
				processVO.setAction(rs.getString("action"));
				processVO.setSets(rs.getInt("sets"));
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
		return processVO;
	}


	@Override
	public void insert(ProcessVO processVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, processVO.getUserID());
			pstmt.setInt(2, processVO.getListID());
			pstmt.setString(3, processVO.getAction());
			
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
	public void update(ProcessVO processVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, processVO.getUserID());
			pstmt.setInt(2, processVO.getListID());
			pstmt.setString(3, processVO.getAction());
			pstmt.setInt(4, processVO.getSets());
			pstmt.setInt(5, processVO.getProcessNo());
			
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
	public void delete(Integer processNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, processNo);
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
	public ProcessVO findByProcessNo(Integer processNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProcessVO processVO = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(FIND_BY_PROCESSNO);
			pstmt.setInt(1, processNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				processVO = new ProcessVO();
				processVO.setProcessNo(rs.getInt("processNo"));
				processVO.setUserID(rs.getInt("userID"));
				processVO.setListID(rs.getInt("listID"));
				processVO.setAction(rs.getString("action"));
				processVO.setSets(rs.getInt("sets"));
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
		return processVO;
	}

	@Override
	public List<ProcessVO> findBuUserID(Integer userID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProcessVO processVO = null;
		List<ProcessVO> list = new ArrayList<ProcessVO>();
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(FIND_BY_USERID);
			pstmt.setInt(1, userID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				processVO = new ProcessVO();
				processVO.setProcessNo(rs.getInt("processNo"));
				processVO.setUserID(rs.getInt("userID"));
				processVO.setListID(rs.getInt("listID"));
				processVO.setAction(rs.getString("action"));
				processVO.setSets(rs.getInt("sets"));
				list.add(processVO);
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
	public List<ProcessVO> findByListID(Integer listID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProcessVO processVO = null;
		List<ProcessVO> list = new ArrayList<ProcessVO>();
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(FIND_BY_LISTID);
			pstmt.setInt(1, listID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				processVO = new ProcessVO();
				processVO.setProcessNo(rs.getInt("processNo"));
				processVO.setUserID(rs.getInt("userID"));
				processVO.setListID(rs.getInt("listID"));
				processVO.setAction(rs.getString("action"));
				processVO.setSets(rs.getInt("sets"));
				list.add(processVO);
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
	public List<ProcessVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProcessVO processVO = null;
		List<ProcessVO> list = new ArrayList<ProcessVO>();
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWRD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				processVO = new ProcessVO();
				processVO.setProcessNo(rs.getInt("processNo"));
				processVO.setUserID(rs.getInt("userID"));
				processVO.setListID(rs.getInt("listID"));
				processVO.setAction(rs.getString("action"));
				processVO.setSets(rs.getInt("sets"));
				list.add(processVO);
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
	
	public static void main(String[] args) {
		ProcessJDBCDAO dao = new ProcessJDBCDAO();
		
		// insert
//		ProcessVO process = new ProcessVO();
//		process.setUserID(1005);
//		process.setListID(8);
//		process.setAction("XXXXXX");
//		dao.insert(process);
		
		// update
//		ProcessVO process = new ProcessVO();
//		process.setUserID(1005);
//		process.setListID(9);
//		process.setAction("zzzzz");
//		process.setSets(3);
//		process.setProcessNo(63);
//		dao.update(process);
		
		// delete
		dao.delete(63);
		
		// find by processNo
//		ProcessVO processVO = dao.findByProcessNo(1);
//		System.out.print(processVO.getProcessNo() + ", ");
//		System.out.print(processVO.getUserID() + ", ");
//		System.out.print(processVO.getListID() + ", ");
//		System.out.print(processVO.getAction() + ", ");
//		System.out.print(processVO.getSets());
//		System.out.println();
		
		// find by userID
//		List<ProcessVO> list = dao.findBuUserID(1002);
//		for(ProcessVO processVO : list) {
//		System.out.print(processVO.getProcessNo() + ", ");
//		System.out.print(processVO.getUserID() + ", ");
//		System.out.print(processVO.getListID() + ", ");
//		System.out.print(processVO.getAction() + ", ");
//		System.out.print(processVO.getSets());
//		System.out.println();
//	}
		
		// find by listID
//		List<ProcessVO> list = dao.findByListID(4);
//		for(ProcessVO processVO : list) {
//		System.out.print(processVO.getProcessNo() + ", ");
//		System.out.print(processVO.getUserID() + ", ");
//		System.out.print(processVO.getListID() + ", ");
//		System.out.print(processVO.getAction() + ", ");
//		System.out.print(processVO.getSets());
//		System.out.println();
//	}
		
		// get all
		List<ProcessVO> list = dao.getAll();
		for(ProcessVO processVO : list) {
			System.out.print(processVO.getProcessNo() + ", ");
			System.out.print(processVO.getUserID() + ", ");
			System.out.print(processVO.getListID() + ", ");
			System.out.print(processVO.getAction() + ", ");
			System.out.print(processVO.getSets());
			System.out.println();
		}
	}
}
