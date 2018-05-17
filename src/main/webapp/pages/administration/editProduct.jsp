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
<div class = "inner_div">
    <form name="productForm" method="get" action="project" >
        <input type="hidden" name="command" value="updateProduct" />
        <input type="hidden" name="id" value="${product.id}" />
        <input type="hidden" name="reserved" value="${product.reservedQuantity}" />
        <h4>Артикул:</h4>
        <input type="text" name="code" size="24" class="input" value="${product.code}" required/><br/>
        <h4>Название Ru:</h4>
        <textarea rows="3" cols="48" name="nameRu" required>${product.nameRu}</textarea><br/>
        <h4>Название En:</h4>
        <textarea rows="3" cols="48" name="nameEn" required>${product.nameEn}</textarea><br/>
        <h4>Описание Ru:</h4>
        <textarea rows="3" cols="48" name="descriptionRu" required>${product.descriptionRu}</textarea><br/>
        <h4>Описание En:</h4>
        <textarea rows="3" cols="48" name="descriptionEn" required>${product.descriptionEn}</textarea><br/>
        <h4>Цена:</h4>
        <input type="text" name="cost" size="24" class="input" value="${product.cost}" required/><br/><br/>
        <c:if test="${product.available}">
            <input type="checkbox" name="isAvailable" checked/><b>Есть на складе</b><br/>
        </c:if>
        <c:if test="${!product.available}">
            <input type="checkbox" name="isAvailable" /><b>Есть на складе</b><br/>
        </c:if>
        <h4>Количество на складе:</h4>
        <input type="text" name="quantity" size="24" class="input" value="${product.quantity}"/><br/>
        <h4>Единицы измерения Ru:</h4>
        <input type="text" name="uomRu" size="24" class="input" required value="${product.uomRu}"/><br/>
        <h4>Единицы измерения En:</h4>
        <input type="text" name="uomEn" size="24" class="input" required value="${product.uomEn}"/><br/>
        <h4>Примечания Ru:</h4>
        <textarea rows="3" cols="48" name="notesRu">${product.notesRu}</textarea><br/>
        <h4>Примечания En:</h4>
        <textarea rows="3" cols="48" name="notesEn">${product.notesEn}</textarea><br/>
        <div class="button_div">
            <input type="submit" class="bigbutton" value="Сохранить"/>
            <input type="submit" class="bigbutton" value="Отмена" onclick="history.back(); return false;"/>
        </div>
    </form>
</div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>
