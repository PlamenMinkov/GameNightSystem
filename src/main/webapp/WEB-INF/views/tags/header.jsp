<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />   
<c:set var="createGameUrl" value="/createGame" />  
<c:set var="gameRegister" value="/gameRegister" />  
<c:set var="customersRegister" value="/customersRegister" />
<c:set var="registration" value="/registration" />
<c:set var="logout" value="/logout" />
<c:set var="login" value="/login" />
<c:set var="editOwnGame" value="/editOwnGame" />
<c:set var="title" value="nanana" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/style/font-awesome.css" />" >
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/style/colorPresets.css"/>">
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/style/styles.css"/>">

<script type="text/javascript" src="<c:url value="/resources/script/libs/jquery-1.12.3.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/script/libs/requester.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/script/ajaxRequest/workWithDB.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/script/script.js"/>"></script>

<!-- bootstrap-css -->
<link href="/resources/style/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!--// bootstrap-css -->
<!-- css -->
<link rel="stylesheet" href="/resources/style/styleTmpl.css" type="text/css" media="all" />
<!--// css -->
<!-- //font-awesome icons -->
<!-- portfolio -->	
<link rel="stylesheet" href="/resources/style/chocolat.css" type="text/css" media="all">
<!-- //portfolio -->	
<!-- font -->
<link href="//fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i" rel="stylesheet">
<link href='//fonts.googleapis.com/css?family=Roboto+Condensed:400,700italic,700,400italic,300italic,300' rel='stylesheet' type='text/css'>
<!-- //font -->
<script src="/resources/script/bootstrap.js"></script>
<title><c:out value="${param.title}"/></title>

	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<header>
	  <section class="stripe sansSerif whiteBG">
	    <div class="content padding20 relative">
	      <div class="topStripeIconGroup">
	        <a href="https://www.facebook.com/groups/672511086212718/" target="_blank">
	          <span class="topStripeIcon"><span class="fa fa-facebook"></span></span>
	        </a>
	
	        <span class="topStripeIcon"><span class="fa fa-search"></span></span>
	
	      </div>
	      <div class="logo"><span class="robotoSlab">Game</span> <span class="prCol">Night</span></div>
	    </div>
	  </section>
	
	  <section class="stripe sansSerif blackBG">
	    <div class="content">
	      <ul class="menu">
	        <li><a class="prColBGHover" href="${contextPath}/">Начало</a></li>
	        <li><a class="prColBGHover" href="${contextPath}${gameRegister}">Игри</a></li>
	        <c:if test="${user != null}">
	        	<li><a class="prColBGHover" id="createGame" href="${contextPath}${createGameUrl}">Създай игра</a></li>
	        </c:if>
	        <c:if test="${user != null}">
	        	<li class="right userMenu">
	        		<a class="prColBGHover showUserMenu" id="prifile" href="${contextPath}/profile">${user.getUsername()}</a>
	        		<div class="hidenUserMenu">
	        			<a class="prColBGHover" id="prifile" href="${contextPath}/profile">Профил</a>
    					<a class="prColBGHover" id="prifile" href="${contextPath}/editProfile">Редакция на профила</a>
    					<a class="prColBGHover" id="ownGames" href="${contextPath}${editOwnGame}">Създадени от теб игри</a>
   						<a class="prColBGHover" id="prifile" href="${contextPath}/logout">Изход</a>
	        		</div>
        		</li>
	        	<%-- <li class="right"><a class="prColBGHover" id="logout" href="${contextPath}${logout}">Изход</a></li>	        	
		        
		        <li class="right"><a class="prColBGHover" id="prifile" href="${contextPath}/profile">Профил</a></li> --%>
	        </c:if>
	        <c:if test="${user == null}">
	        	<li class="right"><a class="prColBGHover" id="login" href="${contextPath}${login}">Вход</a></li>
			    <li class="right"><a class="prColBGHover" id="registration" href="${contextPath}${registration}">Регистрация</a></li>	
	        </c:if>
		        	        
	        <li><a class="prColBGHover" href="${contextPath}${customersRegister}">Потребители</a></li>
	      </ul>
	    </div>
	  </section>
	</header>
	<script type="text/javascript">
		$(function() {
			$(".hidenUserMenu").hide();
		});
	
		$(".showUserMenu").mouseenter(function(){
			$(".hidenUserMenu").show();
		});
		$(".userMenu").mouseleave(function() {
			$(".hidenUserMenu").hide();
		});
	</script>