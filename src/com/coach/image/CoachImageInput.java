package com.coach.image;
import java.io.*;
import java.sql.*;

public class CoachImageInput {
	
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/CloudGYM?serverTimezone=Asia/Taipei";
	private static final String userid = "David"; //這邊記得改
	private static final String passwd = "123456"; //這邊記得改

	private static final String UPDATE_STMT = "UPDATE coach SET coachImg=? WHERE userID=?";
											 // 指令也要記得改
	
	public static byte[] getVideoByte(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	public static void main(String[] args) throws IOException {
		Connection con = null;
		PreparedStatement pstmt = null;
				
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			byte[] video = getVideoByte("C:\\TFA103_CloudGYM\\web_CloudGYM\\img\\coach2.jfif");
										//這邊自己改一下
			
			pstmt.setBytes(1, video);
			pstmt.setInt(2, 2005);
			int i = pstmt.executeUpdate();
			if(i != 0) {
				System.out.println("DONE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
