<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.video.model.*"%>
<%@ page import="redis.clients.jedis.Jedis" %>

<%
	VideoService videoSvc = new VideoService();
	List<VideoVO> list1 = videoSvc.getByPositionNo(1);
	List<VideoVO> list2 = videoSvc.getByPositionNo(2);
	List<VideoVO> list3 = videoSvc.getByPositionNo(3);
	List<VideoVO> list4 = videoSvc.getByPositionNo(4);
	List<VideoVO> list5 = videoSvc.getByPositionNo(5);
	
	request.setAttribute("list1", list1);
	request.setAttribute("list2", list2);
	request.setAttribute("list3", list3);
	request.setAttribute("list4", list4);
	request.setAttribute("list5", list5);
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
%>
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

.sub ul{
	padding-left:0;
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
<meta charset="BIG5">
<title>Insert title here</title>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
	crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/all_video_page.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.1/font/bootstrap-icons.css">
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
	<div id="wrap">
		<div id="left">
			<div id="strenth_div">
				<p class="left_p">強度</p>
				<input type="checkbox"><span class="strenth">弱</span><br>
				<input type="checkbox"><span class="strenth">中</span><br>
				<input type="checkbox"><span class="strenth">強</span><br>
			</div>
			<div id="price_range_div">
				<p class="left_p">價格區間</p>
				<input type="text" class="price">
				<div id="line"></div>
				<input type="text" class="price">
				<button type="button">套用</button>
			</div>
			<div id="star_block_div">
				<p class="left_p">評價</p>
				<span class="star" data-star="1"><i class="fas fa-star"></i></span>
				<span class="star" data-star="2"><i class="fas fa-star"></i></span>
				<span class="star" data-star="3"><i class="fas fa-star"></i></span>
				<span class="star" data-star="4"><i class="fas fa-star"></i></span>
				<span class="star" data-star="5"><i class="fas fa-star"></i></span>
			</div>
		</div>
		<div id="right">
		<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>
			<div class="video_box box1">
				<p class="video_type">練練手</p>
				<i class="bi bi-chevron-left"></i>
				<ul>
					<c:forEach var="videoVO" items="${list1}">
					<li class="video_list">
						<a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${videoVO.videoID}">
							<video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${videoVO.videoID}" alt="" width="250"></video>
						</a>
						
						<p class="video_name">
							${videoVO.title}<span class="coach_name">${ coachSvc.getByUserID(videoVO.userID).coachName }</span>
						</p>
					</li>
					</c:forEach>
				</ul>
				<i class="bi bi-chevron-right"></i>
				<!-- <i class="bi bi-chevron-right"></i> -->
			</div>
			<div class="video_box box2">
				<p class="video_type">練練腿</p>
				<i class="bi bi-chevron-left"></i>
				<ul>
					<c:forEach var="videoVO" items="${list2}">
					<li class="video_list">
						<a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${videoVO.videoID}">
							<video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${videoVO.videoID}" alt="" width="250"></video>
						</a>
						<p class="video_name">
							${videoVO.title}<span class="coach_name">${ coachSvc.getByUserID(videoVO.userID).coachName }</span>
						</p>
					</li>
					</c:forEach>
				</ul>
				<i class="bi bi-chevron-right"></i>
			</div>
			<div class="video_box box3">
				<p class="video_type">練練核心</p>
				<i class="bi bi-chevron-left"></i>
				<ul>
					<c:forEach var="videoVO" items="${list3}">
					<li class="video_list">
						<a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${videoVO.videoID}">
							<video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${videoVO.videoID}" alt="" width="250"></video>
						</a>
						<p class="video_name">
							${videoVO.title}<span class="coach_name">${ coachSvc.getByUserID(videoVO.userID).coachName }</span>
						</p>
					</li>
					</c:forEach>
				</ul>
				<i class="bi bi-chevron-right"></i>
			</div>
			<div class="video_box box4">
				<p class="video_type">練練背</p>
				<i class="bi bi-chevron-left"></i>
				<ul>
					<c:forEach var="videoVO" items="${list4}">
					<li class="video_list">
						<a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${videoVO.videoID}">
						<video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${videoVO.videoID}" alt="" width="250"></video>
						</a>
						<p class="video_name">
							${videoVO.title}<span class="coach_name">${ coachSvc.getByUserID(videoVO.userID).coachName }</span>
						</p>
					</li>
					</c:forEach>
				</ul>
				<i class="bi bi-chevron-right"></i>
			</div>
			<div class="video_box box5">
				<p class="video_type">練練全身</p>
				<i class="bi bi-chevron-left"></i>
				<ul>
					<c:forEach var="videoVO" items="${list5}">
					<li class="video_list">
						<a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${videoVO.videoID}">
							<video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${videoVO.videoID}" alt="" width="250"></video>
						</a>
						<p class="video_name">
							${videoVO.title}<span class="coach_name">${ coachSvc.getByUserID(videoVO.userID).coachName }</span>
						</p>
					</li>
					</c:forEach>
				</ul>
				<i class="bi bi-chevron-right"></i>
			</div>
		</div>
	</div>

	<!-- 載入 Font Awesome -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="../../js/jquery-3.6.0.min.js"></script>
	<script>
		$(function() {
		    var cartCount = ${cartCount};
		    if(cartCount == 0){
		    	$("i.bi-cart-fill span").addClass("-on");
		    	$("i.bi-cart-fill span").attr("style", "display:none");
		    }else{
		    	$("i.bi-cart-fill span").removeClass("-on");
		    	$("i.bi-cart-fill span").attr("style", "");
		    }
			
			
			var box1_seq = 0;

			if (!$("div.box1 li").eq(0).hasClass("-on")) {
				$("div.box1 i.bi-chevron-left").addClass("-on");
				$("div.box1 i.bi-chevron-right").removeClass("-on");
			}
			if ($("div.box1 li").length <= 3) {
				$("div.box1 i.bi-chevron-left").addClass("-on");
				$("div.box1 i.bi-chevron-right").addClass("-on");
			}
			$("div.box1 li").slice(box1_seq + 3).addClass("-on");
			$("div.box1 i.bi-chevron-right").on("click", function() {
				$("div.box1 li").eq(box1_seq).addClass("-on");
				$("div.box1 li").eq(box1_seq + 3).removeClass("-on");
				$("div.box1 li").eq(box1_seq + 2).removeClass("last");
				$("div.box1 li").eq(box1_seq + 3).addClass("last");
				box1_seq++;
				if (!$("div.box1 li").eq(-1).hasClass("-on")) {
					$("div.box1 i.bi-chevron-right").addClass("-on");
					$("div.box1 i.bi-chevron-left").removeClass("-on");
				}
				if ($("div.box1 li").eq(0).hasClass("-on")) {
					$("div.box1 i.bi-chevron-left").removeClass("-on");
				}
			});
			$("div.box1 i.bi-chevron-left").on("click", function() {
				$("div.box1 li").eq(box1_seq + 2).addClass("-on");
				$("div.box1 li").eq(box1_seq + 2).removeClass("last");
				$("div.box1 li").eq(box1_seq - 1).removeClass("-on");
				$("div.box1 li").eq(box1_seq + 1).addClass("last");
				box1_seq--;
				if (!$("div.box1 li").eq(0).hasClass("-on")) {
					$("div.box1 i.bi-chevron-left").addClass("-on");
					$("div.box1 i.bi-chevron-right").removeClass("-on");
				}
				if ($("div.box1 li").eq(-1).hasClass("-on")) {
					$("div.box1 i.bi-chevron-right").removeClass("-on");
				}
			})
			/****************************************************************/
			var box2_seq = 0;

			if (!$("div.box2 li").eq(0).hasClass("-on")) {
				$("div.box2 i.bi-chevron-left").addClass("-on");
				$("div.box2 i.bi-chevron-right").removeClass("-on");
			}
			if ($("div.box2 li").length <= 3) {
				$("div.box2 i.bi-chevron-left").addClass("-on");
				$("div.box2 i.bi-chevron-right").addClass("-on");
			}
			$("div.box2 li").slice(box2_seq + 3).addClass("-on");
			$("div.box2 i.bi-chevron-right").on("click", function() {
				$("div.box2 li").eq(box2_seq).addClass("-on");
				$("div.box2 li").eq(box2_seq + 3).removeClass("-on");
				$("div.box2 li").eq(box2_seq + 2).removeClass("last");
				$("div.box2 li").eq(box2_seq + 3).addClass("last");
				box2_seq++;
				if (!$("div.box2 li").eq(-1).hasClass("-on")) {
					$("div.box2 i.bi-chevron-right").addClass("-on");
					$("div.box2 i.bi-chevron-left").removeClass("-on");
				}
				if ($("div.box2 li").eq(0).hasClass("-on")) {
					$("div.box2 i.bi-chevron-left").removeClass("-on");
				}
			});
			$("div.box2 i.bi-chevron-left").on("click", function() {
				$("div.box2 li").eq(box2_seq + 2).addClass("-on");
				$("div.box2 li").eq(box2_seq + 2).removeClass("last");
				$("div.box2 li").eq(box2_seq - 1).removeClass("-on");
				$("div.box2 li").eq(box2_seq + 1).addClass("last");
				box2_seq--;
				if (!$("div.box2 li").eq(0).hasClass("-on")) {
					$("div.box2 i.bi-chevron-left").addClass("-on");
					$("div.box2 i.bi-chevron-right").removeClass("-on");
				}
				if ($("div.box2 li").eq(-1).hasClass("-on")) {
					$("div.box2 i.bi-chevron-right").removeClass("-on");
				}
			})
			/****************************************************************/
			var box3_seq = 0;

			if (!$("div.box3 li").eq(0).hasClass("-on")) {
				$("div.box3 i.bi-chevron-left").addClass("-on");
				$("div.box3 i.bi-chevron-right").removeClass("-on");
			}
			if ($("div.box3 li").length <= 3) {
				$("div.box3 i.bi-chevron-left").addClass("-on");
				$("div.box3 i.bi-chevron-right").addClass("-on");
			}
			$("div.box3 li").slice(box3_seq + 3).addClass("-on");
			$("div.box3 i.bi-chevron-right").on("click", function() {
				$("div.box3 li").eq(box3_seq).addClass("-on");
				$("div.box3 li").eq(box3_seq + 3).removeClass("-on");
				$("div.box3 li").eq(box3_seq + 2).removeClass("last");
				$("div.box3 li").eq(box3_seq + 3).addClass("last");
				box3_seq++;
				if (!$("div.box3 li").eq(-1).hasClass("-on")) {
					$("div.box3 i.bi-chevron-right").addClass("-on");
					$("div.box3 i.bi-chevron-left").removeClass("-on");
				}
				if ($("div.box3 li").eq(0).hasClass("-on")) {
					$("div.box3 i.bi-chevron-left").removeClass("-on");
				}
			});
			$("div.box3 i.bi-chevron-left").on("click", function() {
				$("div.box3 li").eq(box3_seq + 2).addClass("-on");
				$("div.box3 li").eq(box3_seq + 2).removeClass("last");
				$("div.box3 li").eq(box3_seq - 1).removeClass("-on");
				$("div.box3 li").eq(box3_seq + 1).addClass("last");
				box3_seq--;
				if (!$("div.box3 li").eq(0).hasClass("-on")) {
					$("div.box3 i.bi-chevron-left").addClass("-on");
					$("div.box3 i.bi-chevron-right").removeClass("-on");
				}
				if ($("div.box3 li").eq(-1).hasClass("-on")) {
					$("div.box3 i.bi-chevron-right").removeClass("-on");
				}
			})
			/****************************************************************/
			var box4_seq = 0;

			if (!$("div.box4 li").eq(0).hasClass("-on")) {
				$("div.box4 i.bi-chevron-left").addClass("-on");
				$("div.box4 i.bi-chevron-right").removeClass("-on");
			}
			if ($("div.box4 li").length <= 3) {
				$("div.box4 i.bi-chevron-left").addClass("-on");
				$("div.box4 i.bi-chevron-right").addClass("-on");
			}
			$("div.box4 li").slice(box4_seq + 3).addClass("-on");
			$("div.box4 i.bi-chevron-right").on("click", function() {
				$("div.box4 li").eq(box4_seq).addClass("-on");
				$("div.box4 li").eq(box4_seq + 3).removeClass("-on");
				$("div.box4 li").eq(box4_seq + 2).removeClass("last");
				$("div.box4 li").eq(box4_seq + 3).addClass("last");
				box4_seq++;
				if (!$("div.box4 li").eq(-1).hasClass("-on")) {
					$("div.box4 i.bi-chevron-right").addClass("-on");
					$("div.box4 i.bi-chevron-left").removeClass("-on");
				}
				if ($("div.box4 li").eq(0).hasClass("-on")) {
					$("div.box4 i.bi-chevron-left").removeClass("-on");
				}
			});
			$("div.box4 i.bi-chevron-left").on("click", function() {
				$("div.box4 li").eq(box4_seq + 2).addClass("-on");
				$("div.box4 li").eq(box4_seq + 2).removeClass("last");
				$("div.box4 li").eq(box4_seq - 1).removeClass("-on");
				$("div.box4 li").eq(box4_seq + 1).addClass("last");
				box4_seq--;
				if (!$("div.box4 li").eq(0).hasClass("-on")) {
					$("div.box4 i.bi-chevron-left").addClass("-on");
					$("div.box4 i.bi-chevron-right").removeClass("-on");
				}
				if ($("div.box4 li").eq(-1).hasClass("-on")) {
					$("div.box4 i.bi-chevron-right").removeClass("-on");
				}
			})
			/****************************************************************/
			var box5_seq = 0;

			if (!$("div.box5 li").eq(0).hasClass("-on")) {
				$("div.box5 i.bi-chevron-left").addClass("-on");
				$("div.box5 i.bi-chevron-right").removeClass("-on");
			}
			if ($("div.box5 li").length <= 3) {
				$("div.box5 i.bi-chevron-left").addClass("-on");
				$("div.box5 i.bi-chevron-right").addClass("-on");
			}
			$("div.box5 li").slice(box5_seq + 3).addClass("-on");
			$("div.box5 i.bi-chevron-right").on("click", function() {
				$("div.box5 li").eq(box5_seq).addClass("-on");
				$("div.box5 li").eq(box5_seq + 3).removeClass("-on");
				$("div.box5 li").eq(box5_seq + 2).removeClass("last");
				$("div.box5 li").eq(box5_seq + 3).addClass("last");
				box5_seq++;
				if (!$("div.box5 li").eq(-1).hasClass("-on")) {
					$("div.box5 i.bi-chevron-right").addClass("-on");
					$("div.box5 i.bi-chevron-left").removeClass("-on");
				}
				if ($("div.box5 li").eq(0).hasClass("-on")) {
					$("div.box5 i.bi-chevron-left").removeClass("-on");
				}
			});
			$("div.box5 i.bi-chevron-left").on("click", function() {
				$("div.box5 li").eq(box5_seq + 2).addClass("-on");
				$("div.box5 li").eq(box5_seq + 2).removeClass("last");
				$("div.box5 li").eq(box5_seq - 1).removeClass("-on");
				$("div.box5 li").eq(box5_seq + 1).addClass("last");
				box5_seq--;
				if (!$("div.box5 li").eq(0).hasClass("-on")) {
					$("div.box5 i.bi-chevron-left").addClass("-on");
					$("div.box5 i.bi-chevron-right").removeClass("-on");
				}
				if ($("div.box5 li").eq(-1).hasClass("-on")) {
					$("div.box5 i.bi-chevron-right").removeClass("-on");
				}
			})

		})
	</script>
</body>
</html>