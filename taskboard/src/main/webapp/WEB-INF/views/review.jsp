<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>Project | Taskboard</title>
<link rel="stylesheet" href="../../css/style.css">
<link rel="stylesheet" href="../../css/bootstrap.min.css">
<script src="../../js/jquery-3.2.1.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/backToTheSamePlace.js"></script>
</head>
<body>
	<nav class="navbar navbar-default navbar-static-top">
		<div class="container">
			<p class="navbar-text navbar-left">Taskboard</p>
			<ul class="nav navbar-nav navbar-left">
				<li><a href="company">Company</a></li>
				<li><a href="profile">Profile</a></li>
				<li class="active"><a href="review">Review</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a>${sessionScope.user.firstName}
						${sessionScope.user.lastName}</a></li>
				<li><a href="logout">Log out</a></li>
			</ul>
		</div>
	</nav>
	<form:form method="post" action="review" modelAttribute="reviewModel">
		<!-- 		<main class="row"> -->
		<div class="col-sm-6">
			<div class="row">
				<div class="col-xs-2"></div>
				<div class="col-xs-8">
					<h3>Select Options:</h3>
					<br>
					<table>
						<c:if test="${!empty reviewModel.projects}">
							<tr>
								<td>Project:</td>
								<td><form:select name="projects" multiple="multiple"
										path="projects" class="form-control">
										<c:forEach items="${reviewModel.projects}" var="item">
											<form:option value="${item}">${item.id}, ${item.name}</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:errors path="projects" cssClass="error" /></td>
							</tr>
						</c:if>
						<c:if test="${!empty reviewModel.users}">
							<tr>
								<td>User:</td>
								<td><form:select name="users" multiple="multiple"
										path="users" class="form-control">
										<c:forEach items="${reviewModel.users}" var="item">
											<form:option value="${item}">${item.id}, ${item.userId}</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:errors path="users" cssClass="error" /></td>
							</tr>
						</c:if>
						<tr>
							<td>Task State:</td>
							<td><form:select name="taskStates" multiple="multiple"
									path="taskStates" class="form-control">
									<c:forEach items="${reviewModel.taskStates}" var="item">
										<form:option value="${item}">${item}</form:option>
									</c:forEach>
								</form:select></td>
							<td><form:errors path="taskStates" cssClass="error" /></td>
						</tr>
					</table>
				</div>
				<div class="col-xs-2"></div>
			</div>
			<nav class="row" style="margin-bottom: 1%;">
				<div class="col-xs-2"></div>
				<div class="col-xs-3 col-md-2 text-right" style="margin-bottom: 5%;">
                            <button class="btn btn-primary">Submit</button>
				</div>
				<div class="col-xs-2"></div>
			</nav>
		</div>
	</form:form>
</body>
</html>