package com.posts.model;
 
import java.util.List;

public interface PostsDAO_interface {
	public void insert(PostsVO postsVO);
	public void update(PostsVO postsVO);
	public void delete(Integer postsID);
	public PostsVO findByPrimaryKey(Integer postsID);
	public List<PostsVO> findByTopPost();
	public List<PostsVO> findMore();
	public List<PostsVO> findAll();
	public List<PostsVO> findAll2();
	public List<PostsVO> findKeyword(String str);
	public void updatePostsReportedTimes(Integer postsReportedTimes, Integer postsID);
}
