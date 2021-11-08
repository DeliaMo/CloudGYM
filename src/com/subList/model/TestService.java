package com.subList.model;

import java.util.List;

public class TestService {

	
	public static void main(String[] args) {
		SubListService svc = new SubListService();
		System.out.println(svc);
		List<SubListVO> xxx = svc.getAll();
		System.out.println(xxx);
	}
	
}
