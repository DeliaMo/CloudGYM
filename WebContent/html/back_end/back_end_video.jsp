<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.video.model.*"%>

<jsp:useBean id="coachSvc" scope="page"	class="com.coach.model.CoachService" />
<jsp:useBean id="adminSvc" scope="page" class="com.admin.model.AdminService" />
<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache");        //HTTP 1.0
  response.setDateHeader ("Expires", 0);
%>

<%
	VideoService videoSvc = new VideoService();
	List<VideoVO> list = videoSvc.getAll2();
	pageContext.setAttribute("list", list);
	session.getAttribute("adminNo");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>影片管理</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_index.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/back_end_video.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
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
		<p>影片管理</p>
		<div id="right">
			<div class="main">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">編號</th>
							<th scope="col">影片ID</th>
							<th scope="col">影片標題</th>
							<th scope="col">上傳者</th>
							<th scope="col">上傳時間</th>
							<th scope="col">被檢舉次數</th>
							<th scope="col">影片</th>
							<th scope="col">狀態</th>
							<th scope="col">修改</th>
						</tr>
					</thead>
					<tbody>
						<%--<%@ include file="/pages/page1.file" %> --%>

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
				       		session.setAttribute("whichPage",whichPage);
						%>
						<c:forEach var="videoVO" items="${list}" begin="<%=pageIndex%>"	end="<%=pageIndex+rowsPerPage-1%>">
							<tr>
								<th scope="row"><%=number++%></th>
								<td>${videoVO.videoID}</td>
								<td>${videoVO.title}</td>
								<td>${videoVO.userID}-
									${coachSvc.getByUserID(videoVO.userID).coachName}</td>
								<td>${videoVO.publishTime}</td>
								<td>${videoVO.reportedTimes}</td>
								<td>
										<i class="bi bi-camera-reels-fill"></i>
                       				 	<input class="videoid" type="hidden" value="${videoVO.videoID}">
								</td>
								<td>
								<input type="radio" name="videoshow${videoVO.videoID}"
									${videoVO.listed == true ? "checked='true'" : ""}>公開
								<input type="radio" name="videoshow${videoVO.videoID}"
									${videoVO.listed == false ? "checked='true'" : ""}>不公開
								</td>
								<td>
<%-- 									<form method="post" action="<%=request.getContextPath()%>/html/video.do"> --%>
									<form class="video">
									<input type="hidden" name="videoID" value="${videoVO.videoID}">
									<input type="hidden" name="action" value="updatelist">
										<button class ="btn_update" type="button">
											<i class="bi bi-check-all"></i>
										</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="page">
					<%
						for (int i = 0; i < pageIndexArray.length; i++) {
					%>
					<a href="<%=request.getContextPath()%>/html/back_end/back_end_video.jsp?whichPage=<%=i + 1%>"><%=i + 1%></a>
					<%
						}
					%>
				</div>
			</div>
		</div>

		<script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
		<script>
		$(function(){
			//ajax更改video狀態
			$(".video button").on("click", function(){
				$.ajax({
					url: "<%=request.getContextPath()%>/html/video.do",
					type: "post",
					data: $(this).closest("form.video").serialize(),
					dataType: "json",
					success: function(data){
						alert("狀態更新成功");
						console.log("success");
						console.log(data[0]);
					},
					error: function(xhr){
						alert("狀態更新失敗");
						console.log("fail");
					}
				})
			})
			
		    $('.bi.bi-camera-reels-fill').on('click',function(){
		    	var videoid=$(this).next().val();
		    	console.log(videoid);
		        if($('div').hasClass('pushvideo') == true){
		            $('.pushvideo').remove();
		        }else{
		            let video = `
		            <div class="pushvideo">
		            <video controls autoplay>
		                <source src="<%=request.getContextPath()%>/html/VideoOutput?videoID=`+videoid+`" type="video/mp4">
		            </video>
		            <button type="button" class="btn_modal_close">關閉</button>
		         </div>
		            `;
		            $('table').prepend(video);
		        }

		        $('button.btn_modal_close').on('click',function(){
		            $('.pushvideo').remove();
		        })
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