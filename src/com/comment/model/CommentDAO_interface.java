package com.comment.model;

import java.util.List;

public interface CommentDAO_interface {
	public Integer insert(CommentVO commentVO);
	public void update(CommentVO commentVO);
	public void delete(Integer commentID);
	public CommentVO findByPrimaryKey(Integer commentID);
	public Integer countByComment(Integer postsID);
	public List<CommentVO> findAll();
}
