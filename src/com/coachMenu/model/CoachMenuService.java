package com.coachMenu.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class CoachMenuService {
	private CoachMenuDAO_interface dao;
	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String str = sd.format(new Date(System.currentTimeMillis()));
	Timestamp ts = Timestamp.valueOf(str);
	
	public CoachMenuService() {
		dao = new CoachMenuJDBCDAO();
	}
	
	public CoachMenuVO addCoachMenu(Integer userID, String menuName, Integer price, Boolean isPublic, Integer positionNo) {
		CoachMenuVO coachMenuVO = new CoachMenuVO();
		
		coachMenuVO.setUserID(userID);
		coachMenuVO.setMenuName(menuName);
		coachMenuVO.setPrice(price);
		coachMenuVO.setIsPublic(isPublic);
		coachMenuVO.setPositionNo(positionNo);
		Integer menuID = dao.insert(coachMenuVO);
		coachMenuVO.setMenuID(menuID);
		return coachMenuVO;
	}
	
//	public CoachMenuVO updateCoachMenu(Integer menuID, String menuName, Integer price, Integer positionNo){
//		
//		CoachMenuVO coachMenuVO = new CoachMenuVO();
//		
//		coachMenuVO.setUserID(menuID);
//		coachMenuVO.setMenuName(menuName);
//		coachMenuVO.setPrice(price);
//		coachMenuVO.setPositionNo(positionNo);
//		dao.update(coachMenuVO);
//		return coachMenuVO;
//	}
	
	public CoachMenuVO updateCoachMenu(CoachMenuVO coachMenuVO) {
		dao.update(coachMenuVO);
		return coachMenuVO;
	}
	
	public void deleteCoachMenu(Integer menuID) {
		dao.delete(menuID);
	}
	
	public CoachMenuVO getByMenuID(Integer menuID) {
		return dao.findByMenuID(menuID);
	}
	
	public List<CoachMenuVO> getByUserID(Integer userID){
		return dao.findByUserID(userID);
	}
	
	public List<CoachMenuVO> getAll(){
		return dao.findAll();
	}
	
	public List<CoachMenuVO> findMenuIDByUserID(Integer userID) {
		return dao.findMenuIDByUserID(userID);
	}
}
