package com.subscription.model;

import java.util.List;

public interface SubscriptionDAO_interface {
	public void insert(SubscriptionVO subscriptionVO);
	public void update(SubscriptionVO subscriptionVO);
	public void delete(Integer subNo);
	public SubscriptionVO findBySubNo(Integer subNo);
	public List<SubscriptionVO> findByUserID(Integer userID);
	public List<SubscriptionVO> findAll();
}
