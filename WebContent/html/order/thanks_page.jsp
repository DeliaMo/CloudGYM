<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.orderList.model.*" %>
<%@ page import="com.orders.model.*" %>
<%@ page import="com.video.model.*" %>
<%@ page import="com.coachMenu.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.subList.model.*"%>
<%@ page import="redis.clients.jedis.Jedis" %>

<%

	Integer orderNo = null;
	try{
		orderNo = (Integer) request.getAttribute("orderNo");
		if(orderNo == null){
			response.sendRedirect(request.getContextPath() + "/html/order/pay_page.jsp");
			return;
		}
		OrderListService orderlistSvc = new OrderListService();
		List<OrderListVO> list = orderlistSvc.getOrderListByOrderNo(orderNo);
		List<String> itemName = new ArrayList<>();
		
		VideoService videoSvc = new VideoService();
		CoachMenuService coachMenuSvc = new CoachMenuService();
		for(OrderListVO orderlistVO : list){
			if(orderlistVO.getItemID().toString().charAt(0) == '3'){
				VideoVO videoVO = videoSvc.findByPrimaryKey(orderlistVO.getItemID());
				itemName.add(videoVO.getTitle());
			}else if(orderlistVO.getItemID().toString().charAt(0) == '6'){
				CoachMenuVO coachMenuVO = coachMenuSvc.getByMenuID(orderlistVO.getItemID());
				itemName.add(coachMenuVO.getMenuName());
			}
		}
		
		pageContext.setAttribute("itemName", itemName);
	}catch(Exception e){

	}
	
	
%>

<%
	Jedis jedis = new Jedis("localhost", 6379);
	pageContext.setAttribute("jedis", jedis);
	
	String userID = null;
	try{
		userID = session.getAttribute("userID").toString();
	}catch(Exception e){
		userID = null;
	}
	
	long cartCount = 0;
	try{
		cartCount = jedis.hlen(userID);
	}catch(Exception e){
		cartCount = 0;
	}
			
	pageContext.setAttribute("cartCount", cartCount);
%>
<!DOCTYPE html>
<html>
<head>
<style>
#menu {
	margin: auto;
	float: right;
}

.main {
	background-color:#5C37A1;
	color: white;
	font-size: 14px;
	cursor: pointer;
	text-align: center;
	height: 40px;
	line-height: 40px;
	width: 70px;
}

.main a {
	color: #fff;
	text-decoration: none;
	line-height: 40px;
	margin: auto;
}

#logout{
	width: 100%;
}

.url {
	margin: auto;
}

.main:hover {
	background-color: #4d4949;
}

.sub {
	position:fixed;
	cursor: pointer;
	background-color: #4d4949;
	color: white;
	font-size: 12px;
	font-family: 微軟正黑體;
	text-align: center;
	width: 70px;
}

.sub li {
	list-style-type: none;
	line-height: 40px;
}

.sub a {
	list-style-type: none;
	line-height: 40px;
	margin: auto;
}

.item {
	float: left;
}

#menu li {
	list-style-type: none;
}

/****************** bar css end ******************/
</style>
<meta charset="BIG5">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/thanks.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
</head>
<body>
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
		$("#menu").css("width", $(".main").length * 200)
		$(document).ready(function() {
			//  一進入畫面時收合選單
			$(".sub").slideUp(0);

			for (i = 0; i < $(".main").length; i++) {
				//  點選按扭時收合或展開選單
				$(".main:eq(" + i + ")").on("click", {
					id : i
				}, function(e) {
					n = e.data.id
					$(".sub:eq(" + n + ")").slideToggle()
					$(".sub:not(:eq(" + n + "))").slideUp()
				})
				$(".main:eq(" + i + ")").on("mouseleave", {
					id : i
				}, function(e) {
					n = e.data.id
					$(".sub").stop();
				})
			}
		})
	</script>

	<!-- bar begining -->
	<div id="bar">
        <div id="title">
            <ul>
                <li class="bar_li">
                    <img src="<%=request.getContextPath()%>/img/logo.png" alt="" for="#CloudGYM">
                </li>
                <li class="bar_li">
                    <a href="#" id="CloudGYM">CloudGYM</a>
                </li>
            </ul>
        </div>
        <div id="option">
            <ul>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/video/all_video_page.jsp">運動類型</a></li>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/coach/all_coach_page.jsp">教練</a></li>
				<li class="option"><c:if test="${not empty userID}">
						<a
							href="<%=request.getContextPath()%>/html/user/protected_user/userMainPage.jsp?userID=${userID}">個人專區</a>
					</c:if> <c:if test="${ empty userID}">個人專區</c:if></li>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/article/ArticleList.jsp">討論區</a></li>
				<c:if test="${empty userID}">
					<div class="item">
						<div class="main">註冊/登入</div>
						<div class="sub">
							<ul>
								<li><a
									href="${pageContext.request.contextPath}/html/login/sign_up_page.jsp">會員註冊</a></li>
								<li><a
									href="${pageContext.request.contextPath}/html/login/login_user.jsp">會員登入</a></li>
								<li><a
									href="${pageContext.request.contextPath}/html/login/login_coach.jsp">教練登入</a></li>
								<li><a
									href="${pageContext.request.contextPath}/html/login/login_admin.jsp"
									target="_blank">後台管理</a></li>
							</ul>
						</div>
					</div>
				</c:if>
				<c:if test="${not empty userID}">
				<div class="item">
					<div class="main" id="logout"><a href="javascript:if (confirm('確認登出?')) location.href='<%=request.getContextPath()%>/LogoutHandler'">${name} 登出</a></div>
						<div class="sub"></div>
					</div>
				</c:if>
				<li class="option"><a
					href="<%=request.getContextPath()%>/html/order/pay_page.jsp">
						<i class="bi bi-cart-fill"> <c:if test="${hlen != 0}">
								<span>${hlen}</span>
							</c:if> <c:if test="${hlen == 0}">
								<span>${cartCount}</span>
							</c:if> <span>${cartCount}</span>
					</i>
				</a></li>
			</ul>
        </div>
    </div>
    <div id="wrap">
        <div id="main">
            <h2>感謝您的購買</h2>
			<div id="order_content">
				<p>
					訂單編號： <span>${orderNo}</span>
				</p>
				<p class="list">購買明細：</p>
				<div id="content">
					<c:if test="${not empty itemName}">
						<c:forEach var="name" items="${itemName}">
							<p>${name}</p>
						</c:forEach>
					</c:if>

					<%
                CoachService coachSvc = new CoachService();
                SubListService subListSvc = new SubListService();
                List<Integer> coachIDs = (ArrayList<Integer>) request.getAttribute("coachIDs");
                List<Integer> subIDs = (ArrayList<Integer>) request.getAttribute("subIDs");
                
                if(coachIDs != null && subIDs != null){
                	for(int i = 0; i < coachIDs.size(); i++){ %>
					<p><%=coachSvc.getByUserID(coachIDs.get(i)).getCoachName()%>
						-
						<%=subListSvc.getBySubID(subIDs.get(i)).getDuration()%></p>
					<%	}
                }
                %>
				</div>
				
				<%if(coachIDs != null && subIDs != null){%>
				<p class="sub">
					訂閱期間： 
					<%for(int i = 0; i < subIDs.size(); i++){ 
						OrdersService ordersSvc = new OrdersService();
						OrdersVO ordersVO = ordersSvc.gerOrdersByOrderNo(orderNo);
						Timestamp builtDate = ordersVO.getBuiltDate();
						Date startdate = new Date(builtDate.getTime());
						Date enddate = new Date();
						SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
						
						if(subListSvc.getBySubID(subIDs.get(i)).getDuration().startsWith("一")){
							Calendar c = Calendar.getInstance();
					        c.setTime(startdate);
					        c.add(Calendar.MONTH, 1);
					        enddate = c.getTime();
						}else if(subListSvc.getBySubID(subIDs.get(i)).getDuration().startsWith("三")){
							Calendar c = Calendar.getInstance();
					        c.setTime(startdate);
					        c.add(Calendar.MONTH, 3);
					        enddate = c.getTime();
						}else if(subListSvc.getBySubID(subIDs.get(i)).getDuration().startsWith("十")){
							Calendar c = Calendar.getInstance();
					        c.setTime(startdate);
					        c.add(Calendar.YEAR, 1);
					        enddate = c.getTime();
						}
						String startdateStr = sd.format(startdate);
						String enddateStr = sd.format(enddate);
					%>
					<span><%=startdateStr %> ~ <%=enddateStr %></span><br>
				<%	} %>
				</p>
				<%} %>
				
				<jsp:useBean id="orders" scope="page"
					class="com.orders.model.OrdersService" />
				<p class="price">
					總金額：$ <span>${orders.gerOrdersByOrderNo(orderNo).totalPrice}</span>
				</p>
			</div>

			<button type="button" onclick="javascript:location.href='<%=request.getContextPath()%>/main_page.jsp'">返回首頁</button>
        </div>
    </div>
    <script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
    <script>
        $("p.sub span").first().addClass("first");
        if($("p.sub span").hasClass("first")){
            $(".first").css("margin-left", "0");
        }
		
        var cartCount = ${cartCount};
        if(cartCount == 0){
        	$("i.bi-cart-fill span").addClass("-on");
        	$("i.bi-cart-fill span").attr("style", "display:none");
        }else{
        	$("i.bi-cart-fill span").removeClass("-on");
        	$("i.bi-cart-fill span").attr("style", "");
        }
    </script>
</body>
</html>