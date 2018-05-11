<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<div style="margin: 10px; text-align: right">
    <c:out value="${sessionScope.get(\"user\").name}" /><br/>
    <c:out value="${sessionScope.get(\"user\").email}" /><br/>
</div>
<div style="width: 50%; align: center;">
    <table>
        <tr>
            <th style="width: 10%;">Артикул</th>
            <th style="width: 10%;">Количество</th>
            <th style="width: 10%;">Цена</th>
        </tr>
        <c:forEach items="${sessionScope.get(\"cart\").products}" var="product">
        <tr>
            <td>${product.key}</td>
            <td>${product.value}</td>
            <td></td>
            <td>
                <form name="remove" method="get" action="project" >
                    <input type="hidden" name="command" value="removeProductFromCart" />
                    <button type="submit" name="productCode" value="${product.key}" class="menu-button">Удалить</button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
    <div class="button_div">
        <form name="remove" method="get" action="project" >
            <input type="hidden" name="command" value="main" />
            <button type="submit" class="bigbutton">Вернуться в магазин</button>
        </form>
        <form name="remove" method="get" action="project" >
            <input type="hidden" name="command" value="createInvoice" />
            <button type="submit" class="bigbutton">Оформить заказ</button>
        </form>
        <form name="remove" method="get" action="project" >
            <input type="hidden" name="command" value="createInvoiceAndPay" />
            <button type="submit" class="bigbutton">Оплатить заказ</button>
        </form>
    </div>
</div>
</body>
</html>
