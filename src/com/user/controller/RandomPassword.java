package com.user.controller;

import java.util.Random;

public class RandomPassword {
	
	public static String getRandom(int length) {
		StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i ++) {
		sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
		}
	
	public static void main(String[] args) {
		RandomPassword test = new RandomPassword();
		System.out.println(test.getRandom(8));
		
	}
}
