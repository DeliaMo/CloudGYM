package com.video.model;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

public class VideoService {
	private VideoDAO_interface dao;
	
	public VideoService() {
		dao = new VideoJDBCDAO();
	}

	public VideoVO add(Integer userID, String title, Integer price, String intro, byte[] content, String level, Integer thePosition) {
		VideoVO videoVO = new VideoVO();
		videoVO.setUserID(userID);
		videoVO.setTitle(title);
		videoVO.setPrice(price);
		videoVO.setIntro(intro);
//		videoVO.setImg(img);
		videoVO.setContent(content);
		videoVO.setLevel(level);
		videoVO.setThePosition(thePosition);
//		videoVO.setDuration(duration);
//		videoVO.setListed(listed);
//		videoVO.setReportedTimes(reportedTimes);
//		videoVO.setPublishTime(publishTime);
		Integer videoID = dao.add(videoVO);
		videoVO.setVideoID(videoID);
		return videoVO;
	}

//	public VideoVO update(String title, Integer price, String intro, byte[] img, byte[] content, String level , Integer videoID, Integer thePosition) {
//		VideoVO videoVO = new VideoVO();
//		videoVO.setTitle(title);
//		videoVO.setPrice(price);
//		videoVO.setIntro(intro);
//		videoVO.setImg(img);
//		videoVO.setContent(content);
//		videoVO.setLevel(level);
//		videoVO.setVideoID(videoID);
//		videoVO.setThePosition(thePosition);
//		dao.update(videoVO);
//		return videoVO;
//	}
	
	public VideoVO updateVideo(VideoVO videoVO) {
		dao.update(videoVO);
		return videoVO;
	}

	public void delete(Integer videoID) {
		dao.delete(videoID);
	}

	public VideoVO findByPrimaryKey(Integer videoID) {
		return dao.findByPrimaryKey(videoID);
	}
	
	public List<VideoVO> getByUserID(Integer userID){
		return dao.findByUserID(userID);
	}

	public List<VideoVO> getAll() {
		return dao.getAll();
	}
	
	public List<VideoVO> getAll2(){
		return dao.getAll2();
	}
	
	public List<VideoVO> getByPositionNo(Integer positionNo){
		return dao.findByPositionNo(positionNo);
	}
	
	public VideoVO findByPrimaryKeyNoVideo(Integer videoID) {
		return dao.findByPrimaryKeyNoVideo(videoID);
	}
	
	public List<VideoVO> getRecommendedVideos(){
		return dao.recommendedVideos();
	}
	
	public void updateReportedTimes(Integer reportedTimes, Integer videoID) {
		dao.updateReportedTimes(reportedTimes, videoID);
	}
}
