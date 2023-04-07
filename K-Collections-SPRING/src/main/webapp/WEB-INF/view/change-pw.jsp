<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
  <title>Change Password</title>

  <style>
  input:-webkit-autofill { -webkit-box-shadow: 0 0 0 30px #272C32 inset ; -webkit-text-fill-color: #fff; }
  input:-webkit-autofill, input:-webkit-autofill:hover, input:-webkit-autofill:focus, input:-webkit-autofill:active { transition: background-color 5000s ease-in-out 0s; }
  body {background-color:#272C32;}
  .change_box {width:400px; height:400px; margin:auto; margin-top:100px;}
  .title_box {width:100%; height:100px; margin:auto; line-height:100px; text-align:center;}
  .title_box>h1 {color : #fff; font-size:40px;}
  .username_box {width:100%; height:50px; margin-top:40px; border:0; outline: none;}
  .password_box {width:100%; height:50px; margin-top:20px; border:0; outline: none;} 
  .username {width:100%; height:50px; border:0; outline: none; border-bottom: 1px solid #ccc; font-size:18px; color:#fff; background-color:transparent;}
  .password {width:100%; height:50px; border:0; outline: none; border-bottom: 1px solid #ccc; font-size:18px; color:#fff; background-color:transparent;}
  .change_button {width:100%; height:50px; margin-top:40px; background-color:#007BFF; color:white; font-size:20px; 
				border:0; outline: none; border-radius:6px; cursor:pointer;}
  .changepw {margin-top:10px; float:right; }
  .changepw_home {border:0; outline: none; background-color:#272C32; color:#fff; cursor:pointer;}  
  </style>	
</head>

<body>
<div class="change_box">
  <div class="title_box"><h1>Change Password</h1></div>
	<form:form action="changepw" modelAttribute="Users" method="GET" >
		<!-- User name -->
		<div class="username_box">			
			<form:input path="userRealName" class="username"/>
		</div>
		
		<!-- Password -->
		<div class="password_box">
			<input type="text" name="password" placeholder="password" class="password">
		</div>
		
		<!-- Login/Submit Button -->
		<div>	
			<button type="submit" class="change_button" onclick="if(!(confirm('비밀번호를 변경하시겠습니까?'))) return false">비밀번호 변경</button>
		</div>
		
		<div class="changepw">
			<button type="button" class="changepw_home" onclick="window.location.href='userPage'; return false;">이전으로</button>
		</div>
	</form:form>
</div>	
</body>
</html>