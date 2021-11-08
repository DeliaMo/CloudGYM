<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*" %>
<%@ page import="com.user.model.*" %>

<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache");        //HTTP 1.0
  response.setDateHeader ("Expires", 0);
%>
<%
	UserService userSvc = new UserService();
	List<UserVO> list = userSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="userAuthSvc" scope="page" class="com.userAuth.model.UserAuthService" />
<jsp:useBean id="adminSvc" scope="page" class="com.admin.model.AdminService" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>會員管理表單</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_user.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
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
            <p>會員管理</p>
            <div id="right">
            <div class="main">
              <form method="post" action="<%=request.getContextPath()%>/html/UserAuth.do">
              	<input type="hidden" name="page" value="usermanager">
                <button id="bat_btn" class="ban_user" type="submit" name="AuthMgt" value="stopUser">停權/解除</button>
                <button id="bat_btn" class="ban_shopping" type="submit" name="AuthMgt" value="stopBuy">禁止購買/解除</button>
                <button id="bat_btn" class="ban_comm" type="submit" name="AuthMgt" value="stopComment">禁言/解除</button>
              
                <table class="table">
                    <thead>
                      <tr>
                        <th scope="col"></th>
                        <th scope="col">會員編號</th>
                        <th scope="col">姓名</th>
                        <th scope="col">帳號</th>
                        <th scope="col">手機</th>
                        <th scope="col">生日</th>
                        <th scope="col">性別</th>
                        <th scope="col">檢舉次數</th>
                        <th scope="col">註冊日期</th>  
                        <th scope="col">權限管理</th>                        
                        <th scope="col">權限最後更新日期</th>                        
                      </tr>
                    </thead>
                    <tbody>
<%--                     <%@ include file="/pages/page1.file" %>  --%>
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
						<% session.setAttribute("whichPage",whichPage); %>
                    <c:forEach var="userVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                      <tr>
                        <th scope="row">
                        <input class="getall" type="checkbox" name="userid" value="${userVO.userID}">
                        </th>
                        <td>${userVO.userID}</td>
                        <td>${userVO.userName}</td>
                        <td>${userVO.userAccount}</td>
                        <td>${userVO.userMobile}</td>
                        <td>${userVO.userBirthday}</td>
                        <td>${userVO.userSex}</td>
                        <td>
                        <c:choose>
                        	<c:when test="${userVO.userReportedTimes >= 3}">
                        		<font style="color: red">${userVO.userReportedTimes}</font>
                        	</c:when>
	                        <c:otherwise>
	                    	    ${userVO.userReportedTimes}
	                        </c:otherwise>
                        </c:choose>
                        </td>
                        <td>
                        <fmt:parseDate value="${userVO.userRegisterDate}" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"/>
						<fmt:formatDate value="${myDate}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                          <span class="td_comm">
                            <i class="bi bi-chat-text-fill"  
                            ${userAuthSvc.getUserID(userVO.userID).banComment == 1 ? "style='color:red'" : ""}
                            ></i>
                          </span>
                            <span class="td_shopping">
                              <i class="bi bi-cart-x-fill"
                              ${userAuthSvc.getUserID(userVO.userID).banShopping == 1 ? "style='color:red'" : ""}
                              ></i>
                            </span>
                            <span class="td_user">
                              <i class="bi bi-person-x-fill"
                               ${userAuthSvc.getUserID(userVO.userID).banUsers == 1 ? "style='color:red'" : ""}
                              ></i>
                            </span>
                          </td>
                          <td>${userAuthSvc.getUserID(userVO.userID).startTime}</td>                        
                      </tr>                     
                    </c:forEach>
                    </tbody>
                  </table>
<%--                   <%@ include file="/pages/page2.file" %>                  --%>
                 <div class="page">
                  <%for(int i = 0; i < pageIndexArray.length; i++){%>
                  		<a href="<%=request.getContextPath()%>/html/back_end/back_end_user.jsp?whichPage=<%=i + 1%>"><%=i + 1%></a>
                  	<%}%>
                  </div>    
            	</form>
            </div>
        </div>
    </div>
    <script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
	<script>
		$(function(){
		    $('.ban_user,.ban_shopping,.ban_comm').on('click',function(){
		        var checkbox = $('input:checkbox:checked').length;
		        if(checkbox == 0){
		            alert("請勾選會員!");
		        }
		        console.log(checkbox);       
		    })
			
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