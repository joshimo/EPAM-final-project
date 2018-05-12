<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Новый пользователь</title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>
<body>
<h1>Профиль пользователя</h1>
<div class = index_div>
    <c:if test="${param.edit == false}">
    <form name="LoginForm" method="get" action="project" >
        <input type="hidden" name="command" value="showUserProfile" />
        <input type="hidden" name="edit" value="true" />
        <div class="inner_div">
            <p>Имя пользователя:<br/>
                <b><i><c:out value="${sessionScope.get(\"user\").name}"/></i></b>
            </p>
            <p>e-mail:<br/>
                <b><i><c:out value="${sessionScope.get(\"user\").email}"/></i></b>
            </p>
            <p>Телефон:<br/>
                <b><i><c:out value="${sessionScope.get(\"user\").phoneNumber}"/></i></b>
            </p>
            <p>Адрес:<br/>
                <b><i><c:out value="${sessionScope.get(\"user\").address}"/></i></b>
            </p>
            <p>Примечания:<br/>
                <b><i><c:out value="${sessionScope.get(\"user\").notes}"/></i></b>
            </p>
        </div>
        <input type="submit" class="bigbutton" value="Редактировать"/>
        <input type="submit" class="bigbutton" value="Назад" onclick="history.back(); return false;"/>
    </form>
    </c:if>
    <c:if test="${param.edit == true}">
    <form name="LoginForm" method="post" action="project" >
        <input type="hidden" name="command" value="saveUserProfile" />
        <input type="hidden" name="userId" value="<c:out value="${sessionScope.get(\"user\").id}"/>" />
        <h4>Имя пользователя:</h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").name}"/>" name="name" size="36" class="input" required/><br/>
        <h4>Пароль:</h4>
        <input type="password" value="<c:out value="${sessionScope.get(\"user\").password}"/>" name="password" size="36" class="input" required/><br/><br/>
        <h4>Телефон:</h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").phoneNumber}"/>" name="phone" size="36" class="input" /><br/><br/>
        <h4>e-mail:</h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").email}"/>" name="email" size="36" class="input" /><br/><br/>
        <h4>Адрес:</h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").address}"/>" name="address" size="36" class="input" /><br/><br/>
        <h4>Примечания:</h4>
        <input type="text" value="<c:out value="${sessionScope.get(\"user\").notes}"/>" name="notes" size="36" class="input" /><br/><br/>
        <input type="submit" class="bigbutton" value="Сохранить"/>
        <input type="submit" class="bigbutton" value="Отмена" onclick="history.back(); return false;"/>
    </form>
    </c:if>
</div>
</body>
</html>