package com.user.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserService {

	private UserDAO_interface dao;

	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String str = sd.format(new Date(System.currentTimeMillis()));
	Timestamp ts = Timestamp.valueOf(str);

	public UserService() {
		dao = new UserJDBCDAO();
	}

	public UserVO addUser(String userAccount, String userName, String userPassword, String userMobile, String userSex,
			Date userBirthday, Timestamp userRegisterDate, Integer userReportedTimes) {

		UserVO userVO = new UserVO();

//		userVO.setUserID(userID);
		userVO.setUserAccount(userAccount);
		userVO.setUserName(userName);
		userVO.setUserPassword(userPassword);
		userVO.setUserMobile(userMobile);
		userVO.setUserSex(userSex);
		userVO.setUserBirthday(userBirthday);
		userVO.setUserRegisterDate(ts);
		userVO.setUserReportedTimes(userReportedTimes);
		dao.insert(userVO);

		return userVO;
	}

	public UserVO updateUser(String userAccount, String userName, String userPassword, String userMobile,
			String userSex, Date userBirthday, Integer userReportedTimes, Integer userID) {
		UserVO userVO = new UserVO();

		userVO.setUserID(userID);
		userVO.setUserAccount(userAccount);
		userVO.setUserName(userName);
		userVO.setUserPassword(userPassword);
		userVO.setUserMobile(userMobile);
		userVO.setUserSex(userSex);
		userVO.setUserBirthday(userBirthday);
		userVO.setUserReportedTimes(userReportedTimes);
		dao.update(userVO);

		return userVO;
	}

	public void deleteUser(Integer userID) {
		dao.delete(userID);
	}

	public UserVO findByUserId(Integer userID) {
		return dao.findByUserId(userID);
	}

	public UserVO findByUserAccount(String userAccount) {
		return dao.findByUserAccount(userAccount);
	}

	public List<UserVO> getAll() {
		return dao.getAll();
	}

	public UserVO changePassword(String userPassword, Integer userID) {
		UserVO userVO = new UserVO();

		userVO.setUserPassword(userPassword);
		userVO.setUserID(userID);
		dao.changePassword(userVO);

		return userVO;
	}

}
