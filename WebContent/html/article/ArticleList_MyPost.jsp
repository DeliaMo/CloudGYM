<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.posts.model.*"%>
<%@ page import="redis.clients.jedis.Jedis"%>

<jsp:useBean id="postsSvc" scope="page"
	class="com.posts.model.PostsService" />
<jsp:useBean id="userSvc" scope="page"
	class="com.user.model.UserService" />
<jsp:useBean id="likesSvc" scope="page"
	class="com.likes.model.LikesService" />
<jsp:useBean id="commentSvc" scope="page"
	class="com.comment.model.CommentService" />
<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />

<%
	List<PostsVO> list = postsSvc.getAll();
	pageContext.setAttribute("list", list);

	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	Jedis jedis = new Jedis("localhost", 6379);
	pageContext.setAttribute("jedis", jedis);

	String userID = null;
	try {
		userID = session.getAttribute("userID").toString();
	} catch (Exception e) {
		userID = null;
	}

	long cartCount = 0;
	try {
		cartCount = jedis.hlen(userID);
	} catch (Exception e) {
		cartCount = 0;
	}

	pageContext.setAttribute("cartCount", cartCount);
%>

<!DOCTYPE html>
<html lang="ZH-TW">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>我的文章</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
	crossorigin="anonymous"></script>

<style>
body {
	/*     color: white; */
	background-color: #31105E;
}

#bar {
	height: 40px;
	background-color: #5C37A1;
	overflow: hidden;
	position: fixed;
	width: 100%;
	z-index: 10;
}

#title {
	float: left;
	margin-left: 20px;
	overflow: hidden;
}

img {
	width: 35px;
	margin-top: 3px;
}

.bar_li {
	float: left;
}

a {
	color: #fff;
	text-decoration: none;
	line-height: 40px;
}

a#CloudGYM {
	margin-left: 10px;
}

i.bi-cart-fill span.-on {
	display: none;
}

#option {
	float: right;
	overflow: hidden;
}

#option ul {
	margin-right: 20px;
}

.option {
	float: left;
	color: white;
	line-height: 40px;
	margin: 0 10px;
	font-size: 14px;
	position: relative;
}

.bi {
	margin-top: 7px;
}

/***************************以上複製貼上****************************/

/* main */
div.main_div{
	padding-top: 100px;
}

.container {
	background-color: #ECE9F2;
	border-radius: 25px;
	margin: 0 auto;
}

.container h1 {
	padding-top: 30px;
	margin-left: 30px;
}

.col-md-4 {
	width: 280px;
	height: 210px;
}

.col-md-4 img {
	width: 100%;
	height: 100%;
}

.card-title {
	font-weight: bold;
}

.card-text {
	display: -webkit-box;
	/*值必須為-webkit-box或者-webkit-inline-box*/
	-webkit-box-orient: vertical;
	/*值必須為vertical*/
	-webkit-line-clamp: 3;
	/*值為數字，表示一共顯示幾行*/
	overflow: hidden;
}

/***************************以下複製貼上****************************/
i.-on {
	display: none;
}

i.bi-cart-fill {
	font-size: 25px;
	position: absolute;
	top: -6px;
	left: -6px;
}

i.bi-cart-fill span {
	border-radius: 50%;
	width: 15px;
	height: 15px;
	display: flex;
	justify-content: center;
	align-items: center;
	background-color: red;
	color: #ffffff;
	font-size: 8px;
	position: absolute;
	top: 0;
	right: -3px;
	font-family: arial;
}

i.bi:hover {
	color: #bebebe;
}

#menu {
	margin: auto;
	float: right;
}

.main {
	background-color: #5C37A1;
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

#logout {
	width: 100%;
}

.url {
	margin: auto;
}

.main:hover {
	background-color: #4d4949;
}

.sub {
	position: fixed;
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
</style>

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
	<!-- Navbar -->
	<div id="bar">
        <div id="title">
            <ul>
                <li class="bar_li">
                    <img src="<%=request.getContextPath()%>/img/logo.png" alt="" for="#CloudGYM">
                </li>
                <li class="bar_li">
                    <a href="#" id="CloudGYM">CloudGYM</a>
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


	<!-- main -->
	<div class="main_div">
		<div class="container">
			<h1>我的文章</h1>
			<hr>
			<div class="row">
				<div class="col">

					<c:forEach var="PostsVO" items="${list}">
						<c:if test="${PostsVO.userID == userID}">
							<div class="card mb-3">
								<div class="d-flex position-relative">

									<div class="col-md-4">
										<img
											src="<%=request.getContextPath()%>/Reader?id=${PostsVO.postsID}">
									</div>

									<div class="ms-4 mt-3">
										<h5 class="card-title">${PostsVO.postsTitle}</h5>
										<small class="text-muted">作者：${userSvc.findByUserId(PostsVO.userID).userName}${coachSvc.getByUserID(PostsVO.userID).coachName}</small>
										<p class="card-text">${PostsVO.postsContent}</p>
										<a href="#" class="btn btn-outline-primary"
											href="<%=request.getContextPath()%>/html/ArticlePage.jsp?postsID=${PostsVO.postsID}">繼續閱讀</a>
										<i class="far fa-thumbs-up"
											style="margin-left: 750px; font-size: 20px;"> <span
											class="mx-2">${likesSvc.getCountLike(PostsVO.postsID)}</span></i>
										<i class="far fa-comment-alt" style="font-size: 20px;"> <span
											class="mx-2">${commentSvc.getCountComment(PostsVO.postsID)}</span></i>
									</div>
								</div>
							</div>
							<hr>
						</c:if>
					</c:forEach>

				</div>
			</div>
		</div>
	</div>
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