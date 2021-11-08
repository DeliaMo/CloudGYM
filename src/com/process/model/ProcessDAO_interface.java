package com.process.model;

import java.util.List;

public interface ProcessDAO_interface {
	public void insert(ProcessVO processVO);
	public void update(ProcessVO processVO);
	public void delete(Integer processNo);
	public ProcessVO findByProcessNo(Integer processNo);
	public List<ProcessVO> findBuUserID(Integer userID);
	public List<ProcessVO> findByListID(Integer listID);
	public ProcessVO findByActNo(Integer actNo);
	public List<ProcessVO> getAll();
}
