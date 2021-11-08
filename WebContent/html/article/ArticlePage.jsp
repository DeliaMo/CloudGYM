<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.posts.model.*"%>
<%@ page import="com.comment.model.*"%>
<%@ page import="com.user.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.video.model.*"%>
<%@ page import="com.report.model.*" %>
<%@ page import="redis.clients.jedis.Jedis" %>

<jsp:useBean id="userSvc" scope="page" class="com.user.model.UserService" />
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" />

<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache");        //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>

<%	
	VideoService videoSvc = new VideoService();
	List<VideoVO> video = videoSvc.getRecommendedVideos();
	pageContext.setAttribute("video", video);
	
	PostsService postsSvc = new PostsService();
	PostsVO postsVO = postsSvc.getByPostsID(new Integer(request.getParameter("postsID")));
	pageContext.setAttribute("postsVO", postsVO);

	CommentService commentSvc = new CommentService();
	List<CommentVO> list = commentSvc.getAll();
	pageContext.setAttribute("list", list);

	UserService userSvc１ = new UserService();
	UserVO userVO = userSvc１.findByUserId(postsVO.getUserID());

	CoachService coachSvc1 = new CoachService();
	CoachVO coachVO = coachSvc1.getByUserID(postsVO.getUserID());
	
	List<PostsVO> more = postsSvc.getMorePost();
	pageContext.setAttribute("more", more);
	
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

// 	Integer userid = 1005;
// 	session.setAttribute("userID", userID);
	String username = "";
	if(userID != null && userID.startsWith("1")){
		UserVO userVO1 = userSvc.findByUserId(Integer.parseInt(userID));
		username = userVO1.getUserName();
	}
	if(userID != null && userID.startsWith("2")){
		CoachVO coachVO1 = coachSvc.getByUserID(Integer.parseInt(userID));
		username = coachVO1.getCoachName();
		
	}
	
	ReportService reportSvc = new ReportService();
	List<ReportVO> reports = reportSvc.getByUser(Integer.parseInt(userID));
	List<Integer> items = new ArrayList<Integer>();
	for(ReportVO reportVO : reports){
		items.add(reportVO.getItemID());
	}
	request.setAttribute("items", items);
%>

<!DOCTYPE html>
<html lang="ZH-TW">
<head>
<style>
div#title ul{
	padding-left: 0;
}

div.sub ul{
	padding-left: 0;
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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CloudGYM討論區</title>
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ArticlePage.css">
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
	<!-- header -->
	<div id="bar">
		<div id="title">
			<ul>
				<li class="bar_li"><img src="<%=request.getContextPath()%>/img/logo.png" alt="" for="#CloudGYM"></li>
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
	<!-- header_end -->

	<!-- main -->
	<c:if test="${userID == postsVO.getUserID()}">
	<div id="updatearticle">
		<div class="container">
			<div class="row">
				<form class="row" METHOD="post" ACTION="<%=request.getContextPath()%>/html/Article.do" name="form1">
					<div class="col">
						<input type="hidden" name="page" value="APG">
						<input type="hidden" name="postsid" value="<%=postsVO.getPostsID()%>">
						<button type="submit" class="btn btn-outline-light me-4" name="action" value="getOne_For_Update">
							<i class="far fa-edit me-2"></i>編輯文章
						</button>
						<button type="submit" class="btn btn-outline-light" name="action" value="delete">
							<i class="fas fa-trash-alt me-2"></i>刪除文章
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	</c:if>

	<div class="container pt-5">
		<div class="row">

			<div class="col-8">
				<article>
					<header class="mb-4">
						<h1 class="fw-bolder mb-1" style="color: rgb(132, 145, 145)"><%=postsVO.getPostsTitle()%></h1>
						<div class="text-muted mb-2">作者：
							${coachSvc.getByUserID(postsVO.userID).coachName}
							${userSvc.findByUserId(postsVO.userID).userName}
						</div>
					</header>
					<figure class="mb-4">
						<img class="img-fluid rounded" src="<%=request.getContextPath()%>/Reader?id=<%=postsVO.getPostsID()%>" alt="..." />
					</figure>
					<section class="mb-5">
						<p class="fs-5 mb-4"><%=postsVO.getPostsContent()%></p>
					</section>
					<p class="text-muted" style="text-align: right">
						發表時間：
						<fmt:formatDate value="<%=postsVO.getPostsPublishDate()%>" pattern="yyyy-MM-dd HH:mm" />
					</p>
				</article>
				<hr>
				<div class="icon">
					<!-- 按讚按鈕 -->
					<form id="addlikes"> <!--<form METHOD="post" action="likes.do">-->
						<input type="hidden" name="postsid" value="<%=postsVO.getPostsID()%>">
						<input type="hidden" name="userid" value="${userID}"> 
						<input type="hidden" value="insert" name="action">
						<button id="aa" class="far fa-thumbs-up" type="button"></button>
					</form>
					
					<!-- 收藏按鈕 -->
					<form id="addcollection">
						<input type="hidden" name="userid" value="${userID}">
						<input type="hidden" name="itemid" value="<%=postsVO.getPostsID()%>">
						<input type="hidden" name="action" value="insert">
						<button id="bb" class="far fa-bookmark" type="button"></button>
					</form>
					
					<!-- 分享按鈕 -->
						<button id="cc" class="fas fa-share-alt" data-clipboard-text="http://localhost:8081<%=request.getContextPath()%>/html/article/ArticlePage.jsp?postsID=<%=postsVO.getPostsID()%>"></button>
					<!-- 檢舉按鈕 -->
						
					<form>
				      <%if(!items.contains(postsVO.getPostsID())){%>
					       <button id="dd" type="button" class="btn btn-outline-danger" style="">檢舉</button>
					       <input class="postsAction" type="hidden" name="action" value="addreport">
				      <%}else{%>
					       <button id="dd" type="button" class="btn btn-outline-dark" style="background-color:gray; color:white; border:none;">已檢舉</button>
					       <input class="postsAction" type="hidden" name="action" value="deleteReport">
				      <%} %>
				    	   <input type="hidden" name="postsID" value="<%=postsVO.getPostsID()%>"> 
    				</form>
				</div>
				<hr>
				<!-- 相似文章 -->
				<div class="mt-3 mb-4">
					<h5 style="font-weight:bold;">相似文章</h5>
					<ol class="more">
						<c:forEach var="PostsVO" items="${more}" begin="0" end="15">
						<c:if test="${PostsVO.tagID == postsVO.getTagID() && PostsVO.postsID != postsVO.getPostsID()}">
							<li><a href="<%=request.getContextPath()%>/html/article/ArticlePage.jsp?postsID=${PostsVO.postsID}">${PostsVO.postsTitle}</a></li>
						</c:if>
						</c:forEach>
					</ol>				
				</div>
				
				
				<!-- 留言區塊 -->
				<section class="mb-5">
					<div class="card bg-light">
						<div id="com" class="card-body">
							<!-- 發文表單 -->
							<form id="add"><!-- <form class="mb-2" METHOD="post" action="comment.do"> -->
								<input type="hidden" name="postsid" value="<%=postsVO.getPostsID()%>"> 
									<input type="hidden" name="userid" value="${userID}"> 
									<input type="hidden" value="insert" name="action">
								<textarea class="form-control" name="commentcontent" rows="2" placeholder="撰寫公開留言..."></textarea>
								<button id="newcomment" type="button" class="btn btn-secondary">新增留言</button>
							</form>
							<hr>
							<!-- 留言內容 -->
							<c:forEach var="commentVO" items="${list}">
								<c:if test="${commentVO.postsID == postsVO.postsID}">
									<div class="d-flex mb-1">
										<div class="ms-3">
											<div class="fw-bold">
											${coachSvc.getByUserID(commentVO.userID).coachName}
											${userSvc.findByUserId(commentVO.userID).userName}
											<form>
											<c:if test="${!items.contains(commentVO.commentID) }">
												<button id="ff" type="button" class="btn btn-outline-danger" style="font-size: 10px; position: absolute; right: 27px;">檢舉</button>
												<input class="commentAction" type="hidden" name="action" value="addreport">
											</c:if>
											<c:if test="${items.contains(commentVO.commentID) }">
												<button id="ff" type="button" class="btn btn-outline-danger" style="font-size: 10px; position: absolute; right: 27px; background-color:red; color:white;">已檢舉</button>
												<input class="commentAction" type="hidden" name="action" value="deleteReport">
											</c:if>
												
												<input type="hidden" name="commentID" value="${commentVO.commentID}">
												
											</form>
											</div>					
											${commentVO.commentContent}
											<br> <!-- <form class="mb-2" METHOD="post" action="comment.do"> -->
<!-- 											<form METHOD="post" action="comment.do"> -->
											<form METHOD="post" action="<%=request.getContextPath()%>/html/comment.do">
												<span style="font-size: 10px; color: rgb(155, 151, 151);">
													<fmt:formatDate value="${commentVO.commentPublishDate}" pattern="yyyy-MM-dd HH:mm:ss" />
												</span>
<!-- 												<button type="submit" class="btn btn-secondary" style="font-size: 10px; margin-left: 560px;" value="">修改</button> -->
												<input type="hidden" name="page" value="APG">
												<input type="hidden" name="commentid" value="${commentVO.commentID}">
												<c:if test="${userID == commentVO.getUserID()}">
												<button type="submit" class="btn btn-secondary" style="font-size: 10px; margin-left: 628px;" value="delete" name="action">刪除</button>
												</c:if>
											</form>
										</div>
									</div>
									<hr>
								</c:if>
							</c:forEach>
							<!-- 留言內容end -->
						</div>
					</div>
				</section>
				<!-- 留言區塊end -->
			</div>

			<!-- 右側區塊 -->
			<div class="col-4">
				<!--搜尋 -->
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-search me-2"></i>搜尋文章
					</div>
					<form METHOD="post" ACTION="Article.do">
						<div class="card-body">
							<div class="input-group">
								<input class="form-control" type="text" placeholder="請輸入關鍵字" aria-describedby="button-search" name="str" autocomplete="off" />
								<button class="btn btn-outline-dark" id="button-search" type="submit" name="action" value="search">搜尋</button>
							</div>
						</div>
					</form>
				</div>
				<!--分類 -->
				<div class="card mb-4">
					<div class="card-header">
						<i class="fab fa-hotjar me-2"></i>文章分類
					</div>
					<div class="feed">
						<a href="ArticleList_A.jsp"><button type="button" class="btn btn-outline-secondary">健身知識</button></a> 
						<a href="ArticleList_B.jsp"><button type="button" class="btn btn-outline-secondary">健康飲食</button></a> 
						<a href="ArticleList_C.jsp"><button type="button" class="btn btn-outline-secondary">成果分享</button></a> 
						<a href="ArticleList_D.jsp"><button type="button" class="btn btn-outline-secondary">商品推薦</button></a> 
						<a href="ArticleList_E.jsp"><button type="button" class="btn btn-outline-secondary">綜合閒聊</button></a>
					</div>
				</div>
				<!-- 影片推薦 -->
				<div class="card mb-4">
                    <div class="card-header mb-4"><i class="fas fa-dumbbell me-2"></i>課程推薦</div>
                    <c:forEach var="VideoVO" items="${video}" begin="0" end="2">
                    <a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${VideoVO.videoID}" class="mx-4 mb-4">
                        <div class="card bg-dark text-white">
                            <video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${VideoVO.videoID}" width="100%" height="100%"></video>
                            <div class="card-img-overlay">
                                <h5 class="card-title">${VideoVO.title}</h5>
                            </div>
                        </div>
                    </a>
                    </c:forEach>
                </div>	
			</div>
			<!-- 右側區塊end -->
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
		crossorigin="anonymous"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/clipboard@2.0.8/dist/clipboard.min.js"></script>

	<script>
	
	//留言AJAX
	$(function(){

	    var cartCount = ${cartCount};
	    if(cartCount == 0){
	    	$("i.bi-cart-fill span").addClass("-on");
	    	$("i.bi-cart-fill span").attr("style", "display:none");
	    }else{
	    	$("i.bi-cart-fill span").removeClass("-on");
	    	$("i.bi-cart-fill span").attr("style", "");
	    }
		
		
		$("#newcomment").on("click",function(){
	 		console.log($(this).closest("from"));
			$.ajax({
				url : "<%=request.getContextPath()%>/html/comment.do",
				type : "post",
				data: $("form#add").serialize(),
				dataType : "json",
					success : function(data) {
						console.log(data);
						let userid = data[0];
						let content = data[1];
						let timestamp = data[2];
						let commentID = data[3];
						let list_html = "";
						list_html += '<div class="d-flex mb-1">';
						list_html += '<div class="ms-3">';
						list_html += '<div class="fw-bold"><%=username%>';
						list_html += '<form>';
						list_html += '<button id="ff" type="button" class="btn btn-outline-danger" style="font-size: 10px; margin-left: 740px;">檢舉</button>';
						list_html += '<input class="commentAction" type="hidden" name="action" value="addreport">';
						list_html += '<input type="hidden" name="commentID" value="' + commentID + '">';
						list_html += '</form>';
						list_html += '</div>';
						list_html += content + '<br> <span style="font-size: 10px; color: rgb(155, 151, 151);">';
						list_html += '<span>' + timestamp + '</span>';
// 						list_html += '<button type="submit" class="btn btn-secondary" style="font-size: 10px; margin-left: 560px;" value="">修改</button>';
						list_html += '<button type="submit" class="btn btn-secondary" style="font-size: 10px; margin-left: 628px;" value="">刪除</button>';
						list_html += '</div>';
						list_html += '</div>';
						list_html += '<hr>';
						$("#com").append(list_html);
						$("textarea.form-control").val("");
					},
					error : function(XMLHttpResponse, textStatus, errorThrown) {
						console.log("1 非同步呼叫返回失敗,XMLHttpResponse.readyState:"	+ XMLHttpResponse.readyState);
						console.log("2 非同步呼叫返回失敗,XMLHttpResponse.status:"	+ XMLHttpResponse.status);
						console.log("3 非同步呼叫返回失敗,textStatus:"	+ textStatus);
						console.log("4 非同步呼叫返回失敗,errorThrown:" + errorThrown);
					}
			});
		});
		
		// 檢舉文章ajax
		$("button#dd").on("click", function(){
			console.log("here");
			$.ajax({
				url: "<%=request.getContextPath()%>/report/report.do",
				type: "post",
				data: $(this).closest("form").serialize(),
				dataType: "json",
				success: function(data){
					
					if(data[0] == "cancelSuccess"){
						alert("已取消檢舉");
						$("button#dd").attr("style", "");
						$("button#dd").html("檢舉");
						$("input.postsAction").val("addreport");
					}
					if(data[0] == "reportSuccess"){
						alert("檢舉成功");
						$("button#dd").attr("style", "background-color:gray; color:white; border:none;");
						$("button#dd").html("已檢舉");
						$("input.postsAction").val("deleteReport");
					}
				},
				error: function(xhr){
					console.log("fail");
				}
			})
		})
		
		// 檢舉留言ajax
		 // 檢舉留言ajax
		  $("div#com").on("click", "button#ff", function(){
		  console.log("here");
		  var that = $(this);
		  $.ajax({
			  url: "<%=request.getContextPath()%>/report/report.do",
			  type: "post",
			  data: $(this).closest("form").serialize(),
			  dataType: "json",
			  success: function(data){
			  	console.log("success");
		     
				  if(data[0] == "cancelSuccess"){
					  alert("已取消檢舉");
					  $(that).attr("style", "font-size: 10px; margin-left: 740px;");
					  $(that).html("檢舉");
					  $(that).next().val("addreport");
				  }
				  if(data[0] == "reportSuccess"){
					  alert("檢舉成功");
					  $(that).attr("style", "font-size: 10px; margin-left: 730px; background-color:red; color:white;");
					  $(that).html("已檢舉");
					  $(that).next().val("deleteReport");
				  }
		  	},
			  error: function(xhr){
			  console.log("fail");
			  }
		  })
		  
		 });
		
	});
	
	//按讚AJAX
	$("#aa").on("click",function(){
		$.ajax({
		  	url: "<%=request.getContextPath()%>/html/likes.do",
			type : "POST",
			data : $("form#addlikes").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				$("#aa").removeClass("far fa-thumbs-up");
				$("#aa").addClass("fas fa-thumbs-up");
				$("#aa").attr("disabled", true);
				},
			error : function(XMLHttpResponse, textStatus, errorThrown) {
				console.log("1 非同步呼叫返回失敗,XMLHttpResponse.readyState:"	+ XMLHttpResponse.readyState);
				console.log("2 非同步呼叫返回失敗,XMLHttpResponse.status:"	+ XMLHttpResponse.status);
				console.log("3 非同步呼叫返回失敗,textStatus:"	+ textStatus);
				console.log("4 非同步呼叫返回失敗,errorThrown:" + errorThrown);		
				}
			});
		});
	
	//收藏AJAX
	$("#bb").on("click", function(){
		$.ajax({
		  	url: "<%=request.getContextPath()%>/html/collection.do",
			type : "POST",
			data : $("form#addcollection").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				$("#bb").removeClass("far fa-bookmark");
				$("#bb").addClass("fas fa-bookmark");
				$("#bb").attr("disabled", true);
				},
			error : function(XMLHttpResponse, textStatus, errorThrown) {
				console.log("1 非同步呼叫返回失敗,XMLHttpResponse.readyState:"	+ XMLHttpResponse.readyState);
				console.log("2 非同步呼叫返回失敗,XMLHttpResponse.status:"	+ XMLHttpResponse.status);
				console.log("3 非同步呼叫返回失敗,textStatus:"	+ textStatus);
				console.log("4 非同步呼叫返回失敗,errorThrown:" + errorThrown);		
				}
			});
		});

	//複製連結
	$("#cc").click(function() {
		new ClipboardJS('#cc');
		alert("連結已複製");
	});
	
	</script>


</body>

</html>