package com.userRights.model;

import java.util.List;

public class UserRightsService {
	private UserRightsDAO_interface dao;
	
	public UserRightsService() {
		dao = new UserRightsJDBCDAO();
	}

	public UserRightsVO add(Integer userID, Integer videoID, Integer duration) {
		UserRightsVO userRightsVO = new UserRightsVO();
		userRightsVO.setUserID(userID);
		userRightsVO.setVideoID(videoID);
		userRightsVO.setDuration(duration);
		dao.add(userRightsVO);
		return userRightsVO;
	}

	public void delete(Integer rightsID) {
		dao.delete(rightsID);
	}

	public UserRightsVO update(Integer duration, Integer rightsID) {
		UserRightsVO userRightsVO = new UserRightsVO();
		userRightsVO.setRightsID(rightsID);
		userRightsVO.setDuration(duration);
		dao.update(userRightsVO);
		return userRightsVO;
	}

	public UserRightsVO findByPrimaryKey(Integer rightsID) {
		return dao.findByPrimaryKey(rightsID);
	}

	public List<UserRightsVO> getAll(Integer userID) {
		return dao.getAll(userID);
	}
	
	
}
