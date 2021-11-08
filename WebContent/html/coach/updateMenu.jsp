<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.thePosition.model.*" %>
<%@ page import="com.coachMenu.model.*" %>
<%@ page import="com.coachMenuList.model.*"%>

<%
Integer userID = Integer.parseInt(request.getParameter("userID"));
request.setAttribute("userID", userID);


String location = request.getRequestURI();
request.setAttribute("location", location);

CoachMenuVO coachMenuVO = (CoachMenuVO) request.getAttribute("coachMenuVO");

List<String> errorMsgs = (List<String>) request.getAttribute("errorMsgs");
%>

<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<jsp:useBean id="coachMenuSvc" scope="page" class="com.coachMenu.model.CoachMenuService" />
<jsp:useBean id="positionSvc" scope="page" class="com.thePosition.model.ThePositionService"/>
<jsp:useBean id="videoSvc" scope="page"
	class="com.video.model.VideoService" />
<jsp:useBean id="coachMenuListSvc" scope="page" class="com.coachMenuList.model.CoachMenuListService"/>

<!DOCTYPE html>
<html lang="en">
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
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>教練編輯菜單</title>
<!--     <link rel="stylesheet" href="../css/reset.css"> -->
<!--     <link rel="stylesheet" href="../css/updateMenu.css"> -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/updateMenu.css">
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
                    <img src="../img/logo.png" alt="" for="#CloudGYM">
                </li>
                <li class="bar_li">
                    <a href="${pageContext.request.contextPath}/html/main_page.jsp" id="CloudGYM">CloudGYM</a>
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
        <div id="form">
            <div id="box1">
                <h3 id="form_title">更新菜單</h3>
            </div>
				
            <form action="<%=request.getContextPath()%>/coachMenu/coachMenu.do" method="post">
            <div id="box2">
                <ul>
						<li id="menu_name">想更改的菜單：
  	                    <select name="menuID">
	                        <option selected disabled></option>
	                        <c:forEach var="coachMenuVO" items="${coachMenuSvc.getByUserID(userID)}">
	                        <option value="${coachMenuVO.menuID}">${coachMenuVO.menuName}</option>
	                        </c:forEach>
	                    </select><br><br>
	                    
	                    <li>更改後的名稱：
	                    <input id="name_input" type="text" placeholder="輸入菜單名稱" aria-label="default input example" 
                    	name="menuName" value="<%=(coachMenuVO == null) ? "" : coachMenuVO.getMenuName()%>"><br><br>
                    	<%if(errorMsgs != null){if(errorMsgs.contains("請輸入菜單名稱")) {%>
                        <span style="color:red; margin-left:10px; font-size:10px">請輸入菜單名稱</span>
                    <%}} %>

                    <li>價錢：
                    
                    <input id="price" type="number" placeholder="選擇價格" aria-label="default input example" 
                    	name="price" value="<%=(coachMenuVO == null) ? "" : coachMenuVO.getPrice()%>"><br><br>
                    	 <%if(errorMsgs != null){if(errorMsgs.contains("請輸入菜單價格")) {%>
                        <span style="color:red; margin-left:10px; font-size:10px">請輸入菜單價格</span>
                    <%}} %>
                    </li>

                	<li>部位選擇：
	                    <select name="positionNo">
	                        <option selected disabled></option>
	                        <c:forEach var="positionVO" items="${positionSvc.all}">
	                        <option value="${positionVO.positionNo}">${positionVO.positionName}</option>
	                        </c:forEach>
	                    </select>
	                   <%if(errorMsgs != null){if(errorMsgs.contains("請選擇部位")) {%>
                        <span style="color:red; margin-left:10px; font-size:10px">請選擇部位</span>
                   		 <%}} %>
                    </li>
                </ul>
                <br><br>
                
                    
            </div>
            <div id="post_box">

            	<ul>
            		<li class="post_name">
                        <p>參考影片:</p>
                        <select name="refVideo1">
                            <c:forEach var="videoVO" items="${videoSvc.getByUserID(userID)}">
                            <option value="${videoVO.videoID}">${videoVO.title}</option>
                            </c:forEach>
                        </select>

                    </li><br>
                    <li class="post_name">
                        <p>參考影片:</p>
                        <select name="refVideo2">
                            <c:forEach var="videoVO" items="${videoSvc.getByUserID(userID)}">
                            <option value="${videoVO.videoID}">${videoVO.title}</option>
                            </c:forEach>
                        </select>
                        
                    </li><br>
                    <li class="post_name">
                        <p>參考影片:</p>
                        <select name="refVideo3">
                            <c:forEach var="videoVO" items="${videoSvc.getByUserID(userID)}">
                            <option value="${videoVO.videoID}">${videoVO.title}</option>
                            </c:forEach>
                        </select>

                    </li>
            	</ul>
                        
            </div>
            <div id="button_area">
                <button type="submit" id="button" class="btn btn-primary btn-lg">確認</button>
				<button id="cancel" type="button" class="btn btn-outline-danger">取消</button>
            </div>
            <input type="hidden" name="action"  value="update">
            <input type="hidden" name="userID" value="${userID}">
            </form>
        </div>
    </div>
    
    	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

	<script>

		$("#cancel").click(function() {
			console.log("cancel");

			var yes = confirm('你確定要取消嗎?');
			if (yes) {
				console.log("yse");
				alert("按是返回"); 
				window.history.back(-1); 
			} else {
				console.log("no");
			}
		});
	</script>
</body>
</html>