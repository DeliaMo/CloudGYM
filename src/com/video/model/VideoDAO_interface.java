package com.video.model;

import java.util.List;

public interface VideoDAO_interface {
	Integer add(VideoVO videoVO);
	void update(VideoVO videoVO);
	void delete(Integer videoID);
	VideoVO findByPrimaryKey(Integer videoID);
	List<VideoVO> getAll();
	List<VideoVO> getAll2();
	List<VideoVO> findByUserID(Integer userID);
	List<VideoVO> findByPositionNo(Integer positionNo);
	VideoVO findByPrimaryKeyNoVideo(Integer videoID);
	List<VideoVO> recommendedVideos();
	void updateReportedTimes(Integer reportedTimes, Integer videoID);
}
