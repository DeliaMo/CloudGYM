<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.posts.model.*"%>
<%@ page import="redis.clients.jedis.Jedis"%>

<%
	PostsVO postsVO = (PostsVO) request.getAttribute("postsVO");

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
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
 	if(session.getAttribute("userID")==null){         
%>
		<script>
 		if (window.confirm('是否要登入會員?')){
 			window.location.replace("http://localhost:8081/CloudGYM/html/login/login_user.jsp");
         }else{
         	alert('非會員無法發文，僅能瀏覽');
         	document.location.href="http://localhost:8081/CloudGYM/html/article/ArticleList.jsp";
         }
 		</script>
<% 
 	}
%> 


<jsp:useBean id="tagSvc" scope="page" class="com.tag.model.TagService" />

<!DOCTYPE html>
<html lang="ZH-TW">
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新增文章</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/reset.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/AddArticle.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
	crossorigin="anonymous"></script>
<style>
div#title ul {
	padding-left: 0;
}

div.sub ul {
	padding-left: 0;
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

/****************** bar css end ******************/
</style>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
</head>

<body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
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
	<!-- header -->
	<div id="bar">
		<div id="title">
			<ul>
				<li class="bar_li">
				<img src="<%=request.getContextPath()%>/img/logo.png" alt="" for="#CloudGYM"></li>
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
						<a href="<%=request.getContextPath()%>/html/user/protected_user/userMainPage.jsp?userID=${userID}">個人專區</a>
					</c:if> <c:if test="${ empty userID}">個人專區</c:if></li>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/article/ArticleList.jsp">討論區</a></li>
				<c:if test="${empty userID}">
					<div class="item">
						<div class="main">註冊/登入</div>
						<div class="sub">
							<ul>
								<li><a href="${pageContext.request.contextPath}/html/login/sign_up_page.jsp">會員註冊</a></li>
								<li><a href="${pageContext.request.contextPath}/html/login/login_user.jsp">會員登入</a></li>
								<li><a href="${pageContext.request.contextPath}/html/login/login_coach.jsp">教練登入</a></li>
								<li><a href="${pageContext.request.contextPath}/html/login/login_admin.jsp" target="_blank">後台管理</a></li>
							</ul>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty userID}">
					<div class="item">
						<div class="main" id="logout">
							<a href="javascript:if (confirm('確認登出?')) location.href='<%=request.getContextPath()%>/LogoutHandler'">${name} 登出</a>
						</div>
						<div class="sub"></div>
					</div>
				</c:if>
				<li class="option">
				<a href="<%=request.getContextPath()%>/html/order/pay_page.jsp"> 
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
	<!-- header_end -->
<!-- <div></div> -->
	<div class="container" style="padding-top: 80px; background-color: #31105E;">
		<div class="row">
			<div class="col">
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
		</div>
	</div>


	<!-- main -->
	<div class="container">
		<h1>新增文章</h1>
		<hr>

		<form class="row" METHOD="post" ACTION="<%=request.getContextPath()%>/html/Article.do" name="form1" enctype="multipart/form-data">

			<div class="col">

				<input type="hidden" name="userid" value="${userID}" />

				<div class="mb-3">
					<label class="form-label">文章標題</label> <input type="text" class="form-control" name="poststitle" value="<%=(postsVO == null) ? "" : postsVO.getPostsTitle()%>" />
					<div class="form-text">文章標題請勿空白，字數限20字以內。</div>
				</div>

				<div class="mb-3">
					<label class="form-label">文章圖片</label> <input type="file" class="form-control" name="postsimg" onchange="openFile(event)">
					<div class="form-text">請上傳文章圖片</div>
					<img id="output" height="300" style="display: none">
				</div>

				<!-- 				<div class="mb-3"> -->
				<!-- 					<label class="form-label">文章內容</label>  -->
				<%-- 					<input type="TEXT" class="form-control" name="postscontent" value="<%=(postsVO == null) ? "" : postsVO.getPostsContent()%>" /> --%>
				<!-- 					<div class="form-text">文章內容請勿空白，字數限2000字以內。</div> -->
				<!-- 				</div> -->

				<div class="mb-3">
					<label class="form-label">文章內容</label>
					<textarea id="editor" class="form-control" name="postscontent" value="<%=(postsVO == null) ? "" : postsVO.getPostsContent()%>" rows="40" /></textarea>
					<div class="form-text">文章內容請勿空白，字數限2000字以內。</div>
				</div>


				<div class="mb-3">
					<label class="form-label">文章類型</label> 
					<br> 
					<select class="form-select" aria-label="Default select example" name="tagid">
						<c:forEach var="tagVO" items="${tagSvc.all}">
							<option value="${tagVO.tagID}"
								${(postsVO.tagID==tagVO.tagID)? 'selected':'' }>
								${tagVO.tagName}
						</c:forEach>
					</select>
					<div class="form-text">請選擇文章類型</div>
				</div>

			</div>


			<div class="subA">
				<button type="submit" class="btn btn-outline-primary" name="action" value="insert">確認</button>
				<button id="cancel" type="button" class="btn btn-outline-danger">取消</button>
			</div>

		</form>

	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script
		src="https://cdn.ckeditor.com/ckeditor5/30.0.0/classic/ckeditor.js"></script>
	<script>
		//編輯器
	    ClassicEditor
	    .create(document.querySelector('#editor'))
	    .then(editor => { 
	    	console.log(editor); 
	    	});
	    
        $(function () { CKEDITOR.replace('#editor', { height: '805px', width: '1318px' }); });
	
        // 購物車
		var cartCount = ${cartCount};
	    if(cartCount == 0){
	    	$("i.bi-cart-fill span").addClass("-on");
	    	$("i.bi-cart-fill span").attr("style", "display:none");
	    }else{
	    	$("i.bi-cart-fill span").removeClass("-on");
	    	$("i.bi-cart-fill span").attr("style", "");
	    }

		// 預覽圖
		function openFile(event) {
			var input = event.target; //取得上傳檔案
			var reader = new FileReader(); //建立FileReader物件

			reader.readAsDataURL(input.files[0]); //以.readAsDataURL將上傳檔案轉換為base64字串

			reader.onload = function() { //FileReader取得上傳檔案後執行以下內容
				var dataURL = reader.result; //設定變數dataURL為上傳圖檔的base64字串
				$('#output').attr('src', dataURL).show(); //將img的src設定為dataURL並顯示
			};
		}
		
		// 新增取消
        $("#cancel").click(function(){
            var yes = confirm('確定要取消新增文章嗎?');
            if (yes) {
				history.back()
            } else {
            	console.log("no");
            }
        });

	</script>
</body>
</html>