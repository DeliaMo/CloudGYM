package com.admin.model;

import java.util.List;

public interface AdminDAO_interface {
	public void update(AdminVO adminVO);
	public AdminVO findbyAdminID(Integer adminID);
	public List<AdminVO> findAll();

}
