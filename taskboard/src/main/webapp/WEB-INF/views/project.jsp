<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
\    <title>Project | Taskboard</title>
    <link rel="stylesheet" href="../../css/style.css">
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/jquery-3.2.1.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/backToTheSamePlace.js"></script>
</head>
<body>
    <nav class="navbar navbar-default navbar-static-top">
        <div class="container">
            <p class="navbar-text navbar-left">
                Taskboard
            </p>
            <ul class="nav navbar-nav navbar-left">
                <li><a href="company">Company</a></li>
                <li><a href="profile">Profile</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout">Log out</a></li>
            </ul>
        </div>
    </nav>
    <main>
        <article class="container">
            <header class="row">
                <div class="col-xs-12">
                    <h1><strong>${project.getName()}</strong></h1>
                    <h2>${project.getDescription()}</h2>
                </div>
            </header>
            <section class="row">
                <div class="col-xs-12">

                    <a href="addtask?idProject=${project.getId()}">
                        <button class="btn btn-default" type="button">Add a task</button>
                    </a>

                </div>
            </section>
            <section class="row">
                <div class="col-xs-12">
                    <h3 style="margin-bottom: 2%">Summary:</h3>
                    <a href="#todo">
                        <button class="btn btn-warning" type="button">
                            To do: <span class="badge">${fn:length(todoTasks)}</span>
                        </button>
                    </a>
                    <a href="#doing">
                        <button class="btn btn-success" type="button">
                            Doing: <span class="badge">${fn:length(doingTasks)}</span>
                        </button>
                    </a>
                    <a href="#done">
                        <button class="btn btn-info" type="button">
                            Done: <span class="badge">${fn:length(doneTasks)}</span>
                        </button>
                    </a>
                </div>
            </section>
            <section class="row">
                <div class="col-xs-12">
                    <h3 id="todo"><strong>To do:</strong></h3>
                    <c:if test="${todoTasks[0] == null}">
                        <p>No tasks.</p>
                    </c:if>
                    <c:forEach items="${todoTasks}" var="task">
                        <div class="media yellow-note neat">
                            <img src="../../img/${task.getEmployee().getAvatar()}" alt="Avatar">
                            <h4>${task.getEmployee().getName()}</h4>
                            <p><strong>${task.getContent()}</strong></p>
                        </div>
                    </c:forEach>
                </div>
            </section>
            <section class="row">
                <div class="col-xs-12">
                    <h3 id="doing"><strong>Doing:</strong></h3>
                    <c:if test="${doingTasks[0] == null}">
                        <p>No tasks.</p>
                    </c:if>
                    <c:forEach items="${doingTasks}" var="task">
                        <div class="media green-note neat">
                            <img src="../../img/${task.getEmployee().getAvatar()}" alt="Avatar">
                            <h4>${task.getEmployee().getName()}</h4>
                            <p><strong>${task.getContent()}</strong></p>
                        </div>
                    </c:forEach>
                </div>
            </section>
            <div class="row">
                <div class="col-xs-12">
                    <h3 id="done"><strong>Done:</strong></h3>
                    <c:if test="${doneTasks[0] == null}">
                        <p>No tasks.</p>
                    </c:if>
                    <c:forEach items="${doneTasks}" var="task">
                        <div class="media blue-note neat">
                            <img src="../../img/${task.getEmployee().getAvatar()}" alt="Avatar">
                            <h4>${task.getEmployee().getName()}</h4>
                            <p><strong>${task.getContent()}</strong></p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </article>
    </main>
</body>
</html>
