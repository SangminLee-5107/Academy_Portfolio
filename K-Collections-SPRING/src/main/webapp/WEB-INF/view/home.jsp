<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
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
  /* Make the image fully responsive */
  .carousel-inner img {
    width: 80%;
    height: 80%;
  }
  
  .container_box {width:100%; height:1750px; background-color:#fff;}
  .container_box2 {width:1300px; height:100%; background-color:#fff; margin:auto;}
  .container1 {float:left; margin-right:45px; }
  .container2 {float:left; margin-right:45px;}
  .container3 {float:left; }
  .card {position:relative;}
  .best {position:absolute; top:10px; left:10px; color:white; font-size:18px; 
  		width:75px; height:33px; background-color:#666; border-radius: 6px; text-align:center; line-height:33px;}
  </style>
</head>

<jsp:include page = "header.jsp"></jsp:include>

<body>
<img src="https://c.pxhere.com/photos/01/71/art_black_and_white_exhibition_exposition_inspiration_museum_people_pictures-939643.jpg!d" srcset="https://c.pxhere.com/photos/01/71/art_black_and_white_exhibition_exposition_inspiration_museum_people_pictures-939643.jpg!d" alt="black and white, people, photography, museum, monochrome, pictures, brand, art, exposition, photograph, inspiration, exhibition, monochrome photography, vernissage, Free Images In PxHere"
	style = "width:80%; height:700px; margin: auto; display: block;">

	<c:if test="${unregister eq 'logout'}">
		<form:form id="logout" action="${pageContext.request.contextPath}/logout" method="POST">
		</form:form>
	</c:if>
	<script type="text/javascript">
		document.getElementById("logout").submit();
	</script>
	
	<%-- <security:authorize access = "isAuthenticated()">
		User : <security:authentication property="principal.username" />
		<br><br>
		Role(s) : <security:authentication property="principal.authorities" />
	</security:authorize> --%>

<div class="container_box">
	<div class="container_box2">
		<h1 style="text-align:center; margin-top:150px;">BEST WISH ARTWORKS</h1>
		<% int j=0; %>
		<c:forEach var="Bestwish" items="${bestWish}">
			<% 
				String b = "container";
				String c = "best";
				j++;
				if(j==1){
					b = "container1";
					c = "BEST 1";
				}else if(j==2) {
					b = "container2";
					c = "BEST 2";
				}else {
					b = "container3";
					c = "BEST 3";
				}
			%>
		<div class="<%= b %>">
		 	<div class="card" style="width:400px; margin-top:40px; margin-bottom:150px;">
		  		<c:forEach var="Artworks" items="${artworks}">
		  			<c:if test="${Artworks.artworkNo eq Bestwish.wishlistArtworkNo}">
		  				<c:forEach var="User" items="${Users}">
			      			<c:if test="${Artworks.artworkArtistNo eq User.userNo}">
			      				<div class="best"><%= c %></div>
			      				
			      				<c:url var ="Detaillink" value="/detail">
		   						  <c:param name="ArtworkNumber" value="${Artworks.artworkNo}"/>
		 						</c:url>
			      				
							    <img class="card-img-top" src="resources/images-artwork/${User.userRealName}/${Artworks.artworkFilename}" 
							    alt="이미지 출력에 실패했습니다." style="width:100%; height:450px; cursor:pointer;"
								onclick="window.location.href='${Detaillink}'; return false;">
							    <div class="card-body">
							      <h4 class="card-title">${Artworks.artworkName}</h4>
							      <p class="card-text">${User.userRealName} 작가</p>
							      
							      <div>
							        <c:url var="Buy" value="/BuyArtwork">
		              					<c:param name="artworkNo" value="${Artworks.artworkNo}"></c:param>
		           					</c:url>
		                           	<a href="${Buy}" class="btn btn-primary" style="float:left;">구매하기</a>
							        
							        <c:url var="AddWish" value="/savewish">
              						  <c:param name="artworkNum" value="${Artworks.artworkNo}"></c:param>
          							</c:url> 
       								<a href = "${AddWish}" onclick = "if(!(confirm('해당 상품을 찜 목록에 추가하시겠습니까?'))) return false">
       								<img src = "resources/image/wish.png" id = "Wish" height = "35" width = "35" style="float:right;"></a>
       						
       								<c:url var="AddCart" value="/savecart">
              						  <c:param name="artworkNO" value="${Artworks.artworkNo}"></c:param>
           							</c:url>
           							<a href = "${AddCart}" onclick="if(!(confirm('해당 상품을 장바구니에 추가하시겠습니까?'))) return false">
           							<img src = "resources/image/cart.png" height = "35" width = "35" style="float:right; margin-right:7px;"></a>
							      </div>
							      
							    </div>
							</c:if>
						</c:forEach>
					</c:if>
				</c:forEach>
		  	</div>
		</div>
		</c:forEach>
		
		<h1 style="text-align:center;">NEW ARTWORKS</h1>
		<div id="demo" class="carousel slide" data-ride="carousel" style="margin-top:40px;">
		  <ul class="carousel-indicators">
		    <li data-target="#demo" data-slide-to="0" class="active"></li>
		    <li data-target="#demo" data-slide-to="1"></li>
		    <li data-target="#demo" data-slide-to="2"></li>
		  </ul>
		  <div class="carousel-inner">
		    
		    <% boolean i = false; %>
		
		    <c:forEach var="Artwork" items="${newArtworks}">
		    	<% String a = null; 
					if(i){ 
						a = "carousel-item";
					} else {
						i = true;
						a = "carousel-item active";
					}
				%>    	
		    	<div class= "<%= a %>" >
			    	<c:forEach var="User" items="${Users}">
			      		<c:if test="${Artwork.artworkArtistNo eq User.userNo}">
							<img alt="이미지 출력에 실패했습니다." 
							src="resources/images-artwork/${User.userRealName}/${Artwork.artworkFilename}"
							style="width:1300px; height:700px; display:block; margin:0 auto;">	
			      			<div class="carousel-caption">
			        		<h3>${Artwork.artworkName}</h3> 
			        		<p>${User.userRealName}<p>
			      			</div>
			      		</c:if>
			    	</c:forEach>
		    	</div> 
		    </c:forEach>
		  </div>
  
  <a class="carousel-control-prev" href="#demo" data-slide="prev">
    <span class="carousel-control-prev-icon"></span>
  </a>
  <a class="carousel-control-next" href="#demo" data-slide="next">
    <span class="carousel-control-next-icon"></span>
  </a>
</div>
	</div>
</div>

<jsp:include page = "footer.jsp"></jsp:include>
</body>
</html>