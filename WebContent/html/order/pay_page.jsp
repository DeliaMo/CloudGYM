<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.coachMenu.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.subscription.model.*"%>
<%@ page import="com.subList.model.*"%>
<%@ page import="redis.clients.jedis.Jedis" %>
<%@ page import="com.video.model.*" %>

<%
	response.setHeader("Cache-Control","no-store"); //HTTP 1.1
	response.setHeader("Pragma","no-cache");        //HTTP 1.0
	response.setDateHeader ("Expires", 0);

	String userID = null;
	try{
		userID = session.getAttribute("userID").toString(); 
	}catch(Exception e){
		userID = null;
	}

	List<String> errorMsgs = (List<String>) request.getAttribute("errorMsgs");
	
	int price = 0;
	Jedis jedis = new Jedis("localhost", 6379);
	pageContext.setAttribute("jedis", jedis);
	SubListService sublistSvc = new SubListService();
	CoachMenuService menuSvc = new CoachMenuService();
	VideoService videoSvc = new VideoService();
	CoachService coachSvc = new CoachService();
	
	Set<String> vset = new HashSet<>();
	Set<String> mset = new HashSet<>();
	Set<String> sset = new HashSet<>();
	Set<String> keys = null;
	try{
		keys = jedis.hkeys(userID);
		for(String key : keys){
			if(key.startsWith("2")){
				Integer subID = Integer.parseInt(jedis.hget(userID, key));
				SubListVO subListVO = sublistSvc.getBySubID(subID);
				Integer subPrice = subListVO.getPrice();
				price += subPrice;
			} else {
				price += Integer.parseInt(jedis.hget(userID, key));
			}
		}
		
		
		List<CoachMenuVO> menulist = new ArrayList<>();
		for(String menuID : mset){
			CoachMenuVO coachMenuVO = menuSvc.getByMenuID(Integer.parseInt(menuID));
			menulist.add(coachMenuVO);
		}
		
		
		List<VideoVO> videolist = new ArrayList<>();
		for(String videoID : vset){
			VideoVO videoVO = videoSvc.findByPrimaryKey(Integer.parseInt(videoID));
			videolist.add(videoVO);
		}
		
		pageContext.setAttribute("menulist", menulist);
// 		pageContext.setAttribute("sublist", sublist);
		pageContext.setAttribute("sset", sset);
		pageContext.setAttribute("videolist", videolist);
		
		long cartCount = jedis.hlen(userID);
		pageContext.setAttribute("cartCount", cartCount);
		
		pageContext.setAttribute("price", price);
	}catch(Exception e){
		String error = "請先登入才能看購物車裡的內容喔";
		pageContext.setAttribute("error", error);
		pageContext.setAttribute("cartCount", 0);
		pageContext.setAttribute("price", 0);
		pageContext.setAttribute("menulist", null);
		pageContext.setAttribute("sublist", null);
		pageContext.setAttribute("videolist", null);
	}
	
	
	jedis.close();
	
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/pay_page.css">
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
                    <img src="<%=request.getContextPath()%>/img/logo.png" alt="">
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
        <div id="top">
            <div id="head">
                <span>購買明細</span>
            </div>
            <div id="info">
                <ul>
                	<%
                	if(keys != null){
                	for(String key : keys){
                		if(key.startsWith("6")){ %>
                			<li class="<%=key%>">
		                        <img src="<%=request.getContextPath() %>/img/work_out_2.jpg" alt="">
		                        <div id="wrapper">
		                            <h3><%=menuSvc.getByMenuID(new Integer(key)).getMenuName() %></h3>
		                            <p>商品內容商品內容商品內容商品內容商品內容商品內容商品內容商品內容</p>
		                        </div>
		                        
		                        <div class="price">$ <span><%=jedis.hget(userID, key) %></span></div>
		                        <form id="delete_from_cart" method="post">
		                        	<input type="hidden" name="action" value="deleteProduct"/>
		                        	<input type="hidden" name="menuID" value="<%=key%>"/>
			                        <button class="del" type="button">
			                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
			                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
			                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
			                              </svg>
			                        </button>
		                        </form>
		                    </li>
                	<%	}
                		if(key.startsWith("3")){ %>
                			<li class="<%=key%>">
		                        <img src="<%=request.getContextPath() %>/img/work_out_2.jpg" alt="">
		                        <div id="wrapper">
		                            <h3><%=videoSvc.findByPrimaryKey(new Integer(key)).getTitle() %></h3>
		                            <p>商品內容商品內容商品內容商品內容商品內容商品內容商品內容商品內容</p>
		                        </div>
		                        
		                        <div class="price">$ <span><%=jedis.hget(userID, key) %></span></div>
		                        <form id="delete_from_cart" method="post">
		                        	<input type="hidden" name="action" value="deleteProduct"/>
		                        	<input type="hidden" name="videoID" value="<%=key%>"/>
			                        <button class="del" type="button">
			                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
			                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
			                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
			                              </svg>
			                        </button>
		                        </form>
		                    </li>
                	<%	}
                		if(key.startsWith("2")){ %>
                			<li>
		                        <img src="<%=request.getContextPath() %>/img/work_out_2.jpg" alt="">
		                        <div id="wrapper">
		                            <h3>訂閱方案</h3>
		                            <p><%=coachSvc.getByUserID(new Integer(key)).getCoachName()%> - <%=sublistSvc.getBySubID(new Integer(jedis.hget(userID, key))).getDuration() %></p>
		                        </div>
		                        
		                        <div class="price">$ <span><%=sublistSvc.getBySubID(new Integer(jedis.hget(userID, key))).getPrice() %></span></div>
		                        <form id="delete_from_cart" method="post">
		                        	<input type="hidden" name="action" value="deleteProduct"/>
		                        	<input type="hidden" name="coachID" value="<%=key%>"/>
			                        <button class="del" type="button">
			                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
			                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
			                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
			                              </svg>
			                        </button>
		                        </form>
		                    </li>	
                	<%	}
                	}
                	}else{%>
                		<h4>${error}</h4>
                	<%} %>
                </ul>
            </div>
            <div id="foot">
                <p>總金額：$ <span>${price}</span></p>
            </div>
        </div>
        <div id="bottom">
            <div id="head2">
                <p>付款資訊</p>
            </div>
            <div id="card_info">
                <h3>Credit Card Info</h3>
                <form action="<%=request.getContextPath()%>/orders/orders.do" method="post">
                    <div class="col2">
                        <label>Card Number</label>
                        <%if(errorMsgs != null){if(errorMsgs.contains("卡號不得為空")) {%>
                        <span style="color:red; margin-left:10px; font-size:10px">卡號不得為空</span>
                        <%}} %>
                        <input class="number" type="text" maxlength="19" name="cardNumber"
                        onkeypress='return event.charCode >= 48 && event.charCode <= 57'/> 
                        <label>Cardholder name</label>
                        <%if(errorMsgs != null){if(errorMsgs.contains("請輸入持卡人姓名")) {%>
                        <span style="color:red; margin-left:10px; font-size:10px">請輸入持卡人姓名</span>
                        <%}} %>
                        <input class="inputname" name="cardName" type="text" placeholder="" />
                        <label>Expiry date</label>
                        <%if(errorMsgs != null){if(errorMsgs.contains("請輸入卡片有效期限")) {%>
                        <span style="color:red; margin-left:10px; font-size:10px">請輸入卡片有效期限</span>
                        <%}} %>
                        <input class="expire" name="expire" type="text" placeholder="MM / YYYY" />
                        <label>Security Number</label>
                        <%if(errorMsgs != null){if(errorMsgs.contains("請輸入卡片安全碼")) {%>
                        <span style="color:red; margin-left:10px; font-size:10px">請輸入卡片安全碼</span>
                        <%}} %>
                        <input class="ccv" name="ccv" type="text" placeholder="CVC" maxlength="3"
                            onkeypress='return event.charCode >= 48 && event.charCode <= 57' />
                    </div>
                    <div id="button">
                        <button type="submit">確定</button>
                        <input type="hidden" name="action" value="pay">
                        <button type="button" onclick="javascript:location.href='../video/all_video_page.jsp'">取消</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


	<script src="../../js/jquery-3.6.0.min.js"></script>
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
    		
    		
//     		let origin = ${origin};
//     		console.log(origin);
    		$("button.del").on("click", function(){
    			
    			var that = $(this);
    			$.ajax({
        			url:"<%=request.getContextPath()%>/orders/orders.do",
        			type:"post",
        			data:$(this).closest("form").serialize(),
        			dataType:"json",
        			success: function(data){
        				that.closest("li").fadeOut(1000, function(){
        					$(this).remove();
//        			            console.log($("#info ul").children("li").length);
        		            if($("#info ul").children("li").length == 0){
        		                $("#info ul").html("<p style='font-size: 16px; margin-bottom: 20px; margin-top: 0;'>購物車裡還沒有商品喲~</p>");
        		            }
        		        });
        				console.log(data);
        				console.log("success");
        				$("div#option span").html(data[1]);
        				
        				let newPrice = $("div#foot span").html() - data[0];
        				$("div#foot span").html(newPrice);
        				console.log($("div#option span").html());
        				
        				if($("div#option span").html() == 0){
        					$("i.bi-cart-fill span").addClass("-on");
        					$("i.bi-cart-fill span").attr("style", "display:none");
        				}else{
        					$("i.bi-cart-fill span").removeClass("-on");
        					$("i.bi-cart-fill span").attr("style", "");
        				}
        			},
        			error : function(XMLHttpResponse, textStatus, errorThrown) {
						console.log("1 非同步呼叫返回失敗,XMLHttpResponse.readyState:"	+ XMLHttpResponse.readyState);
						console.log("2 非同步呼叫返回失敗,XMLHttpResponse.status:"	+ XMLHttpResponse.status);
						console.log("3 非同步呼叫返回失敗,textStatus:"	+ textStatus);
						console.log("4 非同步呼叫返回失敗,errorThrown:" + errorThrown);
					}
        		})	
    		});
    		
    		
    		
    		
    		
    		
    		$(".number").on("keyup", function(e){
    	        if(e.which == 229){
    	            $(this).val("");
    	        }
    	        // console.log($(this).val());
    	        // if(e.key >= 0 && e.key <= 9){
    	        //     if($(this).val().length === 4 || $(this).val().length === 9 || $(this).val().length === 14){
    	        //       $(this).val($(this).val() +  " ");
    	        //     }
    	        // }
    	        if($(this).val().length == 4){
    	            $(this).val($(this).val() +  " ");
    	        }
    	        if($(this).val().length == 9){
    	            $(this).val($(this).val() +  " ");
    	        }
    	        if($(this).val().length == 14){
    	            $(this).val($(this).val() +  " ");
    	        }
    	        
    	    })

    	    $(".number").on("keydown", function(e){
    	        if($(this).val().length == 5 && e.which == 8){
    	            let val = $(this).val().trim();
    	            $(this).val(val);
    	        }
    	        if($(this).val().length == 10 && e.which == 8){
    	            let val = $(this).val().trim();
    	            $(this).val(val);
    	        }
    	        if($(this).val().length == 15 && e.which == 8){
    	            let val = $(this).val().trim();
    	            $(this).val(val);
    	        }
    	    })

    	    //Date expire input
    	    $(".expire").keypress(function(event){
    	        if(event.charCode >= 48 && event.charCode <= 57){
    	          if($(this).val().length === 1){
    	              $(this).val($(this).val() + event.key + " / ");
    	          }else if($(this).val().length === 0){
    	            if(event.key == 1 || event.key == 0){
    	              month = event.key;
    	              return event.charCode;
    	            }else{
    	              $(this).val(0 + event.key + " / ");
    	            }
    	          }else if($(this).val().length > 2 && $(this).val().length < 9){
    	            return event.charCode;
    	          }
    	        }
    	        return false;
    	    });

    	    $(".expire").on("keyup", function(e){
    	        if(e.which == 229){
    	            $(this).val("");
    	        }
    	    })

    	    $(".ccv").on("keyup", function(e){
    	        if(e.which == 229){
    	            $(this).val("");
    	        }
    	    });
    		
    		
    	})
    </script>
</body>
</html>