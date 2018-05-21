<%-- 
    Document   : confirm
    Created on : 2018. 5. 21, 오후 4:20:24
    Author     : b005
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Confirm</title>
        </head>
        <body>
            <c:if test='${ok != null}'>
                <c:out value='${ok}' />
            </c:if>
            <c:if test='${user != null}'>
                <c:out value='${user.username}' />
            </c:if>
        </body>
    </html>

