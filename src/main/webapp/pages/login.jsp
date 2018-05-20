<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="menu" var="menu"/>
<fmt:setBundle basename="buttons" var="buttons"/>

<html>
<head>
    <title><fmt:message key="login.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
    <h1><fmt:message key="login.h1" bundle="${legend}"/></h1>
    <div class = index_div>
        <form name="LoginForm" method="post" action="project" >
            <input type="hidden" name="command" value="login" />
            <h4><fmt:message key="login.userName" bundle="${legend}"/></h4>
            <input type="text" name="name" size="36" class="input" required/><br/>
            <h4><fmt:message key="login.password" bundle="${legend}"/></h4>
            <input type="password" name="password" size="36" class="input" required/><br/><br/><br/>
            <input type="submit" class="bigbutton" value="<fmt:message key="login.login" bundle="${buttons}"/>"/>
            <input type="submit" class="bigbutton" value="<fmt:message key="login.cancel" bundle="${buttons}"/>" onclick="history.back(); return false;"/>
        </form>
    </div>
    <footer>
        <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
    </footer>
</body>
</html>