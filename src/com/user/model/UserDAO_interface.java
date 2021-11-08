package com.user.model;

import java.util.List;

public interface UserDAO_interface {
	public void insert(UserVO userVO);
	public void update(UserVO userVO);
	public void delete(Integer userID);
	public UserVO findByUserId(Integer userID);
	public UserVO findByUserAccount(String userAccount);
	public List<UserVO> getAll();
	public void changePassword(UserVO userVO);
}
