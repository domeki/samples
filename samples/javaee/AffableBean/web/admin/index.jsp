<%--
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html

 * author: tgiunipero
--%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/affablebean.css">
        <link rel="shortcut icon" href="../img/favicon.ico">

        <script src="../js/jquery-1.3.2.js" type="text/javascript"></script>
        <script src="../js/jquery.corners.js" type="text/javascript"></script>

        <script type="text/javascript">
            $(document).ready(function(){
                $('.rounded').corners();
            });
        </script>

        <title>The Affable Bean :: Admin Console</title>
    </head>

    <body>
        <div id="main" class="login">
            <div id="header">
                <div id="widgetBar"></div>

                <a href="<c:url value='../index.jsp'/>">
                    <img src="../img/logo.png" alt="Affable Bean logo" id="adminLogo">
                </a>

                <img src="../img/logoText.png" id="logoText" alt="the affable bean">
            </div>

            <h2>admin console</h2>

            <div id="loginBox" class="rounded">

                <p>[ TODO ]</p>

            </div>
        </div>
    </body>
</html>
