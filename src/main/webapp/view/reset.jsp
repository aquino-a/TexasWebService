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
        <style>
            #container {
                border: 1px solid black;
                width:80%;
                height:100px;
                text-align: center;
                margin:auto;
                padding:20px;
                line-height: 30px;
            }
            .main-message {
                font-size: x-large;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div id="container">
            <c:if test="${matching != null}">
                <div class="main-message">
                    Passwords did not match.<br/>
                </div>
                Please reset password again.
            </c:if>
            <c:if test="${already_reset != null}">
                <div class="main-message">
                    This token has already been used.<br/>
                </div>
                Please reset password again.
            </c:if>
            <c:if test="${just_reset != null}">
                <div class="main-message">
                    Your password has successfully been changed.<br/>
                </div>
                You can login with your new password.
            </c:if>
            <c:if test="${expired != null}">
                <div class="main-message">
                    This reset token expired on <c:out value="${expired}" /><br/>
                </div>
                Please reset your password again to get a valid link.
            </c:if>
            <c:if test="${token != null}">
                <form method="post" action="/reset/<c:out value='${token}' />" >
                    <label for="password">New password:</label>
                    <input id="password" type="password" name="password" />
                    <br/>

                    <label for="rePassword">Re-type password:</label>
                    <input id="rePassword" type="password" name="password" />
                    <br/>
                    <input type="submit" value="Submit" />
                </form>
            </c:if>
        </div>

    </body>
</html>
