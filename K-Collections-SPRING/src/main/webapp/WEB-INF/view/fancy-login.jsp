<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
	<title>Login Page</title>
	
	<style>
	input:-webkit-autofill { -webkit-box-shadow: 0 0 0 30px #272C32 inset ; -webkit-text-fill-color: #fff; }
	input:-webkit-autofill, input:-webkit-autofill:hover, input:-webkit-autofill:focus, input:-webkit-autofill:active { transition: background-color 5000s ease-in-out 0s; }
	body {background-color:#272C32;}
	.login_box {width:400px; height:400px; margin:auto; margin-top:100px;}
	.title_box {width:100%; height:100px; margin:auto; line-height:100px; text-align:center;}
	.title_box>h1 {color : #fff; font-size:40px;}
	.error>h3 {text-align:center; color:red;}
	.logout>h3 {text-align:center; color:#fff;}
	.username_box {width:100%; height:50px; margin-top:40px; border:0; outline: none;}
	.password_box {width:100%; height:50px; margin-top:20px; border:0; outline: none;} 
	.username {width:100%; height:50px; border:0; outline: none; border-bottom: 1px solid #ccc; font-size:18px; color:#fff; background-color:transparent;}
	.password {width:100%; height:50px; border:0; outline: none; border-bottom: 1px solid #ccc; font-size:18px; color:#fff; background-color:transparent;}
	.login_button {width:100%; height:50px; margin-top:40px; background-color:#007BFF; color:white; font-size:20px; 
					border:0; outline: none; border-radius:6px; cursor:pointer;}
	.signin {margin-top:30px; display:flex; justify-content:space-between;}
	.signin_user {border:0; outline: none; background-color:#272C32; color:#fff; cursor:pointer;}
	.signin_artist {border:0; outline: none; background-color:#272C32; color:#fff; cursor:pointer;}
	.signin_home {border:0; outline: none; background-color:#272C32; color:#fff; cursor:pointer;}
	</style>
</head>

<body>
<div class="login_box">
   <div class="title_box"><h1>SIGN IN</h1></div>
	  <form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="POST" >
	 	<div class="error">
	       	<c:if test="${param.error != null }">        
			<h3> Invalid username and password. </h3>
			</c:if>
		</div>
		
		<div class="logout">
			<c:if test="${param.logout != null}">            
			<h3> You have been logged out. </h3>
			</c:if>
		</div>
	
		<!-- User name -->
		<div class="username_box">
			<input type="text" name="username" placeholder="username" class="username">
		</div>	
		
		<!-- Password -->
		<div class="password_box">	
			<input type="password" name="password" placeholder="password" class="password">
		</div>	
		
		<!-- Login/Submit Button -->
			<button type="submit" class="login_button">Login</button>
			
		<div class="signin">
			<button type="button" class="signin_user" onclick="window.location.href='common_register'; return false;">일반 회원가입</button>
			<button type="button" class="signin_artist" onclick="window.location.href='artist_register'; return false;">작가 회원가입</button>
			<button type="button" class="signin_home" onclick="location.href='${pageContext.request.contextPath}/'; return false;">메인 페이지</button>
		</div>
		</form:form>
</div>
</body>
</html>