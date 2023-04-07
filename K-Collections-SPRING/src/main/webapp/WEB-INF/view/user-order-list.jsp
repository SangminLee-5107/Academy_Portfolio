<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
  <title>USER ORDER LIST</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
  .basket_box {width:100%; height:1500px;}
  .basket_box2 {width:1100px; height:100%; margin:auto;}
  .deleteAll_button {float:right; text-align:center; margin-right:5px; margin-bottom:12px;}
  #deleteAll_button {width:100px; height:50px; font-size:18px;}
  .basket_table {width:100%; height:60%; border:0; outline:none; text-align:center; margin-top:12px;}
  .basket_table th {border-top:3px solid #000; border-bottom:1px solid #000;}
  .basket_table td {border-bottom:1px solid #ddd;}
  </style>
</head>

 <jsp:include page = "header.jsp"></jsp:include>

<body>
<div class="container mt-3">
  <div class="jumbotron text-center" style= "background-image: url('resources/image/usermypagefront.png');">
  <h1>${user.userRealName}님의 구매내역목록입니다.</h1>
  </div>  
</div>
	
<div class="basket_box">
  <div class="basket_box2">	
	<table class="basket_table">
      <tr>
        <th>작품 사진</th>
        <th>작가</th>
        <th>작품명</th>
        <th>가격</th>
        <th>구매일자</th>
      </tr>
	       <c:forEach var="Sale" items="${Sales}">
	         <c:if test="${Sale.salesUserNo eq user.userNo}">
		       <c:forEach var = "Artwork" items = "${Artworks}">
	             <c:if test="${Sale.salesArtworkNo eq Artwork.artworkNo}">
				   <c:forEach var = "User" items = "${Users}">         
					 <c:if test = "${Artwork.artworkArtistNo eq User.userNo}"> 
					   <tr>
					     <td>
					     <img alt="이미지 출력을 실패했습니다." 
					    	  src = "resources/images-artwork/${User.userRealName}/${Artwork.artworkFilename}"
					     	  height = "200" width = "200"/>
					     </td>
					     <td> ${User.userRealName} </td>
					     <td> ${Artwork.artworkName} </td>
					     <td> ${Artwork.artworkPrice}￦ </td>
					     <td> ${Sale.salesPurchaseTime} </td>
					   </tr>    
					 </c:if>
				   </c:forEach> 
	             </c:if>
	           </c:forEach>  
	         </c:if>
	       </c:forEach>
    </table>
  </div>
</div>
</body>

<jsp:include page = "footer.jsp"></jsp:include>

</html>