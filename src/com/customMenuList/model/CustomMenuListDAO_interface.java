package com.customMenuList.model;

import java.util.List;

public interface CustomMenuListDAO_interface {
	public void add(CustomMenuListVO customMenuList);
	public void delete(Integer listID);
	public CustomMenuListVO findByPrimaryKey(Integer listID);
	public List<CustomMenuListVO> getAll(Integer menuID);
}
