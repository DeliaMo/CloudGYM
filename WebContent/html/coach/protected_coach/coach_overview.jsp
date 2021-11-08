<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.coachMenu.model.*" %>
<%@ page import="com.video.model.*" %>
<%@ page import="redis.clients.jedis.Jedis" %>
<%@ page import="com.userAuth.model.*" %>

<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache");        //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>

<%
	Integer coachID = new Integer(request.getParameter("userID"));
	pageContext.setAttribute("userID", coachID);
	
	CoachMenuService coachmenuSvc = new CoachMenuService();
	List<CoachMenuVO> menulist = coachmenuSvc.getByUserID(coachID);
	pageContext.setAttribute("menulist", menulist);
	
	VideoService videoSvc = new VideoService();
	List<VideoVO> videolist = videoSvc.getByUserID(coachID);
	pageContext.setAttribute("videolist", videolist);
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
	
	Integer banVideo = 0;
	UserAuthService userauthSvc = new UserAuthService();
	UserAuthVO userAuthVO = userauthSvc.getUserID(Integer.parseInt(userID));
	if(userAuthVO != null){
		banVideo = userAuthVO.getBanVideo();
	}
	 
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
<title>教練總覽頁面</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/coach_overview.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.1/font/bootstrap-icons.css">
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
<!-- bar公版 begining -->
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
<!-- bar公版 end -->

    <div id="main">
        <div class="left">

			<a href="<%=request.getContextPath()%>/html/coach/protected_coach/buildMenu.jsp?userID=${userID}">建立菜單</a><br>
			<%if(banVideo == 1){%>
				<span>影片上傳</span>
			<%}else{%>
            <a href="<%=request.getContextPath()%>/html/coach/protected_coach/buildVideo.jsp?userID=${userID}">影片上傳</a><br>
            <%} %>
             <a href="<%=request.getContextPath()%>/html/coach/updateMenu.jsp?userID=${userID}">更新菜單</a><br>
            
            <a href="<%=request.getContextPath()%>/html/coach/updateVideo.jsp?userID=${userID}">更新影片</a>
            
        </div>
        <div class="right">
            <div class="my_menu">
                <h3>我的菜單</h3>
                <i class="bi bi-chevron-left"></i>
                <ul>
                	<c:forEach var="menu" items="${menulist}">
                	<li>
                        <p>${menu.menuName}</p>
                    </li>
                	</c:forEach>
                </ul>
                <i class="bi bi-chevron-right"></i>
            </div>
            <div class="my_video">
                <h3>我的影片</h3>
                <i class="bi bi-chevron-left"></i>
                <ul>
                	<c:forEach var="video" items="${videolist }">
                	<li>
                        <video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${video.videoID}" width="200" height="133" controls controlsList="nodownload">
                        	<source src="http://techslides.com/demos/sample-videos/small.mp4" type="video/mp4">
  							<source src="http://techslides.com/demos/sample-videos/small.ogv" type="video/ogg">
                        </video>
                    </li>
                	</c:forEach>
                </ul>
                <i class="bi bi-chevron-right"></i>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
    <script>
    $(function () {
        var cartCount = ${cartCount};
        if(cartCount == 0){
        	$("i.bi-cart-fill span").addClass("-on");
        	$("i.bi-cart-fill span").attr("style", "display:none");
        }else{
        	$("i.bi-cart-fill span").removeClass("-on");
        	$("i.bi-cart-fill span").attr("style", "");
        }
    	
    	
    	
        var menu_seq = 0;
        var video_seq = 0;
        

        if (!$("div.my_menu li").eq(0).hasClass("-on")) {
            $("div.my_menu i.bi-chevron-left").addClass("-on");
            $("div.my_menu i.bi-chevron-right").removeClass("-on");
        }
        if ($("div.my_menu li").length <= 3) {
            $("div.my_menu i.bi-chevron-left").addClass("-on");
            $("div.my_menu i.bi-chevron-right").addClass("-on");
        }
        $("div.my_menu li").slice(menu_seq + 3).addClass("-on");
        $("div.my_menu i.bi-chevron-right").on("click", function () {
            $("div.my_menu li").eq(menu_seq).addClass("-on");
            $("div.my_menu li").eq(menu_seq + 3).removeClass("-on");
            menu_seq++;
            if (!$("div.my_menu li").eq(-1).hasClass("-on")) {
                $("div.my_menu i.bi-chevron-right").addClass("-on");
                $("div.my_menu i.bi-chevron-left").removeClass("-on");
            }
        });
        $("div.my_menu i.bi-chevron-left").on("click", function () {
            $("div.my_menu li").eq(menu_seq + 2).addClass("-on");
            $("div.my_menu li").eq(menu_seq - 1).removeClass("-on");
            menu_seq--;
            if (!$("div.my_menu li").eq(0).hasClass("-on")) {
                $("div.my_menu i.bi-chevron-left").addClass("-on");
                $("div.my_menu i.bi-chevron-right").removeClass("-on");
            }
        })
        /****************************************************************/
        console.log($("div.my_video li").length);

        if (!$("div.my_video li").eq(0).hasClass("-on")) {
            $("div.my_video i.bi-chevron-left").addClass("-on");
            $("div.my_video i.bi-chevron-right").removeClass("-on");
        }
        if ($("div.my_video li").length <= 3) {
            $("div.my_video i.bi-chevron-right").addClass("-on");
        }
        $("div.my_video li").slice(video_seq + 3).addClass("-on");
        $("div.my_video i.bi-chevron-right").on("click", function () {
            $("div.my_video li").eq(video_seq).addClass("-on");
            $("div.my_video li").eq(video_seq + 3).removeClass("-on");
            video_seq++;
            if($("div.my_video li").eq(video_seq-1).hasClass("-on")){
                $("div.my_video i.bi-chevron-left").removeClass("-on");
            }
            if (!$("div.my_video li").eq(-1).hasClass("-on")) {
                $("div.my_video i.bi-chevron-right").addClass("-on");
                $("div.my_video i.bi-chevron-left").removeClass("-on");
            }
        });
        $("div.my_video i.bi-chevron-left").on("click", function () {
            $("div.my_video li").eq(video_seq + 2).addClass("-on");
            $("div.my_video li").eq(video_seq - 1).removeClass("-on");
            video_seq--;
            if($("div.my_video li").eq(video_seq+3).hasClass("-on")){
                $("div.my_video i.bi-chevron-right").removeClass("-on");
            }
            if (!$("div.my_video li").eq(0).hasClass("-on")) {
                $("div.my_video i.bi-chevron-left").addClass("-on");
                $("div.my_video i.bi-chevron-right").removeClass("-on");
            }
        })
    })
    </script>
</body>
</html>