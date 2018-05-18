<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Новый продукт</title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1>Новый продукт</h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
</div>
<div class = "inner_div">
    <form name="newProductForm" method="post" action="project" >
        <input type="hidden" name="command" value="saveNewProduct" />
        <h4>Артикул:</h4>
        <input class="input" type="text" name="code" size="24" required/><br/>
        <h4>Название Ru:</h4>
        <textarea class="multitext" rows="3" cols="48" name="nameRu" required></textarea><br/>
        <h4>Название En:</h4>
        <textarea class="multitext" rows="3" cols="48" name="nameEn" required></textarea><br/>
        <h4>Описание Ru:</h4>
        <textarea class="multitext" rows="3" cols="48" name="descriptionRu" required></textarea><br/>
        <h4>Описание En:</h4>
        <textarea class="multitext" rows="3" cols="48" name="descriptionEn" required></textarea><br/>
        <h4>Цена:</h4>
        <input class="input" type="text" name="cost" size="24" required/><br/><br/>
        <input type="checkbox" name="isAvailable"><b>Есть на складе</b><br/>
        <h4>Количество на складе:</h4>
        <input class="input" type="text" name="quantity" size="24" /><br/>
        <h4>Единицы измерения Ru:</h4>
        <input class="input" type="text" name="uomRu" size="24" required/><br/>
        <h4>Единицы измерения En:</h4>
        <input class="input" type="text" name="uomEn" size="24" required/><br/>
        <h4>Примечания Ru:</h4>
        <textarea class="multitext" rows="3" cols="48" name="notesRu"></textarea><br/>
        <h4>Примечания En:</h4>
        <textarea class="multitext" rows="3" cols="48" name="notesEn"></textarea><br/>
        <div class="button_div">
            <input type="submit" class="bigbutton" value="Добавить продукт"/>
            <input type="submit" class="bigbutton" value="Отмена" onclick="history.back(); return false;"/>
        </div>
    </form>
</div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>
