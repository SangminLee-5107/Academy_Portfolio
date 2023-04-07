<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>artist page</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
  .container_box {width:1080px; height:700px; background-color:#fff; margin:auto;}
  .container1 {float:left; margin-right:50px;}
/*   .card {position:relative;} */
  </style> 
</head>

<jsp:include page = "header.jsp"></jsp:include>

<body>

	<div class="container mt-3">
	  <div style="margin-top:50px">
	  	<ul class="nav nav-tabs">
	  	<% boolean i = false; %>
	  	<% int j=1; %>
		  	<c:forEach var="Artists" items="${artists}">
		  		<% String a = null; 
					if(i){ 
						a = "nav-link";
					} else {
						i = true;
						a = "nav-link active";
					}
				
					String c = "#artist" + j;
					j++;
				%>
				    <li class="nav-item">
				      <a class="<%= a %>" data-toggle="tab" href="<%= c %>">${Artists.userRealName}</a>
				    </li>	
		    </c:forEach>	           
		</ul>
		
		<% j=1; i=false; %>  
		  <div class="container_box">
			   <div class="tab-content">  
			    <% int k=0; %>
				<c:forEach var="Artists" items="${artists}">
			  		<% 
			  		    String b = null;
					    if(i){ 
							b = "container tab-pane fade";
						} else {
							i = true;
							b = "container tab-pane active";
						}
					    
					    String c = "artist" + j;
					    String d = "container" + k;
						j++;
						k++;
					%>  
				  <div id="<%= c %>" class="<%= b %>"><br>
				    <h3> ${Artists.userRealName} 작가 </h3>
				       <c:forEach var="Artworks" items="${artworks}">
						<c:if test="${Artworks.artworkArtistNo eq Artists.userNo}">
					   	  <div class="container1">
							 <div class="card" style="width:300px; margin-top:15px;">
							    <c:url var ="Detaillink" value="/detail">
		   						  <c:param name="ArtworkNumber" value="${Artworks.artworkNo}"/>
		 						</c:url>
		 						
								<img class=card-img-top" src="resources/images-artwork/${Artists.userRealName}/${Artworks.artworkFilename}"
								alt="이미지 출력에 실패했습니다." style="width:100%; height:400px; cursor:pointer;"
								onclick="window.location.href='${Detaillink}'; return false;">
								
								<div class="card-body">
								  <b>제목: ${Artworks.artworkName}</b>
                 		  		  <br><b>작가: ${user.userRealName}</b>
                 		  		  <br><b>등록일: ${Artworks.artworkRegdate}</b><br><br>
									
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
							 </div>	 
					      </div>
					    </c:if>  
					   </c:forEach>      
				  </div>
			   </c:forEach>
			</div> 
		 </div>
	  </div>
	</div>
	
	 <p style=clear:both;></p> 

<jsp:include page = "footer.jsp"></jsp:include>

</body>
</html>