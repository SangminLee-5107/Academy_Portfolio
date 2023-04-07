<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Admin Main Page</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<style>
th, td {
border : 1px solid gray;
padding : 5px;
}
.box1 {width:100%;}
.box2 {width:1300px; margin:auto;}
</style>
</head>
<jsp:include page = "header2.jsp"></jsp:include>
<body>
<div class="box1">
  <div class="box2">
    <div class="jumbotron text-center" style= "background-image: url('../resources/image/artistmypagefront.png');">
    	<h1>관리자 페이지</h1>
    </div>
	<hr>
	<button class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/adminPage/UserList'; return false;">
		유저 관리
	</button>
	<button class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/adminPage/ArtworkList'; return false;">
		아트워크 관리
	</button>
	<button class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/adminPage/SalesList'; return false;">
		판매 내역 확인
	</button>
	<button class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/'; return false;">
		홈
	</button>
	
	<hr>
	<c:choose>
		<c:when test = "${thisPage eq 'UserList'}">
			<h1>유저리스트</h1>
			<br>
			
		    <form:form action="SearchUser" method="get">
			   	Search User :
			   	<input type="text" id="searchText" name="searchText"/> 
			   	<input type="submit" value='Search' />
			</form:form>
		   
			<table style="margin-top:30px; margin-bottom:50px;">
	            <tr>
	               <th>No</th>
	               <th>ID</th>
	               <th>Password</th>
	               <th>Real Name</th>
	               <th>Purchase amount</th>
	               <th>Category</th>
	               <th>Authority</th>
	               <th>Update</th>
	               <th>Delete</th>
	            </tr>
		       <c:forEach var="User" items="${Users}">
		            <c:url var="UpdateUrl" value="/adminPage/UpdateUser">
		            	<c:param name="userNo" value="${User.userNo}"></c:param>
		            </c:url>
		            <c:url var="DeleteUrl" value="/adminPage/DeleteUser">
		            	<c:param name="userNo" value="${User.userNo}"></c:param>
		            </c:url>
		            <tr>
		               <td>${User.userNo}</td>
		               <td>${User.userName}</td>
		               <td>${User.password}</td>
		               <td>${User.userRealName}</td>
		               <td>${User.userPurchaseAmount}</td>
			           <td>${User.userCategoryNo}</td>
			           <td>
		               <c:forEach var="Authority" items="${Authorities}">
		               		<c:if test="${Authority.userName eq User.userName }">
		               			${Authority.authority}
		               		</c:if>
		               	</c:forEach>
		               </td>
		               <td>
		               		<button onclick="location.href='${UpdateUrl}';" class="btn btn-secondary">
		               		Update
		               		</button>
		               </td>
		               
		               <td>
		               		<button onclick="location.href='${DeleteUrl}';" class="btn btn-secondary">
		               		Delete
		               		</button>
		               </td>
		               
		        </c:forEach>
            </table>
		</c:when>
		
		<c:when test = "${thisPage eq 'UserUpdate'}">
			<h1>유저리스트 수정</h1>
			<br>
			
		   <form:form modelAttribute='User' action='UpdateUser/Update' method='get'>
				<form:hidden path="userNo"/>
				<form:hidden path="enAbled"/>
				<input type="hidden" name="beforeName" value="${User.userName}"/>
				<table style="margin-top:30px; margin-bottom:20px;">
			            <tr><td><label>User Name : </label></td><td><form:input path="userName"/></td></tr>
			            <tr><td><label>Password : </label></td><td><form:input path="password"/></td></tr>
			            <tr><td><label>User Real Name : </label></td><td><form:input path="userRealName"/></td></tr>
			            <tr><td><label>User Purchase Amount : </label></td><td><form:input path="userPurchaseAmount"/></td></tr>
			            <tr><td><label>User Category No : </label></td><td><form:input path="userCategoryNo"/></td></tr>
			    </table>
				<input type='submit' value='UpdateUser' class="btn btn-secondary" style="margin-right:5px; margin-bottom:30px;"/>
				<input type='button' value='돌아가기' class="btn btn-secondary" 
					onclick="location.href='${pageContext.request.contextPath}/adminPage/UserList';" style="margin-bottom:30px;"/>
		 	</form:form>
		</c:when>
		
		<c:when test = "${thisPage eq 'ArtworkList'}">
			<h1>아트워크리스트</h1>
			<br>
			
		    <form:form action="SearchArtwork" method="get">
			   	Search User :
			   	<input type="text" id="searchText" name="searchText"/> 
			   	<input type="submit" value='Search' />
			</form:form>
			
			<table style="margin-top:30px; margin-bottom:50px;">
	            <tr>
	               <th>No.</th>
	               <th>Artwork Name</th>
	               <th>Artist No</th>
	               <th>Artist Name</th>
	               <th>Category No</th>
	               <th>Price</th>
	               <th>Regdate</th>
	               <th>Sold</th>
	               <th>Level</th>
	               <th>Filename</th>
	               <th>Update</th>
	               <th>Delete</th>
	               <th>permission</th>
	            </tr>
		       <c:forEach var="Artwork" items="${Artworks}">
		            <c:url var="UpdateUrl" value="/adminPage/ArtworkUpdate">
		            	<c:param name="ArtworkNo" value="${Artwork.artworkNo}"></c:param>
		            </c:url>
		            <c:url var="DeleteUrl" value="/adminPage/ArtworkDelete">
		            	<c:param name="ArtworkNo" value="${Artwork.artworkNo}"></c:param>
		            </c:url>
		            <tr>
		               <td>${Artwork.artworkNo}</td>
		               <td>${Artwork.artworkName}</td>
		               <td>${Artwork.artworkArtistNo}</td>
		               <c:forEach var="User" items="${Users}">
		               		<c:if test="${Artwork.artworkArtistNo eq User.userNo }">
		               			<td>${User.userRealName}</td>
		               		</c:if>
		               </c:forEach>
		               <td>${Artwork.artworkCategoryNo}</td>
		               <td>${Artwork.artworkPrice}</td>
		               <td>${Artwork.artworkRegdate}</td>
		               <td>${Artwork.artworkSold}</td>
		               <td>${Artwork.artworkLevel}</td>
		               <td>${Artwork.artworkFilename}</td>
		               <td>
		               		<button onclick="location.href='${UpdateUrl}';" class="btn btn-secondary">
		               		Update
		               		</button>
		               </td>
		               
		               <td>
		               		<button onclick="location.href='${DeleteUrl}';" class="btn btn-secondary">
		               		Delete
		               		</button>
		               </td>
		               
		               <c:if test = "${Artwork.artworkLevel eq 'none'}">
			               <td>
			               		<button onclick="location.href='${UpdateUrl}';" class="btn btn-secondary">
			               		permission
			               		</button>
			               </td>
		               </c:if>
		            </tr>
		        </c:forEach>
            </table>
		</c:when>
		
		<c:when test = "${thisPage eq 'ArtworkUpdate'}">
			<h1>아트워크리스트 수정</h1>
			<br>
			
		   <form:form modelAttribute='Artwork' action='ArtworkUpdate/UpdateArtwork' method='get'>
				<form:hidden path="artworkNo"/>
				<form:hidden path="artworkRegdate"/>
				
			   	<div>
               	<c:forEach var="User" items="${Users}">
               		<c:if test="${Artwork.artworkArtistNo eq User.userNo }">
						<img alt="이미지 출력을 실패했습니다." 
						src="../resources/images-artwork/${User.userRealName}/${Artwork.artworkFilename}"
						style="width:500px; height:500px;">
					</c:if>
			   	</c:forEach>
			   	</div>
				<table style="margin-top:30px; margin-bottom:20px;">
		            <tr><td><label>Artwork Name : </label></td>
		            <td><form:input path="artworkName"/></td></tr>
		            
		            <tr><td><label>Artwork Artist No : </label></td>
		            <td><form:input path="artworkArtistNo"/></td></tr>
		            
		            <tr><td><label>Artwork Category No : </label></td>
		            <td><form:input path="artworkCategoryNo"/></td></tr>
		            
		            <tr><td><label>Artwork Price : </label></td>
		            <td><form:input path="artworkPrice"/></td></tr>
		            
		            <tr><td><label>Artwork Regdate : </label></td>
			            <td>${Artwork.artworkRegdate }</td>
		            </tr>
		            
		            <tr><td><label>Artwork Sold : </label></td>
		            <td><form:input path="artworkSold"/></td></tr>
		            
		            <tr><td><label>Artwork Level : </label></td>
		            <td><form:input path="artworkLevel"/></td></tr>
		            
		            <tr><td><label>Artwork Filename : </label></td>
		            <td><form:input path="artworkFilename"/></td></tr>
			            
			    </table>
				<input type='submit' value='UpdateArtwork' class="btn btn-secondary" style="margin-right:5px; margin-bottom:30px;"/>
				<input type='button' value='돌아가기' class="btn btn-secondary" 
					onclick="location.href='${pageContext.request.contextPath}/adminPage/ArtworkList';" style="margin-bottom:30px;"/>
		 	</form:form>
		</c:when>
		
		
		<c:when test = "${thisPage eq 'SalesList'}">
			<h1>판매내역리스트</h1>
			<br>
			
		    <form:form action="SearchSales" method="get">
			   	Search User :
			   	<input type="text" id="searchText" name="searchText"/> 
			   	<input type="submit" value='Search' />
			</form:form>
		   
			<table style="margin-top:30px; margin-bottom:20px;">
	            <tr>
	               <th>No.</th>
	               <th>User No</th>
	               <th>User Name</th>
	               <th>Artwork No</th>
	               <th>Artwork Name</th>
	               <th>Price</th>
	               <th>Purchase Time</th>
	            </tr>
		       <c:forEach var="Sale" items="${Sales}">
		            <tr>
		               <td>${Sale.salesNo}</td>
		               <td>${Sale.salesUserNo}</td>
		               
		               <c:forEach var="User" items="${Users}">
		               		<c:if test="${Sale.salesUserNo eq User.userNo }">
		               			<td>${User.userName}</td>
		               		</c:if>
		               </c:forEach>
		               
		               <td>${Sale.salesArtworkNo}</td>
		               
		               <c:forEach var="Artwork" items="${Artworks}">
		               		<c:if test="${Sale.salesArtworkNo eq Artwork.artworkNo }">
		               			<td>${Artwork.artworkName}</td>
		               		</c:if>
		               </c:forEach>
		               
		               <td>${Sale.salesArtworkPrice}</td>
		               <td>${Sale.salesPurchaseTime}</td>
		            </tr>
		        </c:forEach>
            </table>
            <input type='button' value='돌아가기' class="btn btn-secondary" 
					onclick="location.href='${pageContext.request.contextPath}/adminPage/SalesList';" style="margin-bottom:30px;"/>
		</c:when>
	</c:choose>
  </div>
</div>
</body>
<jsp:include page = "footer2.jsp"></jsp:include>
</html>