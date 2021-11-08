<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*"%>

<%
	UserVO userVO = (UserVO) request.getAttribute("userVO");
%>

<html>
<head>
    <meta charset="BIG5">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>sign_up_page</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sign_up_page.css">
</head>
<body>
	
	<div id="back">
        <img src="<%=request.getContextPath()%>/img/sign_up.jpg" alt="">
        <form method="post" ACTION="<%=request.getContextPath()%>/html/user.do" name="form1">
            <div id="signup">
                <h2>Sign Up</h2>
                <p>註冊信箱
                    <input type="text" placeholder="請輸入信箱" id="email" name="userAccount" 
                    value="<%=(userVO == null) ? "tfa103cloudgym@gmail.com" : userVO.getUserAccount()%>">
                </p>
                <hr>
                <p>密碼　　
                    <input type="password" placeholder="請輸入密碼" id="password" name="userPassword"
                    value="<%=(userVO == null) ? "111111" : userVO.getUserPassword()%>"></p>
                <hr>
                <p>確認密碼
                    <input type="password" placeholder="確認密碼" id="password" name="passwordConfirm"
                    value="<%=(userVO == null) ? "111111" : userVO.getUserPassword()%>"></p>
                <hr>
                
				<div id="errormsg" >
					<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</c:if>
				</div>
                <button type="submit">建立帳戶<input type="hidden" name="action" value="insert"></button>
                <br>
            </div>
        </form>
    </div>
</body>
</html>