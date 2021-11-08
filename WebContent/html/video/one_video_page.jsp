<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.video.model.*" %>
<%@ page import="com.report.model.*" %>
<%@ page import="com.orders.model.*" %>
<%@ page import="com.orderList.model.*" %>
<%@ page import="com.reportRecord.model.*" %>
<%@ page import="com.userRights.model.*" %>
<%@ page import="redis.clients.jedis.Jedis" %>
<jsp:useBean id="videoSvc" scope="page" class="com.video.model.VideoService"/>
<%
	Integer videoID = Integer.parseInt(request.getParameter("videoID"));
	pageContext.setAttribute("videoID", videoID);
	
	VideoVO videoVO = videoSvc.findByPrimaryKey(videoID);
	Timestamp time =  videoVO.getPublishTime();
	Date date =new Date(time.getTime());
	SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
	String publishTime = sd.format(date);
	pageContext.setAttribute("publishTime", publishTime);
	
	List<VideoVO> list1 = videoSvc.getByPositionNo(2);
	request.setAttribute("list1", list1);
	List<VideoVO> list2 = videoSvc.getByPositionNo(5);
	request.setAttribute("list2", list2);

	String oneVideoPage = request.getRequestURI() + "?videoID=" + videoID;
	session.setAttribute("oneVideoPage", oneVideoPage);
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
	
	List<Integer> items = null;
	if(userID != null){
		ReportService reportSvc = new ReportService();
		List<ReportVO> reports = reportSvc.getByUser(Integer.parseInt(userID));
		items = new ArrayList<Integer>();
		for(ReportVO reportVO : reports){
			items.add(reportVO.getItemID());
		}
	}
	
	request.setAttribute("items", items);
	
	List<Integer> items2 = new ArrayList<>();
	if(userID != null){
		UserRightsService userrightSvc = new UserRightsService();
		List<UserRightsVO> userrights = userrightSvc.getAll(Integer.parseInt(userID));
		for(UserRightsVO userright : userrights){
			Integer videoid = userright.getVideoID();
			items2.add(videoid);
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
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/one_video_page.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.1/font/bootstrap-icons.css">
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
	<div id="bar">
        <div id="title">
            <ul>
                <li class="bar_li">
                    <img src="<%=request.getContextPath()%>/img/logo.png" alt="" for="#CloudGYM">
                </li>
                <li class="bar_li">
                    <a href="<%=request.getContextPath()%>/main_page.jsp" id="CloudGYM">CloudGYM</a>
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
				<li class="option">
					<a href="<%=request.getContextPath()%>/html/order/pay_page.jsp">
						<i class="bi bi-cart-fill"> <c:if test="${hlen != 0}">
								<span>${hlen}</span>
							</c:if> <c:if test="${hlen == 0}">
								<span>${cartCount}</span>
							</c:if> <span>${cartCount}</span></i>
					</a>
				</li>
			</ul>
        </div>
    </div>

    <div id="main">
        <div id="top">
            <h3>${videoSvc.findByPrimaryKey(videoID).title }</h3>
            <div id="left">
            	<div class="comment" style="display: none; z-index:10;">
                    <h3>你覺得這支影片讚嗎</h3>
                    <form>
	                    <span class="star" data-star="1"><i class="fas fa-star"></i></span>
	                    <span class="star" data-star="2"><i class="fas fa-star"></i></span>
	                    <span class="star" data-star="3"><i class="fas fa-star"></i></span>
	                    <span class="star" data-star="4"><i class="fas fa-star"></i></span>
	                    <span class="star" data-star="5"><i class="fas fa-star"></i></span>
<!--                     <h4>對辛苦的教練說一點話吧</h4> -->
                    	<input type="hidden" name="stars" value="0" class="stars">
                    	<input type="hidden" name="action" value="addReview">
                    	<input type="hidden" name="videoID" value="${videoID}">
<!--                         <input type="text"> -->
                    </form>
                </div>
                <%if(!videoSvc.findByPrimaryKey(videoID).getListed()){%>
                	<div class="disable" style="z-index:8;"><h3>此影片已被下架</h3></div>
                <%} else{%>
                <%if(videoSvc.findByPrimaryKey(videoID).getPrice() != 0){
                	if(items2.contains(videoID)){%>
                	<%}else{%>
                	<div class="disable" style="z-index:8;"><h3>需先購買此影片才可以觀看</h3></div>
                <%}}} %>
                
<!--                 <img src="../img/work_out_1.jpg" alt="" id="video_img"> -->
                <video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${videoID}" width="700" controls preload="metadata" controlsList="nodownload">
                	<source src="http://techslides.com/demos/sample-videos/small.mp4" type="video/mp4">
  					<source src="http://techslides.com/demos/sample-videos/small.ogv" type="video/ogg">
                </video>
                <div id="video_info">
                	<form style="display:inline-block;">
                		<button id="bb" type="button">
                			<i class="bi bi-heart-fill"></i>
                    		<span>收藏</span>
                		</button>
                		<input type="hidden" name="action" value="insert">
                		<input type="hidden" name="videoID" value="${videoID}">
                		<input type="hidden" name="userid" value="${userID}">
                	</form>
                	<jsp:useBean id="customMenu" scope="page" class="com.customMenu.model.CustomMenuService"/>
                		<button type="button">
                			 <i class="bi bi-plus-circle"></i>
		                     <span class="addmenu">加入菜單</span>
                		</button>
                		<c:if test="${not empty userID}">
                		<div class="addmenu" style="display:none">
                        	<h3>請選擇欲加入菜單</h3>
                        	<ul>
                        	<c:forEach var="menu" items="${customMenu.getAll(userID) }">
                        		<li>${menu.title}
                        		<form>
                        			<input type="hidden" name="menuID" value="${menu.menuID }">
                        			<input type="hidden" name="action" value="putmenu">
                        			<input type="hidden" name="videoID" value="${videoID}">
                					<input type="hidden" name="userID" value="${userID }">
                        		</form>
                        		</li>
                        	</c:forEach>
                            	<hr>
                            	
                            	<li class="addmenu"><a href="<%=request.getContextPath()%>/html/video/addmenu2.jsp?userID=${userID}" style="color:black;">新增菜單</a>
                            	</li>
								                            	
                        	</ul>
                    	</div>
                		</c:if>
                	<form style="display:inline-block;">
                	<i class="bi bi-exclamation-circle-fill"></i>
                	<%if(items == null){%>
                		<span>檢舉</span>
                	<%}else{ %>
                	<%if(!items.contains(videoID) || items == null){%>
                		<button type="button" class="report_btn" style="padding-left:0;">
<!--                 			<i class="bi bi-exclamation-circle-fill"></i> -->
	                        <span>檢舉</span>
                		</button>
                		<input class="videoAction" type="hidden" name="action" value="addreport">
                	<% }
                	if(items.contains(videoID)){%>
                		<button type="button" class="report_btn" style="padding-left:0;">
<!--                 			<i class="bi bi-exclamation-circle-fill"></i> -->
	                        <span>已檢舉</span>
                		</button>
                		<input class="videoAction" type="hidden" name="action" value="deleteReport">
                	<%}}%>
                		<input type="hidden" name="videoID" value="${videoID}">
                	</form>
                    <span class="date">上傳於 ${publishTime}</span>
                </div>
                <p>${videoSvc.findByPrimaryKey(videoID).intro}</p>
            </div>

            <div id="right">
            <jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>
            	<form action="<%=request.getContextPath()%>/coach/coach.do?">
            		<input type="submit" id="gotocoach" style="display:none;">
            		<label for="gotocoach">
            			<img src="<%=request.getContextPath()%>/coachImg/coachImg.do?userID=${videoSvc.findByPrimaryKey(videoID).userID}"
	                   		alt="" id="coach_photo">
            		</label>
            		<p id="coach_name">${coachSvc.getByUserID(videoSvc.findByPrimaryKey(videoID).userID).coachName }</p>
	                
	                <div id="line"></div>
	                <p id="coach_info">${coachSvc.getByUserID(videoSvc.findByPrimaryKey(videoID).userID).coachDescription}</p>
	                <input type="hidden" name="action" value="gotocoach">
	                <input type="hidden" name="userID" value="${videoSvc.findByPrimaryKey(videoID).userID}">
            	</form>
            </div>
        </div>

        <div id="bottom">
            <p>相關影片</p>
            <ul>
            	<c:forEach var="videoVO" items="${list1}" begin="1" end="5">
            	<li class="push">
<!--                     <img src="../img/work_out_2.jpg" alt="" class="video_pic"> -->
					<a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${videoVO.videoID}">
						<video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${videoVO.videoID}" width="200"></video>
					</a>
                    <p class="video_name">${videoVO.title}</p>
                </li>
            	</c:forEach>
            </ul>
            <ul>
            	<c:forEach var="videoVO" items="${list2}">
            	<li class="push">
<!--                     <img src="../img/work_out_2.jpg" alt="" class="video_pic"> -->
					<a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${videoVO.videoID}">
						<video src="<%=request.getContextPath()%>/html/VideoOutput?videoID=${videoVO.videoID}" width="200"></video>
					</a>
                    <p class="video_name">${videoVO.title}</p>
                </li>
            	</c:forEach>
            </ul>
        </div>

    </div>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>
    <script>
    	$(function(){
    	    var cartCount = ${cartCount};
    	    if(cartCount == 0){
    	    	$("i.bi-cart-fill span").addClass("-on");
    	    	$("i.bi-cart-fill span").attr("style", "display:none");
    	    }else{
    	    	$("i.bi-cart-fill span").removeClass("-on");
    	    	$("i.bi-cart-fill span").attr("style", "");
    	    }
    		
    		$("button.report_btn").on("click", function(){
    			$.ajax({
    				url: "<%=request.getContextPath()%>/report/report.do",
    				type: "post",
    				data: $(this).closest("form").serialize(),
    				dataType: "json",
    				success: function(data){
    					console.log("success");
    					console.log(data);
    					if(data[0] == "cancelSuccess"){
    						alert("已取消檢舉");
    						$("button.report_btn").html("檢舉");
    						$("button.report_btn").attr("style", "color:white; padding-left:0;");
    						$("input.videoAction").val("addreport");
    					}
    					if(data[0] == "reportSuccess"){
    						alert("檢舉成功");
    						$("button.report_btn").html("已檢舉");
    						$("button.report_btn").attr("style", "color:white; padding-left:0;");
    						$("input.videoAction").val("deleteReport");
    					}
    					
    				},
    				error: function(xhr){
    					console.log("fail");
    				}
    			})
    		});
    		
    		$("video").on("ended", function(){
    			$("div.comment").attr("style", "z-index:9;");
    		});
    		
    		$("span.star").on("click", function(){
    			console.log($(this).attr("data-star"));
    			let stars = $(this).attr("data-star");
    			$("div.comment input.stars").val(stars);
    			$.ajax({
    				url:"<%=request.getContextPath()%>/review/review.do",
    				type: "post",
    				data: $(this).closest("form").serialize(),
    				dataType: "json",
    				success: function(data){
    					console.log("success");
    					$("div.comment").html('<h3 style="font-size:32px; font-weight:bold; margin-top:200px;">感謝您的回饋</h3>');
    				},
    				error : function(XMLHttpResponse, textStatus, errorThrown) {
						console.log("1 非同步呼叫返回失敗,XMLHttpResponse.readyState:"	+ XMLHttpResponse.readyState);
						console.log("2 非同步呼叫返回失敗,XMLHttpResponse.status:"	+ XMLHttpResponse.status);
						console.log("3 非同步呼叫返回失敗,textStatus:"	+ textStatus);
						console.log("4 非同步呼叫返回失敗,errorThrown:" + errorThrown);
					}
    			})
    		});
    		
    		//收藏AJAX
    		 $("#bb").on("click", function(){
	    		 $.ajax({
	    		 	url: "<%=request.getContextPath()%>/html/collection.do",
	    		    type : "POST",
	    		    data : $(this).closest("form").serialize(),
	    		    dataType : "json",
	    		    success : function(data) {
		    		    console.log(data);
// 		    		    $("#bb").removeClass("far fa-bookmark");
// 		    		    $("#bb").addClass("fas fa-bookmark");
// 		    		    $("#bb").attr("disabled", true);
	    		    },
	    		   error : function(XMLHttpResponse, textStatus, errorThrown) {
		    		    console.log("1 非同步呼叫返回失敗,XMLHttpResponse.readyState:" + XMLHttpResponse.readyState);
		    		    console.log("2 非同步呼叫返回失敗,XMLHttpResponse.status:" + XMLHttpResponse.status);
		    		    console.log("3 非同步呼叫返回失敗,textStatus:" + textStatus);
		    		    console.log("4 非同步呼叫返回失敗,errorThrown:" + errorThrown);  
	    		    }
	    		  });
    		  });
    		
    		$("span.addmenu").click(function (event) { 
    			//取消事件冒泡 
    			event.stopPropagation(); 
    			//按鈕的toggle,如果div是可見的,點選按鈕切換為隱藏的;如果是隱藏的,切換為可見的。 
    			$('div.addmenu').toggle('slow'); 
    			return false;
    			}); 
    		
    		$(document).click(function(event){
    			  var _con = $('div.addmenu');   // 設定目標區域
    			  if(!_con.is(event.target) && _con.has(event.target).length === 0){ // Mark 1
    				//$('#divTop').slideUp('slow');   //滑動消失
    				$('div.addmenu').hide();          //淡出消失
    			  }
    		});
    		
    		
    		
    		// 加入菜單Ajax
    		$("div.addmenu li").on("click", function(){
    			$.ajax({
    				url: "<%=request.getContextPath()%>/customMenu/customMenu.do",
    				type:"post",
    				data: $(this).find("form").serialize(),
    				dataType: "json",
    				success: function(data){
    					console.log("success");
    					alert("已加入到我的菜單");
    					$("div.addmenu").css("display", "none");
    				},
    				error : function(XMLHttpResponse, textStatus, errorThrown) {
		    		    console.log("1 非同步呼叫返回失敗,XMLHttpResponse.readyState:" + XMLHttpResponse.readyState);
		    		    console.log("2 非同步呼叫返回失敗,XMLHttpResponse.status:" + XMLHttpResponse.status);
		    		    console.log("3 非同步呼叫返回失敗,textStatus:" + textStatus);
		    		    console.log("4 非同步呼叫返回失敗,errorThrown:" + errorThrown);  
	    		    }
    			})
    		})
    	})
    </script>
</body>
</html>