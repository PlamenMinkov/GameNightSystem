<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../tags/header.jsp" flush="true"></jsp:include>

<script type="text/javascript">
		document.getElementsByTagName('title')[0].innerHTML = 'Редактор';
	</script>
	<section class="stripe sansSerif">
	<div class="content padding40">
      <h1>Потребителски данни:</h1>
      <ul class="userList">
        <li class="relative">
       		<span class="cell cellTitle username">Потребителско име:</span>
          	<span class="cell cellTitle name">Име:</span>
          	<span class="cell cellTitle familyName">Фамилия:</span>
          	<span class="cell cellTitle activePeriod">Ативен период:</span>
        </li>
		<c:if test="${not empty customers}">
	        <c:forEach var="custumer" items="${customers}">
		         <li class="relative">
		         	<span class="cell username">
		         		<a href="${contextPath}${accountProfile}?username=${custumer.getUsername()}">
		         			${custumer.getUsername()}
	         			</a>
         			</span>
			        <span class="cell name">${custumer.getFirstName()}</span>
			        <span class="cell familyName">${custumer.getLastName()}</span>
			        <span class="cell activePeriod" id="${custumer.getAccount_id()}_period">${custumer.getActivePeriod()}</span>		        
		        </li>
         	</c:forEach>
		</c:if>
      </ul>
    </div>
	    <div class="content padding80">
	      <h1 class="title align_center">Игри на потребителя</h1>
	      <div class="justifyBetween">
	      	<c:if test="${not empty games}">
				<c:forEach var="game" items="${games}">
		        	<div class="oneThird gameTile gameForm">
		          		<h2>${game.getTitle()}</h2>
		          		<span class="votes">
		          			Vote: <span id="${game.getGameId()}_vote">${gameService.getVoteOfGame(game.getGameId())}</span>
	          			</span>
			          	<p>
			          		${game.getDescription()}
			          	</p>
		        	</div>	
		        </c:forEach>
			</c:if>        
	      </div>
	    </div>
  	</section>
	</body>
	<script>
		$(".up").on('click', function() {
			var gameId = $(this).parent().attr("id");
			var value = 1; 
			
			operationsWithDB.addOrUpdateGameVote(gameId, value);
		});
		
		$(".down").on("click", function() {
			var gameId = $(this).parent().attr("id");
			var value = -1;
			
			operationsWithDB.addOrUpdateGameVote(gameId, value);			
		}); 
	</script>
<jsp:include page="../tags/footer.jsp" flush="true"></jsp:include>