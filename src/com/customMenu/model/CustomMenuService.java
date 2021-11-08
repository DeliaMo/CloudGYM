package com.customMenu.model;

import java.sql.Timestamp;
import java.util.List;

public class CustomMenuService {
	private CustomMenuDAO_interface dao;
	
	public CustomMenuService() {
		dao = new CustomMenuJDBCDAO();
	}

	public CustomMenuVO add(Integer userID, String content, String title) {
		CustomMenuVO customMenuVO = new CustomMenuVO();
		customMenuVO.setUserID(userID);
		customMenuVO.setContent(content);
		customMenuVO.setTitle(title);
		dao.add(customMenuVO);
		return customMenuVO;
	}

	public void delete(Integer menuID) {
		dao.delete(menuID);
	}

	public CustomMenuVO update(Integer menuID, String content, String title) {
		CustomMenuVO customMenuVO = new CustomMenuVO();
		customMenuVO.setMenuID(menuID);
		customMenuVO.setContent(content);
		customMenuVO.setTitle(title);
		dao.update(customMenuVO);
		return customMenuVO;
	}

	public CustomMenuVO findByPrimaryKey(Integer menuID) {
		return dao.findByPrimaryKey(menuID);
	}

	public List<CustomMenuVO> getAll(Integer userID) {
		return dao.getAll(userID);
	}
	
	
}
