
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Главная</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>

<body>
    <h1>Главная страница</h1>
    <div style="margin: 10px; text-align: right">
        <c:out value="${sessionScope.get(\"user\").name}"/><br/>
        <a href="<c:url value="/project/login?command=enter"/>" name="login" >login</a>
    </div>
    <div>
        <table>
            <tr>
                <th style="width: 5%;">Артикул</th>
                <th style="width: 25%;">Название</th>
                <th style="width: 25%;">Описание</th>
                <th style="width: 5%;">Цена</th>
                <th style="width: 5%;">Склад</th>
                <th style="width: 10%;">Примечания</th>
            </tr>
            <c:forEach items="${products}" var="product">
            <tr>
                <td class="tdc"><c:out value="${product.code}"/></td>
                <td class="tdl"><c:out value="${product.nameRu}"/></td>
                <td class="tdl"><c:out value="${product.descriptionRu}"/></td>
                <td class="tdl"><c:out value="${product.cost}, ${product.uomRu}"/></td>
                <c:if test="${product.available == true}"><td class="tdc" style="color: green">В наличии</td></c:if>
                <c:if test="${product.available == false}"><td class="tdc" style="color: darkred">Отсутствует</td></c:if>
                <td class="tdc"><c:out value="${product.notesRu}"/></td>
                <td><button type="submit" name="order" value="${product.code}" class="bigbutton">Добавить в корзину</button></td>
            </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>