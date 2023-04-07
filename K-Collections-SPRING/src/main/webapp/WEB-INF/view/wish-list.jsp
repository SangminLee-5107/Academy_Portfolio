<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head> 
  <title>Cart list </title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
  .basket_box {width:100%; height:1300px;}
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
  <div class="jumbotron text-center" style= "background-image: url('resources/image/artistmypagefront.png');">
  <%-- <security:authentication property="principal.username" />님의 장바구니 목록입니다.  --%>
  <h1>${User.userRealName}님의 찜목록입니다.</h1>
  </div>  
</div>

<div class="basket_box">
  <div class="basket_box2">
    <c:url var = "DeleteAll" value = "/deleteAll">
      <c:param name = "userId" value = "${Userlist.userNo}"></c:param>
    </c:url>
    
    <div class="deleteAll_button">
    <a href = "${DeleteAll}"><input type ="button" value = "전체삭제" class="btn btn-secondary" id="deleteAll_button"
	              				  onclick="if(!(confirm('찜목록의 작품을 모두 삭제하시겠습니까?'))) return false"></a>
    </div>
  
	<table class="basket_table">
    <tr>
      <th> 작품 사진 </th>
      <th> 작가 </th>
      <th> 작품명 </th>
      <th> 가격 </th>
      <th> 선택 </th>
    </tr>
	    <c:forEach var = "Wish" items = "${Wishes}">
	      <c:if test="${Wish.wishlistUserNo eq User.userNo}">
	        <c:forEach var="Artwork" items="${Artworks}">
	          <c:if test="${Wish.wishlistArtworkNo eq Artwork.artworkNo}"> 
	            <c:forEach var="Userlist" items="${Users}">
	             <c:if test="${Artwork.artworkArtistNo eq Userlist.userNo}">
	               <c:url var="deletelink" value="/deleteWish">
	                 <c:param name="WishNo" value="${Wish.wishlistNo}"></c:param>
	               </c:url>   
	               <c:url var="AddCart" value="/savecart">
              		 <c:param name="artworkNO" value="${Artwork.artworkNo}"></c:param>
           		   </c:url>	  
	               <tr>
	                 <td><img src = "resources/images-artwork/${Userlist.userRealName}/${Artwork.artworkFilename}"
	                 height = "200" width = "200">
	                 <td>${Userlist.userRealName}</td>
	                 <td>${Artwork.artworkName}</td>
	                 <td>${Artwork.artworkPrice}￦</td>
	                 <td>
	                   <a href = "${AddCart}"><input type="button" value="장바구니추가" class="btn btn-primary" 
	                   					onclick="if(!(confirm('해당 상품을 장바구니에 추가하시겠습니까?'))) return false"></a>
	                   <a href = "${deletelink}"><input type ="button" value = "삭제" class="btn btn-secondary" 
	                 					onclick="if(!(confirm('해당 상품을 찜 목록에서 삭제하시겠습니까?'))) return false"></a>
	              </td>
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
<jsp:include page = "footer.jsp"></jsp:include>
</body>
</html>