package com.subList.model;

import java.util.List;

public class SubListService {
	
	private SubListDAO_interface dao;
	
	public SubListService() {
		dao = new SubListJDBCDAO();
	}
	
	public SubListVO addSubList(Integer subID,String duration,String subName,Integer price) {
		SubListVO subListVO = new SubListVO();
		
		subListVO.setSubID(subID);
		subListVO.setDuration(duration);
		subListVO.setSubName(subName);
		subListVO.setPrice(price);
		dao.insert(subListVO);
		
		return subListVO;
	}
	
	
	public SubListVO updateSubList(Integer subID,String duration,String subName,Integer price) {
		SubListVO subListVO = new SubListVO();
		
		subListVO.setSubID(subID);
		subListVO.setDuration(duration);
		subListVO.setSubName(subName);
		subListVO.setPrice(price);
		dao.update(subListVO);
		
		return subListVO;
	}
	
	public void deleteSubList(Integer subID) {
		dao.delete(subID);
	}
	
	public SubListVO getBySubID(Integer subID) {
		return dao.findBySubID(subID);
	}
	
	public List<SubListVO> getAll() {
		return dao.findAll();
	}
}
