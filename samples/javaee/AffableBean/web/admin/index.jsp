<%--
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html

 * author: tgiunipero
--%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

                $("tr.row").hover(
                    function () {
                        $(this).addClass("hover");
                    },
                    function () {
                        $(this).removeClass("hover");
                    }
                );
            });
        </script>

        <title>The Affable Bean :: Admin Console</title>
    </head>

    <body>
        <div id="main">
            <div id="header">
                <div id="widgetBar"></div>

                <a href="<c:url value='../index.jsp'/>">
                    <img src="../img/logo.png" alt="Affable Bean logo" id="adminLogo">
                </a>

                <img src="../img/logoText.png" id="logoText" alt="the affable bean">
            </div>

            <h2>admin console</h2>

            <div id="adminMenu" class="rounded alignLeft">
                <p><a href="<c:url value='viewCustomers'/>">view all customers</a></p>

                <p><a href="<c:url value='viewOrders'/>">view all orders</a></p>

                <p><a href="<c:url value='logout'/>">log out</a></p>
            </div>

            <c:if test="${!empty requestScope.orderList}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="5">orders</th>
                    </tr>

                    <tr class="tableHeading">
                        <td>order id</td>
                        <td>confirmation number</td>
                        <td>amount</td>
                        <td>date created</td>
                    </tr>

                    <c:forEach var="order" items="${requestScope.orderList}" varStatus="iter">

                        <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} row">

                            <td>${order.id}</td>

                            <td>${order.confirmationNumber}</td>

                            <td><fmt:formatNumber type="currency"
                                                  currencySymbol="&euro; "
                                                  value="${order.amount}"/></td>

                            <td><fmt:formatDate value="${order.dateCreated}"
                                                type="both"
                                                dateStyle="short"
                                                timeStyle="short"/></td>
                        </tr>

                    </c:forEach>

                </table>

            </c:if>

            <c:if test="${!empty requestScope.customerList}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="7">customers</th>
                    </tr>

                    <tr class="tableHeading">
                        <td>customer id</td>
                        <td>name</td>
                        <td>email</td>
                        <td>phone</td>
                    </tr>

                    <c:forEach var="customer" items="${requestScope.customerList}" varStatus="iter">

                        <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} row">

                            <td>${customer.id}</td>
                            <td>${customer.name}</td>
                            <td>${customer.email}</td>
                            <td>${customer.phone}</td>
                        </tr>

                    </c:forEach>

                </table>

            </c:if>

            <div id="footer"></div>
        </div>
    </body>
</html>