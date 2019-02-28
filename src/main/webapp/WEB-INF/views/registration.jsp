<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="tags/header.jsp" flush="true"></jsp:include>

<script type="text/javascript">
	document.getElementsByTagName('title')[0].innerHTML = 'Регистрация';
</script>
<!-- markets -->
<div class="markets" id="markets">
	<div class="container">
		<div class="agileits-title">
			<h3>Регистрация На Потребител</h3>
		</div>
		<div class="markets-grids">
			<form:form method="POST" class="createGameForm"
				action='${contextPath}${saveRegistration}' modelAttribute="student"
				enctype="multipart/form-data">
				<div class="w3ls-markets-grid">
					<div class="agileits-icon-grid">
						<div class="icon-rightCr">
							<p>
								<label for="avatar">Потребителско име:* </label>
								<input type="text" class="editTextField" placeholder="Потребителско име" name="username">
								<br />
								<label for="avatar">Име:* </label>
								<input class="editTextField" type="text" placeholder="Име" name="firstName">
								<br />
								<label for="avatar">Фамилия:* </label>
								<input class="editTextField" type="text" placeholder="Фамилия" name="lastName">
								<br />
								<label for="email">Email:*</label>
								<input class="editTextField" type="email" class="form-control" id="register-email"
									placeholder="example@gmail.com" name="email">
								<span>Представяне:*</span>
								<textarea class="editTextField editDescription" name="description"></textarea>
								<label for="avatar">Профилна снимка: </label>
								<input class="editTextField" type="file" id="avatar" accept="image/png, image/jpeg"
									name="avatar" placeholder="Профилна снимка">
								<label for="avatar">Парола:* </label>
								<input class="editTextField" type="text" placeholder="Парола" name="password">
								<br />
								<label for="avatar">Повтори Парола:* </label>
								<input class="editTextField" type="text" placeholder="Повтори Парола" name="passwordRepeat">
								<br />
							</p>
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
<jsp:include page="tags/footer.jsp" flush="true"></jsp:include>