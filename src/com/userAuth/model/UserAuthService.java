package com.userAuth.model;

import java.sql.Timestamp;
import java.util.List;

public class UserAuthService {
	
	private UserAuthDAO_interface dao;
	
	public UserAuthService() {
		dao = new UserAuthJDBCDAO();
	}
	
	public UserAuthVO addUserAuth(Integer userID,Integer banComment,Integer banShopping,
			Integer banVideo,Integer banUsers,Timestamp startTime) {
		
		UserAuthVO userAuthVO = new UserAuthVO();
		userAuthVO.setUserID(userID);
		userAuthVO.setBanComment(banComment);
		userAuthVO.setBanShopping(banShopping);
		userAuthVO.setBanVideo(banVideo);
		userAuthVO.setBanUsers(banUsers);
		userAuthVO.setStartTime(startTime);
		dao.insert(userAuthVO);
		return userAuthVO;
	}
	
	public UserAuthVO updateUserAuth(UserAuthVO userAuthVO) {
		
//		UserAuthVO userAuthVO = new UserAuthVO();
//		userAuthVO.setUserAuthID(userAuthID);
//		userAuthVO.setUserID(userID);
//		userAuthVO.setBanComment(banComment);
//		userAuthVO.setBanShopping(banShopping);
//		userAuthVO.setBanVideo(banVideo);
//		userAuthVO.setBanUsers(banUsers);
//		userAuthVO.setStartTime(startTime);
		dao.update(userAuthVO);
		return userAuthVO;
	}
	
	public UserAuthVO getUserID(Integer userID){
		return dao.findbyuserID(userID);
	}
	
	public  List<UserAuthVO> findAll(){
		return dao.findAll();
	}

}
