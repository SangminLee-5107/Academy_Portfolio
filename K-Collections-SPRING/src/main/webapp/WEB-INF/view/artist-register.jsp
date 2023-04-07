<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<html>

<head>
	<title>Sign Up</title>
	
	<style>
	input:-webkit-autofill { -webkit-box-shadow: 0 0 0 30px #272C32 inset ; -webkit-text-fill-color: #fff; }
    input:-webkit-autofill, input:-webkit-autofill:hover, input:-webkit-autofill:focus, input:-webkit-autofill:active { transition: background-color 5000s ease-in-out 0s; }
    body {background-color:#272C32;}
    .signup_box {width:500px; height:400px; margin:auto; margin-top:100px;}
    .title_box {width:100%; height:100px; margin:auto; line-height:100px; text-align:center; margin-bottom:10px;}
    .title_box>h1 {color:#fff; font-size:40px;}
    .radio {color:#fff; text-align:center;}
    .userid_box {width:100%; height:50px; margin-top:20px; border:0; outline: none;}
    .password_box {width:100%; height:50px; margin-top:20px; border:0; outline: none;} 
    .username_box {width:100%; height:50px; margin-top:20px; border:0; outline: none;} 
    .userid {width:100%; height:50px; border:0; outline: none; border-bottom: 1px solid #ccc; font-size:18px; color:#fff; background-color:transparent;}
    .password {width:100%; height:50px; border:0; outline: none; border-bottom: 1px solid #ccc; font-size:18px; color:#fff; background-color:transparent;}
    .username {width:100%; height:50px; border:0; outline: none; border-bottom: 1px solid #ccc; font-size:18px; color:#fff; background-color:transparent;}
    .signup_button {width:100%; height:50px; margin-top:40px; background-color:#007BFF; color:white; font-size:20px; 
				border:0; outline: none; border-radius:6px; cursor:pointer;}
	.signup {margin-top:10px; float:right;}
    .signup_login {border:0; outline: none; background-color:#272C32; color:#fff; cursor:pointer;}
	</style>
</head>

<body>
<div class="signup_box">
   <div class="title_box"><h1>SIGN UP</h1></div>
	  <form:form action="${pageContext.request.contextPath}/register" method="GET" >
		 <input type="hidden" name="enAbled" value="1" />
		 <input type="hidden" name="authority" value="ROLE_ARTIST" />
		 
		 <div class="radio">
			 <input type="radio" name="userCategoryNo" value="2"/>서양화
	         <input type="radio" name="userCategoryNo" value="3"/>한국화
	         <input type="radio" name="userCategoryNo" value="4"/>문인화/서예
	         <input type="radio" name="userCategoryNo" value="5"/>조각
	         <input type="radio" name="userCategoryNo" value="6"/>공예/디자인
		 </div>
		 
		 <!-- User ID -->
		 <div class="userid_box">		   
			<input type="text" name="userName" placeholder="userid" class="userid">
         </div>

		 <!-- Password -->
		 <div class="password_box">
			<input type="password" name="password" placeholder="password" class="password" >
		 </div>
			
		 <!-- User name -->
		 <div class="username_box">
			<input type="text" name="userRealName" placeholder="username" class="username">
		 </div>
		 
		 <!-- Login/Submit Button -->
		 <div>
		 	<button type="submit" class="signup_button" onclick="if(!(confirm('회원가입 하시겠습니까?'))) return false">회원가입</button>
		 </div>
		 
		 <div class="signup">
			<button type="button" class="signup_login" onclick="window.location.href='showMyLoginPage'; return false;">이전으로</button>
		 </div>
	  </form:form>
</div>

</body>
</html>