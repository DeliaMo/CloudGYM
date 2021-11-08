<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.customMenuList.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="redis.clients.jedis.Jedis"%>

<jsp:useBean id="menuSvc" scope="page"
	class="com.customMenu.model.CustomMenuService" />
<jsp:useBean id="listSvc" scope="page"
	class="com.customMenuList.model.CustomMenuListService" />
<jsp:useBean id="videoSvc" scope="page"
	class="com.video.model.VideoService" />
<jsp:useBean id="coachSvc" scope="page"
	class="com.coachMenu.model.CoachMenuService" />
<jsp:useBean id="processSvc" scope="page"
	class="com.process.model.ProcessService" />
<jsp:useBean id="actionSvc" scope="page"
	class="com.videoAction.model.VideoActionService" />

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

<%
	Integer listID = (Integer) request.getAttribute("listID");
	request.setAttribute("listID", listID);
	/* List<CustomMenuListVO> menulist = (List<CustomMenuListVO>) request.getAttribute("menulist");
	System.out.println("jsp" + menulist);
	request.setAttribute("menulist", menulist); */
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>個人專區運動歷程</title>
<link rel="stylesheet" href="./css/style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

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
.content {
	padding: 30px;
	width: 70%;
	background-color: #DED0F3;
	border-radius: 1%;
	display: inline-block;
}

.page {
	padding-top: 60px;
	color: white;
}

.progress-section {
	
}

.seperate {
	border-right: 3px solid white;
}

.img-size {
	width: 100%;
	height: 50vh;
	object-fit: cover;
}

.list-padding {
	padding: 0 !important;
}

.img-fluid {
	width: 100%;
	height: 40vh;
	object-fit: cover;
}

.seperate p {
	text-align: center;
	margin-top: 3%;
}

.right-zone {
	height: 700px;
	overflow-y: scroll;
	scroll-behavior: smooth;
	background-color: #DED0F3;
	color: black !important;
}

.right-zone button {
	border-radius: 50%;
	margin-left: 5%;
	background-color: none;
	border: none;
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

.return {
	position: absolute;
	bottom: 50px;
	left: 150px;
}

.return:link, .return:visited {
	background-color: none;
	border: 1px solid white;
	color: white;
	padding: 3px 12px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	border-radius: 5.5%;
}

.return:hover, .return:active {
	background-color: white;
	color: #31105E;
}

.listeffect:hover, .listeffect:active {
	background-color: white;
	color: #31105E !important;
}

.active {
	background-color: white !important;
	color: #31105E !important;
}
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
				<li class="bar_li"><img src="./img/logo.png" alt=""
					for="#CloudGYM"></li>
				<li class="bar_li"><a href="#" id="CloudGYM">CloudGYM</a></li>
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

	<div class="container page">
		<div class="row">
			<div class="col-3 seperate">
				<p style="font-weight: bold;">${menuTitle}</p>
				<ul class="nav flex-column nav-pills nav-fills">
					<c:forEach var="list" items="${menulist}">
						<li class="nav-item"><a
							class="nav-link listeffect ${list.listID == listID ? "
							active": "" }" aria-current="page"
							href="userMenuList.do?action=playVideo&id=${list.videoID}&menuTitle=${menuTitle}&listID=${list.listID}"
							style="color: white;">${videoSvc.findByPrimaryKey(list.videoID).title}</a>
						</li>
					</c:forEach>
				</ul>
				<a class="return"
					href="<%=request.getContextPath()%>/html/user/protected_user/userMenuListPage1.jsp">返回菜單總覽</a>
			</div>
			<div class="col-6 seperate">
				<div>
					<div>
						<video class="img-size" controls>
							<source
								src="<%=request.getContextPath()%>/VideoReader?id=${play.videoID}">
						</video>
					</div>
					<div class="progress-section">
						<div style="border-bottom: 1px solid white;">
							<p>${play.title}</p>
						</div>
						<p>${play.intro}</p>
					</div>
				</div>
			</div>
			<div class="col-3 right-zone">
				<br>
				<c:if test="${fn:length(actions) > 0}">
					<c:forEach var="actions" items="${actions}" begin="0" end="${actions.size()-1}" varStatus="inputno">
						<ul class="buttontest">
							<li style="margin-bottom: 7px;">${actions.action}</li>
							<li><c:forEach begin="0" end="${actions.sets-1}" varStatus="no">
									<input
										value="${processSvc.getByListID(listID).get(inputno.index).processNo}"
										id="${no.count}${processSvc.getByActNo(actions.actNo).processNo}"
										type="checkbox" style="width: 0; height: 0;">
									<label
										for="${no.count}${processSvc.getByActNo(actions.actNo).processNo}">
										<i style="font-size: 33px; margin-right: 5px;" class="far fa-check-circle"></i>
									</label>
									</input>
								</c:forEach></li>
						</ul>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
	<script>
		var cartCount = $
		{
			cartCount
		};
		if (cartCount == 0) {
			$("i.bi-cart-fill span").addClass("-on");
			$("i.bi-cart-fill span").attr("style", "display:none");
		} else {
			$("i.bi-cart-fill span").removeClass("-on");
			$("i.bi-cart-fill span").attr("style", "");
		}
	</script>
	<script>
	var listID = ${listID}
	console.log(listID);
	function init() {
		$.ajax({
			url : "userMenuList.do?action=init",
			dataType : "json",
			type : "POST",
			data : { 
				"listID" : listID
			},
				
			success : function(data) {
				var setsarr = [];
				console.log(data);
				for (var i = 0; i < data.length; i++) {
					setsarr.push(data[i].sets);
 					$(".buttontest").eq(i).find("li").find("label").children("i").slice(0, data[i].sets).removeClass("far"); 
 					$(".buttontest").eq(i).find("li").find("label").children("i").slice(0, data[i].sets).addClass("fas"); 
				}
				console.log(setsarr);
			}
		})
	}

	$(function() {
		init();
		$('input').on('change', function() {
			$(this).next("label").children("i").toggleClass("fas");
			/* 			console.log(this.id, this.checked); */
			if (this.checked) {
				console.log(this.id + "checked");
				/* 				console.log(this.value); */
				var processNo = this.value;
				console.log(processNo)
				$.ajax({
					url : "userMenuList.do?action=changeProcess",
					data : {
						"processNo" : processNo
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						console.log(data);
					}
				})

			}

		})
		
	    var cartCount = ${cartCount};
	    if(cartCount == 0){
	    	$("i.bi-cart-fill span").addClass("-on");
	    	$("i.bi-cart-fill span").attr("style", "display:none");
	    }else{
	    	$("i.bi-cart-fill span").removeClass("-on");
	    	$("i.bi-cart-fill span").attr("style", "");
	    }
	})
</script>
</body>
</html>