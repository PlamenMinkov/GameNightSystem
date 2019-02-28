<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../tags/header.jsp" flush="true"></jsp:include>

<script type="text/javascript">
	document.getElementsByTagName('title')[0].innerHTML = 'Редактор';
</script>
<!-- markets -->
	<div class="markets" id="markets">
		<div class="container">
			<div class="agileits-title"> 
				<h3>Игрите на GAME NIGHT</h3>
			</div>
			<div class="markets-grids">
				<c:if test="${not empty games}">
					<c:forEach var="game" items="${games}">
						<div class="col-md-4 w3ls-markets-grid">
							<div class="agileits-icon-grid">
								<div class="icon-left">
									<c:choose>
										<c:when test="${game.getImage() == 1}">
											<img src="/resources/game_images/${game.getGameId()}.jpg"
												alt="Smiley face">
										</c:when>
										<c:otherwise>
											<img src="/resources/game_images/simple.jpg" alt="Smiley face">
										</c:otherwise>
									</c:choose>
								</div>
								<div class="icon-right">
									<h5>${game.getTitle()}</h5>
									<p class="gameForm">${game.getDescription()}</p>
									<span class="votes"> Vote: <span
										id="${game.getGameId()}_vote">${gameService.getVoteOfGame(game.getGameId())}</span>
									</span>
								</div>
								<div class="voteAction" id="${game.getGameId()}">
									<button class="up">Up</button>
									<button class="down">Down</button>
								</div>
								<div class="clearfix"> </div>
							</div>
						</div>
					</c:forEach>
				</c:if>
				
				
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
	<!-- //markets -->
</body>
<script>
	$(document).ready(function() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});

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
	})
</script>
<jsp:include page="../tags/footer.jsp" flush="true"></jsp:include>