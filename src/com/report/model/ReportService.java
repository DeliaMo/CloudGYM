package com.report.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReportService {
	
	private ReportDAO_interface dao;
	
	public ReportService() {
		dao = new ReportJDBCDAO();
	}
	
	public ReportVO addReport(Integer userID,Integer itemID) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sd.format(new Date(System.currentTimeMillis()));
		Timestamp ts = Timestamp.valueOf(str);
		
		ReportVO reportVO = new ReportVO();
		reportVO.setUserID(userID);
		reportVO.setItemID(itemID);
		reportVO.setReportTime(ts);
		dao.insert(reportVO);
		return reportVO;
	}
	
	public void deleteReport(Integer userID,Integer itemID) {
		ReportVO reportVO = new ReportVO();
		reportVO.setUserID(userID);
		reportVO.setItemID(itemID);
		dao.delete(reportVO);
	}
	
	public List<ReportVO> getByUser(Integer userID) {
		return dao.findbyUserID(userID);
	}
	
	public List<ReportVO> getByItem(Integer itemID) {
		return dao.findbyItemID(itemID);
	}
	
	public List<ReportVO> findAll(){
		return dao.findAll();
	}

}
