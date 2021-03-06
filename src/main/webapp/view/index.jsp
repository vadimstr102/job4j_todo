<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>


<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Favicon -->
    <link rel="shortcut icon" href="/job4j_todo/img/favicon.ico" type="image/x-icon">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">

    <title>TODO list</title>
</head>
<body>
<!--jQuery-->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<!-- Option 1: Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>

<div class="container">
    <div class="row justify-content-center pt-3">
        <div class="col-7 text-end">
            <c:if test="${user != null}">
                <p>Привет, <c:out value="${user.name}"/>! | <a href="<%=request.getContextPath()%>/logout.do">Выйти</a></p>
            </c:if>
        </div>
    </div>
    <div class="row justify-content-center pt-3">
        <div class="col text-center">
            <h1>TODO list</h1>
        </div>
    </div>
    <div class="row justify-content-center pt-3">
        <div class="col-5">
            <form action="<c:url value="/item.do"/>" method="post">
                <div class="mb-3">
                    <label for="description" class="form-label">Добавить новое задание</label>
                    <input type="text" class="form-control" id="description" name="description" placeholder="Новое задание" title="Добавьте задание">
                </div>
                <div class="mb-3">
                    <label for="categories" class="form-label">Категория задания</label>
                    <select class="form-select" id="categories" name="categories" title="Выберите категорию" multiple>
                        <c:forEach items="${categories}" var="category">
                            <option value='<c:out value="${category.id}"/>'><c:out value="${category.name}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="d-grid gap-2 d-md-flex justify-content-md-end mb-3">
                    <button type="submit" class="btn btn-primary" onclick="return validate()">Добавить</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row justify-content-center pt-3">
        <div class="col-7">
            <div class="form-check form-switch mb-3">
                <input class="form-check-input" type="checkbox" id="showAll">
                <label class="form-check-label" for="showAll">Показать выполненные задания</label>
            </div>
            <div class="mb-3">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">№</th>
                        <th scope="col">Задание</th>
                        <th scope="col">Категория</th>
                        <th scope="col">Автор</th>
                        <th scope="col">Дата создания</th>
                        <th scope="col" class="text-center">Статус</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript scripts -->
<script src="/job4j_todo/service/index.js"></script>
</body>
</html>
