<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>忘記密碼</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/forget_email_success.css">
</head>
<body>
		<div id="forget">
			<p>發送成功！請至郵箱確認郵件！</p>
			<button type="button" onclick="location.href='<%=request.getContextPath()%>/html/login/login_user.jsp'">返回登入</button>
		</div>
</body>
</html>