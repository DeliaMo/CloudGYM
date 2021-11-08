package com.collection.model;

import java.util.*;
import java.sql.*;

public class CollectionJDBCDAO implements CollectionDAO_interface {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String USERID = "David";
	private static final String PASSWORD = "123456";

	private static final String INSERT_STMT = "INSERT INTO collection(userID, itemID) VALUES(?, ?);";
//	private static final String UPDATE_STMT = "UPDATE collection SET userID=?, itemID=? WHERE collectionNo=?";
	private static final String DELETE_STMT = "DELETE FROM collection WHERE collectionNo=?";
	private static final String FIND_BY_COLLECTIONNO = "SELECT * FROM collection WHERE collectionNo=?";
	private static final String GET_BY_USERID = "SELECT * FROM collection WHERE userID=?";
	private static final String GET_BY_ITEMID = "SELECT * FROM collection WHERE itemID=?";
	private static final String GET_ALL = "SELECT * FROM collection";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	@Override
	public void insert(CollectionVO collectionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, collectionVO.getUserID());
			pstmt.setInt(2, collectionVO.getItemID());

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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

	}

	@Override
	public void delete(Integer collectionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, collectionNo);
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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

	}

	@Override
	public CollectionVO findByCollectionNo(Integer collectionNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CollectionVO collectionVO = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_COLLECTIONNO);
			pstmt.setInt(1, collectionNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				collectionVO = new CollectionVO();
				collectionVO.setCollectionNo(rs.getInt("collectionNo"));
				collectionVO.setUserID(rs.getInt("userID"));
				collectionVO.setItemID(rs.getInt("itemID"));

			}

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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return collectionVO;
	}

	@Override
	public List<CollectionVO> getByUserId(Integer userID) {
		List<CollectionVO> list = new ArrayList<CollectionVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CollectionVO collectionVO = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(GET_BY_USERID);
			pstmt.setInt(1, userID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				collectionVO = new CollectionVO();
				collectionVO.setCollectionNo(rs.getInt("collectionNo"));
				collectionVO.setUserID(rs.getInt("userID"));
				collectionVO.setItemID(rs.getInt("itemID"));
				list.add(collectionVO);
			}

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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<CollectionVO> getByItemId(Integer itemID) {
		List<CollectionVO> list = new ArrayList<CollectionVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CollectionVO collectionVO = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(GET_BY_ITEMID);
			pstmt.setInt(1, itemID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				collectionVO = new CollectionVO();
				collectionVO.setCollectionNo(rs.getInt("collectionNo"));
				collectionVO.setUserID(rs.getInt("userID"));
				collectionVO.setItemID(rs.getInt("itemID"));
				list.add(collectionVO);
			}

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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<CollectionVO> getAll() {
		List<CollectionVO> list = new ArrayList<CollectionVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CollectionVO collectionVO = null;

		try {
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				collectionVO = new CollectionVO();
				collectionVO.setCollectionNo(rs.getInt("collectionNo"));
				collectionVO.setUserID(rs.getInt("userID"));
				collectionVO.setItemID(rs.getInt("itemID"));
				list.add(collectionVO);
			}

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
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

//	@Override
//	public void update(CollectionVO collectionVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			con = DriverManager.getConnection(URL, USERID, PASSWORD);
//			pstmt = con.prepareStatement(UPDATE_STMT);
//
//			pstmt.setInt(1, collectionVO.getUserID());
//			pstmt.setInt(2, collectionVO.getItemID());
//			pstmt.setInt(3, collectionVO.getCollectionNo());
//
//			pstmt.executeUpdate();
//		} catch (SQLException se) {
//			se.printStackTrace();
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace();
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException se) {
//					se.printStackTrace();
//				}
//			}
//		}
//
//	}

	public static void main(String[] args) {

		CollectionJDBCDAO dao = new CollectionJDBCDAO();

		// INSERT 新增
//		CollectionVO collectionVO1 = new CollectionVO();
//		collectionVO1.setUserID(1020);
//		collectionVO1.setItemID(40001);
//		dao.insert(collectionVO1);
//		System.out.println("新增成功");

		// UPDATE 修改 (應該用不到這個功能)
//		CollectionVO collectionVO2 = new CollectionVO();
//		collectionVO2.setUserID(1020);
//		collectionVO2.setItemID(60009);
//		collectionVO2.setCollectionNo(23);
//		dao.update(collectionVO2);
//		System.out.println("修改成功");

		// DELETE 刪除
//		dao.delete(20);
//		System.out.println("刪除成功");

		// FIND_BY_COLLECTIONNO 收藏編號查詢
//		CollectionVO collectionVO3 = dao.findByCollectionNo(1);
//		System.out.print("collectionNo:" + collectionVO3.getCollectionNo() + ", ");
//		System.out.print("userID:" + collectionVO3.getUserID() + ", ");
//		System.out.print("itemID:" + collectionVO3.getItemID());
//		System.out.println();
//		System.out.println("使用收藏編號查詢");

		// GET_BY_USERID 使用會員編號查詢
//		List<CollectionVO> list1 = dao.getByUserId(1013);
//		for(CollectionVO cltvo1 : list1) {
//			System.out.print("collectionNo:" + cltvo1.getCollectionNo() + ", ");
//			System.out.print("userID:" + cltvo1.getUserID() + ", ");
//			System.out.print("itemID:" + cltvo1.getItemID() +", ");
//			System.out.println();
//		}
//		System.out.println("使用會員編號查詢");	

		// GET_BY_USERID 使用項目編號查詢
//		List<CollectionVO> list2 = dao.getByItemId(40001);
//		for(CollectionVO cltvo2 : list2) {
//			System.out.print("collectionNo:" + cltvo2.getCollectionNo() + ", ");
//			System.out.print("userID:" + cltvo2.getUserID() + ", ");
//			System.out.print("itemID:" + cltvo2.getItemID() +", ");
//			System.out.println();
//		}
//		System.out.println("使用項目編號查詢");	

		// GET_ALL 全部查詢
		List<CollectionVO> list3 = dao.getAll();
		for (CollectionVO cltvo3 : list3) {
			System.out.print("collectionNo:" + cltvo3.getCollectionNo() + ", ");
			System.out.print("userID:" + cltvo3.getUserID() + ", ");
			System.out.print("menuID:" + cltvo3.getItemID() + ", ");
			System.out.println();
		}
		System.out.println("全部查詢");
	}

}
