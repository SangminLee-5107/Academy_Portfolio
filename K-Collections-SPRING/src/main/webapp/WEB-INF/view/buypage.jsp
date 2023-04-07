<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
  <head>
  <title>Buy Page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

  <style>
  .detail_box {width:100%; height:750px; margin-top:50px;}
  .detail_box2 {width:1000px; height:100%; margin:auto;}
  .detail_table {width:100%; height:40%; border:1px; margin-top:12px; border-top:2px solid #bbb;}
  .detail_table td {text-align:right; color:#545454;}
  .detail_table_img {width:50%; padding:0px;}
  .detail_table_img>img {float:left;}  
  
  .order_box {width:100%; height:50%; border-top:1px solid #000; margin-top:50px;}
  .order_left {width:50%; height:60%; float:left; padding:15px; border-bottom:1px solid #000;}
  .order_left>h1 {font-size:30px;}
  .order_right {width:50%; height:60%; float:left; border-bottom:1px solid #000;}
  .order_right_table {width:100%; height:100%;}
  .order_right_table th {text-align:left; color:#545454; }
  .order_right_table td {text-align:right; color:#545454; }
  .order_right_table tr:nth-child(3) {border-bottom:1px solid #ddd;}
  .buy_button_box {width:100%; height:40%; float:left; margin:auto;}
  #buy_button {margin:auto; display:block; margin-top:30px; width:170px; height:50px; font-size:22px;}
  </style>
</head>

<jsp:include page = "header.jsp"></jsp:include>
	
<body>
<div class="detail_box">
  <div class="detail_box2">
    <c:forEach var="User" items="${Users}">
   	  <c:if test="${Artwork.artworkArtistNo eq User.userNo }">
   	    <table class="detail_table">
   		  <td class="detail_table_img" rowspan="4">
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
          <th>입고일</th>
          <td>${Artwork.artworkRegdate}</td>
          </tr>
        </table> 
                
        <div class="order_box">
		  <div class="order_left">
		  	<h1>총 주문금액</h1>
		  </div>
		  
		  <div class="order_right">
		    <table class="order_right_table">
	  	  	  <tr>
			    <th> 주문작품 수 </th>
			    <td> 1개 </td>
		  	  </tr>
		  	  <tr>
			    <th> 주문금액 </th>
			    <td> ${Artwork.artworkPrice}￦ </td>
		  	  </tr>
		  	  <tr>
			    <th> 배송비 </th>
			    <td> 0원 </td>
		  	  </tr>
		  	  <tr>
			    <th> 최종결제금액 </th>
			    <td> ${Artwork.artworkPrice}￦ </td>
		  	  </tr>
		    </table>
		  </div> 
		  
		  <div class="buy_button_box">  
			<c:url var="BuyArtwork" value="/BuyArtwork/Buy">
	    	  <c:param name="artworkNo" value="${Artwork.artworkNo}"></c:param>
	    	  <c:param name="artworkPrice" value="${Artwork.artworkPrice}"></c:param>
	    	</c:url>
	    	<a href="${BuyArtwork}" id="buy_button" class="btn btn-primary"
	    				onclick="if(!(confirm('해당 작품을 구매하시겠습니까?'))) return false">구매하기</a>
    	  </div>
		</div>  
      </c:if>
   	</c:forEach>	  
  </div>
</div>

</body>

<jsp:include page = "footer.jsp"></jsp:include>

</html>