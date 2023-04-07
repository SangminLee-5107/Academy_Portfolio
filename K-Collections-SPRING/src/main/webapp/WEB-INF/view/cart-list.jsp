<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head> <title>Cart list </title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
  .basket_box {width:100%; height:1300px;;}
  .basket_box2 {width:1100px; height:100%; margin:auto;}
  .deleteAll_button {float:right; text-align:center; margin-right:5px; margin-bottom:12px;}
  #deleteAll_button {width:100px; height:50px; font-size:18px;}
  .basket_table {width:100%; height:50%; border:0; outline:none; text-align:center; margin-top:12px;}
  .basket_table th {border-top:3px solid #000; border-bottom:1px solid #000;}
  .basket_table td {border-bottom:1px solid #ddd;}
  .order_box {width:100%; height:50%; border-top:1px solid #000;}
  .order_left {width:50%; height:60%; float:left; padding:15px; border-bottom:1px solid #000;}
  .order_left>h1 {font-size:30px;}
  .order_right {width:50%; height:60%; float:left; border-bottom:1px solid #000;}
  .order_right_table {width:100%; height:100%;}
  .order_right_table th {text-align:left; color:#545454; }
  .order_right_table td {text-align:right; color:#545454; }
  .order_right_table tr:nth-child(3) {border-bottom:1px solid #ddd;}
  .buy_button_box {width:100%; height:40%; float:left; margin:auto;}
  #buy_button {margin:auto; display:block; margin-top:50px; width:170px; height:70px; font-size:22px;}
  </style>
 </head>
 
 <jsp:include page = "header.jsp"></jsp:include>
 
<body>
<div class="container mt-3">
  <div class="jumbotron text-center" style= "background-image: url('resources/image/artistmypagefront.png');">
  <%-- <security:authentication property="principal.username" />님의 장바구니 목록입니다.  --%>
  <h1>${User.userRealName}님의 장바구니 목록입니다.</h1>
  </div>  
</div>

<div class="basket_box">
  <div class="basket_box2">
    <c:url var = "DeleteAll" value = "/deleteAllcart">
      <c:param name = "userId" value = "${Userlist.userNo}"></c:param>
    </c:url>
    
    <div class="deleteAll_button">
    <a href = "${DeleteAll}"><input type ="button" value = "전체삭제" class="btn btn-secondary" id="deleteAll_button"
	              				  onclick="if(!(confirm('장바구니의 작품을 모두 삭제하시겠습니까?'))) return false"></a>
    </div>
  
	<table class="basket_table">
	  <tr>
	    <th> 작품 사진 </th>
	    <th> 작가 </th>
	    <th> 작품명 </th>
	    <th> 금액 </th>
	    <th> 선택 </th>
	  </tr>
	  <c:forEach var = "Cart" items = "${Carts}">
	    <c:if test="${Cart.cartUserNo eq User.userNo}">
	      <c:url var="deletelink" value="/deleteCart">
	        <c:param name="CartNO" value="${Cart.cartNo}"></c:param>
	      </c:url>   
	         <c:forEach var="Artwork" items="${Artworks}">
	          <c:if test="${Cart.cartArtworkNo eq Artwork.artworkNo}">
	           <c:forEach var="Userlist" items="${Users}">
	            <c:if test="${Artwork.artworkArtistNo eq Userlist.userNo}">
	             <tr>
	              <td> <img src = "resources/images-artwork/${Userlist.userRealName}/${Artwork.artworkFilename}"
	              height = "200" width = "200">
	              <td> ${Userlist.userRealName} </td>
	              <td> ${Artwork.artworkName} </td>
	              <td> ${Artwork.artworkPrice} </td>
			      <c:url var="Buylink" value="/BuyArtwork">
			        <c:param name="artworkNo" value="${Artwork.artworkNo}"></c:param>
			      </c:url>   
	              <td>
	              	<a href = "${Buylink}"><input type ="button" value = "바로 구매" class="btn btn-primary"></a> 
	              	<a href = "${deletelink}"><input type ="button" value = "삭제" class="btn btn-secondary" 
	              				  onclick="if(!(confirm('해당 상품을 장바구니에서 삭제하시겠습니까?'))) return false"></a>
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
</body>
</html>
<jsp:include page = "footer.jsp"></jsp:include>