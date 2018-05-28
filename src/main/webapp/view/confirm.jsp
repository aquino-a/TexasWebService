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

            <c:if test='${ok != null}'>
                <div class="main-message">
                    Testing 1,2,3..<br/>
                </div>
                <c:out value='${ok}' /> Hey lalal
            </c:if>
            <c:if test='${verified == true}'>
                <div class="main-message">
                    Account has already been verified.<br/>
                </div>
            </c:if>
            <c:if test='${expired != null}'>
                <div class="main-message">
                    Token expired on <c:out value='${expired}' />.<br/>
                </div>
                You must register again to get a new token.
            </c:if>
            <c:if test='${user != null}'>
                <div class="main-message">
                    Confirmation successful!<br/>
                </div>
                Thank you for joining <c:out value='${user.username}' />!
                You may now login.
            </c:if>

        </div>

    </body>
</html>

