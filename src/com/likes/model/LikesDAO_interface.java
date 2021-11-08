package com.likes.model;

import java.util.List;

public interface LikesDAO_interface {

	public void insert(LikesVO likesVO);

	public void delete(Integer likesID);

	public LikesVO findByPrimaryKey(Integer likesID);

	public Integer countByLike(Integer postsID);

	public List<LikesVO> findAll();

}
