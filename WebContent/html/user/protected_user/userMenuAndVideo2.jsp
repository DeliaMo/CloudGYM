<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="redis.clients.jedis.Jedis"%>

<jsp:useBean id="videoSvc" scope="page"
	class="com.video.model.VideoService" />
<jsp:useBean id="menuSvc" scope="page"
	class="com.customMenu.model.CustomMenuService" />
<jsp:useBean id="listSvc" scope="page"
	class="com.customMenuList.model.CustomMenuListService" />
<jsp:useBean id="rightsSvc" scope="page"
	class="com.userRights.model.UserRightsService" />

<%
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
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>個人已購買菜單及影片</title>
<link rel="stylesheet" href="./css/style.css">
<!-- bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
	crossorigin="anonymous"></script>

<style>
* {
	margin: 0;
}

li {
	list-style-type: none;
}

body {
	background-color: #31105E !important;
}

.page {
	overflow: hidden;
	padding-top: 70px;
	padding-bottom: 100px;
}

.first-row {
	overflow: hidden;
}

.overview {
	background-color: #DED0F3;
	padding: 15px 15px;
}

.overview p {
	display: inline-block;
	padding-right: 18px;
	margin-top: 25px;
	font-size: 14px;
}

.info {
	text-align: right;
	color: white;
}

.second-row {
	padding: 50px 0 20px;
	height: 370px;
}

.second-row .menu {
	background-color: #DED0F3;
	margin-right: 40px;
	overflow: scroll !important;
	max-height: 300px;
	border-radius: 5px;
}

.second-row .menu h5 {
	text-align: center;
	margin: 20px 0 20px;
}

.second-row .progress {
	background-color: #DED0F3
}

.progress {
	display: inline-block;
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
#option img {
	color: white;
}

.left {
	color: white;
	background-color: #5C37A1;
	height: 1500px;
}

.checklist {
	padding: 80px 50px;
}

.menu {
	padding: 80px 20px;
}

.video {
	padding: 0px 20px;
}

.title {
	color: white;
	font-weigth: bold;
	font-size: 20px;
	text-align: center;
}

.title-video {
	color: white;
	padding-top: 0px;
}

.card-block {
	margin-top: 0;
}

.cards {
	text-align: right;
}

.card-zone {
	margin-right: 10px;
	text-align: left;
}

.form-display {
	display: inline-block;
	float: right;
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

div.item div.main {
	background-color: #5C37A1;
	color: white;
	font-size: 14px;
	cursor: pointer;
	text-align: center;
	height: 40px;
	line-height: 40px;
	width: 70px;
}

div.item div.main a {
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

div.item div.main:hover {
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

/****************** bar css end ******************/
</style>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
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
	<div id="bar">
		<div id="title">
			<ul>
				<li class="bar_li"><img
					src="<%=request.getContextPath()%>/img/logo.png" alt=""
					for="#CloudGYM"></li>
				<li class="bar_li"><a
					href="<%=request.getContextPath()%>/main_page.jsp" id="CloudGYM">CloudGYM</a></li>
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
						<div class="main" id="logout">
							<a
								href="javascript:if (confirm('確認登出?')) location.href='<%=request.getContextPath()%>/LogoutHandler'">${name}
								登出</a>
						</div>
						<div class="sub"></div>
					</div>
				</c:if>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/order/pay_page.jsp"> <i
						class="bi bi-cart-fill"> <c:if test="${hlen != 0}">
								<span>${hlen}</span>
							</c:if> <c:if test="${hlen == 0}">
								<span>${cartCount}</span>
							</c:if> <span>${cartCount}</span>
					</i>
				</a></li>
			</ul>
		</div>
	</div>

	<div class="row main">
		<div class="col-2 left">
			<div class="checklist">
				<p>強度</p>
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="strong"
						id="flexCheckDefault"> <label class="form-check-label"
						for="flexCheckDefault"> 強 </label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="medium"
						id="flexCheckDefault"> <label class="form-check-label"
						for="flexCheckChecked"> 中 </label>
				</div>
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="low"
						id="flexCheckDefault"> <label class="form-check-label"
						for="flexCheckDefault"> 弱 </label>
				</div>
			</div>
		</div>
		<div class="col-10 right">
			<div class="row menu row-cols-1 g-4">
				<p class="title">菜單</p>
				<div class="card-group cards">
					<c:forEach var="customMenuVO" items="${menuSvc.getAll(userID)}">
						<%-- 省略pageScope --%>
						<div class="card card-zone">
							<div class="card-body">
								<h5 class="card-title">${customMenuVO.title}</h5>

								<p class="card-text">
									<small class="text-muted">${customMenuVO.content}</small>
								</p>
								<hr>
								<!-- eachlist -->
								<c:forEach var="customMenuListVO"
									items="${listSvc.getAll(customMenuVO.menuID)}" varStatus="i">

									<p class="card-text">
									<div class="row">
										<div class="col-8">
											${videoSvc.findByPrimaryKey(customMenuListVO.videoID).title}
											<%-- ${customMenuListVO.listID} --%>
											<%-- <c:forEach var="videoVO" items="${videoSvc.findByPrimaryKey(customMenuListVO.videoID)}">
												<small class="text-muted">${videoVO.title}</small>
											</c:forEach>  --%>
										</div>
										<div class="col-4">
											<FORM class="delete_post1" METHOD="post"
												class="form-inline form-display"
												ACTION="<%=request.getContextPath()%>/userMenuAndVideo/userMenu.do">
												<input type="hidden" name="listID"
													value="${customMenuListVO.listID}"> <input
													type="hidden" name="action" value="delete_list">
												<button type="submit"
													class="btn btn-secondary btn-sm delete-btn">刪除</button>
											</FORM>
										</div>
									</div>
									</p>
								</c:forEach>
								<br>
								<FORM class="update_post_menu" METHOD="post" class="form-inline"
									ACTION="<%=request.getContextPath()%>/userMenuAndVideo/userMenu.do"
									style="display: inline-block;">
									<input type="hidden" name="menuID"
										value="${customMenuVO.menuID}"> <input type="hidden"
										name="action" value="update_prepare">
									<button type="submit"
										class="btn btn-outline-secondary btn-sm delete-btn btn-block">修改</button>
								</FORM>


								<FORM class="delete_post_menu" METHOD="post"
									class="form-inline form-display" style="display: inline-block;">
									<input type="hidden" name="menuID"
										value="${customMenuVO.menuID}"> <input type="hidden"
										name="action" value="delete_menu">
									<button type="submit"
										class="btn btn-secondary btn-sm delete-btn btn-block">刪除</button>
								</FORM>
								</p>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="row video">
				<p class="title-video title">影片</p>
				<div class="row row-cols-1 row-cols-md-3 g-4">
					<c:forEach var="userRightsVO" items="${rightsSvc.getAll(userID)}">
						<div class="col">
							<div class="card">
								<a
									href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${userRightsVO.videoID}">
									<img
									src="<%=request.getContextPath()%>/reader?id=${userRightsVO.videoID}"
									class="card-img-top">
								</a>
								<div class="card-body">
									<h5 class="card-title">${videoSvc.findByPrimaryKey(userRightsVO.videoID).title}</h5>
									<p class="card-text">${videoSvc.findByPrimaryKey(userRightsVO.videoID).intro}</p>
								</div>

							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
	<script>
		var cartCount = ${cartCount};
		if (cartCount == 0) {
			$("i.bi-cart-fill span").addClass("-on");
			$("i.bi-cart-fill span").attr("style", "display:none");
		} else {
			$("i.bi-cart-fill span").removeClass("-on");
			$("i.bi-cart-fill span").attr("style", "");
		}
	</script>
</body>
</html>