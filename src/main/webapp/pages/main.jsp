<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set value="${sessionScope.get(\"user\")}" var="user" scope="page" />

<html>
<head>
    <title>Главная</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
    <h1>Главная страница</h1>
    <div class="menu">
        <p><c:out value="${user.name}, ${user.userRole}"/></p>
        <c:if test="${user.name == 'Guest'}">
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="addNewUser" />
                <button type="submit" class="menubutton">Зарегистрироваться</button>
            </form>
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="enter" />
                <button type="submit" class="menubutton">Вход</button>
            </form>
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="usersCart" />
                <button type="submit" class="menubutton">Корзина</button>
            </form>
        </c:if>
        <c:if test="${user.name != 'Guest'}">
            <c:if test="${user.name != 'Guest' && user.userRole != 'USER'}">
                <form name="addProductForm" method="get" action="project" class="menuitem">
                    <input type="hidden" name="command" value="administration" />
                    <button type="submit" class="menubutton">Администрирование</button>
                </form>
            </c:if>
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="usersCart" />
                <button type="submit" class="menubutton">Корзина</button>
            </form>
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="showUserProfile" />
                <input type="hidden" name="edit" value="false" />
                <button type="submit" class="menubutton">Профиль</button>
            </form>
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="logout" />
                <button type="submit" class="menubutton">Выход</button>
            </form>
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
                <th></th>
            </tr>
            <c:forEach items="${products}" var="product">
            <tr>
                <td class="tdc"><c:out value="${product.code}"/></td>
                <td class="tdl"><c:out value="${product.nameRu}"/></td>
                <td class="tdl"><c:out value="${product.descriptionRu}"/></td>
                <td class="tdl"><fmt:formatNumber value="${product.cost}" maxFractionDigits="2" minFractionDigits="2"/></td>
                <c:if test="${product.available == true}"><td class="tdc" style="color: green">В наличии</td></c:if>
                <c:if test="${product.available == false}"><td class="tdc" style="color: darkred">Отсутствует</td></c:if>
                <td class="tdc"><c:out value="${product.notesRu}"/></td>
                <td class="tdc">
                    <form name="addProductForm" method="get" action="project" >
                        <input type="hidden" name="command" value="addProductToCart" />
                        <input type="text" name="productQuantity" size="6" required/>
                        <button type="submit" name="productCode" value="${product.code}" class="smallbutton">Добавить в корзину</button>
                    </form>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
    <footer>
        <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
    </footer>
</body>
</html>