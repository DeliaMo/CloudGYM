package com.thePosition.model;

import java.util.List;

public class ThePositionService {
	private ThePositionDAO_interface dao;
	
	public ThePositionService() {
		dao = new ThePositionJDBCDAO();
	}
	
	public ThePositionVO addPosition(String positionName) {
		ThePositionVO thePositionVO = new ThePositionVO();
		
		thePositionVO.setPositionName(positionName);
		dao.insert(thePositionVO);
		return thePositionVO;
	}
	
	public ThePositionVO updatePosition(String positionName, Integer positionNo) {
		ThePositionVO thePositionVO = new ThePositionVO();
		
		thePositionVO.setPositionNo(positionNo);
		thePositionVO.setPositionName(positionName);
		dao.update(thePositionVO);
		return thePositionVO;
	}
	
	public void deletePosition(Integer positionNo) {
		dao.delete(positionNo);
	}
	
	public ThePositionVO getOnePosition(Integer positionNo) {
		return dao.getOnePosition(positionNo);
	}
	
	public List<ThePositionVO> getAll(){
		return dao.getAll();
	}
}
