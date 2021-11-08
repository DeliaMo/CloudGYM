package com.report.model;

import java.util.List;

public interface ReportDAO_interface {
	
	public void insert(ReportVO reportVO);
	public void delete(ReportVO reportVO);
	public List<ReportVO> findbyUserID(Integer userID);
	public List<ReportVO> findbyItemID(Integer itemID);
	public List<ReportVO> findAll();
}
