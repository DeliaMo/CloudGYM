<%@ page contentType="text/html; charset=BIG5" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.video.model.*"%>

<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" />
<%
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache");        //HTTP 1.0
  response.setDateHeader ("Expires", 0);
%>

<%
	VideoService videoSvc = new VideoService();
	List<VideoVO> list = videoSvc.getAll2();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="BIG5">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>main_video</title>

    <!-- CSS -->
    <link href="css/main_video.css" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

  </head>
  <body>
      
    <div id="exampleSlider">
       <div class="MS-content">
       		<c:forEach var="videoVO" items="${list}" begin="0" end="${list.size()}">
	       		<div class="item">
		       		<div class="img_block">
<%-- 			       		<form action="<%=request.getContextPath()%>/coach/coach.do?"> --%>
<%-- 							<input type="submit" id="${coachVO.userID }" style="display: none;">  --%>
<!-- 							<input type="hidden" name="action" value="gotocoach">  -->
<%-- 							<input type="hidden"name="userID" value="${coachVO.userID }">  --%>
<%-- 							<label for="${coachVO.userID }">  --%>
					            <video width="250" height="160" controls>
			                        <source src="<%=request.getContextPath()%>/video/VideoOutputMain?videoID=${videoVO.videoID}" type="video/mp4" />
			                        <source src="<%=request.getContextPath()%>/video/VideoOutputMain?videoID=${videoVO.videoID}" type="video/ogg" />
		                        </video>
					            <a href="<%=request.getContextPath()%>/html/video/one_video_page.jsp?videoID=${videoVO.videoID}" target="_blank">
			            		<p class="video_title">${videoVO.title}<span class="video_coach">${coachSvc.getByUserID(videoVO.userID).coachName}</sapn></p>
		                        </a>
<!-- 			                </label> -->
<!-- 						</form> -->
		            </div>
	            </div>   
            </c:forEach>

       </div>
       
       <div class="MS-controls">
           <button class="MS-left"><i class="fa fa-chevron-left" aria-hidden="true"></i></button>
           <button class="MS-right"><i class="fa fa-chevron-right" aria-hidden="true"></i></button>
       </div>
   </div>

    <!-- Include jQuery -->
    <script src="vendors/jquery/jquery-3.4.1.min.js"></script>

    <!-- Include Multislider -->
    <script src="js/multislider.min.js"></script>

    <!-- Initialize element with Multislider -->
    <script>
    $('#exampleSlider').multislider({
        interval: 0,
        slideAll: false,
        duration: 1500
    });
    </script>
  </body>
</html>