<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.video.model.*"%>
<%@ page import="redis.clients.jedis.Jedis"%>


<jsp:useBean id="coachSvc1" scope="page"
	class="com.coach.model.CoachService" />

<%
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
%>

<%
	CoachService coachSvc = new CoachService();
	List<CoachVO> list = coachSvc.getAll();
	pageContext.setAttribute("list", list);

	VideoService videoSvc = new VideoService();
	List<VideoVO> list1 = videoSvc.getAll2();
	pageContext.setAttribute("list1", list1);
%>

<%
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
<meta charset="BIG5">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CloudGYM</title>
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/main_page.css">
<link rel="stylesheet" href="css/bar.css">

<!-- Mutislider CSS -->
<link href="css/main_coach.css" rel="stylesheet">
<!-- <link href="css/main_video.css" rel="stylesheet"> -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<!-- Font Awesome -->
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<style>
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
</head>
<body>
	<%-- <%@include file="../bar.file"%> --%>
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
				<li class="bar_li"><img src="img/logo.png" alt=""
					for="#CloudGYM"></li>
				<li class="bar_li"><a
					href="${pageContext.request.contextPath}/main_page.jsp"
					id="CloudGYM">CloudGYM</a></li>
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
	<!-- bar end -->

	<div id="top">
		<img src="img/EVO-2021-PP-Feb-Banner_10-1200x675.jpg" alt=""
			id="main_img">
	</div>

	<div id="center">
		<div id="center2">
			<div id="text1">
				<ul>
					<li id="hot_video">熱門影片</li>
					<li id="all_videos"><a href="<%=request.getContextPath()%>/html/video/all_video_page.jsp">顯示更多</a></li>
				</ul>
			</div>

			<div id="video_pic_div">
				<iframe src="main_video.jsp" width="100%;" height="400" frameborder="0" scrolling="no"></iframe>
			</div>


			<div id="coach_rank">教練金榜</div>
			<div id="coach_pic_div">

				<div id="exampleSlider">
					<div class="MS-content">
						<c:forEach var="coachVO" items="${list}" begin="0"
							end="${list.size()}">


							<div class="item">
								<div class="img_block">
									<form action="<%=request.getContextPath()%>/coach/coach.do?">
										<input type="submit" id="${coachVO.userID }"
											style="display: none;"> <input type="hidden"
											name="action" value="getOne_For_Display"> <input
											type="hidden" name="userID" value="${coachVO.userID}">
										<label for="${coachVO.userID }"> <img
											src="<%=request.getContextPath()%>/coachImg/coachImg.do?userID=${coachVO.userID}"
											alt="" class="pic" target="_blank">
											<p class="coach_list">${coachVO.coachName}</p>
										</label>
									</form>
								</div>
							</div>


						</c:forEach>
					</div>

					<div class="MS-controls">
						<button class="MS-left">
							<i class="fa fa-chevron-left" aria-hidden="true"></i>
						</button>
						<button class="MS-right">
							<i class="fa fa-chevron-right" aria-hidden="true"></i>
						</button>
					</div>

				</div>
			</div>
		</div>
	</div>


	<div id="bottom">
		<p id="about">關於我們</p>
		<p id="contact">聯絡我們</p>
		<p id="address">台灣台北市信義區信義路五段7號</p>
		<p>029484184</p>
	</div>

	<!-- Include jQuery -->
	<script src="vendors/jquery/jquery-3.4.1.min.js"></script>

	<!-- Include Multislider -->
	<script src="js/multislider.min.js"></script>

	<!-- Initialize element with Multislider -->
	<script>
		$('#exampleSlider').multislider({
			interval : 0,
			slideAll : false,
			duration : 1500
		});

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