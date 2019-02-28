<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ page session="false" %> --%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="tags/header.jsp" >
  <jsp:param name="title" value="Home"/>
  <jsp:param name="username" value="${user.getUsername()}" />
</jsp:include>

	<section id="hero" class="stripe sansSerif"></section>
  
  <section class="stripe sansSerif">
    <div class="content padding20 justifyBetween">
      <div class="oneThird">
        <div class="infoBitPic">
          <img src="<c:url value="/resources/images/playstation.jpg" />">
        </div>
        
        <div class="infoBitText">
          В миналото свободното време било не, за да се отпуснеш, а за да се отдадеш на увлечението си, да преследваш собствените си интереси и страсти.
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
