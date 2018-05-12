
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
        <c:out value="${sessionScope.get(\"user\").userRole}"/><br/>
        <c:if test="${sessionScope.get(\"user\").name == 'Guest'}">
            <a href="<c:url value="/project?command=addNewUser"/>" name="newUser" >Зарегистрироваться</a>
            <a href="<c:url value="/project?command=enter"/>" name="login" >Вход</a>
            <a href="<c:url value="/project?command=usersCart"/>" name="cart" >Корзина</a>
        </c:if>
        <c:if test="${sessionScope.get(\"user\").name != 'Guest'}">
            <a href="<c:url value="/project?command=usersCart"/>" name="cart" >Корзина</a>
            <a href="<c:url value="/project?command=showUserProfile&edit=false"/>" name="userProfile" >Профиль</a>
            <a href="<c:url value="/project?command=logout"/>" name="logout" >Выход</a>
        </c:if>
    </div>
    <div>
        <table>
            <tr>
                <th style="width: 5%;">Артикул</th>
                <th style="width: 20%;">Название</th>
                <th style="width: 20%;">Описание</th>
                <th style="width: 5%;">Цена</th>
                <th style="width: 5%;">Склад</th>
                <th style="width: 10%;">Примечания</th>
                <th style="width: 10%;"></th>
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
                <td>
                    <form name="addProductForm" method="get" action="project" >
                        <input type="hidden" name="command" value="addProductToCart" />
                        <input type="text" name="productQuantity" size="6" required/>
                        <button type="submit" name="productCode" value="${product.code}" class="menu-button">Добавить в корзину</button>
                    </form>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>