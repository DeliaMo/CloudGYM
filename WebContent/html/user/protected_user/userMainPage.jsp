<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.collection.model.*" %>
<%@ page import="com.video.model.*" %>
<%@ page import="com.orders.model.*" %>
<%@ page import="com.orderList.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="redis.clients.jedis.Jedis" %>
<%@ page import="com.customMenu.model.*" %>
<jsp:useBean id="menuSvc" scope="page" class="com.customMenu.model.CustomMenuService" />
<jsp:useBean id="rightsSvc" scope="page" class="com.userRights.model.UserRightsService" />
<jsp:useBean id="videoSvc" scope="page" class="com.video.model.VideoService" />
<jsp:useBean id="collectionSvc" scope="page" class="com.collection.model.CollectionService" />
<jsp:useBean id="coachSvc" scope="page" class="com.coachMenu.model.CoachMenuService" />
<jsp:useBean id="postsSvc" scope="page" class="com.posts.model.PostsService" />

<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache");        //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>

<%
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
	
	List<CollectionVO> collectionlist = collectionSvc.getByUserId(Integer.parseInt(userID));
	List<OrdersVO> order = new OrdersService().getOrdersByUserID(Integer.parseInt(userID));
	List<Integer> itemIDs = new ArrayList<>();
	for(OrdersVO ordersVO : order){
		List<OrderListVO> list = new OrderListService().getOrderListByOrderNo(ordersVO.getOrderNo());
		for(OrderListVO orderlist : list){
			Integer itemID = orderlist.getItemID();
			itemIDs.add(itemID);
		}
	}
	
	CustomMenuService menusvc = new CustomMenuService();
	List<CustomMenuVO> percentlist = menusvc.getAll(1003);
	Integer percentlistsize = percentlist.size();
	Integer total = 0;
	for(int i = 0; i < percentlistsize; i++){
		total += percentlist.get(i).getCompleted();
	}
	Integer percent = (total/percentlistsize);
%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>個人專區總覽</title>

<link rel="stylesheet" href="/page-1/css/style.css">

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

/***************************以上複製貼上****************************/

#option img {
	color: white;
}

.bar {
	width: 55%;
	float: right;
}

.menu {
	padding-bottom: 3%;
}

.general{
	color: grey;
}

.listeffect:hover, .listeffect:active{
	background-color: #31105E;
	color: white !important;
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
		<div class="row first-row">
			<div class="col-4 overview rounded">
				<h5>我的總覽</h5>
				<p class="general">個人菜單數：${fn:length(menuSvc.getAll(userID))}</p>
				<p class="general">運動歷程完成度:<%= percent%>%</p>
				<p class="general">收藏數：<%=collectionlist.size() %></p>
			</div>
			<div class="col-8 info">
				<ul>
					<li>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/userMainPage/userInfo.do"
							style="margin-bottom: 0px;">
							<input type="submit" value="修改個人資料" style="background-color: transparent; border: none; color: white;"> <input type="hidden"
								name="userID" value="${userID }"> <input type="hidden"
								name="action" value="update_prepare">
						</FORM>
					</li>

					<li>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/userMainPage/userInfo.do"
							style="margin-bottom: 0px;">
							<input type="submit" value="歷史訂單" style="background-color: transparent; border: none; color: white;"> 
							<input type="hidden" name="userID" value="${userID}"> 
							<input type="hidden" name="action" value="history_orders">
						</FORM>
					</li>
					<li>
						<%-- <FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/userMainPage/userInfo.do"
							style="margin-bottom: 0px;">
							<input type="submit" value="我的文章" style="background-color: transparent; border: none; color: white;"> <input type="hidden"
								name="user" value="${userID}"> <input
								type="hidden" name="action" value="update_prepare">
						</FORM> --%>
						<FROM>
						<a class="" href="<%=request.getContextPath() %>/html/article/ArticleList_MyPost.jsp">我的文章</a>
						</FROM>
					</li>
				</ul>
			</div>
		</div>


		<div class="row gx-5 second-row ">
			<div class="col menu">
				<h5 style="cursor:pointer" onclick="javascript:location.href='<%=request.getContextPath()%>/html/user/protected_user/userMenuAndVideo2.jsp'">我的菜單</h5>
				<ul class="list-group">
					<c:forEach var="customMenuVO" items="${menuSvc.getAll(userID)}">
						<li class="list-group-item listeffect" style="cursor:pointer" onclick="javascript:location.href='<%=request.getContextPath()%>/html/user/protected_user/userMenuAndVideo2.jsp'">${customMenuVO.title}</li>
					</c:forEach>
				</ul>
			</div>
			<div class="col menu">
				<h5 style="cursor:pointer" onclick="javascript:location.href='<%=request.getContextPath()%>/html/user/protected_user/userMenuListPage1.jsp'">我的運動歷程</h5>

				<ul class="list-group">
					<c:forEach var="customMenuVO" items="${menuSvc.getAll(userID)}">
						<li class="list-group-item listeffect" style="cursor:pointer" onclick="javascript:location.href='<%=request.getContextPath()%>/html/user/protected_user/userMenuListPage1.jsp'">${customMenuVO.title}

							<div class="progress bar">
								<div class="progress-bar bg-warning" role="progressbar"
									style="width: ${customMenuVO.completed}%;" aria-valuenow="75" aria-valuemin="0"
									aria-valuemax="100">${customMenuVO.completed}%</div>
							</div>

						</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div class="row gx-5 second-row ">
			<div class="col menu">
				<h5>已購買</h5>
				<ul class="list-group">
<%-- 					<c:forEach var="userRightsVO" items="${rightsSvc.getAll(1003)}"> --%>
<!-- 						<li class="list-group-item">教練菜單1</li>
<!-- 					<li class="list-group-item">教練菜單2</li> -->
<%-- 						<li class="list-group-item">${videoSvc.findByPrimaryKey(userRightsVO.videoID).title}</li> --%>
<%-- 					</c:forEach> --%>
					<%if(itemIDs != null){
						for(Integer itemid : itemIDs){
							if(itemid.toString().startsWith("3")){ %>
								<li class="list-group-item listeffect"><%=videoSvc.findByPrimaryKey(itemid).getTitle() %></li>
					<%		}
							if(itemid.toString().startsWith("6")){ %>
								<li class="list-group-item listeffect"><%=coachSvc.getByMenuID(itemid).getMenuName() %></li>
					<%		}
						}
					}%>
				</ul>
			</div>
			<div class="col menu">
				<h5>已收藏</h5>
				<ul class="list-group">
 				<%-- ${collectionSvc.getByUserId(1003)[0].menuID} --%>
				<%-- <c:choose>
					<c:when test="${collectionSvc.getByUserId(1003) == null}"
				</c:choose> --%>
					<%-- <c:if
						test="${collectionSvc.getByUserId(1003) == null} | ${fn:length(collectionSvc.getByUserId(1003)) == 0}">
						<c:out value="暫無收藏"></c:out>
					</c:if> --%>
					<%-- <c:forEach var="collectionVO"
						items="${collectionSvc.getByUserId(1001)}">
						<li class="list-group-item">${videoSvc.findByPrimaryKey(collectionVO.videoID).title}</li>
					</c:forEach> --%>
<%-- 					<c:forEach var="coachMenuVO" --%>
<%-- 						items="${collectionSvc.getByUserId(1003)}"> --%>
<%-- 						<li class="list-group-item">${coachSvc.getByMenuID(coachMenuVO.menuID).menuName}</li> --%>
<%-- 						<li class="list-group-item">${videoSvc.findByPrimaryKey(coachMenuVO.videoID).title}</li> --%>
<%-- 					</c:forEach> --%>
					<%
					for(CollectionVO collectionVO : collectionlist){
						Integer itemID = collectionVO.getItemID();
						String str = itemID.toString();
						if(str.startsWith("3")){ %>
							<li class="list-group-item listeffect"><%=videoSvc.findByPrimaryKey(itemID).getTitle() %></li>
					<%	} %>
					  <%if(str.startsWith("4")){ %>
							<li class="list-group-item listeffect"><%=postsSvc.getByPostsID(itemID).getPostsTitle() %></li>
					  <%} %>
					  <%if(str.startsWith("6")){ %>
					  		<li class="list-group-item listeffect"><%=coachSvc.getByMenuID(itemID).getMenuName() %></li>
					  <%} %>
					<%}
					
					%>
				</ul>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
	<script>
		$(function(){
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