<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="menu" var="menu"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="page" />
<c:set value="${pageScope.get(\"pageNum\")}" var="pageNum" scope="page" />

<html>
<head>
    <title><fmt:message key="manageProducts.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1><fmt:message key="manageProducts.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
    <form name="addProductForm" method="post" action="project" class="menuitem">
        <input type="hidden" name="command" value="administration" />
        <button type="submit" class="menubutton">
            <fmt:message key="manageProducts.back" bundle="${menu}"/>
        </button>
    </form>
</div>
<div>
    <table class="widetable">
        <tr>
            <th style="width: 4%;"><fmt:message key="manageProducts.table.col1" bundle="${legend}"/></th>
            <th style="width: 12%;"><fmt:message key="manageProducts.table.col2" bundle="${legend}"/></th>
            <th style="width: 12%;"><fmt:message key="manageProducts.table.col3" bundle="${legend}"/></th>
            <th style="width: 12%;"><fmt:message key="manageProducts.table.col4" bundle="${legend}"/></th>
            <th style="width: 12%;"><fmt:message key="manageProducts.table.col5" bundle="${legend}"/></th>
            <th style="width: 3%;"><fmt:message key="manageProducts.table.col6" bundle="${legend}"/></th>
            <th style="width: 3%;"><fmt:message key="manageProducts.table.col7" bundle="${legend}"/></th>
            <th style="width: 3%;"><fmt:message key="manageProducts.table.col8" bundle="${legend}"/></th>
            <th style="width: 3%;"><fmt:message key="manageProducts.table.col9" bundle="${legend}"/></th>
            <th style="width: 12%;"><fmt:message key="manageProducts.table.col10" bundle="${legend}"/></th>
            <th style="width: 12%;"><fmt:message key="manageProducts.table.col11" bundle="${legend}"/></th>
            <th style="width: 12%;"></th>
        </tr>
        <c:forEach items="${products}" var="product">
            <tr>
                <td class="tdc"><c:out value="${product.code}"/></td>
                <td class="tdl"><c:out value="${product.nameRu}"/></td>
                <td class="tdl"><c:out value="${product.nameEn}"/></td>
                <td class="tdl"><c:out value="${product.descriptionRu}"/></td>
                <td class="tdl"><c:out value="${product.descriptionEn}"/></td>
                <td class="tdl">
                    <fmt:formatNumber value="${product.cost}" maxFractionDigits="2" minFractionDigits="2"/>
                    <fmt:message key="currency" bundle="${legend}"/>
                </td>
                <c:if test="${product.available == true}">
                    <td class="tdc" style="color: green">
                        <fmt:message key="manageProducts.inStock" bundle="${legend}"/>
                    </td>
                </c:if>
                <c:if test="${product.available == false}">
                    <td class="tdc" style="color: darkred">
                        <fmt:message key="manageProducts.outOfStock" bundle="${legend}"/>
                    </td>
                </c:if>
                <td class="tdc"><c:out value="${product.quantity}"/></td>
                <td class="tdc"><c:out value="${product.reservedQuantity}"/></td>
                <td class="tdc"><c:out value="${product.notesRu}"/></td>
                <td class="tdc"><c:out value="${product.notesEn}"/></td>
                <td class="tdc">
                    <form name="productUpd" method="post" action="project" >
                        <input type="hidden" name="command" value="editProductPage" />
                        <input type="hidden" name="productCode" value="${product.code}" />
                        <button type="submit" class="smallbutton">
                            <fmt:message key="manageProducts.edit" bundle="${buttons}"/>
                        </button>
                    </form>
                    <form name="productDel" method="post" action="project" >
                        <input type="hidden" name="command" value="deleteProduct" />
                        <input type="hidden" name="productCode" value="${product.code}" />
                        <button type="submit" class="smallbutton">
                            <fmt:message key="manageProducts.remove" bundle="${buttons}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div class="pagination">
        <c:forEach var = "i" begin="1" end = "${totalPages}">
            <c:if test="${i==pageNum}">
                <a class="active" href="/project?command=manageProducts&pageNum=${i}">${i}</a>
            </c:if>
            <c:if test="${i!=pageNum}">
                <a href="/project?command=manageProducts&pageNum=${i}">${i}</a>
            </c:if>
        </c:forEach>
    </div>
</div>
<div class="button_div">
    <form action="project" method="post">
        <input type="hidden" name="command" value="addNewProductPage" />
        <button type="submit" class="bigbutton">
            <fmt:message key="manageProducts.addNew" bundle="${buttons}"/>
        </button>
    </form>
</div>
<t:colontitle/>
</body>
</html>