package com.tag.model;

import java.util.List;

public class TagService {

	private TagDAO_interface dao;

	public TagService() {
		dao = new TagJDBCDAO();
	}

	public TagVO getByTagID(Integer tagID) {
		return dao.findByPrimaryKey(tagID);
	}

	public List<TagVO> getAll() {
		return dao.findAll();
	}

}
