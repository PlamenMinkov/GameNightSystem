<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../tags/header.jsp" flush="true"></jsp:include>

<script type="text/javascript">
	document.getElementsByTagName('title')[0].innerHTML = 'Създаване на игра';
</script>
<!-- markets -->
<div class="markets" id="markets">
	<div class="container">
		<div class="agileits-title">
			<h3>Създайте Предложение За Игра</h3>
		</div>
		<div class="markets-grids">
			<form:form method="POST" class="createGameForm" action='${contextPath}${saveGame}'
				modelAttribute="student" enctype="multipart/form-data">
				<div class="w3ls-markets-grid">
					<div class="agileits-icon-grid">
						<div class="icon-rightCr">								
							<p>
								<span>Заглавие:*</span>
								<input class="editTextField" type="text" name="title">
								<span>Описание:*</span>
								<textarea class="editTextField editDescription" name="description"></textarea>
							</p>
						</div>						
						<div class="icon-leftCr">
							<span>Снимка:</span>
							<input type="file" id="image" accept="image/png, image/jpeg"
								name="image" style="margin-top: 0.6em;" placeholder="Снимка">
						</div>
						<div>
							<button type="submit" class="edit editButton">Създай</button>
						</div>
						<div class="clearfix"></div><br>
						<span>Полетата със звезда(*) са задължителни</span>
					</div>
				</div>
			</form:form>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!-- //markets -->
</body>
<jsp:include page="../tags/footer.jsp" flush="true"></jsp:include>