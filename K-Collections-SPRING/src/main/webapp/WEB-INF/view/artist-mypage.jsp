<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Artist - My Page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>

<jsp:include page = "header2.jsp"></jsp:include>

<body>
<div class="container mt-3">
  <div class="jumbotron text-center" style= "background-image: url('../resources/image/artistmypagefront.png');  font-size:20px ">
    <h1>${Users.userRealName} 작가님의 마이페이지입니다.</h1>
    <br>
    <p>작가님의 판매 중인 작품 목록을 관리하고, 판매된 작품 목록을 볼 수 있는 페이지입니다. </p>
    <p>새로운 작품을 업로드해보세요!</p>
    </div>
    
		<input type="button" value="작품업로드" 
       		onclick="window.location.href='showForUpload'; return false;" class="btn btn-secondary" style="float: right;"/>
 		<br>
 		
 		<c:if test = "${thisPage eq 'ArtworkUpdate'}">
		   <form:form modelAttribute='Artwork' action='UpdateArtwork' method='get'>
				<form:hidden path="artworkNo"/>
				<form:hidden path="artworkRegdate"/> 
			 	<form:hidden path="artworkLevel"/>
	        	<form:hidden path="artworkArtistNo"/>
	        	<form:hidden path="artworkSold"/>
	        	
			<c:choose>
				<c:when test = "${saveOrUpdate eq 'ArtworkUpload'}">
		            <h4>작품 업로드</h4>
		            <p>판매하고자 하는 새로운 작품의 이미지를 업로드하고 상세 정보를 설정합니다.</p>
		            <p>새로운 작품은 관리자의 승인을 통한 작품 등급 확정 후에 업로드됩니다.</p> <br>
		  		</c:when>

		  		<c:when test = "${saveOrUpdate eq 'ArtworkUpdate'}">
			        <h4>작품 정보 수정</h4>
			        <p>판매 중인 작품의 정보를 수정합니다.</p>
			        <br>
			        <div><img src="../resources/images-artwork/${Users.userRealName}/${Artwork.artworkFilename}"
								alt="이미지 출력을 실패했습니다." style="width:50% " >
					<hr>
					</div>
		        </c:when>
			</c:choose>	
			
				<table>
		            <tr><td><label>Artwork Name : </label></td>
		            <td><form:input path="artworkName"/></td></tr>
		            
		            <tr><td><label>Artwork Artist No : </label></td>
		            <td> ${Artwork.artworkArtistNo}</td></tr>
		            
		            <tr><td><label>Artwork Category No : </label></td>
		            <td>
		                   <form:select path="artworkCategoryNo">
                                <form:option value="2"> 서양화</form:option>
                                <form:option value="3"> 한국화</form:option>
                                <form:option value="4"> 문인화,서예</form:option>
                                <form:option value="5"> 조각</form:option>
                                <form:option value="6"> 공예,디자인</form:option>
                            </form:select>
                    </td></tr>
		            
		            <tr><td><label>Artwork Price : </label></td>
		            <td><form:input path="artworkPrice"/></td></tr>
		            
		            <tr><td><label>Artwork Regdate : </label></td>
			            <td>${Artwork.artworkRegdate}</td>
		            </tr>
		            
		            <tr><td><label>Artwork Level : </label></td>
		            <td>${Artwork.artworkLevel}</td></tr>
		            
		            <tr><td><label>Artwork Filename : </label></td>
		            <td><form:input path="artworkFilename"/></td></tr>
			            
			    </table>
			    <br>
			    <div style="margin-top:20px; margin-bottom:50px;">
					<input type='submit' value='UpdateArtwork' class="btn btn-secondary" style="margin-right:5px;"/>  
					<input type='button' value='돌아가기' onclick="location.href='${pageContext.request.contextPath}/artistPage/artist';" class="btn btn-secondary"/>
				</div>
		 	</form:form>
         </c:if>  
 		
 		 <c:if test = "${thisPage eq 'ArtworkShow'}">		
			 <ul class="nav nav-tabs">
			   <li class="nav-item">
			     <a class="nav-link active" data-toggle="tab" href="#home">판매중</a>
			   </li>
			   <li class="nav-item">
			     <a class="nav-link" data-toggle="tab" href="#menu1">Vip/Vvip</a>
			   </li>
			   <li class="nav-item">
			     <a class="nav-link" data-toggle="tab" href="#menu2">판매완료</a>
			   </li>
			 </ul>
	  
 
    		<div class="tab-content">

		       <div id="home" class="container tab-pane active"><br>
		         <h3>판매 중인 작품</h3>
		         <p>판매 중인 작품에 대한 상세정보 수정과 삭제가 가능합니다.</p>
		       
		           <div class="card-columns">
		           <c:forEach var="tempArtwork" items="${artworksSaleNormal}">
		           
	                  <c:url var="UpdateUrl" value="/artistPage/showForUpdate">
	                     <c:param name="artworkNo" value="${tempArtwork.artworkNo}"></c:param>
	                  </c:url>
	                  <c:url var="DeleteUrl" value="/artistPage/Delete">
	                     <c:param name="artworkNo" value="${tempArtwork.artworkNo}"></c:param>
	                  </c:url>
		                  
		                 <div class="card" style="width:300px">
							<img class="card-img-top" src="../resources/images-artwork/${Users.userRealName}/${tempArtwork.artworkFilename}" 
							alt="이미지 출력을 실패했습니다." style="width:100%">              
							<div class="card-body">
		                     <h4 class="card-title">${tempArtwork.artworkName}</h4>
		                     <p class="card-text"> 가격: ${tempArtwork.artworkPrice}<br> 등록일: ${tempArtwork.artworkRegdate}</p>
		                     <a href="${UpdateUrl}" class="btn btn-primary">수정</a>
		                     <a href="${DeleteUrl}" class="btn btn-primary">삭제</a>
		                   </div>
		                 </div>
		            </c:forEach>
		            </div>    
		        </div>
        
		        <div id="menu1" class="container tab-pane fade"><br>
		          <h3>Vip/Vvip를 위한 작품</h3>
		          <p>Vip와 Vvip만을 위해 보여질 작품을 업로드하고, 관리하실 수 있습니다. 작품등급은 관리자에 의해 변동될 수 있습니다.</p>
		       
		          <div class="card-columns">
		           <c:forEach var="tempArtwork" items="${artworksSaleVip}">
		        
	                  <c:url var="UpdateUrl" value="/artistPage/showForUpdate">
	                     <c:param name="artworkNo" value="${tempArtwork.artworkNo}"></c:param>
	                  </c:url>
	                  <c:url var="DeleteUrl" value="/artistPage/Delete">
	                     <c:param name="artworkNo" value="${tempArtwork.artworkNo}"></c:param>
	                  </c:url>
		                  
		                 <div class="card" style="width:300px; margin-bottom:50px;">
		                   <img class="card-img-top" src="../resources/images-artwork/${Users.userRealName}/${tempArtwork.artworkFilename}" 
							alt="이미지 출력을 실패했습니다." style="width:100%">
		                   <div class="card-body">
		                     <h4 class="card-title">${tempArtwork.artworkName}</h4>
		                     <p class="card-text">가격: ${tempArtwork.artworkPrice}<br> 등록일: ${tempArtwork.artworkRegdate}</p>
		                     <a href="${UpdateUrl}" class="btn btn-primary">수정</a>
		                     <a href="${DeleteUrl}" class="btn btn-primary">삭제</a>
		                   </div>
		                 </div>
		            </c:forEach>
		            </div>
		        </div>
        
		        <div id="menu2" class="container tab-pane fade"><br>
		         <h3>판매 완료된 작품</h3>
		         <p>판매 완료된 작품에 대해 확인할 수 있습니다.</p>
		       
		          <div class="card-columns">
		           <c:forEach var="tempArtwork" items="${artworksSold}">
		                
		               	<c:url var="UpdateUrl" value="/artistPage/showForUpdate">
		                     <c:param name="artworkNo" value="${tempArtwork.artworkNo}"></c:param>
		                  </c:url>
		                  <c:url var="DeleteUrl" value="/artistPage/Delete">
		                     <c:param name="artworkNo" value="${tempArtwork.artworkNo}"></c:param>
		                  </c:url>
		                  
		                 <div class="card" style="width:300px">
							<img class="card-img-top" src="../resources/images-artwork/${Users.userRealName}/${tempArtwork.artworkFilename}" 
							alt="이미지 출력을 실패했습니다." style="width:100%">
		                   <div class="card-body">
		                     <h4 class="card-title">${tempArtwork.artworkName}</h4>
		                     <p class="card-text"> 가격: ${tempArtwork.artworkPrice}<br> 등록일: ${tempArtwork.artworkRegdate}</p>
		                     <a href="${UpdateUrl}" class="btn btn-primary">수정</a>
		                     <a href="${DeleteUrl}" class="btn btn-primary">삭제</a>
		                   </div>
		                 </div>
		
		            </c:forEach>
		            </div>
		        </div>
  			</div>
		</c:if>
</div>

</body>

<jsp:include page = "footer2.jsp"></jsp:include>
</html>