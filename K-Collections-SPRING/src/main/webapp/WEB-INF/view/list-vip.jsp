<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>VIP ZONE</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
  .container_box {width:1080px; height:1100px; background-color:#fff; margin:auto;}
  .container1 {float:left; margin-right:50px;}
  #card-body {height:200px;}
  </style>
</head>

<jsp:include page = "header.jsp"></jsp:include>

<body>

<div class="container mt-3">
  <div style="margin-top:50px">
	  <div class="jumbotron text-center" style= "background-image: url('resources/image/vippagefront.png'); font-size:20px">
	    <h1>VIP ZONE입니다.</h1>      
	    <p>${User.userRealName}님의 회원등급 : "${Role}" </p>
	    <p>이곳은 VIP 이상의 등급을 가지신 회원님만을 위해 특별히 엄선한 작품들을 선보입니다.</p>
	    
	  </div>
  
  	   <ul class="nav nav-tabs">
		   <li class="nav-item">
		     <a class="nav-link active" data-toggle="tab" href="#vip">VIP</a>
		   </li>
		   <li class="nav-item">
		     <a class="nav-link" data-toggle="tab" href="#vvip">VVIP</a>
		   </li>
	   </ul>
	   
       <div class="container_box">
   		 <div class="tab-content">

	       <div id="vip" class="container tab-pane active"><br>
	         <h3>VIP Artworks</h3>
	         <p>VIP 회원님만을 위해 엄선한 작품들로 컬렉션을 구성해보세요!</p>
	           <c:forEach var="tempArtwork" items="${artworksVip}">
				  <div class="container1">	
	                 <div class="card" style="width:300px; margin-top:15px;">
						<c:forEach var="Users" items="${Users}">
	                 		<c:if test= "${Users.userNo eq tempArtwork.artworkArtistNo}">
	                 		  <c:url var ="Detaillink" value="/detail">
			   					<c:param name="ArtworkNumber" value="${tempArtwork.artworkNo}"/>
			 				  </c:url>	                 		
								<img class="card-img-top" src="resources/images-artwork/${Users.userRealName}/${tempArtwork.artworkFilename}" 
								alt="이미지 출력을 실패했습니다." style="width:100%; height:400px; cursor:pointer;"
								onclick="window.location.href='${Detaillink}'; return false;"> 
							            
						<div class="card-body" id="card-body">
						  <b>제목: ${tempArtwork.artworkName}</b>
	                      <br><b>작가: ${Users.userRealName}</b>
	                      <br><b>가격: ${tempArtwork.artworkPrice}￦</b>
	                      <br><b>등록일: ${tempArtwork.artworkRegdate}</b><br><br>
	                      
	                      <div>	
                 			<c:url var="Buy" value="/BuyArtwork">
              					<c:param name="artworkNo" value="${tempArtwork.artworkNo}"></c:param>
           					</c:url>
                           	<a href="${Buy}" class="btn btn-primary" style="float:left;">구매하기</a>
        							
           					<c:url var="AddWish" value="/savewish">
              					<c:param name="artworkNum" value="${tempArtwork.artworkNo}"></c:param>
          					</c:url> 
       						<a href = "${AddWish}" onclick = "if(!(confirm('해당 상품을 찜 목록에 추가하시겠습니까?'))) return false">
       						<img src = "resources/image/wish.png" id = "Wish" height = "35" width = "35" style="float:right;"></a>
       						
       						<c:url var="AddCart" value="/savecart">
              					<c:param name="artworkNO" value="${tempArtwork.artworkNo}"></c:param>
           					</c:url>
           					<a href = "${AddCart}" onclick="if(!(confirm('해당 상품을 장바구니에 추가하시겠습니까?'))) return false">
           					<img src = "resources/image/cart.png" height = "35" width = "35" style="float:right; margin-right:7px;"></a>
	                      </div>
	                    </div>
                      </c:if>
					 </c:forEach> 
                   </div>  
                 </div>
               </c:forEach>
	          </div> 
	         
	         
	      <div id="vvip" class="container tab-pane fade"><br>
		      <c:choose>	          
			    <c:when test = "${Role eq 'VVIP' or Role eq '관리자'}">
			         <h3>VVIP Artworks</h3>
			         <p>VVIP 회원님만을 위해 엄선한 작품들로 컬렉션을 구성해보세요!</p>
				           <c:forEach var="tempArtwork" items="${artworksVvip}">	
				           	  <div class="container1">					  
				                 <div class="card" style="width:300px; margin-top:15px;">
									<c:forEach var="Users" items="${Users}">
				                 		<c:if test= "${Users.userNo eq tempArtwork.artworkArtistNo}">
				                 		  <c:url var ="Detaillink" value="/detail">
						   					<c:param name="ArtworkNumber" value="${tempArtwork.artworkNo}"/>
						 				  </c:url>	                 		
											<img class="card-img-top" src="resources/images-artwork/${Users.userRealName}/${tempArtwork.artworkFilename}" 
											alt="이미지 출력을 실패했습니다." style="width:100%; height:400px; cursor:pointer;"
											onclick="window.location.href='${Detaillink}'; return false;"> 
										            
										<div class="card-body" id="card-body">
										  <b>제목: ${tempArtwork.artworkName}</b>
					                      <br><b>작가: ${Users.userRealName}</b>
					                      <br><b>가격: ${tempArtwork.artworkPrice}￦</b>
					                      <br><b>등록일: ${tempArtwork.artworkRegdate}</b><br><br>
				                      
					                      <div>	
				                 			<c:url var="Buy" value="/BuyArtwork">
				              					<c:param name="artworkNo" value="${tempArtwork.artworkNo}"></c:param>
				           					</c:url>
				                           	<a href="${Buy}" class="btn btn-primary" style="float:left;">구매하기</a>
				        							
				           					<c:url var="AddWish" value="/savewish">
				              					<c:param name="artworkNum" value="${tempArtwork.artworkNo}"></c:param>
				          					</c:url> 
				       						<a href = "${AddWish}" onclick = "if(!(confirm('해당 상품을 찜 목록에 추가하시겠습니까?'))) return false">
				       						<img src = "resources/image/wish.png" id = "Wish" height = "35" width = "35" style="float:right;"></a>
				       						
				       						<c:url var="AddCart" value="/savecart">
				              					<c:param name="artworkNO" value="${tempArtwork.artworkNo}"></c:param>
				           					</c:url>
				           					<a href = "${AddCart}" onclick="if(!(confirm('해당 상품을 장바구니에 추가하시겠습니까?'))) return false">
				           					<img src = "resources/image/cart.png" height = "35" width = "35" style="float:right; margin-right:7px;"></a>
				                          </div>
				                        </div>
			                      	    </c:if>
							 		</c:forEach>  
			                	 </div>
			                   </div>	 
		               </c:forEach>
	 			  </c:when>
	 			  
	 			  <c:otherwise>           
	            	<div class="jumbotron text-center" style= "background-image: url('resources/image/sorryvip.png');">
				    	<h3>죄송합니다, 고객님은 "${Role}" 등급으로, 해당 배너는 VVIP 등급 회원만이 입장 가능합니다.</h3>  
				    </div>
	 			  </c:otherwise>
	 			</c:choose> 
 			</div>   
 				
	      </div>   
	    </div> 
     </div>
  </div>  
  
    <p style=clear:both;></p> 

</body>

<jsp:include page = "footer.jsp"></jsp:include>

</html>