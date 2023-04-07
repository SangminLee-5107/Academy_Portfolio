<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>#K-Collections</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
    @font-face {
    font-family: 'Pretendard-Regular';
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
    font-weight: 400;
    font-style: normal;
	}
	
	body {font-family: 'Pretendard-Regular';}
  </style>
</head>
<body>

<Footer>
<div class="jumbotron text-center" onclick="window.location.href='${pageContext.request.contextPath}/'; return false;">
  <img src="../resources/image/logo_kcollections.png" class="logo" style="width:20%"
            onclick="location.href='${pageContext.request.contextPath}/'; return false;"><br><br><br>
  <p>서울특별시 종로구 묘동 56 단성사 빌딩</p>
  <p>TEL) 010-7749-5107</p>
</div>
</Footer>


</body>
</html>