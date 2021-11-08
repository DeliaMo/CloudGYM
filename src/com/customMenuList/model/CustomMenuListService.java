package com.customMenuList.model;

import java.util.List;

public class CustomMenuListService {
	private CustomMenuListDAO_interface dao;
	
	public CustomMenuListService() {
		dao = new CustomMenuListJDBCDAO();
	}

	public CustomMenuListVO add(Integer menuID, Integer videoID) {
		CustomMenuListVO customMenuListVO = new CustomMenuListVO();
		customMenuListVO.setMenuID(menuID);
		customMenuListVO.setVideoID(videoID);
		dao.add(customMenuListVO);
		return customMenuListVO;
	}

	public void delete(Integer listID) {
		dao.delete(listID);
	}

	public CustomMenuListVO findByPrimaryKey(Integer listID) {
		return dao.findByPrimaryKey(listID);
	}

	public List<CustomMenuListVO> getAll(Integer menuID) {
		return dao.getAll(menuID);
	}
	
	
}
