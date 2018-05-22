<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<html>
<head>
    <title><fmt:message key="newProduct.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='admin_style.css' %>
</style>
<body>
<h1><fmt:message key="newProduct.h1" bundle="${legend}"/></h1>
<div class="widemenu">
    <p><c:out value="${user.name}, ${user.userRole}"/></p>
</div>
<div class = "inner_div">
    <form name="newProductForm" method="post" action="project" >
        <input type="hidden" name="command" value="saveNewProduct" />
        <h4><fmt:message key="newProduct.code" bundle="${legend}"/></h4>
        <input class="input" type="text" name="code" size="24" required/><br/>
        <h4><fmt:message key="newProduct.nameRu" bundle="${legend}"/></h4>
        <textarea class="multitext" rows="3" cols="48" name="nameRu" required></textarea><br/>
        <h4><fmt:message key="newProduct.nameEn" bundle="${legend}"/></h4>
        <textarea class="multitext" rows="3" cols="48" name="nameEn" required></textarea><br/>
        <h4><fmt:message key="newProduct.descriptionRu" bundle="${legend}"/></h4>
        <textarea class="multitext" rows="3" cols="48" name="descriptionRu" required></textarea><br/>
        <h4><fmt:message key="newProduct.descriptionEn" bundle="${legend}"/></h4>
        <textarea class="multitext" rows="3" cols="48" name="descriptionEn" required></textarea><br/>
        <h4><fmt:message key="newProduct.cost" bundle="${legend}"/></h4>
        <input class="input" type="text" name="cost" size="24" required/><br/><br/>
        <input type="checkbox" name="isAvailable">
            <b><fmt:message key="newProduct.inStock" bundle="${legend}"/></b>
        <br/>
        <h4><fmt:message key="newProduct.quantity" bundle="${legend}"/></h4>
        <input class="input" type="text" name="quantity" size="24" /><br/>
        <h4><fmt:message key="newProduct.uomRu" bundle="${legend}"/></h4>
        <input class="input" type="text" name="uomRu" size="24" required/><br/>
        <h4><fmt:message key="newProduct.uomEn" bundle="${legend}"/></h4>
        <input class="input" type="text" name="uomEn" size="24" required/><br/>
        <h4><fmt:message key="newProduct.notesRu" bundle="${legend}"/></h4>
        <textarea class="multitext" rows="3" cols="48" name="notesRu"></textarea><br/>
        <h4><fmt:message key="newProduct.notesEn" bundle="${legend}"/></h4>
        <textarea class="multitext" rows="3" cols="48" name="notesEn"></textarea><br/>
        <div class="button_div">
            <button class="bigbutton">
                <fmt:message key="newProduct.add" bundle="${buttons}"/>
            </button>
            <button class="bigbutton" onclick="history.back(); return false;">
                <fmt:message key="newProduct.cancel" bundle="${buttons}"/>
            </button>
        </div>
    </form>
</div>
<t:colontitle/>
</body>
</html>