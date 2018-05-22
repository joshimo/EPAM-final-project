<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${requestScope.get(\"cartView\")}" var="view" scope="page" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="menu" var="menu"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<c:set value="${sessionScope.get(\"locale\").language}" var="lang"  scope="page"/>

<html>
<head>
    <title><fmt:message key="userCart.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1><fmt:message key="userCart.h1" bundle="${legend}"/></h1>
<div class="narrowmenu">
    <p><c:out value="${sessionScope.get(\"user\").name}" /></p>
    <form name="remove" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="main" />
        <button type="submit" class="menubutton"><fmt:message key="userCart.back" bundle="${menu}"/></button>
    </form>
</div>
<div>
    <table class="narrowtable">
        <tr>
            <th style="width: 20%;"><fmt:message key="userCart.table.col1" bundle="${legend}"/></th>
            <th style="width: 20%;"><fmt:message key="userCart.table.col2" bundle="${legend}"/></th>
            <th style="width: 20%;"><fmt:message key="userCart.table.col3" bundle="${legend}"/></th>
            <th style="width: 20%;"><fmt:message key="userCart.table.col4" bundle="${legend}"/></th>
            <th style="width: 20%;"><fmt:message key="userCart.table.col5" bundle="${legend}"/></th>
            <th style="width: 40%;"></th>
        </tr>
        <c:forEach items="${view.products}" var="product">
        <tr>
            <td class="tdc">${product.key.code}</td>
            <td class="tdc"><c:out value="${lang == 'ru' ? product.key.nameRu : product.key.nameEn}"/></td>
            <td class="tdc"><c:out value="${lang == 'ru' ? product.key.descriptionRu : product.key.descriptionEn}" /></td>
            <td class="tdc">
                <c:out value="${product.value}"/>
                <c:out value="${lang == 'ru' ? product.key.uomRu : product.key.uomEn}"/>
            </td>
            <td class="tdc">
                <fmt:formatNumber value="${product.value * product.key.cost}" maxFractionDigits="2" minFractionDigits="2"/>
                <fmt:message key="currency" bundle="${legend}"/>
            </td>
            <td class="tdc">
                <form name="remove" method="post" action="project" >
                    <input type="hidden" name="command" value="removeProductFromCart" />
                    <button type="submit" name="productCode" value="${product.key.code}" class="menu-button">
                        <fmt:message key="userCart.remove" bundle="${buttons}"/>
                    </button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
    <h3 style="margin-left: 20%; margin-right: 20%;">
        <fmt:message key="userCart.total" bundle="${legend}"/>
        <fmt:formatNumber value="${view.totalCost}" maxFractionDigits="2" minFractionDigits="2"/>
        <fmt:message key="currency" bundle="${legend}"/>
    </h3>
</div>
    <div class="button_div">
        <c:if test="${view.size > 0}">
            <form name="remove" method="post" action="project" >
                <input type="hidden" name="command" value="createInvoice" />
                <button type="submit" class="bigbutton"><fmt:message key="userCart.order" bundle="${buttons}"/></button>
            </form>
            <form name="remove" method="post" action="project" >
                <input type="hidden" name="command" value="createInvoiceAndPay" />
                <button type="submit" class="bigbutton"><fmt:message key="userCart.pay" bundle="${buttons}"/></button>
            </form>
        </c:if>
        <c:if test="${view.size == 0}">
            <form name="remove" method="post" action="project" >
                <input type="hidden" name="command" value="createInvoice" />
                <button type="submit" class="bigbutton" disabled><fmt:message key="userCart.order" bundle="${buttons}"/></button>
            </form>
            <form name="remove" method="post" action="project" >
                <input type="hidden" name="command" value="createInvoiceAndPay" />
                <button type="submit" class="bigbutton" disabled><fmt:message key="userCart.pay" bundle="${buttons}"/></button>
            </form>
        </c:if>
    </div>
<t:colontitle/>
</body>
</html>
