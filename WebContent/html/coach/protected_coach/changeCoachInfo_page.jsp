<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="redis.clients.jedis.Jedis" %>

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

%>

<%
	CoachService coachSvc = new CoachService();
	CoachVO coachVO = coachSvc.getByUserID(Integer.parseInt(request.getParameter("userID")));
	pageContext.setAttribute("coachVO", coachVO);
%>

<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<jsp:useBean id="CoachSvc" scope="page"
	class="com.coach.model.CoachService" />

<!DOCTYPE html>
<html>
<head>
<style>
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

.subA {
/* 	position:fixed; */
	cursor: pointer;
/* 	background-color: #4d4949; */
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
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>教練列表</title>
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/changeCoachInfo_page.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.ckeditor.com/ckeditor5/30.0.0/classic/ckeditor.js"></script>
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

	<div class="container">
		<h1>更改資料</h1>
		<hr>

            <%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
		<form class="row" METHOD="post" ACTION="<%=request.getContextPath() %>/coach/coach.do" name="form1"
			enctype="multipart/form-data">

			<div class="col">

				<div class="mb-3">
					<label class="form-label">教練名字</label> <input type="text"
						class="form-control" name="coachName"
						value="<%=coachVO.getCoachName()%>" />
					<div class="form-text">名字不要空白</div>

				</div>

				<div class="mb-3">
					<label class="form-label">個人圖片</label> <input type="file"
						class="form-control" name="coachImg" onchange="openFile(event)">
					<div class="form-text">禁止上傳違禁圖片</div>
				</div>

				<div class="mb-3">
					<label class="form-label">自我介紹</label> <input type="TEXT"
						class="form-control" name="coachDescription"
						value="<%=coachVO.getCoachDescription()%>" />
					<div class="form-text">可盡量填寫</div>
				</div>

				<div class="mb-3">
					<label class="form-label">證照</label> <input type="TEXT"
						class="form-control" name="coachCertificate"
						value="<%=coachVO.getCoachCertificate()%>" />
					<div class="form-text">可填可不填</div>
				</div>
			</div>


			<div class="subA">
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="userID" value="<%=coachVO.getUserID()%>">
				<button type="submit" class="btn btn-outline-primary">確認</button>
				<button id="cancel" type="button" class="btn btn-outline-danger">取消</button>
			</div>

		</form>

	</div>

	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
 	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
 
	<script>
		function openFile(event) {
			var input = event.target; //取得上傳檔案
			var reader = new FileReader(); //建立FileReader物件

			reader.readAsDataURL(input.files[0]); //以.readAsDataURL將上傳檔案轉換為base64字串

			reader.onload = function() { //FileReader取得上傳檔案後執行以下內容
				var dataURL = reader.result; //設定變數dataURL為上傳圖檔的base64字串
				$('#output').attr('src', dataURL).show(); //將img的src設定為dataURL並顯示
			};
		}

		$("#cancel").click(function() {
			console.log("cancel");

			var yes = confirm('你確定要取消嗎?');
			if (yes) {
				console.log("yse");
				alert(history.back());
			} else {
				console.log("no");
			}
		});
		
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