package com.coachMenuList.model;

import java.util.List;

public class CoachMenuListService {
	private CoachMenuListDAO_interface dao;

	public CoachMenuListService() {
		dao = new CoachMenuListJDBCDAO();
	}

	public CoachMenuListVO addCoachMenuList(Integer menuID, Integer videoID) {
		CoachMenuListVO coachMenuListVO = new CoachMenuListVO();

		coachMenuListVO.setMenuID(menuID);
		coachMenuListVO.setVideoID(videoID);
		dao.insert(coachMenuListVO);
		return coachMenuListVO;
	}

	public CoachMenuListVO updateCoachMenuList(Integer menuNo,Integer videoID) {
		CoachMenuListVO coachMenuListVO = new CoachMenuListVO();

//		coachMenuListVO.setMenuID(menuID);
		coachMenuListVO.setMenuNo(menuNo);
		coachMenuListVO.setVideoID(videoID);

		dao.update(coachMenuListVO);
		return coachMenuListVO;
	}
	
	public void deleteCoachMenuList(Integer menuNo) {
		dao.delete(menuNo);
	}
	
	public CoachMenuListVO getByMenuNo(Integer menuNo) {
		return dao.findByMenuNo(menuNo);
	}
	
	public List<CoachMenuListVO> getByMenuID(Integer menuID){
		return dao.findByMenuID(menuID);
	}
	
	public List<CoachMenuListVO> getAll(){
		return dao.findAll();
	}
}
