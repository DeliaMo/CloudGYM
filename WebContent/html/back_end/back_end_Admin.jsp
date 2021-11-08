<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.admin.model.*"%>
<%
	AdminService adminSvc = new AdminService();
	List<AdminVO> list = adminSvc.getAll();
	pageContext.setAttribute("list", list);
// 	session.setAttribute("adminNo", 9001);
	Integer adminNo = (Integer) session.getAttribute("adminNo");
	System.out.println(adminSvc.getOneAdmin(adminNo).getAdminName());
%>

<c:if test="${adminNo == 9001}">
	<c:set var="states" value="true"></c:set>
</c:if>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>管理員表單</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_Admin.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
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
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getVideoAuth() == 1 %>">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_video.jsp">
					<span class="li_btn">影片管理</span></a> 
				</c:if>
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getVideoAuth() == 0 %>">
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
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getCommentAuth() == 1 %>">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_post.jsp">
					<span class="li_btn">文章管理</span></a>
				</c:if>
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getCommentAuth() == 0 %>">
					<span id="ban_post_li" class="li_btn">
						<font style="color: #ccc">文章管理</font>
					</span>
					</c:if>
				</li>
				<li>
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getUserAuth() == 1 %>">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_user.jsp">
					<span class="li_btn">會員管理</span></a>
				</c:if>
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getUserAuth() == 0 %>">
					<span id="ban_user_li" class="li_btn">
						<font style="color: #ccc">會員管理</font>
					</span>
				</c:if>
				</li>
				<li>
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getUserAuth() == 1 %>">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_coach.jsp">
					<span class="li_btn">教練管理</span></a>
				</c:if>
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getUserAuth() == 0 %>">
					<span id="ban_coach_li" class="li_btn">
						<font style="color: #ccc">教練管理</font>
					</span>
				</c:if>
				</li>
				<li>
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getSubAuth() == 1 %>">
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_sublist.jsp">
					<span class="li_btn">訂閱管理</span></a>
				</c:if>
				<c:if test="<%=adminSvc.getOneAdmin(adminNo).getSubAuth() == 0 %>">
					<span id="ban_sub_li" class="li_btn">
						<font style="color: #ccc">訂閱管理</font>
					</span>
				</c:if>
				</li>
			</ul>
		</div>

		<div id="right">
			<div class="main">
				<a href="<%=request.getContextPath()%>/html/back_end/back_end_Admin_page.jsp">
					<button class="update" type="submit">
						<i class="bi bi-pencil-fill"></i>
					</button>
				</a>
				<c:forEach var="adminVO" items="${list}">
					<c:if test="${adminVO.adminID != adminNo}">
						<div class="main-out">
							<div class="admin">管理員</div>
							<div class="name">
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/html/admin.do" name="form1">
									<ul>
										<li class="name-li">ID: <span>${adminVO.adminID}</span></li>
										<li class="name-li">姓名: <span>${adminVO.adminName}</span></li>
										<li class="name-li">權限: 
										<span><input type="checkbox" name="commentAuth" value="${adminVO.commentAuth}"
												${adminVO.commentAuth == 1 ? "checked='true'" : ""}
												${states == true ?  "" : 'disabled'}>文章審核</span> 
										<span><input type="checkbox" name="videoAuth" value="${adminVO.videoAuth}"
												${adminVO.videoAuth == 1 ? "checked='true'" : ""}
												${states == true ? "" : 'disabled'}>影片審核</span> 
										<span><input type="checkbox" name="subAuth" value="${adminVO.subAuth}"
												${adminVO.subAuth == 1 ? "checked='true'" : ""}
												${states == true ? "" : 'disabled'}>方案調整</span> 
										<span><input type="checkbox" name="userAuth" value="${adminVO.userAuth}"
												${adminVO.userAuth == 1 ? "checked='true'" : ""}
												${states == true ? "" : 'disabled'}>會員/教練封鎖</span> 
										<c:if test="${states == true}">
												<input type="hidden" name="action" value="updatebyAuth"> 
												<input type="hidden" name="id" value="${adminVO.adminID}"> 
												<input type="submit" value="送出修改">
										</c:if>
									</ul>
								</Form>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
		<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
		<script>
		$(function(){
	        $('#ban_video_li').on('click',function(){
	            if(<%=adminSvc.getOneAdmin(adminNo).getVideoAuth() == 0 %>){
	            alert("您沒有管理影片的權限!");
	                }
	            })
	        $('#ban_user_li').on('click',function(){
	            if(<%=adminSvc.getOneAdmin(adminNo).getUserAuth() == 0 %>){
	            alert("您沒有管理會員的權限!");
	                } 
	            })
	            
		    $('#ban_coach_li').on('click',function(){
		        if(<%=adminSvc.getOneAdmin(adminNo).getUserAuth() == 0 %>){
		        alert("您沒有管理教練的權限!");
		             } 
		        })
		        
			 $('#ban_post_li').on('click',function(){
			    if(<%=adminSvc.getOneAdmin(adminNo).getCommentAuth() == 0 %>){
			    alert("您沒有管理文章的權限!");
			          } 
			     })
			     
			$('#ban_sub_li').on('click',function(){
			 	if(<%=adminSvc.getOneAdmin(adminNo).getSubAuth() == 0 %>){
				alert("您沒有管理訂閱的權限!");
					  } 
			   })	
		})
		
		</script>
</body>
</html>