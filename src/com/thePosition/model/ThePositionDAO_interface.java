package com.thePosition.model;

import java.util.List;

public interface ThePositionDAO_interface {
	public void insert(ThePositionVO thePositionVO);
	public void update(ThePositionVO thePositionVO);
	public void delete(Integer positionNo);
	public ThePositionVO getOnePosition(Integer positionNo);
	public List<ThePositionVO> getAll();
}
