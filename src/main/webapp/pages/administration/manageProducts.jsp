<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="page" />

<html>
<head>
    <title>Главная</title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1>Управление складом</h1>
<div class="widemenu">
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">Назад</button>
    </form>
</div>
<div>
    <table class="widetable">
        <tr>
            <th style="width: 4%;">Артикул</th>
            <th style="width: 12%;">Название Ru</th>
            <th style="width: 12%;">Название En</th>
            <th style="width: 12%;">Описание Ru</th>
            <th style="width: 12%;">Описание En</th>
            <th style="width: 3%;">Цена</th>
            <th style="width: 3%;">Склад</th>
            <th style="width: 3%;">Остаток</th>
            <th style="width: 3%;">Резерв</th>
            <th style="width: 12%;">Примечания Ru</th>
            <th style="width: 12%;">Примечания En</th>
            <th style="width: 12%;"></th>
        </tr>
        <c:forEach items="${products}" var="product">
            <tr>
                <td class="tdc"><c:out value="${product.code}"/></td>
                <td class="tdl"><c:out value="${product.nameRu}"/></td>
                <td class="tdl"><c:out value="${product.nameEn}"/></td>
                <td class="tdl"><c:out value="${product.descriptionRu}"/></td>
                <td class="tdl"><c:out value="${product.descriptionEn}"/></td>
                <td class="tdl"><fmt:formatNumber value="${product.cost}" maxFractionDigits="2" minFractionDigits="2"/></td>
                <c:if test="${product.available == true}"><td class="tdc" style="color: green">В наличии</td></c:if>
                <c:if test="${product.available == false}"><td class="tdc" style="color: darkred">Отсутствует</td></c:if>
                <td class="tdc"><c:out value="${product.quantity}"/></td>
                <td class="tdc"><c:out value="${product.reservedQuantity}"/></td>
                <td class="tdc"><c:out value="${product.notesRu}"/></td>
                <td class="tdc"><c:out value="${product.notesEn}"/></td>
                <td class="tdc">
                    <form name="productUpd" method="get" action="project" >
                        <input type="hidden" name="command" value="editProductPage" />
                        <input type="hidden" name="productCode" value="${product.code}" />
                        <button type="submit" class="smallbutton">Изменить</button>
                    </form>
                    <form name="productDel" method="get" action="project" >
                        <input type="hidden" name="command" value="deleteProduct" />
                        <input type="hidden" name="productCode" value="${product.code}" />
                        <button type="submit" class="smallbutton">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="button_div">
    <form action="project" method="get">
        <input type="hidden" name="command" value="addNewProductPage" />
        <button type="submit" class="bigbutton">Добавить новый продукт</button>
    </form>
</div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>