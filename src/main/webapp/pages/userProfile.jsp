<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/bodytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.get(\"locale\")}" />
<fmt:setBundle basename="legend" var="legend"/>
<fmt:setBundle basename="buttons" var="buttons"/>

<html>
<head>
    <title><fmt:message key="userProfile.title" bundle="${legend}"/></title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1><fmt:message key="userProfile.h1" bundle="${legend}"/></h1>
<div class = index_div>
    <c:if test="${param.edit == false}">
    <form name="LoginForm" method="get" action="project" >
        <input type="hidden" name="command" value="showUserProfile" />
        <input type="hidden" name="edit" value="true" />
        <div class="inner_div">
            <p><fmt:message key="userProfile.name" bundle="${legend}"/><br/>
                <b><i><c:out value="${sessionScope.get(\"user\").name}"/></i></b>
            </p>
            <p><fmt:message key="userProfile.email" bundle="${legend}"/><br/>
                <b><i><c:out value="${sessionScope.get(\"user\").email}"/></i></b>
            </p>
            <p><fmt:message key="userProfile.tel" bundle="${legend}"/><br/>
                <b><i><c:out value="${sessionScope.get(\"user\").phoneNumber}"/></i></b>
            </p>
            <p><fmt:message key="userProfile.address" bundle="${legend}"/><br/>
                <b><i><c:out value="${sessionScope.get(\"user\").address}"/></i></b>
            </p>
            <p><fmt:message key="userProfile.notes" bundle="${legend}"/><br/>
                <b><i><c:out value="${sessionScope.get(\"user\").notes}"/></i></b>
            </p>
        </div>
        <button class="bigbutton"><fmt:message key="userProfile.edit" bundle="${buttons}"/></button>
        <button class="bigbutton" onclick="history.back(); return false;"><fmt:message key="userProfile.back" bundle="${buttons}"/></button>
    </form>
    </c:if>
    <c:if test="${param.edit == true}">
    <form name="LoginForm" method="post" action="project" >
        <input type="hidden" name="command" value="saveUserProfile" />
        <input type="hidden" name="userId" value="<c:out value="${sessionScope.get(\"user\").id}"/>" />
        <h4><fmt:message key="userProfile.name" bundle="${legend}"/></h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").name}"/>" name="name" size="36" class="input" required/><br/>
        <h4><fmt:message key="userProfile.password" bundle="${legend}"/></h4>
        <input type="password" value="<c:out value="${sessionScope.get(\"user\").password}"/>" name="password" size="36" class="input" required/><br/><br/>
        <h4><fmt:message key="userProfile.tel" bundle="${legend}"/></h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").phoneNumber}"/>" name="phone" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="userProfile.email" bundle="${legend}"/></h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").email}"/>" name="email" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="userProfile.address" bundle="${legend}"/></h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").address}"/>" name="address" size="36" class="input" /><br/><br/>
        <h4><fmt:message key="userProfile.notes" bundle="${legend}"/></h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").notes}"/>" name="notes" size="36" class="input" /><br/><br/>
        <button class="bigbutton"><fmt:message key="userProfile.save" bundle="${buttons}"/></button>
        <button class="bigbutton" onclick="history.back(); return false;"><fmt:message key="userProfile.back" bundle="${buttons}"/></button>
    </form>
    </c:if>
</div>
<t:colontitle/>
</body>
</html>