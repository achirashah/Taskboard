<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<h3>Select Review Sources</h3>
	    <nav class="navbar navbar-default navbar-static-top">
        <div class="container">
            <p class="navbar-text navbar-left">
                Taskboard
            </p>
            <ul class="nav navbar-nav navbar-left">
                <li><a href="company">Company</a></li>
                <li><a href="profile">Profile</a></li>
                <li class="active"><a href="review">Review</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout">Log out</a></li>
            </ul>
        </div>
    </nav>
	<form:form method="post">
		<!-- 		<main class="row"> -->
		<div class="col-sm-6">
			<div class="row">
				<div class="col-xs-2"></div>
				<div class="col-xs-8">
					<table>
						<tr>
							<td>Company:</td>
							<td><form:select path="companyList">
									<form:option value="ALL" label="--- All ---" />
<%-- 									<form:options items="${}" /> --%>
								</form:select></td>
							<td><form:errors path="companyList" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Project:</td>
							<td><form:select path="projectList">
									<form:option value="ALL" label="--- All ---" />
<%-- 									<form:options items="${}" /> --%>
								</form:select></td>
							<td><form:errors path="projectList" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Employee:</td>
							<td><form:select path="employeeList">
									<form:option value="ALL" label="--- All ---" />
<%-- 									<form:options items="${}" /> --%>
								</form:select></td>
							<td><form:errors path="employeeList" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Task Status:</td>
							<td><form:select path="taskList">
									<form:option value="ALL" label="--- All ---" />
<%-- 									<form:options items="${}" /> --%>
								</form:select></td>
							<td><form:errors path="tasksList" cssClass="error" /></td>
						</tr>
					</table>
				</div>
				<div class="col-xs-2"></div>
			</div>
		</div>
		<nav class="row" style="margin-bottom: 1%;">
			<div class="col-xs-1"></div>
			<div class="col-xs-3 col-md-2 text-right">
				<a class="btn btn-default" href="index">Submit</a>
			</div>
			<div class="col-xs-1"></div>
		</nav>
	</form:form>
</body>
</html>