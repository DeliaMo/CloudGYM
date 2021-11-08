package com.coach.model;

import java.util.List;

public interface CoachDAO_interface {
	public void insert(CoachVO coachVO);
	public void update(CoachVO coachVO);
	public void delete(Integer userID);
	public CoachVO findByUserID(Integer userID);
	public CoachVO findByCoachAccount(String coachAccount);
	public List<CoachVO>findAll();
}
