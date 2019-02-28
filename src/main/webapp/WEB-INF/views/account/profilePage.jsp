<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../tags/header.jsp" flush="true"></jsp:include>

<script type="text/javascript">
	document.getElementsByTagName('title')[0].innerHTML = 'Редактор';
</script>
		<c:choose>
			<c:when test="${editable == true}">
				<!-- markets -->
				<div class="markets" id="markets">
					<div class="container">
						<div class="agileits-title">
							<h3>Редакция На Профил</h3>
						</div>
						<div class="markets-grids">
							<form:form method="POST" class="createGameForm"
								action='${contextPath}${saveRegistration}'
								modelAttribute="student" enctype="multipart/form-data">
								<div class="w3ls-markets-grid">
									<div class="agileits-icon-grid">										
										<div class="icon-rightCr">
											<p>
												<span>Име: </span>
												<input class="editTextField" type="text" value="${account.getFirstName()}"
													name="firstName">
												<br />
												<span>Фамилия: </span>
												<input class="editTextField" type="text" value="${account.getLastName()}" name="lastName">
												<br />
												<span>Email: </span>
												<input class="editTextField" type="text" value="${account.getEmail()}" name="email">
												<br />
												<span>Представяне:*</span>
												<textarea class="editTextField editDescription" name="description">${account.getDescription()}</textarea>
											</p>
										</div>
										<div class="icon-left">
											<c:choose>
												<c:when test="${account.getAvatar() == 1}">
													<img
														src="/resources/avatars/${account.getAccount_id()}.jpg"
														alt="Smiley face" style="width:150px">
												</c:when>
												<c:otherwise>
													<img src="/resources/avatars/simpleAvatar.jpg"
														alt="Smiley face" style="width:150px">
												</c:otherwise>
											</c:choose>
											<input type="hidden" value="${account.getAccount_id()}" name="accountId"> 
											<input type="hidden" value="${account.getAvatar()}" name="haveAvatar"> 
											<input type="file" id="avatar" accept="image/png, image/jpeg"
												name="avatar" placeholder="Профилна снимка">
										</div>
										<div>
											<input type="submit" class="edit editButton" value="Промяна">
										</div>
										<div class="clearfix"></div>
									</div>
								</div>
							</form:form>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				<!-- //markets -->
			</c:when>
			<c:otherwise>
				<!-- about -->
				<div class="about" id="about">
					<div class="container">
						<div class="welcome">
							<div class="agileits-title">
								<h2>${account.getFirstName()}${account.getLastName()}</h2>
								<p>
									Активен период: ${account.getActivePeriod()} <br /> Email: <a
										href="${contextPath}/sender?mailAdress=${account.getEmail()}">${account.getEmail()}</a>
								</p>
							</div>
						</div>
						<div class="about-w3lsrow">
							<c:choose>
								<c:when test="${account.getAvatar() == 1}">
									<div class="col-md-7 col-sm-7 w3about-img"
										style="background: url(/resources/avatars/${account.getAccount_id()}.jpg)no-repeat 0px 0px;">
								</c:when>
								<c:otherwise>
									<div class="col-md-7 col-sm-7 w3about-img"
										style="background: url(/resources/avatars/simpleAvatar.jpg) no-repeat 0px 0px;">
								</c:otherwise>
							</c:choose>

							<div class="w3about-text">
								<h5 class="w3l-subtitle">- За Мен</h5>
								<p>${account.getDescription()}</p>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
	<!-- //about -->
	<!-- markets -->
	<div class="markets" id="markets">
		<div class="container">
			<div class="agileits-title">
				<h3>Игрите на Потребителя</h3>
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
											<img src="/resources/game_images/simple.jpg"
												alt="Smiley face">
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
								<div class="clearfix"></div>
							</div>
						</div>
					</c:forEach>
				</c:if>


				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!-- //markets -->
	</c:otherwise>
	</c:choose>
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