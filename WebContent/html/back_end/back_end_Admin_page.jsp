<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.admin.model.*"%>

<%
	AdminService adminSvc = new AdminService();
	Integer adminNo = (Integer) session.getAttribute("adminNo");
	AdminVO adminVO = adminSvc.getOneAdmin(adminNo);
	pageContext.setAttribute("adminVO", adminVO);
%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>編輯管理員表單</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_Admin_page.css">
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
		<div id="left"></div>

		<div id="right">
			<div class="main">
				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>

				<div class="main-out">
					<div class="name">管理員資料修改</div>
					<form method="post" action="<%=request.getContextPath()%>/html/admin.do" name="form1">
						<ul class="name-ul">
							<li class="name-li">ID: <span>${adminNo}</span></li>
							<li class="name-li"><span class="li-span">姓名</span> 
							<input type="text" name="name" value="${adminVO.adminName}">
							</li>
							<li class="name-li"><span class="li-span">密碼</span> 
							<input type="password" name="pws"></li>
							<li class="name-li">確認密碼 
							<input type="password" name="confpws">
							</li>
							<div>
								<input type="hidden" name="id" value="${adminNo}">
								<button class="confirm" type="submit" name="action"
									value="update">確認</button>
								<a href="<%=request.getContextPath()%>/html/back_end/back_end_Admin.jsp">
									<button class="cancel" type="button">取消</button>
								</a>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>