package com.admin.model;

import java.util.List;

public class AdminService {

	private AdminDAO_interface dao;
	
	public AdminService() {
		dao = new AdminJDBCDAO();
	}
	
//	public AdminVO updatAdmin(Integer adminID,String adminName,String adminPW,
//			Integer commentAuth,Integer videoAuth,Integer subAuth,Integer userAuth) {
//		
//		AdminVO adminVO = new AdminVO();
//		adminVO.setAdminID(adminID);
//		adminVO.setAdminName(adminName);
//		adminVO.setAdminPW(adminPW);
//		adminVO.setCommentAuth(commentAuth);
//		adminVO.setVideoAuth(videoAuth);
//		adminVO.setSubAuth(subAuth);
//		adminVO.setUserAuth(userAuth);
//		dao.update(adminVO);
//		return adminVO;
//	}
	
	public AdminVO updatAdmin(AdminVO adminVO) {
		dao.update(adminVO);
		return adminVO;
	}
	
	public AdminVO getOneAdmin(Integer adminID) {
		return dao.findbyAdminID(adminID);
	}
	
	public List<AdminVO> getAll(){
		return dao.findAll();
	}
}
