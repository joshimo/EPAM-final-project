<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<html>
<head>
    <title><fmt:message key="editProduct.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1><fmt:message key="editProduct.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
</div>
<div class = "inner_div">
    <form name="productForm" method="post" action="project" >
        <input type="hidden" name="command" value="updateProduct" />
        <input type="hidden" name="id" value="${product.id}" />
        <input type="hidden" name="reserved" value="${product.reservedQuantity}" />
        <h4><fmt:message key="editProduct.code" bundle="${legend}"/></h4>
        <input type="text" name="code" size="24" class="input" value="${product.code}" required/><br/>
        <h4><fmt:message key="editProduct.nameRu" bundle="${legend}"/></h4>
        <textarea rows="3" cols="48" name="nameRu" required>${product.nameRu}</textarea><br/>
        <h4><fmt:message key="editProduct.nameEn" bundle="${legend}"/></h4>
        <textarea rows="3" cols="48" name="nameEn" required>${product.nameEn}</textarea><br/>
        <h4><fmt:message key="editProduct.descriptionRu" bundle="${legend}"/></h4>
        <textarea rows="3" cols="48" name="descriptionRu" required>${product.descriptionRu}</textarea><br/>
        <h4><fmt:message key="editProduct.descriptionEn" bundle="${legend}"/></h4>
        <textarea rows="3" cols="48" name="descriptionEn" required>${product.descriptionEn}</textarea><br/>
        <h4><fmt:message key="editProduct.cost" bundle="${legend}"/></h4>
        <input type="text" name="cost" size="24" class="input" value="${product.cost}" required/><br/><br/>
        <c:if test="${product.available}">
            <input type="checkbox" name="isAvailable" checked/>
                <b><fmt:message key="editProduct.inStock" bundle="${legend}"/></b>
            <br/>
        </c:if>
        <c:if test="${!product.available}">
            <input type="checkbox" name="isAvailable" />
                <b><fmt:message key="editProduct.inStock" bundle="${legend}"/></b>
            <br/>
        </c:if>
        <h4><fmt:message key="editProduct.quantity" bundle="${legend}"/></h4>
        <input type="text" name="quantity" size="24" class="input" value="${product.quantity}"/><br/>
        <h4><fmt:message key="editProduct.uomRu" bundle="${legend}"/></h4>
        <input type="text" name="uomRu" size="24" class="input" required value="${product.uomRu}"/><br/>
        <h4><fmt:message key="editProduct.uomEn" bundle="${legend}"/></h4>
        <input type="text" name="uomEn" size="24" class="input" required value="${product.uomEn}"/><br/>
        <h4><fmt:message key="editProduct.notesRu" bundle="${legend}"/></h4>
        <textarea rows="3" cols="48" name="notesRu">${product.notesRu}</textarea><br/>
        <h4><fmt:message key="editProduct.notesEn" bundle="${legend}"/></h4>
        <textarea rows="3" cols="48" name="notesEn">${product.notesEn}</textarea><br/>
        <div class="button_div">
            <button class="bigbutton">
                <fmt:message key="editProduct.save" bundle="${buttons}"/>
            </button>
            <button class="bigbutton" onclick="history.back(); return false;">
                <fmt:message key="editProduct.cancel" bundle="${buttons}"/>
            </button>
        </div>
    </form>
</div>
<t:colontitle/>
</body>
</html>
