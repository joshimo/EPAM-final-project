<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="page" />

<html>
<head>
    <title><fmt:message key="administration.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1><fmt:message key="administration.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
</div>
<div class="index_div">
    <c:if test="${user.userRole == 'ADMIN' || user.userRole == 'CASHIER' || user.userRole == 'SENIOR_CASHIER'}">
    <form action="project" method="get">
        <input type="hidden" name="command" value="manageInvoices" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="giantbutton">
            <fmt:message key="administration.orders" bundle="${buttons}"/>
        </button>
    </form>
    </c:if>
    <c:if test="${!(user.userRole == 'ADMIN' || user.userRole == 'CASHIER' || user.userRole == 'SENIOR_CASHIER')}">
        <form action="project" method="get">
            <input type="hidden" name="command" value="manageInvoices" />
            <input type="hidden" name="type" value="all" />
            <button type="submit" class="giantbutton" disabled>
                <fmt:message key="administration.orders" bundle="${buttons}"/>
            </button>
        </form>
    </c:if>
    <c:if test="${user.userRole == 'ADMIN'}">
    <form action="project" method="get">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="giantbutton">
            <fmt:message key="administration.users" bundle="${buttons}"/>
        </button>
    </form>
    </c:if>
    <c:if test="${user.userRole != 'ADMIN'}">
        <form action="project" method="get">
            <input type="hidden" name="command" value="manageUsers" />
            <input type="hidden" name="type" value="all" />
            <button type="submit" class="giantbutton" disabled>
                <fmt:message key="administration.users" bundle="${buttons}"/>
            </button>
        </form>
    </c:if>
    <c:if test="${user.userRole == 'ADMIN' || user.userRole == 'MERCHANT'}">
    <form action="project" method="get">
        <input type="hidden" name="command" value="manageProducts" />
        <button type="submit" class="giantbutton">
            <fmt:message key="administration.stock" bundle="${buttons}"/>
        </button>
    </form>
    </c:if>
    <c:if test="${!(user.userRole == 'ADMIN' || user.userRole == 'MERCHANT')}">
        <form action="project" method="get">
            <input type="hidden" name="command" value="manageProducts" />
            <button type="submit" class="giantbutton" disabled>
                <fmt:message key="administration.stock" bundle="${buttons}"/>
            </button>
        </form>
    </c:if>
    <c:if test="${user.userRole == 'ADMIN' || user.userRole == 'SENIOR_CASHIER'}">
    <form action="project" method="get">
        <input type="hidden" name="command" value="manageTransactions" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="giantbutton">
            <fmt:message key="administration.transactions" bundle="${buttons}"/>
        </button>
    </form>
    </c:if>
    <c:if test="${!(user.userRole == 'ADMIN' || user.userRole == 'SENIOR_CASHIER')}">
        <form action="project" method="get">
            <input type="hidden" name="command" value="manageTransactions" />
            <input type="hidden" name="type" value="all" />
            <button type="submit" class="giantbutton" disabled>
                <fmt:message key="administration.transactions" bundle="${buttons}"/>
            </button>
        </form>
    </c:if>
    <form action="project" method="get">
        <input type="hidden" name="command" value="main" />
        <button type="submit" class="giantbutton">
            <fmt:message key="administration.exit" bundle="${buttons}"/>
        </button>
    </form>
</div>
<t:colontitle/>
</body>
</html>