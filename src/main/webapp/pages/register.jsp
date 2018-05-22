<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="buttons" var="buttons"/>

<html>
<head>
    <title><fmt:message key="register.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>

<body>
<h1><fmt:message key="register.h1" bundle="${legend}"/></h1>
<div class = index_div>
    <form name="LoginForm" method="post" action="project" >
        <input type="hidden" name="command" value="registerNewUser" />
        <h4><fmt:message key="register.name" bundle="${legend}"/></h4>
        <input type="text" name="name" size="36" class="input" required/><br/>
        <h4><fmt:message key="register.password" bundle="${legend}"/></h4>
        <input type="password" name="password" size="36" class="input" required/><br/><br/>
        <h4><fmt:message key="register.tel" bundle="${legend}"/></h4>
        <input type="text" name="phone" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="register.email" bundle="${legend}"/></h4>
        <input type="text" name="email" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="register.address" bundle="${legend}"/></h4>
        <input type="text" name="address" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="register.notes" bundle="${legend}"/></h4>
        <input type="text" name="notes" size="36" class="input" /><br/><br/>
        <button class="bigbutton"><fmt:message key="register.register" bundle="${buttons}"/></button>
        <button class="bigbutton" onclick="history.back(); return false;"><fmt:message key="register.cancel" bundle="${buttons}"/></button>
    </form>
</div>
<footer>
    <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
</footer>
</body>
</html>
