package com.review.model;

import java.util.List;

public interface ReviewDAO_interface {
	public void add(ReviewVO reviewVO);
	public void delete(Integer reviewNo);
	public void deleteByVideoID(Integer videoID);
	public ReviewVO findByPrimaryKey(Integer videoID);
	public List<ReviewVO> getAll();
	
}
