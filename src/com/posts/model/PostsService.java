package com.posts.model;

import java.sql.Timestamp;
import java.util.List;

public class PostsService {

	private PostsDAO_interface dao;

	public PostsService() {
		dao = new PostsJDBCDAO();
	}

	public PostsVO addPosts(Integer userID, String postsTitle, String postsContent, byte[] postsImg,
			Timestamp postsPublishDate, Integer tagID) {
		PostsVO postsVO = new PostsVO();
		postsVO.setUserID(userID);
		postsVO.setPostsTitle(postsTitle);
		postsVO.setPostsContent(postsContent);
		postsVO.setPostsImg(postsImg);
		postsVO.setPostsPublishDate(postsPublishDate);
		postsVO.setTagID(tagID);
		dao.insert(postsVO);
		return postsVO;
	}

	public PostsVO updatePosts(String postsTitle, String postsContent, byte[] postsImg, Integer tagID,
			Integer postsID) {
		PostsVO postsVO = new PostsVO();
		postsVO.setPostsTitle(postsTitle);
		postsVO.setPostsContent(postsContent);
		postsVO.setPostsImg(postsImg);
		postsVO.setTagID(tagID);
		postsVO.setPostsID(postsID);
		dao.update(postsVO);
		return postsVO;
	}

	public PostsVO updatePosts(PostsVO postsVO) {
		dao.update(postsVO);
		return postsVO;
	}

	public void deletePosts(Integer postsID) {
		dao.delete(postsID);
	}

	public PostsVO getByPostsID(Integer postsID) {
		return dao.findByPrimaryKey(postsID);
	}

	public List<PostsVO> getTopPost() {
		return dao.findByTopPost();
	}

	public List<PostsVO> getMorePost() {
		return dao.findMore();
	}

	public List<PostsVO> getAll() {
		return dao.findAll();
	}

	public List<PostsVO> getAll2() {
		return dao.findAll2();
	}

	public List<PostsVO> search(String str) {
		return dao.findKeyword(str);
	}
	
	public void updatePostsReportedTimes(Integer postsReportedTimes, Integer postsID) {
		dao.updatePostsReportedTimes(postsReportedTimes, postsID);
	}
}