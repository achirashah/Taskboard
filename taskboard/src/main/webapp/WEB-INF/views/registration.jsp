<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta name="description" content="Taskboard board implemented in Java EE, Hibernate and JPA - Registration">
    <title>Registration | TaskBoard</title>
    <link rel="stylesheet" href="../../css/style.css">
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/jquery-3.2.1.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
	        <h2 class="text-center">Register</h2>
	        <form:form modelAttribute="user" method="post">
	            <main class="row">
	                <div class="col-sm-6">
	                    <div class="row">
	                        <div class="col-xs-2"></div>
	                        <div class="col-xs-8">
	                            <div class="form-group">
	                                <label for="firstName">FirstName:</label>
	                                <input class="form-control" id="firstName" placeholder="firstName" name="firstName" minlength="3"
	                                       maxlength="15" required>
	                            </div>
	                            <div class="form-group">
	                                <label for="lastName">LastName:</label>
	                                <input class="form-control" id="lastName" placeholder="lastName" name="lastName"
	                                       minlength="3" maxlength="20" required>
	                            </div>
	                            <div class="form-group">
	                                <label for="userId">User ID:</label>
	                                <input class="form-control" id="userId" placeholder="User ID" name="userId" minlength="3"
	                                       maxlength="10" required>
	                            </div>
	                            <div class="form-group">
	                                <label for="email">Email:</label>
	                                <input class="form-control" id="email" placeholder="Email" name="email" pattern="[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z0-9]+" minlength="5"
	                                       maxlength="45" required>
	                            </div>
	                            <div class="form-group">
	                                <label for="password">Password:</label>
	                                <input type="password" class="form-control" id="password" placeholder="Password"
	                                       name="password" minlength="6" maxlength="15" required>
	                            </div>
	                            <div class="form-group">
	                                <label for="company">Company's code:<br>
	                                    <small>If there isn't in database, the company will be created.</small>
	                                </label>
	                                <input class="form-control" id="company" placeholder="Company" name="company"
	                                       minlength="6" maxlength="15" required>
	                            </div>
	                        </div>
	                        <div class="col-xs-2"></div>
	                    </div>
	                </div>
                <div class="col-sm-2">
                    <div class="row">
                        <p><strong>Select icon:</strong></p>
                        <c:forEach var = "i" begin = "1" end = "10">
                            <div class="radio" style="display: inline;">
                                <label>
                                    <input type="radio" id="icon" name="icon" value="${i}.png" required>
                                    <img src="../../img/${i}.png" alt="icon" style="width: 50px; padding: 5% 2%;">
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </main>
            <article class="row">
                <div class="col-xs-3"></div>
                <div class="col-xs-6">
                    <p class="bg-info text-center">${infoRegistration}</p>
                </div>
                <div class="col-xs-3"></div>
            </article>
            <nav class="row" style="margin-bottom: 1%;">
                <div class="col-xs-1"></div>
                <div class="col-xs-3 col-md-2 text-right">
                    <a class="btn btn-default" href="index">« Home page</a>
                </div>
                <div class="col-xs-4 col-md-2 text-right">
                    <a class="btn btn-default" href="login">« Login</a>
                </div>
                <div class="col-xs-3 col-md-2 text-right">
                    <button class="btn btn-primary">Register »</button>
                </div>
                <div class="col-xs-1"></div>
            </nav>
        </form:form>
    </div>
</body>
</html>