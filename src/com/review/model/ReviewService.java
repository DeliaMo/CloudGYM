package com.review.model;

import java.util.List;

public class ReviewService {
	private ReviewDAO_interface dao;

	public ReviewService() {
		dao = new ReviewJDBCDAO();
	}

	public ReviewVO add(Integer videoID, Integer star) {
		ReviewVO reviewVO = new ReviewVO();
		reviewVO.setVideoID(videoID);
		reviewVO.setStar(star);
		dao.add(reviewVO);
		return reviewVO;
	}

	public void delete(Integer reviewNo) {
		dao.delete(reviewNo);
	}

	public void deleteByVideoID(Integer videoID) {
		dao.deleteByVideoID(videoID);
	}

	public ReviewVO findByPrimaryKey(Integer videoID) {
		return dao.findByPrimaryKey(videoID);
	}

	public List<ReviewVO> getAll() {
		return dao.getAll();
	}
	
	
	
	
}
