<%-- 
    Document   : reset
    Created on : 2018. 5. 29, 오전 11:36:11
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Reset Password</title>
    </head>
    <body>
        <c:if test="${token != null}">
            <form method="post" action="/reset/<c:out value='${token}' />" >
                <label for="password">New password:</label>
                <input id="password" type="password" name="password" />
                <br/>

                <label for="rePassword">Re-type password:</label>
                <input id="rePassword" type="password" name="password" />
                <input type="submit" value="Submit" />
            <form>
        </c:if>
    </body>
</html>
