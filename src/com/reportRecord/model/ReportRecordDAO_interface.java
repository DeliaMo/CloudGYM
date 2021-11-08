package com.reportRecord.model;

import java.util.List;

public interface ReportRecordDAO_interface {
	
	public void insert (ReportRecordVO reportRecordVO);
	public void delete (Integer itemID);
	public List<ReportRecordVO> findbyuserID(Integer userID);
	public List<ReportRecordVO> findAll();
	

}
