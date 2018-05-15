<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Новый пользователь</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1>Авторизация</h1>
<div class = index_div>
    <form name="LoginForm" method="post" action="project" >
        <input type="hidden" name="command" value="registerNewUser" />
        <h4>Имя пользователя:</h4>
        <input type="text" name="name" size="36" class="input" required/><br/>
        <h4>Пароль:</h4>
        <input type="password" name="password" size="36" class="input" required/><br/><br/>
        <h4>Телефон:</h4>
        <input type="text" name="phone" size="36" class="input" /><br/><br/>
        <h4>e-mail:</h4>
        <input type="text" name="email" size="36" class="input" /><br/><br/>
        <h4>Адрес:</h4>
        <input type="text" name="address" size="36" class="input" /><br/><br/>
        <h4>Примечания:</h4>
        <input type="text" name="notes" size="36" class="input" /><br/><br/>
        <input type="submit" class="bigbutton" value="Добавить пользователя"/>
        <input type="submit" class="bigbutton" value="Отмена" onclick="history.back(); return false;"/>
    </form>
</div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>
