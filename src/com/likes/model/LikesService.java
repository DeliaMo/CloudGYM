package com.likes.model;

import java.util.List;

public class LikesService {

	private LikesDAO_interface dao;

	public LikesService() {
		dao = new LikesJDBCDAO();
	}

	public LikesVO addLikes(Integer postsID, Integer userID) {
		LikesVO likesVO = new LikesVO();
		likesVO.setPostsID(postsID);
		likesVO.setUserID(userID);
		dao.insert(likesVO);
		return likesVO;
	}

	public void deleteLikes(Integer likesID) {
		dao.delete(likesID);
	}

	public LikesVO getByLikesID(Integer likesID) {
		return dao.findByPrimaryKey(likesID);
	}

	public Integer getCountLike(Integer postsID) {
		return dao.countByLike(postsID);
	}

	public List<LikesVO> getAll() {
		return dao.findAll();
	}

}
