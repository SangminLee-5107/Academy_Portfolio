<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head> 
  <title> Artwork list </title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
  .detail_box {width:100%; height:500px; margin-top:50px;}
  .detail_box2 {width:1000px; height:100%; margin:auto;}
  .detail_table {width:100%; height:50%; border:1px; margin-top:12px; border-top:2px solid #bbb; border-bottom:2px solid #bbb;}
  .detail_table td {text-align:right; color:#545454;}
  .detail_table tr:nth-child(3) {border-bottom:1px solid #ddd;}
  .detail_table_img {width:50%; padding:0px;}
  .detail_table_img>img {float:left;} 
  </style>
</head>

<jsp:include page = "header.jsp"></jsp:include>

<body>
<div class="container mt-3">
  <div class="jumbotron text-center">
    <h2>해당 작품의 상세페이지입니다.</h2>
  </div>  
</div>

<div class="detail_box">
  <div class="detail_box2">
     <table class="detail_table">
	   <c:forEach var = "User" items = "${Users}">
         <c:if test = "${Artwork.artworkArtistNo eq User.userNo}">
           <td class="detail_table_img" rowspan="4" >
           <img src = "resources/images-artwork/${User.userRealName}/${Artwork.artworkFilename}" height="350" width="450"
            alt="이미지 출력을 실패했습니다.">
           </td>
           
           <th>작품 이름 :</th>
           <td>${Artwork.artworkName}</td>
           
           <tr>
           <th>작가 :</th>
           <td>${User.userRealName}</td>
           </tr>
                  
           <tr>
           <th>가격 :</th>
           <td>${Artwork.artworkPrice}￦</td>
           </tr>
           
           <tr>
           <td colspan="2">
 			<c:url var="Buy" value="/BuyArtwork">
				<c:param name="artworkNo" value="${Artwork.artworkNo}"></c:param>
			</c:url>
           	<a href="${Buy}" class="btn btn-primary">구매하기</a>
             
             <c:url var="AddCart" value="/savecart">
				<c:param name="artworkNO" value="${Artwork.artworkNo}"></c:param>
			 </c:url>
			 <a href = "${AddCart}" onclick="if(!(confirm('해당 상품을 장바구니에 추가하시겠습니까?'))) return false">
			 <img src = "resources/image/cart.png" height = "35" width = "35" style="margin-left:15px;"></a>
             
             <c:url var="AddWish" value="/savewish">
				 <c:param name="artworkNum" value="${Artwork.artworkNo}"></c:param>
			 </c:url> 
			 <a href = "${AddWish}" onclick = "if(!(confirm('해당 상품을 찜 목록에 추가하시겠습니까?'))) return false">
			 <img src = "resources/image/wish.png" id = "Wish" height = "35" width = "35" style="margin-left:15px;"></a>
           </td>
           </tr>
           
        </c:if>
       </c:forEach>            
     </table>
  </div>
</div>

</body>

<jsp:include page = "footer.jsp"></jsp:include>

</html>
