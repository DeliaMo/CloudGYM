package com.videoAction.model;

import java.util.List;

public interface VideoActionDAO_interface {
	public void insert(VideoActionVO videoActionVO);
	public void update(VideoActionVO videoActionVO);
	public void delete(Integer actionNo);
	public void deleteByVideoID(Integer videoID);
	public VideoActionVO findByActionNo(Integer actionNo);
	public List<VideoActionVO> findByVideoID(Integer videoID);
	public List<VideoActionVO> getAll();
}
