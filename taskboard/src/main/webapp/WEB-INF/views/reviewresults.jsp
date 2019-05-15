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

<body class="m-3">

	<div class="row col-md-6">
		<table class="table table-striped table-bordered table-sm">
			<tr>
				<th>Id</th>
				<th>State</th>
				<th>Content</th>
				<th>User Id</th>
				<th>Project Name</th>
			</tr>

			<c:forEach items="${tasks}" var="task">
				<tr>
					<td>${task.getId()}</td>
					<td>${task.getState()}</td>
					<td>${task.getContent()}</td>
					<td>${task.getUser().getUserId()}</td>
					<td>${task.getProject().getName()}</td>
				</tr>
			</c:forEach>
		</table>
	<nav aria-label="taskListing">
		<ul class="pagination">
			<c:if test="${currentPage != 1}">
				<li class="page-item"><a class="page-link"
					href="reviewresults?currentPage=${currentPage-1}">Previous</a>
				</li>
			</c:if>

			<c:forEach begin="1" end="${pageCount}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<li class="page-item active"><a class="page-link"> ${i} <span class="sr-only">(current)</span></a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link"
							href="reviewresults?currentPage=${i}">${i}</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:if test="${currentPage lt pageCount}">
				<li class="page-item"><a class="page-link"
					href="reviewresults?currentPage=${currentPage+1}">Next</a>
				</li>
			</c:if>
			<li><a class="page-item" href="pdfview">Print / PDF View</a></li>
		</ul>
	</nav>	
	</div>


	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>