<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.posts.model.*"%>
<%@ page import="redis.clients.jedis.Jedis" %>

<jsp:useBean id="userSvc" scope="page"
	class="com.user.model.UserService" />
<jsp:useBean id="likesSvc" scope="page"
	class="com.likes.model.LikesService" />
<jsp:useBean id="commentSvc" scope="page"
	class="com.comment.model.CommentService" />
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" />
<%
	PostsService postssvc = new PostsService();
	List<PostsVO> list = postssvc.getAll();
	pageContext.setAttribute("list", list);
	List<PostsVO> top = postssvc.getTopPost();
	pageContext.setAttribute("top", top);

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	
	Jedis jedis = new Jedis("localhost", 6379);
	pageContext.setAttribute("jedis", jedis);

	String userID = null;
	try{
		userID = session.getAttribute("userID").toString();
	}catch(Exception e){
		userID = null;
	}
	
	long cartCount = 0;
	try{
		cartCount = jedis.hlen(userID);
	}catch(Exception e){
		cartCount = 0;
	}
			
	pageContext.setAttribute("cartCount", cartCount);
%>

<!DOCTYPE html>
<html lang="ZH-TW">
<head>
<style>
div#title ul{
	padding-left: 0;
}

div.sub ul{
	padding-left: 0;
}

#menu {
	margin: auto;
	float: right;
}

.main {
	background-color:#5C37A1;
	color: white;
	font-size: 14px;
	cursor: pointer;
	text-align: center;
	height: 40px;
	line-height: 40px;
	width: 70px;
}

.main a {
	color: #fff;
	text-decoration: none;
	line-height: 40px;
	margin: auto;
}

#logout{
	width: 100%;
}

.url {
	margin: auto;
}

.main:hover {
	background-color: #4d4949;
}

.sub {
	position:fixed;
	cursor: pointer;
	background-color: #4d4949;
	color: white;
	font-size: 12px;
	font-family: 微軟正黑體;
	text-align: center;
	width: 70px;
}

.sub li {
	list-style-type: none;
	line-height: 40px;
}

.sub a {
	list-style-type: none;
	line-height: 40px;
	margin: auto;
}

.item {
	float: left;
}

#menu li {
	list-style-type: none;
}

/****************** bar css end ******************/
</style>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CloudGYM討論區</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ArticleList.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
</head>
<body>
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
		$("#menu").css("width", $(".main").length * 200)
		$(document).ready(function() {
			//  一進入畫面時收合選單
			$(".sub").slideUp(0);

			for (i = 0; i < $(".main").length; i++) {
				//  點選按扭時收合或展開選單
				$(".main:eq(" + i + ")").on("click", {
					id : i
				}, function(e) {
					n = e.data.id
					$(".sub:eq(" + n + ")").slideToggle()
					$(".sub:not(:eq(" + n + "))").slideUp()
				})
				$(".main:eq(" + i + ")").on("mouseleave", {
					id : i
				}, function(e) {
					n = e.data.id
					$(".sub").stop();
				})
			}
		})
	</script>

	<!-- bar begining -->

	<!-- header -->
	<div id="bar">
        <div id="title">
            <ul>
                <li class="bar_li">
                    <img src="<%=request.getContextPath()%>/img/logo.png" alt="" for="#CloudGYM">
                </li>
                <li class="bar_li">
                    <a href="<%=request.getContextPath()%>/main_page.jsp" id="CloudGYM">CloudGYM</a>
                </li>
            </ul>
        </div>
        <div id="option">
            <ul>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/video/all_video_page.jsp">運動類型</a></li>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/coach/all_coach_page.jsp">教練</a></li>
				<li class="option"><c:if test="${not empty userID}">
						<a
							href="<%=request.getContextPath()%>/html/user/protected_user/userMainPage.jsp?userID=${userID}">個人專區</a>
					</c:if> <c:if test="${ empty userID}">個人專區</c:if></li>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/article/ArticleList.jsp">討論區</a></li>
				<c:if test="${empty userID}">
					<div class="item">
						<div class="main">註冊/登入</div>
						<div class="sub">
							<ul>
								<li><a
									href="${pageContext.request.contextPath}/html/login/sign_up_page.jsp">會員註冊</a></li>
								<li><a
									href="${pageContext.request.contextPath}/html/login/login_user.jsp">會員登入</a></li>
								<li><a
									href="${pageContext.request.contextPath}/html/login/login_coach.jsp">教練登入</a></li>
								<li><a
									href="${pageContext.request.contextPath}/html/login/login_admin.jsp"
									target="_blank">後台管理</a></li>
							</ul>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty userID}">
				<div class="item">
					<div class="main" id="logout"><a href="javascript:if (confirm('確認登出?')) location.href='<%=request.getContextPath()%>/LogoutHandler'">${name} 登出</a></div>
						<div class="sub"></div>
					</div>
				</c:if>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/order/pay_page.jsp">
						<i class="bi bi-cart-fill"> <c:if test="${hlen != 0}">
								<span>${hlen}</span>
							</c:if> <c:if test="${hlen == 0}">
								<span>${cartCount}</span>
							</c:if> <span>${cartCount}</span>
					</i>
				</a></li>
			</ul>
        </div>
    </div>
    <!-- header_end -->

	<div class="navimg">
		<img src="<%=request.getContextPath()%>/img/forum_navimg.png">
	</div>

	<div class="menu-wrapper">
		<nav class="main-nav grid-container grid-parent">
			<ul class="menu">
				<li><a href="<%=request.getContextPath()%>/html/article/ArticleList.jsp">首頁</a></li>
				<li><a href="<%=request.getContextPath()%>/html/article/ArticleList_A.jsp">健身知識</a></li>
				<li><a href="<%=request.getContextPath()%>/html/article/ArticleList_B.jsp">健康飲食</a></li>
				<li><a href="<%=request.getContextPath()%>/html/article/ArticleList_C.jsp">成果分享</a></li>
				<li><a href="<%=request.getContextPath()%>/html/article/ArticleList_D.jsp">商品推薦</a></li>
				<li><a href="<%=request.getContextPath()%>/html/article/ArticleList_E.jsp">綜合閒聊</a></li>
			</ul>
		</nav>
	</div>


	<!-- main -->
	<div id="addarticle">
		<div class="container">
			<div class="row">
				<div class="col">
					<a href="<%=request.getContextPath()%>/html/article/AddArticle.jsp">
						<button type="button" class="btn btn-outline-light">
							<i class="far fa-edit me-2"></i>新增文章
						</button>
					</a>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">

			<div class="col-8">

				<c:forEach var="PostsVO" items="${list}">
					<c:if test="${PostsVO.tagID == 10}">
						<div class="card mb-3">
							<div class="row g-0">
								<div class="col-md-4">
									<img
										src="<%=request.getContextPath()%>/Reader?id=${PostsVO.postsID}"
										alt="...">
								</div>
								<div class="col-md-8">
									<div class="card-body ms-3">
										<h5 class="card-title">${PostsVO.postsTitle}</h5>
										<small class="text-muted">作者：${userSvc.findByUserId(PostsVO.userID).userName}${coachSvc.getByUserID(PostsVO.userID).coachName}</small>
										<p class="card-text">${PostsVO.postsContent}</p>
										<div class="cb">
											<a class="btn btn-outline-primary"
												href="<%=request.getContextPath()%>/html/article/ArticlePage.jsp?postsID=${PostsVO.postsID}">繼續閱讀</a>
											<i class="far fa-thumbs-up"><span>${likesSvc.getCountLike(PostsVO.postsID)}</span></i>
											<i class="far fa-comment-alt"><span>${commentSvc.getCountComment(PostsVO.postsID)}</span></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<hr>
					</c:if>
				</c:forEach>

			</div>


			<!-- 右側區塊 -->
			<div class="col-4">

				<!-- 搜尋文章-->
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-search me-2"></i>搜尋文章
					</div>
					<form METHOD="post" ACTION="Article.do">
						<div class="card-body">
							<div class="input-group">
								<input class="form-control" type="text" placeholder="請輸入關鍵字" aria-describedby="button-search" name="str" autocomplete="off" />
								<button class="btn btn-outline-dark" id="button-search" type="submit" name="action" value="search">搜尋</button>
							</div>
						</div>
					</form>
				</div>
				<!-- 熱門文章 -->
				<div class="card mb-4">
					<div class="card-header">
						<i class="fab fa-hotjar me-2"></i>熱門文章
					</div>
					<ol class="recent-posts">
						<c:forEach var="PostsVO" items="${top}" begin="0" end="4">
							<li><a
								href="<%=request.getContextPath()%>/html/article/ArticlePage.jsp?postsID=${PostsVO.postsID}">${PostsVO.postsTitle}</a></li>
						</c:forEach>
					</ol>
				</div>
				<!-- 最新文章 -->
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-rss me-2"></i>最新文章
					</div>
					<ol class="recent-posts">
						<c:forEach var="PostsVO" items="${list}" begin="0" end="4">
							<li><a
								href="<%=request.getContextPath()%>/html/article/ArticlePage.jsp?postsID=${PostsVO.postsID}">${PostsVO.postsTitle}</a></li>
						</c:forEach>
					</ol>
				</div>
			</div>
			<!-- 右側區塊_end -->

		</div>
	</div>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
		crossorigin="anonymous"></script>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		
	<script>
	    var cartCount = ${cartCount};
	    if(cartCount == 0){
	    	$("i.bi-cart-fill span").addClass("-on");
	    	$("i.bi-cart-fill span").attr("style", "display:none");
	    }else{
	    	$("i.bi-cart-fill span").removeClass("-on");
	    	$("i.bi-cart-fill span").attr("style", "");
	    }
	</script>

</body>

</html>