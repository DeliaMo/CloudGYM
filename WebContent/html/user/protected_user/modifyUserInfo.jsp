<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*"%>
<%@ page import="redis.clients.jedis.Jedis" %>

<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache");        //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>

<%
	UserVO userVO = (UserVO) request.getAttribute("userVO");
	System.out.println(userVO);

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
<title>個人資料修改</title>
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

/***************************以上複製貼上****************************/

.content {
	padding: 30px 30px 60px;
	width: 70%;
	background-color: #DED0F3;
	border-radius: 1%;
	display: inline-block;
}

.page {
	padding-top: 100px;
	text-align: center;
	padding-bottom: 100px;
}

.btn {
	margin-top: 10%;
	width: 20%;
	height: 20%;
	border-radius: 5%;
	border-style: none;
	background-color: #31105E;
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
		<form class="row g-3 content" method="post">
			<h3 style="text-align: center; margin-bottom: 6%;">個人資料修改</h3>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<form>
				<div class="row mb-3">
					<label for="inputEmail3" class="col-sm-2 col-form-label">Email</label>
					<div class="col-sm-8">
						<input type="hidden" name="userAccount"
							value="<%=userVO.getUserAccount()%>">
						<p><%=userVO.getUserAccount()%></p>
					</div>
				</div>
				<div class="row mb-3">
					<label for="inputName" class="col-sm-2 col-form-label">名字</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="inputName"
							name="userName" value="<%=userVO.getUserName() == null ? "":userVO.getUserName()%>">
					</div>
				</div>
				<div class="row mb-3">
					<label for="inputPassword3" class="col-sm-2 col-form-label">密碼</label>
					<div class="col-sm-8">
						<input type="password" class="form-control" id="inputPassword"
							name="userPassword" value="<%=userVO.getUserPassword() == null ? "":userVO.getUserPassword()%>">
					</div>
				</div>
				<div class="row mb-3">
					<label for="inputPassword3" class="col-sm-2 col-form-label">確認密碼</label>
					<div class="col-sm-8">
						<input type="password" class="form-control"
							id="inputPasswordConfirm" name="passwordConfirm"
							value="<%=userVO.getUserPassword() == null ? "":userVO.getUserPassword()%>"></input>
					</div>
				</div>
				<div class="row mb-3">
					<label for="inputPhone" class="col-sm-2 col-form-label">電話</label>
					<div class="col-sm-8">
						<input type="tel" pattern="[0-9]{4}[0-9]{3}[0-9]{3}"
							class="form-control" id="inputPhone" name="userMobile"
							value="<%=userVO.getUserMobile() == null ? "":userVO.getUserMobile()%>">
					</div>
				</div>
				<div class="row mb-3">
					<label for="inputPassword3" class="col-sm-2 col-form-label">性別</label>
					<div class="col-sm-8" style="text-align:left;">
						<%-- <input type="text" class="form-control" name="userSex"
							value="<%=userVO.getUserSex()%>"></input> --%>
						<input type="radio" name="userSex" value="男"
							<%if(userVO.getUserSex() != null){String male = userVO.getUserSex();
			if (male.equals("男"))
				out.println("checked");}%> />
						<label for="radio">男</label> <input type="radio" name="userSex"
							value="女"
							<%if(userVO.getUserSex() != null){String userSex = userVO.getUserSex();
			if (userSex.equals("女"))
				out.println("checked");}%> />
						<label for="radio">女</label>
					</div>
				</div>

				<div class="row mb-3">
					<label for="inputDate" class="col-sm-2 col-form-label">生日</label>
					<div class="col-sm-8">
						<input id="bday" type="text" class="form-control"
							name="userBirthday">
					</div>
				</div>
				<div>
					<input type="hidden" name="action" value="update"> <input
						type="hidden" name="userID" value="<%=userVO.getUserID() == null ? "":userVO.getUserID()%>">
					<input class="btn" type="submit" value="確認修改">
				</div>
			</form>
		</form>
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

<%
	java.sql.Date userBirthday = null;
	try {
		userBirthday = userVO.getUserBirthday();
	} catch (Exception e) {
		userBirthday = new java.sql.Date(System.currentTimeMillis());
	}
%>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#bday').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=userBirthday%>'  // value:   new Date(),
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});
</script>

</html>