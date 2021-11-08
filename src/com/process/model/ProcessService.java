package com.process.model;

import java.util.List;

public class ProcessService {
	
	private ProcessDAO_interface dao;
	
	public ProcessService() {
		dao = new ProcessJDBCDAO();
	}
	
	public ProcessVO addProcess(Integer userID, Integer listID, String action) {
		ProcessVO processVO = new ProcessVO();
		processVO.setUserID(userID);
		processVO.setListID(listID);
		processVO.setAction(action);
		dao.insert(processVO);
		return processVO;
	}
	
	public ProcessVO updateProcess(Integer userID, Integer listID, String action, Integer sets, Integer processNo) {
		ProcessVO processVO = new ProcessVO();
		processVO.setUserID(userID);
		processVO.setListID(listID);
		processVO.setSets(sets);
		processVO.setAction(action);
		processVO.setProcessNo(processNo);
		dao.update(processVO);
		return processVO;
	}
	
	public void deleteProcess(Integer processNo) {
		dao.delete(processNo);
	}
	
	public ProcessVO getByProcessNo(Integer processNo) {
		return dao.findByProcessNo(processNo);
	}
	
	public List<ProcessVO> getByUserID(Integer userID){
		return dao.findBuUserID(userID);
	}
	
	public List<ProcessVO> getByListID(Integer listID){
		return dao.findByListID(listID);
	}
	
	public List<ProcessVO> getAll(){
		return dao.getAll();
	}
	
	public ProcessVO getByActNo(Integer actNo) {
		return dao.findByActNo(actNo);
	}
}
