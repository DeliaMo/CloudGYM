package com.reportRecord.model;

import java.util.List;

public class ReportRecordService {
	
	private ReportRecordDAO_interface dao;
	
	public ReportRecordService() {
		dao = new ReportRecordJDBCDAO();
	}

	public ReportRecordVO addReportRecord(Integer itemID,Integer userID) {
		
		ReportRecordVO reportRecordVO = new ReportRecordVO();
//		reportRecordVO.setRecordID(recordID);
		reportRecordVO.setItemID(itemID);
		reportRecordVO.setUserID(userID);
		dao.insert(reportRecordVO);
		return reportRecordVO;
	}
	
	public void deleteReportRecord(Integer itemID) {
		dao.delete(itemID);
	}
	
	public List<ReportRecordVO> getByUser(Integer userID){
		return dao.findbyuserID(userID);
	}
	
	public List<ReportRecordVO> getAll(){
		return dao.findAll();
	}
}
