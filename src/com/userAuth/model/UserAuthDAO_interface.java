package com.userAuth.model;

import java.util.List;

public interface UserAuthDAO_interface {
	
	public void insert (UserAuthVO userAuthVO);
	public void update (UserAuthVO userAuthVO);
	public UserAuthVO findbyuserID(Integer userID);
	public List<UserAuthVO> findAll();

}
