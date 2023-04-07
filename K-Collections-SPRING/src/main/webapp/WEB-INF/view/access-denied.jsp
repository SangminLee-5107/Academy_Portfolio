<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Access Denied</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  
  <style>
		@import url("https://fonts.googleapis.com/css?family=Lato");
* {
  position: relative;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Lato", sans-serif;
}

body {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: linear-gradient(to bottom right, #EEE, #AAA);
}

h1 {
  margin: 40px 0 20px;
}

.lock {
  border-radius: 5px;
  width: 55px;
  height: 45px;
  background-color: #333;
  animation: dip 1s;
  animation-delay: 1.5s;
}
.lock::before, .lock::after {
  content: "";
  position: absolute;
  border-left: 5px solid #333;
  height: 20px;
  width: 15px;
  left: calc(50% - 12.5px);
}
.lock::before {
  top: -30px;
  border: 5px solid #333;
  border-bottom-color: transparent;
  border-radius: 15px 15px 0 0;
  height: 30px;
  animation: lock 2s, spin 2s;
}
.lock::after {
  top: -10px;
  border-right: 5px solid transparent;
  animation: spin 2s;
}

@keyframes lock {
  0% {
    top: -45px;
  }
  65% {
    top: -45px;
  }
  100% {
    top: -30px;
  }
}
@keyframes spin {
  0% {
    transform: scaleX(-1);
    left: calc(50% - 30px);
  }
  65% {
    transform: scaleX(1);
    left: calc(50% - 12.5px);
  }
}
@keyframes dip {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(10px);
  }
  100% {
    transform: translateY(0px);
  }
}

	</style>
</head>

<body>
	<div class="lock"></div>
	<div class="message">
	  <h1>고객님의 등급은 "${Role}"입니다.<br>해당 페이지에 접근이 불가합니다.</h1>
	  <button type="button" class="btn btn-lg btn-outline-dark" 
	  style ="width:100px; margin:auto; display:block;"
	  onclick="location.href='${pageContext.request.contextPath}';">
		홈으로 </button>
	</div>

</body>

</html>