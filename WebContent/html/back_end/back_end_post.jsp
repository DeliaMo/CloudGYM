<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.posts.model.*"%>

<jsp:useBean id="userSvc" scope="page" class="com.user.model.UserService" />
<jsp:useBean id="adminSvc" scope="page" class="com.admin.model.AdminService" />
<jsp:useBean id="coachSvc" scope="page"	class="com.coach.model.CoachService" />
<jsp:useBean id="commSvc" scope="page"	class="com.comment.model.CommentService" />
<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache");        //HTTP 1.0
  response.setDateHeader ("Expires", 0);
%>
<%
	PostsService postSvc = new PostsService();
	List<PostsVO> list = postSvc.getAll2();
	pageContext.setAttribute("list", list);
	session.getAttribute("adminNo");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>文章管理</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_post.css">
</head>
<body>
<!-- 	<div id="bar"> -->
<!-- 		<div id="title"> -->
<!-- 			<ul> -->
<%-- 				<li class="bar_li"><img src="<%=request.getContextPath()%>/img/logo.png" alt="" --%>
<!-- 					for="#CloudGYM"></li> -->
<%-- 				<li class="bar_li"><a href="<%=request.getContextPath()%>/main_page.jsp" id="CloudGYM">CloudGYM</a></li> --%>
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 		<div id="option"> -->
<!-- 			<ul> -->
<!-- 				<li class="option"><a class="logout" href="#">登出</a></li> -->
<!-- 				<li class="option"><a class="login_ad" -->
<%-- 					href="<%=request.getContextPath()%>/html/back_end_Admin.jsp">管理員</a></li> --%>
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<%@ include file="/html/back_end/bar_admin.file" %>
	<div id="wrap">
		<div id="left">
			<ul id="btn_fa">
				<li>
				<c:if test="${adminSvc.getOneAdmin(adminNo).videoAuth == 1 }">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_video.jsp">
					<span class="li_btn">影片管理</span></a> 
				</c:if>
				<c:if test="${adminSvc.getOneAdmin(adminNo).videoAuth == 0 }">
					<span id="ban_video_li" class="li_btn">
						<font style="color: #ccc">影片管理</font>
					</span>
				</c:if>
				</li>
				<li>
				<a href="<%=request.getContextPath()%>/html/back_end/back_end_order.jsp"> 
					<span class="li_btn">訂單查詢</span></a>
				</li>
				<li>
				<c:if test="${adminSvc.getOneAdmin(adminNo).commentAuth == 1 }">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_post.jsp">
					<span class="li_btn">文章管理</span></a>
				</c:if>
				<c:if test="${adminSvc.getOneAdmin(adminNo).commentAuth == 0 }">
					<span id="ban_post_li" class="li_btn">
						<font style="color: #ccc">文章管理</font>
					</span>
					</c:if>
				</li>
				<li>
				<c:if test="${adminSvc.getOneAdmin(adminNo).userAuth == 1 }">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_user.jsp">
					<span class="li_btn">會員管理</span></a>
				</c:if>
				<c:if test="${adminSvc.getOneAdmin(adminNo).userAuth == 0 }">
					<span id="ban_user_li" class="li_btn">
						<font style="color: #ccc">會員管理</font>
					</span>
				</c:if>
				</li>
				<li>
				<c:if test="${adminSvc.getOneAdmin(adminNo).userAuth == 1 }">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_coach.jsp">
					<span class="li_btn">教練管理</span></a>
				</c:if>
				<c:if test="${adminSvc.getOneAdmin(adminNo).userAuth == 0 }">
					<span id="ban_coach_li" class="li_btn">
						<font style="color: #ccc">教練管理</font>
					</span>
				</c:if>
				</li>
				<li>
				<c:if test="${adminSvc.getOneAdmin(adminNo).subAuth == 1 }">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_sublist.jsp">
					<span class="li_btn">訂閱管理</span></a>
				</c:if>
				<c:if test="${adminSvc.getOneAdmin(adminNo).subAuth == 0 }">
					<span id="ban_sub_li" class="li_btn">
						<font style="color: #ccc">訂閱管理</font>
					</span>
				</c:if>
				</li>
			</ul>
		</div>
		<p>文章管理</p>
		<div id="right">
			<div class="main">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">編號</th>
							<th scope="col">文章ID</th>
							<th scope="col">文章標題</th>
							<th scope="col">上傳者</th>
							<th scope="col">上傳時間</th>
							<th scope="col">被檢舉次數</th>
							<th scope="col">文章留言數</th>
						</tr>
					</thead>
					<tbody>
						<%--                     <%@ include file="/pages/page1.file" %> --%>
						<%
							int rowsPerPage = 10; //每頁的筆數    
							int rowNumber = 0; //總筆數
							int pageNumber = 0; //總頁數      
							int whichPage = 1; //第幾頁
							int pageIndexArray[] = null;
							int pageIndex = 0;
						%>

						<%
							rowNumber = list.size();
							if (rowNumber % rowsPerPage != 0)
								pageNumber = rowNumber / rowsPerPage + 1;
							else
								pageNumber = rowNumber / rowsPerPage;

							pageIndexArray = new int[pageNumber];
							for (int i = 1; i <= pageIndexArray.length; i++)
								pageIndexArray[i - 1] = i * rowsPerPage - rowsPerPage;
						%>

						<%
							try {
								whichPage = Integer.parseInt(request.getParameter("whichPage"));
								pageIndex = pageIndexArray[whichPage - 1];
							} catch (NumberFormatException e) { //第一次執行的時候
								whichPage = 1;
								pageIndex = 0;
							} catch (ArrayIndexOutOfBoundsException e) { //總頁數之外的錯誤頁數
								if (pageNumber > 0) {
									whichPage = pageNumber;
									pageIndex = pageIndexArray[pageNumber - 1];
								}
							}
						%>
						<%
							int number = pageIndex + 1;
						%>
						<c:forEach var="postsVO" items="${list}" begin="<%=pageIndex%>"	end="<%=pageIndex+rowsPerPage-1%>">
							<tr	data-href="<%=request.getContextPath()%>/html/back_end/back_end_post_page.jsp?postID=${postsVO.postsID}">
								<th scope="row">
								<c:choose>
									<c:when test="${postsVO.postsReportedTimes > 2 && postsVO.postsShow == true}">
										<font style="color: red"><%=number++%></font>
									</c:when>
									<c:otherwise>
										<%=number++%>
									</c:otherwise>
								</c:choose>
								</th>
								<td>
								<c:choose>
									<c:when test="${postsVO.postsReportedTimes > 2 && postsVO.postsShow == true}">
										<font style="color: red">${postsVO.postsID}</font>
									</c:when>
									<c:otherwise>
										${postsVO.postsID}
									</c:otherwise>
								</c:choose>
								</td>
								<td style="text-align: left">
								<c:choose>
									<c:when test="${postsVO.postsReportedTimes > 2 && postsVO.postsShow == true}">
										<font style="color: red">${postsVO.postsTitle}</font>
									</c:when>
									<c:otherwise>
										${postsVO.postsTitle}
									</c:otherwise>
								</c:choose>
								<td>
								<c:choose>
									<c:when test="${postsVO.postsReportedTimes > 2 && postsVO.postsShow == true}">
										<font style="color: red">${postsVO.userID} -
										${userSvc.findByUserId(postsVO.userID).userName}
										${coachSvc.getByUserID(postsVO.userID).coachName}
									</font>
									</c:when>
									<c:otherwise>
										${postsVO.userID}-
										${userSvc.findByUserId(postsVO.userID).userName}
										${coachSvc.getByUserID(postsVO.userID).coachName}
									</c:otherwise>
								</c:choose>
								</td>
								<td>
								<c:choose>
									<c:when test="${postsVO.postsReportedTimes > 2 && postsVO.postsShow == true}">
										<font style="color: red">${postsVO.postsPublishDate}</font>
									</c:when>
									<c:otherwise>
										${postsVO.postsPublishDate}
									</c:otherwise>
								</c:choose>
								</td>
								<td>
								<c:choose>
									<c:when test="${postsVO.postsReportedTimes > 2 && postsVO.postsShow == true}">
										<font style="color: red">${postsVO.postsReportedTimes}</font>
									</c:when>
									<c:otherwise>
										${postsVO.postsReportedTimes}
									</c:otherwise>
								</c:choose>
								</td>
								<td>${commSvc.getCountComment(postsVO.postsID)}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="page">
					<%
						for (int i = 0; i < pageIndexArray.length; i++) {
					%>
					<a href="<%request.getRequestURI();%>?whichPage=<%=i + 1%>"><%=i + 1%></a>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>

	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/back_end_post.js"></script>
	<script>
		$(function(){
	        $('#ban_video_li').on('click',function(){
	            if(${adminSvc.getOneAdmin(adminNo).videoAuth == 0}){
	            alert("您沒有管理影片的權限!");
	                }
	            })
	        $('#ban_user_li').on('click',function(){
	            if(${adminSvc.getOneAdmin(adminNo).userAuth == 0}){
	            alert("您沒有管理會員的權限!");
	                } 
	            })
	            
		    $('#ban_coach_li').on('click',function(){
		        if(${adminSvc.getOneAdmin(adminNo).userAuth == 0}){
		        alert("您沒有管理教練的權限!");
		             } 
		        })
		        
			 $('#ban_post_li').on('click',function(){
			    if(${adminSvc.getOneAdmin(adminNo).commentAuth == 0}){
			    alert("您沒有管理文章的權限!");
			          } 
			     })
			     
			$('#ban_sub_li').on('click',function(){
			 	if(${adminSvc.getOneAdmin(adminNo).subAuth == 0}){
				alert("您沒有管理訂閱的權限!");
					  } 
			   })	
		})
		
		</script>
</body>
</html>