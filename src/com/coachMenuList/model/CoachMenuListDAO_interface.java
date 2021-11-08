package com.coachMenuList.model;

import java.util.List;

public interface CoachMenuListDAO_interface {
	public void insert(CoachMenuListVO coachMenuListVO);
	public void update(CoachMenuListVO coachMenuListVO);
	public void delete(Integer menuNo);
	public CoachMenuListVO findByMenuNo(Integer menuNo);
	public List<CoachMenuListVO> findByMenuID(Integer menuID);
	public List<CoachMenuListVO> findAll();
}
