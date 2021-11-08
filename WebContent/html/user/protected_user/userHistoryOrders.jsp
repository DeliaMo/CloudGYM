<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="redis.clients.jedis.Jedis" %>
<jsp:useBean id="orderlistSvc" scope="page"
	class="com.orderList.model.OrderListService" />
<jsp:useBean id="videoSvc" scope="page"
	class="com.video.model.VideoService" />
<jsp:useBean id="coachSvc" scope="page"
	class="com.coachMenu.model.CoachMenuService" />
<jsp:useBean id="subSvc" scope="page"
	class="com.subscription.model.SubscriptionService" />
<jsp:useBean id="sublistSvc" scope="page"
	class="com.subList.model.SubListService" />
<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache");        //HTTP 1.0
	response.setDateHeader ("Expires", 0);
	
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
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>個人歷史訂單</title>
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

body{
/*     color: white; */
    background-color: #31105E;
}

#bar{
    height: 40px;
    background-color: #5C37A1;
    overflow: hidden;
    position:fixed;
    width: 100%;
    z-index: 10;
}
 
#title{
    float: left;
    margin-left: 20px;
    overflow: hidden;
}
 
img{
    width: 35px;
    margin-top: 3px;
}
 
.bar_li{
    float: left;
}
 
a{
    color: #fff;
    text-decoration: none;
    line-height: 40px;
}

a#CloudGYM{
	margin-left: 10px;
}

i.bi-cart-fill span.-on{
	display: none;
}
 
#option{
    float: right;
    overflow: hidden;
}

#option ul{
    margin-right: 20px;
}
 
.option{
    float: left;
    color: white;
    line-height: 40px;
    margin: 0 10px;
    font-size: 14px;
    position: relative;
}

.bi{
    margin-top: 7px;
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

/***************************以上複製貼上****************************/

.content {
	padding: 30px;
	width: 70%;
	background-color: #DED0F3;
	border-radius: 1%;
	display: inline-block;
}

.page {
	padding-top: 70px;
	color: white;
}

/***************************以下複製貼上****************************/

i.-on{
    display: none;
}

i.bi-cart-fill{
    font-size: 25px;
    position: absolute;
    top: -6px;
    left: -6px;
}

i.bi-cart-fill span{
    border-radius: 50%;
    width: 15px;
    height: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: red;
    color:#ffffff;
    font-size: 8px;
    position: absolute;
    top:0;
    right:-3px;
    font-family: arial;
}

i.bi:hover{
    color:#bebebe;
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
	<div id="bar">
		<div id="title">
			<ul>
				<li class="bar_li"><img src="<%=request.getContextPath()%>/img/logo.png" alt=""
					for="#CloudGYM"></li>
				<li class="bar_li"><a href="<%=request.getContextPath()%>/main_page.jsp" id="CloudGYM">CloudGYM</a></li>
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

	<div class="container page">
	<a class= "return" style="text-align:left;" href="<%=request.getContextPath()%>/html/user/protected_user/userMainPage.jsp">返回個人總覽</a>
		<h5 style="text-align: center;">歷史訂單</h5>
		<br>
		<c:forEach var="orders" items="${orders}">
			<div class="card">
				<div class="card-header" style="color: black; font-weight: bold;">訂單編號：
					${orders.orderNo}</div>
				<div class="card-body" style="color: grey;">

					<c:forEach var="orderlist"
						items="${orderlistSvc.getOrderListByOrderNo(orders.orderNo)}">
						<c:if test="${orderlist.itemID.toString().startsWith(3)}">
							<p class="card-text">購買品項：${videoSvc.findByPrimaryKey(orderlist.itemID).title}</p>
						</c:if>
						<c:if test="${orderlist.itemID.toString().startsWith(6)}">
							<p class="card-text">購買品項：${coachSvc.getByMenuID(orderlist.itemID).menuName}</p>
						</c:if>
						<c:if test="${orderlist.itemID.toString().startsWith(7)}">
							<p class="card-text">購買品項：${sublistSvc.getBySubID(orderlist.itemID).subName}</p>
						</c:if>

					</c:forEach>
					<br>
					
					<p class="card-text">訂單總金額： ${orders.totalPrice}</p>
					<p class="card-text">訂單建立時間： ${orders.builtDate}</p>
				</div>
			</div>
			<br>
		</c:forEach>
	</div>
	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
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