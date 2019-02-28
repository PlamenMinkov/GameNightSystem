<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="tags/header.jsp" flush="true"></jsp:include>
<%-- <p>${user.getUsername()}</p> --%>
<%-- <section class="stripe sansSerif"> --%>
<!-- 	<div class="content padding80"> -->
<!-- 		<form name="login" action="/login" method="POST"> -->
<!-- 			<label>Username</label>  -->
<!-- 			<input id="username" name="username" type="text" />  -->
<!-- 			<label>Password</label>  -->
<!-- 			<input id="password" name="password" type="password" />  -->
<!-- 			<input name="submit" type="submit" value="Login" /> <input type="hidden" -->
<%-- 				name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
<!-- 		</form> -->

<!-- 		<div class="registerInfo"></div> -->
<!-- 	</div> -->
<%-- </section> --%>
<div class="markets" id="markets">
	<div class="container">
		<div class="loginForm">
			<form name="login" action="/login" method="POST">
				<label>Username</label> 
				<input id="username" name="username" type="text" /> <br>
				<label>Password</label> 
				<input id="password" name="password" type="password" /> <br>
				<input name="submit" class="button" type="submit" value="Login" /> 
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</div>
</div>
<section class="stripe sansSerif">
    <div class="content padding20 justifyBetween">
      <div class="oneThird">
        <div class="infoBitPic">
          <img src="<c:url value="/resources/images/playstation.jpg" />">
        </div>
        
        <div class="infoBitText">
          Lorem Ipsum е елементарен примерен текст, използван в печатарската и типографската индустрия. Lorem Ipsum е индустриален стандарт от около 1500 година, 
        </div>
      </div>
      
      <div class="oneThird">
        <div class="infoBitPic">
          <img src="<c:url value="/resources/images/microsoft.jpg" />">
        </div>
        
        <div class="infoBitText">
          Kогато неизвестен печатар взема няколко печатарски букви и ги разбърква, за да напечата с тях книга с примерни шрифтове. Този начин не само е оцелял повече от 5 века, но е навлязъл и в публикуването на електронни издания като е запазен почти без промяна. 
        </div>
      </div>
      
      <div class="oneThird">
        <div class="infoBitPic">
          <img src="<c:url value="/resources/images/nintendo.jpg" />">
        </div>
        
        <div class="infoBitText">
          Популяризиран е през 60те години на 20ти век със издаването на Letraset листи, съдържащи Lorem Ipsum пасажи, популярен е и в наши дни във софтуер за печатни издания като Aldus PageMaker, който включва различни версии на Lorem Ipsum.
        </div>
      </div>  
    </div>
  </section>
</body>
<jsp:include page="tags/footer.jsp" flush="true"></jsp:include>