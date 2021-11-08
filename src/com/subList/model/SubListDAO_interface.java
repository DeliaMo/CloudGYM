package com.subList.model;

import java.util.List;

public interface SubListDAO_interface {
	public void insert(SubListVO subListVO);
	public void update(SubListVO subListVO);
	public void delete(Integer subID);
	public SubListVO findBySubID(Integer subID);
	public List<SubListVO> findAll();
}
