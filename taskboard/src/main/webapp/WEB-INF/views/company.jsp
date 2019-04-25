<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

	<title>Company | Taskboard</title>
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
				<li class="active"><a href="company">Company</a></li>
				<li><a href="profile">Profile</a></li>
				<li><a href="review">Review</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a>${sessionScope.user.firstName} ${sessionScope.user.lastName}</a></li> 
				<li><a href="logout">Log out</a></li>
			</ul>
		</div>
	</nav>
	<main>
	<article class="container">
		<header class="row">
			<div class="col-xs-12">
				<h2>Team:</h2>
			</div>
		</header>
		<section class="row">
			<div class="col-xs-12">
				<c:forEach items="${users}" var="user">
					<figure style="display: inline-block;">
						<img src="../../img/${user.getIcon()}" alt="Icon"
							style="margin: 10px; width: 150px;">
						<figcaption>
							<c:choose>
								<c:when
									test="${user.getFirstName().length() + user.getLastName().length() < 20}">
                                        ${user.getFirstName()} ${user.getLastName()}
                                    </c:when>
								<c:otherwise>
                                        ${user.getFirstName().charAt(0)}. ${user.getLastName()}
                                    </c:otherwise>
							</c:choose>
						</figcaption>
					</figure>
				</c:forEach>
			</div>
		</section>
		<section class="row">
			<div class="col-xs-12">
				<h2 style="margin-bottom: 2%;">Projects:</h2>
				<p class="bg-info text-center">${infoProject}</p>
				<table class="table table-hover table-bordered">
					<thead>
						<tr>
							<th>Delete</th>
							<th>Project</th>
							<th>Description</th>
							<th>Tasks</th>
							<th>Enter</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${projects}" var="project">
							<tr>
								<td>
									<form method="post">
										<input type="hidden" name="idProject"
											value="${project.getId()}">
										<button class="btn btn-danger btn-xs">Delete</button>
									</form>
								</td>
								<td>${project.getName()}</td>
								<td>${project.getDescription()}</td>
								<td>${project.getTasks().size()}</td>
								<td>
									<form method="post" action="project">
										<input type="hidden" id="idProject" name="idProject"
											value="${project.getId()}">
										<button class="btn btn-info btn-xs">Enter</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</section>
		<section class="row">
			<div class="col-xs-12">
				<h2 style="margin-bottom: 4%;">Add a new project:</h2>
				<form method="post">
					<div class="col-sm-4 text-center">
						<div class="form-group">
							<label for="name">Name:</label> <input class="form-control"
								id="name" placeholder="Name" name="name" minlength="3"
								maxlength="15" style="margin-top: 1%;" required>
						</div>
					</div>
					<div class="col-sm-4 text-center">
						<div class="form-group">
							<label for="description">Content:</label> <input
								class="form-control" id="description" placeholder="Description"
								name="description" minlength="8" maxlength="64"
								style="margin-top: 1%;" required>
						</div>
					</div>
					<div class="col-sm-4 text-center">
						<input type="hidden" name="idProject" value="0">
						<button class="btn btn-primary" style="margin-top: 25px;">Add
							the project »</button>
					</div>
				</form>
			</div>
		</section>
	</article>
	</main>
</body>
</html>