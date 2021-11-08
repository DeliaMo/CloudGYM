<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.customMenu.model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	CustomMenuVO customMenuVO = (CustomMenuVO) request.getAttribute("customMenuVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改菜單</title>
</head>
<body>
	<h3>修改選取的菜單資料</h3>
	<form>
		<div class="form-group">
			<label for="menuid">菜單編號</label> 
			<input type="text"
				class="form-control" id="menuid" value="${customMenuVO.menuID}"
				disabled>
		</div>
		<div class="form-group">
			<label for="titleInput">菜單名稱</label> 
			<input type="text"
				class="form-control" id="title" name="title" value="${customMenuVO.title}">
		</div>
		<div class="form-group">
			<label for="c">菜單介紹</label> 
			<input type="text" class="form-control" name="content"
				id="content" value="${customMenuVO.content}">
		</div>

		<input type="hidden" name="action" value="update"> 
		<input type="hidden" name="menuID" value="${customMenuVO.menuID}"> 
		<input type="submit" value="送出修改">
	</form>
</body>
</html>