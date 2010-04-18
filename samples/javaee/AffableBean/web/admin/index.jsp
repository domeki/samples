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
                <p style="margin-left: 15px"><a href="viewOrders">view all orders</a></p>

                <p style="margin-left: 15px"><a href="viewCustomers">view all customers</a></p>
            </div>

            <c:if test="${!empty requestScope.orderList}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="4">customer orders</th>
                    </tr>

                    <tr>
                        <td class="tableHeading">order id</td>
                        <td class="tableHeading">confirmation number</td>
                        <td class="tableHeading">amount</td>
                        <td class="tableHeading">date created</td>
                    </tr>

                    <c:forEach var="order" items="${requestScope.orderList}" varStatus="iter">

                        <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'}">

                            <td>${order.id}</td>
                            <td>${order.confirmationNumber}</td>
                            <td>${order.amount}</td>
                            <td><fmt:formatDate value="${order.dateCreated}"
                                type="both" dateStyle="short" timeStyle="short"/></td>
                        </tr>

                    </c:forEach>

                </table>

            </c:if>

            <c:if test="${!empty requestScope.customerList}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="7">customers</th>
                    </tr>

                    <tr>
                        <td class="tableHeading">customer id</td>
                        <td class="tableHeading">name</td>
                        <td class="tableHeading">email</td>
                        <td class="tableHeading">phone</td>
                    </tr>

                    <c:forEach var="customer" items="${requestScope.customerList}" varStatus="iter">

                        <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'}">

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