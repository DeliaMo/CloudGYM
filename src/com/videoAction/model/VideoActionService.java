package com.videoAction.model;

import java.util.List;

public class VideoActionService {
	private VideoActionDAO_interface dao;

	public VideoActionService() {
		dao = new VideoActionJDBCDAO();
	}

	public VideoActionVO addVideoAction(Integer videoID, String action, Integer times, Integer sets) {
		VideoActionVO videoActionVO = new VideoActionVO();

		videoActionVO.setVideoID(videoID);
		videoActionVO.setAction(action);
		videoActionVO.setTimes(times);
		videoActionVO.setSets(sets);
		dao.insert(videoActionVO);
		return videoActionVO;
	}
	
	public VideoActionVO updateVideoAction(Integer actionNo, Integer videoID, String action, Integer times, Integer sets) {
		VideoActionVO videoActionVO = new VideoActionVO();

		videoActionVO.setActNo(actionNo);
		videoActionVO.setVideoID(videoID);
		videoActionVO.setAction(action);
		videoActionVO.setTimes(times);
		videoActionVO.setSets(sets);
		dao.update(videoActionVO);
		return videoActionVO;
	}
	
	public boolean deleteByactionNo(Integer actionNo) {
		try {
			dao.delete(actionNo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteByVideoID(Integer videoID) {
		try {
			dao.deleteByVideoID(videoID);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public VideoActionVO getByActionNo(Integer actionNo) {
		return dao.findByActionNo(actionNo);
	}
	
	public List<VideoActionVO> getByVideoID(Integer videoID){
		return dao.findByVideoID(videoID);
	}
	
	public List<VideoActionVO> getAll(){
		return dao.getAll();
	}
}
