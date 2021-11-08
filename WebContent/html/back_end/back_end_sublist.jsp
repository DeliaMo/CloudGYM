<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.subList.model.*"%>

<jsp:useBean id="adminSvc" scope="page" class="com.admin.model.AdminService" />
<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache");        //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>
<%
	SubListService sublistSvc = new SubListService();
	List<SubListVO> list = sublistSvc.getAll();
	pageContext.setAttribute("list", list);
	session.getAttribute("adminNo");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>訂閱管理</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_sublist.css">
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
		<p>訂閱管理</p>
		<div id="right">
<%-- 		<a href="<%=request.getContextPath()%>/html/back_end_sublist_add.jsp"> --%>
<!-- 				<button id="bat_btn" type="submit">新增方案</button> -->
<!-- 		</a> -->
			<div class="main">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">方案名稱</th>
							<th scope="col">方案時長</th>
							<th scope="col">售價</th>
							<th scope="col"></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sublistVO" items="${list}">
							<tr>
								<th scope="row">${sublistVO.subName}
								</td>
								<td>${sublistVO.duration}</td>
								<td>${sublistVO.price}</td>
								<td><a href="<%=request.getContextPath()%>/html/back_end/back_end_sublist_update.jsp?subID=${sublistVO.subID}">
										<button class="update" type="submit">修改</button>
								</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
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