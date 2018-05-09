<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1>Авторизация</h1>
<div class = index_div>
    <form action="/auth" method="post" enctype="multipart/form-data">
        <h2>Ошибка авторизации: неправильный логин или пароль!</h2>
        <input type="submit" class="bigbutton" value="Назад" onclick="history.back(); return false;"/>
    </form>
</div>
</body>
</html>