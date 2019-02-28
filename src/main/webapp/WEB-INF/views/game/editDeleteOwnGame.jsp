<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../tags/header.jsp" flush="true"></jsp:include>
	
	<script type="text/javascript">
		document.getElementsByTagName('title')[0].innerHTML = 'Редактор';
	</script>
  	<!-- markets -->
	<div class="markets" id="markets">
		<div class="container">
			<div class="agileits-title"> 
				<h3>Редактирай Създадените От Теб Игри</h3>
			</div>
			<div class="markets-grids">
				<c:if test="${not empty games}">
					<c:forEach var="game" items="${games}">
						<form:form method="POST" class="" action='${contextPath}${editGame}'
						modelAttribute="student" enctype="multipart/form-data">
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
										<input type="file" id="image" accept="image/png, image/jpeg" 
											name="image" style="margin-top: 0.6em;" placeholder="Снимка">
										<input type="hidden" value="${game.getGameId()}" name="gameId">
									</div>
									<div class="icon-right">
										<h5><input class="editTextField" type="text" id="${game.getGameId()}_title" value="${game.getTitle()}" name="title"></h5>
										<p>
							            	<textarea id="${game.getGameId()}_description" class="editTextField editDescription" 
							            		name="description">${game.getDescription()}
							            	</textarea> 
							          	</p>
									</div>
									<div id="${game.getGameId()}">
										<button type="submit" class="edit editButton">Edit</button>
										<button class="delete editButton">Delete</button>
									</div>
									<div class="clearfix"> </div>
								</div>
							</div>
						</form:form>
					</c:forEach>
				</c:if>
				
				
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
	<!-- //markets -->
	</body>
	<script>
	$( document ).ready(function() {
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
		
		$(".delete").on('click', function() {
			var gameId = $(this).parent().attr("id");
			
			operationsWithDB.deleteGame(gameId);
		});
		
// 		$(".edit").on("click", function() {
// 			var gameId = $(this).parent().attr("id");			
// 			var title = $("#" + gameId + "_title").val();
// 			var description = $("#" + gameId + "_description").val();
			
// 			operationsWithDB.editGame(gameId, title, description);
// 		}); 
	})		
	</script>
	
<jsp:include page="../tags/footer.jsp" flush="true"></jsp:include>