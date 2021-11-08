<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.subList.model.*"%>

<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache");        //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>

<%
	SubListService sublistSvc = new SubListService();
	SubListVO sublistVO = sublistSvc.getBySubID(Integer.parseInt(request.getParameter("subID")));
	pageContext.setAttribute("sublistVO", sublistVO);	
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改訂閱管理</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_sublist_page.css">
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
                    <div class="name">訂閱方案修改</div>               
                    <form method="post" action="<%=request.getContextPath() %>/html/sublist.do">
                        <ul class="name-ul">
                        	<input type="hidden" name="subName" value="${sublistVO.subName}">
                            <li class="name-li">方案名稱 <span>${sublistVO.subName}</span>
                            </li>
                            <li class="name-li"><span>方案時長</span>
                                <input type="text" name="duration" value="${sublistVO.duration}">
                            </li>
                            <li class="name-li"><span class="li-span">售價</span>
                                <input type="text" name="price" value="${sublistVO.price}">
                            </li>                            
                            
                            <div>
                            	<input type="hidden" name="subID" value="${sublistVO.subID}">
                                <button class="confirm" type="submit" name="action" value="update">確認</button>
                                <a href="<%=request.getContextPath()%>/html/back_end/back_end_sublist.jsp">
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