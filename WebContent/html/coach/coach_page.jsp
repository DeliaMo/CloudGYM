<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coachMenu.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="redis.clients.jedis.Jedis"%>
<%@ page import="com.subscription.model.*"%>
<%@ page import="com.video.model.*"%>
<%@ page import="com.orders.model.*"%>
<%@ page import="com.orderList.model.*"%>
<%@ page import="com.userAuth.model.*" %>


<%

// 	CoachVO coachVO = null;
	Integer coachID = Integer.parseInt(request.getParameter("userID"));
	System.out.println(coachID);
	request.setAttribute("coachID", coachID);
// 	try {
// 		coachVO = (CoachVO) request.getAttribute("coachVO");
// 		coachID = coachVO.getUserID();
// 		request.setAttribute("coachID", coachID);
// 	} catch (Exception e) {
// 		coachVO = new CoachService().getByUserID(2004);
// 		pageContext.setAttribute("coachVO", coachVO);
// 		coachID = 2004;
// 	}

	CoachMenuService svc = new CoachMenuService();
	List<CoachMenuVO> menuList = svc.getByUserID(coachID);
	pageContext.setAttribute("menuList", menuList);

	SubscriptionService subscriSvc = new SubscriptionService();
	List<SubscriptionVO> subList = subscriSvc.getByUserID(coachID);
	pageContext.setAttribute("subList", subList);

	VideoService videoSvc = new VideoService();
	List<VideoVO> videoList = videoSvc.getByUserID(coachID);
	pageContext.setAttribute("videoList", videoList);

	String uri = request.getRequestURI();
	session.setAttribute("uri", uri);
	
// 	String username = "peter";
// 	session.setAttribute("username", username);
// 	Integer userID = 1003;
// 	session.setAttribute("userID", userID);
	Integer userID = (Integer)session.getAttribute("userID");
	System.out.println(userID);
	List<Integer> itemIDs = null;
	if(userID != null && userID.toString().startsWith("1")){
		List<OrdersVO> orders = new OrdersService().getOrdersByUserID(userID);
		OrderListService orderlistSvc = new OrderListService();
		itemIDs = new ArrayList<>();
		for(OrdersVO ordersVO : orders){
			List<OrderListVO> orderlists = orderlistSvc.getOrderListByOrderNo(ordersVO.getOrderNo());
			for(OrderListVO orderListVO : orderlists){
				Integer itemID = orderListVO.getItemID();
				itemIDs.add(itemID);
			}
		}
	}

	pageContext.setAttribute("itemIDs", itemIDs);

	long cartCount = 0;
	Jedis jedis = new Jedis("localhost", 6379);
	try {
		cartCount = jedis.hlen(userID.toString());
		pageContext.setAttribute("cartCount", cartCount);
	} catch (Exception e) {
		cartCount = 0;
		pageContext.setAttribute("cartCount", cartCount);
	}
	jedis.close();

	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	
	Integer banShopping = 0;
	if(userID != null){
		UserAuthService userauthSvc = new UserAuthService();
		UserAuthVO userAuthVO = userauthSvc.getUserID(userID);
		if(userAuthVO != null){
			banShopping = userAuthVO.getBanShopping();
		}
	}
	
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
<title>教練個人頁面</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/coach_page.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
<style type="text/css">
div.menu input:disabled {
	background-color: rgb(143, 143, 143);
}
</style>
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
<!-- bar公版 begining -->
    <div id="bar">
        <div id="title">
            <ul>
                <li class="bar_li">
                    <img src="<%=request.getContextPath()%>/img/logo.png" alt="" for="#CloudGYM">
                </li>
                <li class="bar_li">
                    <a href="<%=request.getContextPath()%>/html/main_page.jsp" id="CloudGYM">CloudGYM</a>
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
<!-- bar公版 end -->

	<div id="main">
		<div class="coach_info">
			<div class="photo">
				<img
					src="<%=request.getContextPath()%>/coachImg/coachImg.do?userID=${coachVO.userID}"
					alt="">
			</div>
			<div class="info">
				<h3 class="name">${coachVO.coachName }</h3>
				<span>自我介紹:</span>
				<div class="intro">${coachVO.coachDescription }</div>
				<p class="licnese">證照:</p>
				<div>${coachVO.coachCertificate }</div>
				<c:if test="${userID == coachID }">
					<ul>
						<li><a
							href="<%=request.getContextPath()%>/html/coach/protected_coach/changeCoachInfo_page.jsp?userID=${coachVO.userID}">修改個人資料</a></li>
						<li><a
							href="<%=request.getContextPath()%>/html/coach/protected_coach/coach_overview.jsp?userID=${coachVO.userID}">影片/菜單管理</a></li>
					</ul>
				</c:if>
				
			</div>
		</div>
		<div class="video">
			<i class="bi bi-chevron-compact-left left"></i>
			<ul>
				<c:forEach var="video" items="${videoList}" varStatus="i">
					<li><img
						src="<%=request.getContextPath() %>/img/menu${i.count }.jpg" alt="">
						<h4 class="video_name">${video.title}</h4>
						<%if(banShopping == 1){ %>
							<button type="button" value="加入購物車" disabled>無法購買</button>
							<c:if test="${itemIDs.contains(video.videoID) }">
								<button type="button" value="加入購物車" disabled>已購買</button>
							</c:if>
						<%}else{ %>
						<form id="add_cart" method="post">
							<!-- 	                        <input type="submit" value="加入購物車"> -->
							<c:if test="${video.price == 0}">
								<button type="button" value="加入購物車" disabled>加入購物車</button>
							</c:if>
							<c:if test="${video.price != 0}">
								<button type="button" value="加入購物車">加入購物車</button>
							</c:if>
							<%if(itemIDs != null){%>
								<c:if test="${itemIDs.contains(video.videoID) }">
									<button type="button" value="加入購物車" disabled>已購買</button>
								</c:if>
							<%} %>
							
							<input type="hidden" name="videoID" value="${video.videoID}">
							<input type="hidden" name="videoPrice" value="${video.price}">
							<input type="hidden" name="action" value="addCart">
						</form>
						<%} %>
					</li>
				</c:forEach>
			</ul>
			<i class="bi bi-chevron-compact-right right"></i>
		</div>
		<div class="menu">
			<i class="bi bi-chevron-compact-left left"></i>
			<ul>
				<c:forEach var="coachMenu" items="${menuList}">
					<li>
						<h4 class="menu_name">${coachMenu.menuName}</h4>
						<p class="menu_info">菜單介紹</p> <img src="<%=request.getContextPath()%>/img/menu_pic.png"
						alt="">
						<div class="times">

							<p>20下/組</p>
							<p>20下/組</p>
							<p>20下/組</p>
							<p>20下/組</p>
						</div>
						<form id="add_cart" method="post">
							<!-- 	                        <input type="submit" value="加入購物車"> -->
							<c:if test="${coachMenu.price == 0}">
								<button type="button" value="加入購物車" disabled>加入購物車</button>
							</c:if>
							<c:if test="${coachMenu.price != 0}">
								<button type="button" value="加入購物車">加入購物車</button>
							</c:if>
							<c:if test="${itemIDs.contains(coachMenu.menuID) }">
								<button type="button" value="加入購物車" disabled>已購買</button>
							</c:if>
							<input type="hidden" name="menuID" value="${coachMenu.menuID}">
							<input type="hidden" name="menuPrice" value="${coachMenu.price}">
							<input type="hidden" name="action" value="addCart">
						</form>
					</li>
				</c:forEach>

			</ul>
			<i class="bi bi-chevron-compact-right right"></i>
		</div>
		<div id="fuck">
			<ul>
				<c:forEach var="subscription" items="${subList}">
					<jsp:useBean id="sub" scope="page"
						class="com.subList.model.SubListService" />
					<li>
						<h3 class="plan">訂閱方案</h3>
						<p>時間： ${sub.getBySubID(subscription.subID).duration }</p>
						<p>價格： ${sub.getBySubID(subscription.subID).price }</p>
						<form>
							<button class="btn_sub" type="button">訂閱</button>
							<input type="hidden" name="action" value="addCart"> 
							<input type="hidden" name="subID" value="${subscription.subID}">
							<input type="hidden" name="coachID"	value="${subscription.userID}">
						</form>
					</li>
				</c:forEach>

			</ul>
		</div>
	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
	<script src="<%=request.getContextPath() %>/js/jquery-3.6.0.min.js"></script>

	<script>
    	$(function(){
    		var cartCount = ${cartCount};
    		let menu_length = $("div.menu").find("li").length;
    	    console.log(menu_length);
    	    if(menu_length < 4){
    	    	$("div.menu i.right").addClass("-on");
    	    }
    		
    		$("div.video button").on("click", function(){
    			$.ajax({
    				url: "<%=request.getContextPath()%>/orders/orders.do",
    				type: "post",
    				data:$(this).closest("form").serialize(),
    				dataType:"json",
    				success: function(data){
    					console.log("success");
    					console.log(data[0]);
    					$("div#option span").html(data[0]).attr("style", "");
    					$("div#option span").attr("style", "display:flex");
    				},
    				error: function(xhr){
    					console.log("fail");
    				}
    			});
    		});
    		
    		
    		$("div.menu button").on("click", function(){
    			$.ajax({
    				url: "<%=request.getContextPath()%>/orders/orders.do",
    				type: "post",
    				data:$(this).closest("form").serialize(),
    				dataType:"json",
    				success: function(data){
    					console.log("success!");
    					console.log(data[0]);
    					$("div#option span").html(data[0]).attr("style", "");
    					$("div#option span").attr("style", "display:flex");
    				},
    				error: function(xhr){
    					console.log("fail!");
    				}
    			})
    		});
    		
    		
    		$("button.btn_sub").on("click", function(){
    			$.ajax({
    				url: "<%=request.getContextPath()%>/orders/orders.do",
					type : "post",
					data : $(this).closest("form").serialize(),
					dataType : "json",
					success : function(data) {
						console.log("success~");
						console.log(data);
						console.log(data[0]);
						$("div#option span").html(data[0]).attr("style", "");
						$("div#option span").attr("style", "display:flex");
					},
					error : function(xhr) {
						console.log("fail~");
					}
				})
			});

			if (cartCount == 0) {
				$("i.bi-cart-fill span").addClass("-on");
				$("i.bi-cart-fill span").css("display", "none");
			} else {
				$("i.bi-cart-fill span").removeClass("-on");
				$("i.bi-cart-fill span").attr("style", "");
			}

		})
	</script>
	<script src="<%=request.getContextPath() %>/js/coach_page.js"></script>
</body>
</html>