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
    <title><fmt:message key="manageUsers.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1><fmt:message key="manageUsers.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageUsers.back" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageUsers.allUsers" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="user" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageUsers.users" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="cashier" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageUsers.cashiers" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="seniorCashier" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageUsers.senior_cashiers" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="merchant" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageUsers.merchants" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageUsers" />
        <input type="hidden" name="type" value="admin" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageUsers.admins" bundle="${menu}"/>
        </button>
    </form>
</div>
<div>
    <table class="widetable">
        <tr>
            <th style="width: 15%;"><fmt:message key="manageUsers.table.col1" bundle="${legend}"/></th>
            <th style="width: 10%;"><fmt:message key="manageUsers.table.col2" bundle="${legend}"/></th>
            <th style="width: 15%;"><fmt:message key="manageUsers.table.col3" bundle="${legend}"/></th>
            <th style="width: 15%;"><fmt:message key="manageUsers.table.col4" bundle="${legend}"/></th>
            <th style="width: 25%;"><fmt:message key="manageUsers.table.col5" bundle="${legend}"/></th>
            <th style="width: 20%;"><fmt:message key="manageUsers.table.col6" bundle="${legend}"/></th>
            <th></th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td class="tdl"><c:out value="${user.name}"/></td>
                <td class="tdl"><c:out value="${user.userRole}"/></td>
                <td class="tdl"><c:out value="${user.phoneNumber}"/></td>
                <td class="tdl"><c:out value="${user.email}"/></td>
                <td class="tdl"><c:out value="${user.address}"/></td>
                <td class="tdl"><c:out value="${user.notes}"/></td>
                <td class="tdc">
                    <form name="details" method="post" action="project" >
                        <input type="hidden" name="command" value="editUserByAdmin" />
                        <input type="hidden" name="id" value="${user.id}" />
                        <!-- UNSUPPORTED -->
                        <button type="submit" class="smallbutton" disabled>
                            <fmt:message key="manageUsers.detailed" bundle="${buttons}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<t:colontitle/>
</body>
</html>