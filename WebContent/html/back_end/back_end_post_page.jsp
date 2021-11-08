<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.posts.model.*"%>
<%@ page import="com.comment.model.*"%>

<jsp:useBean id="userSvc" scope="page"	class="com.user.model.UserService" />
<jsp:useBean id="coachSvc" scope="page"	class="com.coach.model.CoachService" />
<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache");        //HTTP 1.0
  response.setDateHeader ("Expires", 0);
%>

<%
	PostsService postSvc = new PostsService();
	PostsVO postsVO = postSvc.getByPostsID(Integer.parseInt(request.getParameter("postID")));
	pageContext.setAttribute("postsVO", postsVO);

	CommentService commentSvc = new CommentService();
	List<CommentVO> list = commentSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>使用者文章</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_post_page.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
</head>
<body>
<!-- 	<div id="bar"> -->
<!-- 		<div id="title"> -->
<!-- 			<ul> -->
<%-- 				<li class="bar_li"><img src="<%=request.getContextPath()%>/img/logo.png" alt="" --%>
<!-- 					for="#CloudGYM"></li> -->
<%-- 				<li class="bar_li"><a href="<%=request.getContextPath()%>/main_page.jsp" id="CloudGYM">CloudGYM</a></li> --%>
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 		<div id="option"> -->
<!-- 			<ul> -->
<!-- 				<li class="option"><a class="logout" href="#">登出</a></li> -->
<!-- 				<li class="option"><a class="login_ad" -->
<%-- 					href="<%=request.getContextPath()%>/html/back_end_Admin.jsp">管理員</a></li> --%>
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<%@ include file="/html/back_end/bar_admin.file" %>
	<div id="wrap">
		<div id="left"></div>
		<a href="<%=request.getContextPath()%>/html/back_end/back_end_post.jsp">
		<p>返回文章列表</p>
		</a>
		<div id="right">
			<div class="main">
				<div class="main_right">
					<span>所有留言:</span>
					<div class="comment">
						<c:forEach var="commentVO" items="${list}">
								<c:if test="${commentVO.postsID == postsVO.postsID}">
									<ul class="com_ul">
										<span>${commentVO.userID} -
											${userSvc.findByUserId(commentVO.userID).userName}
											${coachSvc.getByUserID(commentVO.userID).coachName}</span>
										<form method="post" action="<%=request.getContextPath()%>/html/comment.do">
											<input type="hidden" name="commentid" value="${commentVO.commentID}">
											<button type="submit" name="action" value="delete">
												<i class="bi bi-trash-fill"></i>
											</button>
										</form>
										<li class="comment_li">${commentVO.commentContent}</li>
										<li class="time">${commentVO.commentPublishDate}</li>
									</ul>
								</c:if>
						</c:forEach>

					</div>
				</div>
				<div class="post">
					${postsVO.postsID}<br>${postsVO.postsTitle}</div>
					<div class="post_text">文章內容:</div>
					<div class="postshow">
				<form method="post" action="<%=request.getContextPath()%>/html/Article.do">
						<input type="radio" name="postshow"
							${postsVO.postsShow == true ? "checked='true'" : ""} disabled>公開 
						<input type="radio" name="postshow"
							${postsVO.postsShow == false ? "checked='true'" : ""} disabled>不公開
						<input type="hidden" name="action" value="delete"> 
						<input type="hidden" name="postsid" value="${postsVO.postsID}"> 
						<input type="submit" value="文章異常">
						
				</form>
					</div>
				<textarea id="content">${postsVO.postsContent}</textarea>
				<a
					href="<%=request.getContextPath()%>/html/PostsImageOutput?postsID=${postsVO.postsID}">
					<i class="bi bi-images"></i>
				</a>
				<div class="up_name">
					上傳者: &nbsp&nbsp<span>${postsVO.userID} -
						${userSvc.findByUserId(postsVO.userID).userName}
						${coachSvc.getByUserID(postsVO.userID).coachName}</span>
				</div>
				<div class="up_time">
					上傳時間: <span>${postsVO.postsPublishDate}</span>
				</div>
			</div>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/back_end_post_page.js"></script>
</body>
</html>