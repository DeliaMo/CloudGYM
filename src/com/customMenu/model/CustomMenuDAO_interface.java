package com.customMenu.model;

import java.util.List;

public interface CustomMenuDAO_interface {
	public void add(CustomMenuVO customMenu);
	public void delete(Integer menuID);
	public void update(CustomMenuVO customMenu);
	public CustomMenuVO findByPrimaryKey(Integer menuID);
	public List<CustomMenuVO> getAll(Integer userID);
}
