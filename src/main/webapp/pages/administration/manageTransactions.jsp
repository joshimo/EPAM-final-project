<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="menu" var="menu"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="page" />

<html>
<head>
    <title><fmt:message key="transactions.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1><fmt:message key="transactions.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageTransactions" />
        <input type="hidden" name="type" value="payment" />
        <button type="submit" class="menubutton">
            <fmt:message key="transactions.payments" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageTransactions" />
        <input type="hidden" name="type" value="refund" />
        <button type="submit" class="menubutton">
            <fmt:message key="transactions.refunds" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageTransactions" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="menubutton">
            <fmt:message key="transactions.all" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">
            <fmt:message key="transactions.back" bundle="${menu}"/>
        </button>
    </form>
</div>
<div>
    <table class="widetable">
        <tr>
            <th style="width: 15%;"><fmt:message key="transactions.table.col1" bundle="${legend}"/></th>
            <th style="width: 15%;"><fmt:message key="transactions.table.col2" bundle="${legend}"/></th>
            <th style="width: 15%;"><fmt:message key="transactions.table.col3" bundle="${legend}"/></th>
            <th style="width: 10%;"><fmt:message key="transactions.table.col4" bundle="${legend}"/></th>
            <th style="width: 20%;"><fmt:message key="transactions.table.col5" bundle="${legend}"/></th>
            <th style="width: 25%;"><fmt:message key="transactions.table.col6" bundle="${legend}"/></th>
        </tr>
        <c:forEach items="${transactions}" var="transaction">
            <tr>
                <td class="tdc"><c:out value="${transaction.invoiceCode}"/></td>
                <td class="tdl"><c:out value="${transaction.userName}"/></td>
                <c:if test="${transaction.transactionType == 'PAYMENT'}">
                    <td class="tdl" style="color: green">
                        <fmt:message key="transactions.payment" bundle="${legend}"/>
                    </td>
                </c:if>
                <c:if test="${transaction.transactionType != 'PAYMENT'}">
                    <td class="tdl" style="color: darkred">
                        <fmt:message key="transactions.refund" bundle="${legend}"/>
                    </td>
                </c:if>
                <td class="tdc">
                    <fmt:formatNumber value="${transaction.paymentValue}" maxFractionDigits="2" minFractionDigits="2"/>
                    <fmt:message key="currency" bundle="${legend}"/>
                </td>
                <td class="tdc"><fmt:formatDate type="both" value="${transaction.time}" /></td>
                <td class="tdc"><c:out value="${transaction.notes}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<t:colontitle/>
</body>
</html>