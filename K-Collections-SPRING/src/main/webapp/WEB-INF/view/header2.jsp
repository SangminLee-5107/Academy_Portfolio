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
  <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
  
  <style>
  .logo {cursor:pointer;}
  .nav-item {
  cursor:pointer;
  font-weight: bold;
  font-size:18px 
  }
  .navbar-nav > li{
  padding-left:5px;
  padding-right:5px;
  }

  </style>
</head>
<body>


<header>
<div class="jumbotron text-center" style="background: white; margin-bottom:0;" onclick="window.location.href='${pageContext.request.contextPath}/'; return false;">
  <img src="../resources/image/logo_kcollections.png" class="logo" style="width:20%"
            onclick="location.href='${pageContext.request.contextPath}/'; return false;">
</div>

<nav class="navbar navbar-expand-sm bg-light navbar-light"> 
  <a class="navbar-brand"><span class="badge badge-secondary">MENU</span></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  
   <div class="collapse navbar-collapse justify-content-between" id="collapsibleNavbar">
    <ul class="navbar-nav" id="nav5" >
      <li class="nav-item">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/byCategory'; return false;">ARTWORKS</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/byArtist'; return false;">ARTISTS</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/vipPage'; return false;">VIP ZONE</a>
      </li> 
     </ul>
     <ul class="navbar-nav" id="nav5" >   
     <li class="nav-item">
      <security:authorize access = "isAnonymous()">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/showMyLoginPage'; return false;">MYPAGE</a>
      </security:authorize>  
      </li> 
      <li class="nav-item">
      <security:authorize access = "hasAnyRole('USER', 'VIP', 'VVIP', 'ARTIST')">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/userPage'; return false;">MYPAGE</a>
      </security:authorize>
      </li>
      <li class="nav-item">
      <security:authorize access = "hasRole('ARTIST')">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/artistPage/artist'; return false;">작가페이지</a>
      </security:authorize>        
      </li> 
      <li class="nav-item">
      <security:authorize access = "hasRole('ADMIN')">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/adminPage/admin'; return false;">관리자페이지</a>
      </security:authorize>  
      </li> 
      <li class="nav-item">
      <security:authorize access = "isAnonymous()">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/showMyLoginPage'; return false;"><i class='fas fa-heart' style='font-size:24px'></i></a>
      </security:authorize>  
      </li>
      <li class="nav-item">
      <security:authorize access = "hasAnyRole('USER', 'VIP', 'VVIP', 'ARTIST')">
        <a class="nav-link" href="${pageContext.request.contextPath}/showWish"><i class='fas fa-heart' style='font-size:24px'></i></a>
      </security:authorize>  
      </li>  
      <li class="nav-item">
      <security:authorize access = "isAnonymous()">
        <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/showMyLoginPage'; return false;"><i class='fas fa-shopping-cart' style='font-size:24px'></i></a>
      </security:authorize>  
      </li>
      <li class="nav-item">
      <security:authorize access = "hasAnyRole('USER', 'VIP', 'VVIP', 'ARTIST')">
        <a class="nav-link" href="${pageContext.request.contextPath}/showcart"><i class='fas fa-shopping-cart' style='font-size:24px'></i></a>
      </security:authorize>  
      </li>  
      <li class="nav-item">   
      <security:authorize access = "isAnonymous()">
         <a class="nav-link" onclick="window.location.href='${pageContext.request.contextPath}/showMyLoginPage'; return false;">
			<button type="submit" class="btn btn-outline-dark">Login</button></a>
      </security:authorize>
      </li>     
      <li class="nav-item ml-auto">
      <security:authorize access = "isAuthenticated()">
       <form:form action="${pageContext.request.contextPath}/logout" method="POST">
         <button type="submit" class="btn btn-outline-dark">Logout</button>
      </form:form> 
     </security:authorize>
      </li>
    </ul>
    </div>
    
<%-- 검색 기능
     <form class="form-inline" action="/action_page.php">
    <input class="form-control mr-sm-2" type="text" placeholder="Search">
    <button class="btn btn-secondary" type="submit">Search</button>
     </form> --%>
</nav>

</header>

</body>
</html>