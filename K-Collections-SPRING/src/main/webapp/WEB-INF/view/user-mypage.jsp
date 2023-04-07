<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<title>MY PAGE</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
  .mypage_box {width:100%; height:600px;}
  .mypage_box2 {width:1300px; height:80%; margin:auto;}
  .button_box {display:flex; justify-content: space-around; margin-top:70px;}
  .button1, .button2, .button3, .button4 {height:150px;}
  .button1-1, .button2-1, .button3-1, .button4-1 {width:80px; height:80px; margin:auto; display:block; cursor:pointer;}
  .button1-2, .button2-2, .button3-2, .button4-2 {height:30px; text-align:center; line-height:30px;}
  .level {display:inline;}
  </style>
</head>

<jsp:include page = "header.jsp"></jsp:include>

<body>
<div class="mypage_box">
  <div class="mypage_box2">
	<div class="container mt-3">
	  <div class="jumbotron text-center" style= "background-image: url('resources/image/usermypagefront.png');">
	    <h1>${Users.userRealName}님의 마이페이지입니다.</h1><br>
	    <h4>현재 <b class="level">"${Role}"</b> 등급입니다.</h4>
	    <h5>${Users.userRealName}님의 총 구매액: ${Users.userPurchaseAmount}원</h5> 
	    <h6>(VIP 등급: 총 구매액 300만원 이상 / VVIP 등급: 총 구매액 600만원 이상)</h6>  
	    <%--  User : <security:authentication property="principal.username"/>
	    <br><br>
	    Role(s): <security:authentication property="principal.authorities" /> --%>
	  </div>	  
	  <div class="button_box">
	  	<div class="button1">
	  	  <div>
	  	    <img src="resources/image/password.png" class="button1-1"
	  	    onclick="location.href='${pageContext.request.contextPath}/change-pw'; return false;">
	  	  </div><br>
	  	  <div class="button1-2">
	  	    <button class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/change-pw'; return false;">비밀번호 변경</button>
	  	  </div>
	  	</div>
		  
		<div class="button2">
	  	  <div>
	  	    <img src="resources/image/list.png" class="button2-1"
	  	    onclick="location.href='${pageContext.request.contextPath}/order-list'; return false;">
	  	  </div><br>
	  	  <div class="button2-2">
	  	    <button class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/order-list'; return false;">주문내역</button>
	  	  </div>
	  	</div>  
	  	
	  	<div class="button3">
	  	  <div>
	  	  	<c:url var="unregister" value="/unregister"/>
	  	    <a href="${unregister}"><img src="resources/image/resign.png" class="button3-1"
	  	    onclick="if(!(confirm('정말 회원 탈퇴를 하시겠습니까?'))) return false"></a>
	  	  </div><br>
	  	  <div class="button3-2">
	  	  	<c:url var="unregister" value="/unregister"/>
	  	    <a href="${unregister}"><button class="btn btn-dark" onclick="if(!(confirm('정말 회원 탈퇴를 하시겠습니까?'))) return false">회원탈퇴</button></a>
	  	  </div>
	  	</div>
	  	
	  	<div class="button4">
	  	  <div>
	  	    <img src="resources/image/home.png" class="button4-1"
	  	    onclick="location.href='${pageContext.request.contextPath}/'; return false;">
	  	  </div><br>
	  	  <div class="button4-2">
	  	    <button class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/'; return false;">홈 화면</button>
	  	  </div>
	  	</div>
	  </div>
	</div>
  </div>
</div>  	
</body>

<jsp:include page = "footer.jsp"></jsp:include>

</html>