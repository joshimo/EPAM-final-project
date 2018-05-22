<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="menu" var="menu"/>
<fmt:setBundle basename="buttons" var="buttons"/>

<html>
<head>
    <title><fmt:message key="invoiceDetails.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1><fmt:message key="invoiceDetails.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="manageInvoices" />
        <input type="hidden" name="type" value="all" />
        <button type="submit" class="menubutton">
            <fmt:message key="invoiceDetails.back" bundle="${menu}"/>
        </button>
    </form>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">
            <fmt:message key="invoiceDetails.administration" bundle="${menu}"/>
        </button>
    </form>
</div>
<div class="inner_div">
    <h3><fmt:message key="invoiceDetails.req" bundle="${legend}"/></h3>
    <ul>
        <li>
            <fmt:message key="invoiceDetails.code" bundle="${legend}"/>
            <b><i><c:out value="${invoice.invoiceCode}"/></i></b>
        </li>
        <li>
            <fmt:message key="invoiceDetails.customer" bundle="${legend}"/>
            <b><i><c:out value="${invoice.userName}"/></i></b>
        </li>
        <li>
            <fmt:message key="invoiceDetails.date" bundle="${legend}"/>
            <b><i><fmt:formatDate type="both" value="${invoice.date}" /></i></b>
        </li>
        <li>
            <fmt:message key="invoiceDetails.total" bundle="${legend}"/>
            <b><i>
                <fmt:formatNumber value="${invoice.cost}" maxFractionDigits="2" minFractionDigits="2"/>
                <fmt:message key="currency" bundle="${legend}"/>
            </i></b>
        </li>
        <li>
            <fmt:message key="invoiceDetails.payed" bundle="${legend}"/>
            <c:if test="${invoice.paid}">
                <b><i><fmt:message key="invoiceDetails.payedYes" bundle="${legend}"/></i></b>
            </c:if>
            <c:if test="${!invoice.paid}">
                <b><i><fmt:message key="invoiceDetails.payedNo" bundle="${legend}"/></i></b>
            </c:if>
        </li>
        <li>
            <fmt:message key="invoiceDetails.status" bundle="${legend}"/>
            <c:if test="${invoice.status == 'CREATED'}">
                <b><i><fmt:message key="invoiceDetails.creared" bundle="${legend}"/></i></b>
            </c:if>
            <c:if test="${invoice.status == 'FINISHED'}">
                <b><i><fmt:message key="invoiceDetails.finished" bundle="${legend}"/></i></b>
            </c:if>
            <c:if test="${invoice.status == 'CANCELLED'}">
                <b><i><fmt:message key="invoiceDetails.cancelled" bundle="${legend}"/></i></b>
            </c:if>
        </li>
        <li>
            <fmt:message key="invoiceDetails.notes" bundle="${legend}"/>
            <b><i><c:out value="${invoice.invoiceNotes}"/></i></b>
        </li>
    </ul>
    <h3><fmt:message key="invoiceDetails.details" bundle="${legend}"/></h3>
    <table class="widetable">
        <tr>
            <th class="tdc"><fmt:message key="invoiceDetails.table.col1" bundle="${legend}"/></th>
            <th class="tdc"><fmt:message key="invoiceDetails.table.col2" bundle="${legend}"/></th>
            <th class="tdc"><fmt:message key="invoiceDetails.table.col3" bundle="${legend}"/></th>
            <th class="tdc"><fmt:message key="invoiceDetails.table.col4" bundle="${legend}"/></th>
            <th></th>
        </tr>
        <c:forEach items="${invoice.payments}" var="payment">
            <tr>
                <td class="tdl">${payment.key}</td>
                <td class="tdl">${payment.value.quantity}</td>
                <td class="tdc">
                    <fmt:formatNumber value="${payment.value.paymentValue}" maxFractionDigits="2" minFractionDigits="2"/>
                    <fmt:message key="currency" bundle="${legend}"/>
                </td>
                <td class="tdl">${payment.value.paymentNotes}</td>
                <td class="tdc">
                    <form name="remove" method="post" action="project" >
                        <input type="hidden" name="command" value="removeProductFromInvoice" />
                        <input type="hidden" name="invCode" value="${payment.value.orderCode}" />
                        <input type="hidden" name="productCode" value="${payment.key}" />
                        <c:if test="${!invoice.paid && invoice.status == 'CREATED'}">
                            <button type="submit" class="smallbutton">
                                <fmt:message key="invoiceDetails.remove" bundle="${buttons}"/>
                            </button>
                        </c:if>
                        <c:if test="${invoice.paid || invoice.status != 'CREATED'}">
                            <button type="submit" class="smallbutton" disabled>
                                <fmt:message key="invoiceDetails.remove" bundle="${buttons}"/>
                            </button>
                        </c:if>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${!invoice.paid && invoice.status == 'CREATED'}">
        <form name="remove" method="post" action="project" >
            <tr>
                <td class="tdl">
                    <select name="productCode" required>
                        <c:forEach items="${products}" var="product">
                            <option><c:out value="${product}"/></option>
                        </c:forEach>
                    </select>
                </td>
                <td class="tdl"><input class="input" type="text" name="quantity" size="8" required /></td>
                <td class="tdc"></td>
                <td class="tdl"><input class="input" type="text" name="paymentNotes" size="24" /></td>
                <td class="tdc">
                    <input type="hidden" name="command" value="addNewPayment" />
                    <input type="hidden" name="orderCode" value="${invoice.invoiceCode}" />
                    <button type="submit" class="smallbutton">
                        <fmt:message key="invoiceDetails.add" bundle="${buttons}"/>
                    </button>
                </td>
            </tr>
        </form>
        </c:if>
    </table>
    <div class="button_div">
        <form action="project" method="post">
            <input type="hidden" name="command" value="cancelInvoice" />
            <input type="hidden" name="invCode" value="${invoice.invoiceCode}" />
            <c:if test="${invoice.status != 'CREATED'}">
                <button type="submit" class="bigbutton" disabled>
                    <fmt:message key="invoiceDetails.cancel" bundle="${buttons}"/>
                </button>
            </c:if>
            <c:if test="${invoice.status == 'CREATED'}">
                <button type="submit" class="bigbutton">
                    <fmt:message key="invoiceDetails.cancel" bundle="${buttons}"/>
                </button>
            </c:if>
        </form>
        <form action="project" method="post">
            <input type="hidden" name="command" value="confirmPayment" />
            <input type="hidden" name="invCode" value="${invoice.invoiceCode}" />
            <c:if test="${invoice.status != 'CREATED' || invoice.paid == true}">
                <button type="submit" class="bigbutton" disabled>
                    <fmt:message key="invoiceDetails.confirm" bundle="${buttons}"/>
                </button>
            </c:if>
            <c:if test="${invoice.status == 'CREATED' && invoice.paid == false}">
                <button type="submit" class="bigbutton">
                    <fmt:message key="invoiceDetails.confirm" bundle="${buttons}"/>
                </button>
            </c:if>
        </form>
        <form action="project" method="post">
            <input type="hidden" name="command" value="closeInvoice" />
            <input type="hidden" name="invCode" value="${invoice.invoiceCode}" />
            <c:if test="${invoice.paid && invoice.status == 'CREATED'}">
                <button type="submit" class="bigbutton">
                    <fmt:message key="invoiceDetails.close" bundle="${buttons}"/>
                </button>
            </c:if>
            <c:if test="${!(invoice.paid && invoice.status == 'CREATED')}">
                <button type="submit" class="bigbutton" disabled>
                    <fmt:message key="invoiceDetails.close" bundle="${buttons}"/>
                </button>
            </c:if>
        </form>
    </div>
</div>
<t:colontitle/>
</body>
</html>