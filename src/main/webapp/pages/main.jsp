<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set value="${sessionScope.get(\"cart\")}" var="userCart" scope="page" />
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="page" />
<c:set value="${pageScope.get(\"pageNum\")}" var="pageNum" scope="page" />
<c:set value="${sessionScope.get(\"locale\")}" var="locale" scope="page" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename = "legend" var = "legend"/>
<fmt:setBundle basename = "menu" var = "menu"/>
<fmt:setBundle basename = "buttons" var = "buttons"/>

<html>
<head>
    <title><fmt:message key = "main.title" bundle = "${legend}"/></title>
</head>
<style>
    <%@include file='main_style.css' %>
</style>

<body>
    <h1><fmt:message key = "main.h1" bundle = "${legend}"/></h1>
    <div class="langmenu">
        <c:out value="${user.name}, ${user.userRole}"/>
        <c:if test="${locale.language == 'ru'}">
            <a class="active" href="/project?command=changeLang&lang=ru">Ru</a>
            <a href="/project?command=changeLang&lang=en">En</a>
        </c:if>
        <c:if test="${locale.language == 'en'}">
            <a href="/project?command=changeLang&lang=ru">Ru</a>
            <a class="active" href="/project?command=changeLang&lang=en">En</a>
        </c:if>
    </div>
    <div class="widemenu">
        <c:if test="${user.name == 'Guest'}">
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="addNewUser" />
                <button type="submit" class="menubutton"><fmt:message key = "main.register" bundle = "${menu}"/></button>
            </form>
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="enter" />
                <button type="submit" class="menubutton"><fmt:message key = "main.login" bundle = "${menu}"/></button>
            </form>
            <c:if test="${userCart.size > 0}">
                <form name="addProductForm" method="get" action="project" class="menuitem">
                    <input type="hidden" name="command" value="usersCart" />
                    <button type="submit" class="menubutton">
                        <fmt:message key = "main.usersCart" bundle = "${menu}"/><b>${userCart.size}</b>
                    </button>
                </form>
            </c:if>
        </c:if>
        <c:if test="${user.name != 'Guest'}">
            <c:if test="${user.name != 'Guest' && user.userRole != 'USER'}">
                <form name="addProductForm" method="get" action="project" class="menuitem">
                    <input type="hidden" name="command" value="administration" />
                    <button type="submit" class="menubutton">
                        <fmt:message key = "main.administration" bundle = "${menu}"/>
                    </button>
                </form>
            </c:if>
            <c:if test="${userCart.size > 0}">
                <form name="addProductForm" method="get" action="project" class="menuitem">
                    <input type="hidden" name="command" value="usersCart" />
                    <button type="submit" class="menubutton">
                        <fmt:message key = "main.usersCart" bundle = "${menu}"/><b>${userCart.size}</b>
                    </button>
                </form>
            </c:if>
            <form name="addProductForm" method="get" action="project" class="menuitem">
                <input type="hidden" name="command" value="showUserProfile" />
                <input type="hidden" name="edit" value="false" />
                <button type="submit" class="menubutton">
                    <fmt:message key = "main.profile" bundle = "${menu}"/>
                </button>
            </form>
            <form name="addProductForm" method="post" action="project" class="menuitem">
                <input type="hidden" name="command" value="logout" />
                <button type="submit" class="menubutton">
                    <fmt:message key = "main.logout" bundle = "${menu}"/>
                </button>
            </form>
        </c:if>
    </div>
    <div>
        <table class="widetable">
            <tr>
                <th style="width: 5%;"><fmt:message key = "main.table.col1" bundle = "${legend}"/></th>
                <th style="width: 20%;"><fmt:message key = "main.table.col2" bundle = "${legend}"/></th>
                <th style="width: 20%;"><fmt:message key = "main.table.col3" bundle = "${legend}"/></th>
                <th style="width: 5%;"><fmt:message key = "main.table.col4" bundle = "${legend}"/></th>
                <th style="width: 5%;"><fmt:message key = "main.table.col5" bundle = "${legend}"/></th>
                <th style="width: 10%;"><fmt:message key = "main.table.col6" bundle = "${legend}"/></th>
                <th></th>
            </tr>
            <c:forEach items="${products}" var="product">
            <tr>
                <td class="tdc"><c:out value="${product.code}"/></td>
                <td class="tdl"><c:out value="${product.nameRu}"/></td>
                <td class="tdl"><c:out value="${product.descriptionRu}"/></td>
                <td class="tdl">
                    <fmt:formatNumber value="${product.cost}" maxFractionDigits="2" minFractionDigits="2"/>
                </td>
                <c:if test="${product.available == true}">
                    <td class="tdc" style="color: green"><fmt:message key = "main.inStock" bundle = "${legend}"/></td>
                </c:if>
                <c:if test="${product.available == false}">
                    <td class="tdc" style="color: darkred"><fmt:message key = "main.outOfStock" bundle = "${legend}"/></td>
                </c:if>
                <td class="tdc"><c:out value="${product.notesRu}"/></td>
                <td class="tdc">
                    <form name="addProductForm" method="post" action="project" >
                        <input type="hidden" name="command" value="addProductToCart" />
                        <input type="text" name="productQuantity" size="6" required/>
                        <button type="submit" name="productCode" value="${product.code}" class="smallbutton"><fmt:message key = "main.addToCart" bundle = "${buttons}"/></button>
                    </form>
                </td>
            </tr>
            </c:forEach>
        </table>
        <div class="pagination">
            <c:forEach var = "i" begin="1" end = "${totalPages}">
                <c:if test="${i==pageNum}">
                    <a class="active" href="/project?command=main&pageNum=${i}">${i}</a>
                </c:if>
                <c:if test="${i!=pageNum}">
                    <a href="/project?command=main&pageNum=${i}">${i}</a>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <footer>
        <p class="footer">Учебный проект по курсу Java Winter, Киев, 2018</p>
    </footer>
</body>
</html>