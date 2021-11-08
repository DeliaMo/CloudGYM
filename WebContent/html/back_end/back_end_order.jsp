<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.orderList.model.*"%>

<jsp:useBean id="userSvc" scope="page" class="com.user.model.UserService" />
<jsp:useBean id="ordersSvc" scope="page" class="com.orders.model.OrdersService" />
<jsp:useBean id="videoSvc" scope="page" class="com.video.model.VideoService" />
<jsp:useBean id="coachmenuSvc" scope="page"	class="com.coachMenu.model.CoachMenuService" />
<jsp:useBean id="sublistSvc" scope="page" class="com.subList.model.SubListService" />
<jsp:useBean id="adminSvc" scope="page" class="com.admin.model.AdminService" />

<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache");        //HTTP 1.0
  response.setDateHeader ("Expires", 0);
%>
<%
	OrderListService orderListSvc = new OrderListService();
	List<OrderListVO> list = orderListSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<!-- --------------------------------- -->
<%-- ${userSvc.findByUserId(ordersSvc.gerOrdersByOrderNo(90001).userID).userMobile } --%>
<!-- --------------------------------------- -->


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>訂單查詢</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_order.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
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
		<p>訂單查詢</p>
		<div id="right">
			<div class="main">
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color:red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
				</c:forEach>
			</ul>
			</c:if>
			
			 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/html/orderList.do">
                  <b>查詢訂單編號:</b>
                  <input type="text" name="orderNo">
                  <input type="hidden" name="action" value="getbyOrderNO">
                  <input type="submit" value="查詢">
              </FORM>
<%--               <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/html/orderList.do"> --%>
<!--                   <b>查詢訂單日期:</b> -->
<!--                   <input id="start_date" type="text" name="builtDate"> -->
<!--                   <input type="hidden" name="action" value="getbyDate"> -->
<!--                   <input type="submit" value="查詢"> -->
<!--               </FORM> -->
				<table class="table">
					<thead>
						<tr>
							<th scope="col">編號</th>
							<th scope="col">訂單編號</th>
							<th scope="col">購買人</th>
							<th scope="col">品項</th>
							<th scope="col">購買人手機</th>
							<th scope="col">訂單建立時間</th>
						</tr>
					</thead>
					<tbody>
						<%-- 						<%@ include file="/pages/page1.file"%> --%>
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

						<c:forEach var="orderListVO" items="${list}"
							begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<tr>
								<th scope="row"><%=number++%></th>
								<td>${orderListVO.orderNo}
								</th>
								<td>${ordersSvc.gerOrdersByOrderNo(orderListVO.orderNo).userID}-
									${userSvc.findByUserId(ordersSvc.gerOrdersByOrderNo(orderListVO.orderNo).userID).userName}</td>
								<td>${orderListVO.itemID}-
									<c:if
										test="${orderListVO.itemID.toString().substring(0,1)=='3'}">
										${videoSvc.findByPrimaryKeyNoVideo(orderListVO.itemID).getTitle()}(影片)</c:if>
									<c:if
										test="${orderListVO.itemID.toString().substring(0,1)=='6'}">
										${coachmenuSvc.getByMenuID(orderListVO.itemID).getMenuName()}(教練菜單)</c:if>
									<c:if
										test="${orderListVO.itemID.toString().substring(0,1)=='7'}">
										${sublistSvc.getBySubID(orderListVO.itemID).getSubName()}(訂閱)</c:if>
								</td>
								<td>${userSvc.findByUserId(ordersSvc.gerOrdersByOrderNo(orderListVO.orderNo).userID).userMobile }</td>
								<td>
									${ordersSvc.gerOrdersByOrderNo(orderListVO.orderNo).builtDate}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="page">
                  <%for(int i = 0; i < pageIndexArray.length; i++){%>
                  		<a href="<%=request.getContextPath()%>/html/back_end/back_end_order.jsp?whichPage=<%=i + 1%>"><%=i + 1%></a>
                  	<%}%>
                  </div> 
			</div>
		</div>
	</div>
	    <script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
		<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

	<script>
		 $.datetimepicker.setLocale('zh'); // kr ko ja en
		 $(function(){
			 $('#start_date').datetimepicker({
			       theme: '',              //theme: 'dark',
			       timepicker:false,       //timepicker:true,
			       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
			       format:'Y-m-d',         //format:'Y-m-d H:i:s',
				   value: new Date(), // value:   new Date(),
		           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		           //startDate:	            '2017/07/10',  // 起始日
		           //minDate:               '-1970-01-01', // 去除今日(不含)之前
		           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		        });
			
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