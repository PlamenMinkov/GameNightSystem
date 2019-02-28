<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="tags/header.jsp" flush="true"></jsp:include>
	
	<script type="text/javascript">
		document.getElementsByTagName('title')[0].innerHTML = 'Профили';
	</script>
	
  <!-- team -->
	<div class="team" id="team">
		<div class="container">
			<div class="agileits-title"> 
				<h3>Участници</h3> 
				<form:form class="searchForm" method="GET" action="${contextPath}${accountRegister}" modelAttribute="UserSearch">
					<table>
						<tr>
							<td class="search"><input type="text" name="username" style="margin: 0.5em 0 0 0;"><button type=submit id="searchBtn" name="Search"><span class="fa fa-search"></span></button><td>
						</tr>
					</table>		
				</form:form>
			</div>
			<div class="agileits-team-grids">
				<c:if test="${not empty customers}">
			        <c:forEach var="custumer" items="${customers}">
				         <div class="col-md-3 agileits-team-grid">
							<div class="team-info">
								<c:choose>
									<c:when test="${custumer.getAvatar() == 1}">
										<img src="/resources/avatars/${custumer.getAccount_id()}.jpg" alt="" style="margin-top: 1em;">
									</c:when>
									<c:otherwise>
										<img src="/resources/avatars/simpleAvatar.jpg" alt="" style="margin-top: 1em;">
									</c:otherwise>
								</c:choose>								
								<div class="team-caption"> 
									<h4>${custumer.getUsername()}</h4>
									<p>${custumer.getFirstName()} ${custumer.getLastName()}</p>
									<ul>
										<li><a href="${contextPath}/sender?mailAdress=${custumer.getEmail()}">${custumer.getEmail()}</a></li>
										
											<sec:authorize access="hasRole('ROLE_ADMIN')">
									        	<c:if test='${custumer.getAuthorities().get(0).getAuthority() == "ROLE_USER"}'>
									        		<li id="${custumer.getAccount_id()}_period">
														Ативен период: ${custumer.getActivePeriod()}
									        			<button class="addPeriod" id="${custumer.getAccount_id()}">
									        				<i class="fa fa-plus" aria-hidden="true"></i>
								        				</button>
									        			<span class="cell toAdmin" id="${custumer.getAccount_id()}_toAdmin">
									        				<button >Направи го администратор</button>
									        			</span>
									        		</li>
									        	</c:if>
									        	<c:if test='${custumer.getAuthorities().get(0).getAuthority() == "ROLE_ADMIN"}'>
									        		<li class="cell toUser" id="${custumer.getAccount_id()}_toUser"><button >Направи го потребител</button></li>
									        	</c:if>
											</sec:authorize>
											<sec:authorize access="hasRole('ROLE_USER')">
									        	<c:if test='${custumer.getAuthorities().get(0).getAuthority() == "ROLE_USER"}'>
									        		<li>Ативен период: ${custumer.getActivePeriod()}</li>
									        	</c:if>
									        	<c:if test='${custumer.getAuthorities().get(0).getAuthority() == "ROLE_ADMIN"}'>
									        		<li>Администратор</li>
									        	</c:if>
											</sec:authorize>
									</ul>
								</div>
							</div>
						</div>
		         	</c:forEach>
				</c:if>
				<div class="col-md-3 agileits-team-grid">
					<div class="team-info">
						<img src="images/t1.jpg" alt="">
						<div class="team-caption"> 
							<h4>Peter Parker</h4>
							<p>Fusce laoreet</p>
							<ul>
								<li><a href="#"><i class="fa fa-facebook"></i></a></li>
								<li><a href="#"><i class="fa fa-twitter"></i></a></li>
								<li><a href="#"><i class="fa fa-rss"></i></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-3 agileits-team-grid">
					<div class="team-info">
						<img src="images/t2.jpg" alt="">
						<div class="team-caption"> 
							<h4>Johan Botha</h4>
							<p>Fusce laoreet</p>
							<ul>
								<li><a href="#"><i class="fa fa-facebook"></i></a></li>
								<li><a href="#"><i class="fa fa-twitter"></i></a></li>
								<li><a href="#"><i class="fa fa-rss"></i></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-3 agileits-team-grid">
					<div class="team-info">
						<img src="images/t3.jpg" alt="">
						<div class="team-caption"> 
							<h4>Justo Congue</h4>
							<p>Fusce laoreet</p>
							<ul>
								<li><a href="#"><i class="fa fa-facebook"></i></a></li>
								<li><a href="#"><i class="fa fa-twitter"></i></a></li>
								<li><a href="#"><i class="fa fa-rss"></i></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-3 agileits-team-grid">
					<div class="team-info">
						<img src="images/t4.jpg" alt="">
						<div class="team-caption"> 
							<h4>Steven Wilson</h4>
							<p>Fusce laoreet</p>
							<ul>
								<li><a href="#"><i class="fa fa-facebook"></i></a></li>
								<li><a href="#"><i class="fa fa-twitter"></i></a></li>
								<li><a href="#"><i class="fa fa-rss"></i></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
	<!-- //team -->
</body>
<script>
	$( document ).ready(function() {
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
	    $(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	    });
	    
		$(".addPeriod").on('click', function() {
			var accountId = $(this).attr("id");
			
			operationsWithDB.addPeriod(accountId);
		});
		
		$(".toAdmin").on('click', function() {
			var accountId = $(this).attr("id").split("_")[0];
			
			operationsWithDB.changeAuthority(accountId, 1);
		});
		
		$(".toUser").on('click', function() {
			var accountId = $(this).attr("id").split("_")[0];
			
			operationsWithDB.changeAuthority(accountId, 0);
		});
	});
</script>
	
<jsp:include page="tags/footer.jsp" flush="true"></jsp:include>