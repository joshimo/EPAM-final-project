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
    <title><fmt:message key="manageInvoices.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1><fmt:message key="manageInvoices.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageInvoices" />
        <input type="hidden" name="type" value="new" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageInvoices.new" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageInvoices" />
        <input type="hidden" name="type" value="closed" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageInvoices.closed" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="get" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageInvoices" />
        <input type="hidden" name="type" value="cancelled" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageInvoices.cancelled" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageInvoices.back" bundle="${menu}"/>
        </button>
    </form>
</div>
<div>
    <table class="widetable">
        <tr>
            <th style="width: 10%;"><fmt:message key="manageInvoices.table.col1" bundle="${legend}"/></th>
            <th style="width: 20%;"><fmt:message key="manageInvoices.table.col2" bundle="${legend}"/></th>
            <th style="width: 15%;"><fmt:message key="manageInvoices.table.col3" bundle="${legend}"/></th>
            <th style="width: 7%;"><fmt:message key="manageInvoices.table.col4" bundle="${legend}"/></th>
            <th style="width: 8%;"><fmt:message key="manageInvoices.table.col5" bundle="${legend}"/></th>
            <th style="width: 25%;"><fmt:message key="manageInvoices.table.col6" bundle="${legend}"/></th>
            <th style="width: 15%;"></th>
        </tr>
        <c:forEach items="${invoices}" var="invoice">
            <tr>
                <td class="tdc"><c:out value="${invoice.invoiceCode}"/></td>
                <td class="tdc"><fmt:formatDate type="both" value="${invoice.date}" /></td>
                <td class="tdc"><c:out value="${invoice.userName}"/></td>
                <td class="tdc">
                    <form>
                        <c:if test="${invoice.paid}"><input type="checkbox" checked disabled/></c:if>
                        <c:if test="${!invoice.paid}"><input type="checkbox" unchecked disabled/></c:if>
                    </form>
                </td>
                <td class="tdc">
                    <c:if test="${invoice.status == 'CREATED'}">
                        <b><fmt:message key="manageInvoices.created" bundle="${legend}"/></b>
                    </c:if>
                    <c:if test="${invoice.status == 'FINISHED'}">
                        <b><fmt:message key="manageInvoices.finished" bundle="${legend}"/></b>
                    </c:if>
                    <c:if test="${invoice.status == 'CANCELLED'}">
                        <b><fmt:message key="manageInvoices.cancelled" bundle="${legend}"/></b>
                    </c:if>
                </td>
                <td class="tdl"><c:out value="${invoice.invoiceNotes}"/></td>
                <td class="tdc">
                    <form>
                        <form name="details" method="get" action="project" >
                            <input type="hidden" name="command" value="showInvoiceDetails" />
                            <input type="hidden" name="code" value="${invoice.invoiceCode}" />
                            <button type="submit" class="smallbutton">
                                <fmt:message key="manageInvoices.detailed" bundle="${buttons}"/>
                            </button>
                        </form>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<t:colontitle/>
</body>
</html>