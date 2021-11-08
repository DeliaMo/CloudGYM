package com.userRights.model;

import java.util.List;

public interface UserRightsDAO_interface {
	public void add(UserRightsVO userrights);
	public void delete(Integer rightsID);
	public void update(UserRightsVO userrights);
	public UserRightsVO findByPrimaryKey(Integer rightsID);
	public List<UserRightsVO> getAll(Integer userID);
	
}
