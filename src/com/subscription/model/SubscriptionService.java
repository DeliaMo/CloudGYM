package com.subscription.model;

import java.util.List;

public class SubscriptionService {
	
	private SubscriptionDAO_interface dao;
	
	public SubscriptionService() {
		dao = new SubscriptionJDBCDAO();
	}
	
	public SubscriptionVO addSubscription(Integer subNo,Integer subID,Integer userID) {
		SubscriptionVO subscriptionVO = new SubscriptionVO();
		
		subscriptionVO.setSubNo(subNo);
		subscriptionVO.setSubID(subID);
		subscriptionVO.setUserID(userID);
		dao.insert(subscriptionVO);
		
		return subscriptionVO;
	}

	
	public SubscriptionVO updateSubscription(Integer subNo,Integer subID,Integer userID) {
		SubscriptionVO subscriptionVO = new SubscriptionVO();
		
		subscriptionVO.setSubNo(subNo);
		subscriptionVO.setSubID(subID);
		subscriptionVO.setUserID(userID);
		
		dao.update(subscriptionVO);
		
		return subscriptionVO;
	}
	
	
	public void deleteSubscription(Integer subNo) {
		dao.delete(subNo);
	}
	
	public SubscriptionVO getBySubNo(Integer subNo) {
		return dao.findBySubNo(subNo);
	}
	
	public List<SubscriptionVO> getByUserID(Integer userID){
		return dao.findByUserID(userID);
	}
	
	public List<SubscriptionVO> getAll() {
		return dao.findAll();
	}
}
