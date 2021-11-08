package com.tag.model;

import java.util.List;

public interface TagDAO_interface {
//	public void insert(TagVO tagVO);
//	public void update(TagVO tagVO);
//	public void delete(Integer tagID);
	public TagVO findByPrimaryKey(Integer tagID);
	public List<TagVO> findAll();
}
