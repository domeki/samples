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

                $('tr.tableRow').hover(
                    function () {
                        $(this).addClass('selectedRow');
                    },
                    function () {
                        $(this).removeClass('selectedRow');
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

            <%-- customerList is requested --%>
            <c:if test="${!empty requestScope.customerList}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="4">customers</th>
                    </tr>

                    <tr class="tableHeading">
                        <td>customer id</td>
                        <td>name</td>
                        <td>email</td>
                        <td>phone</td>
                    </tr>

                    <c:forEach var="customer" items="${requestScope.customerList}" varStatus="iter">

                        <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                            onclick="document.location.href='customerRecord?${customer.id}'">

                          <script type="text/javascript">
                            document.write('<td>${customer.id}</td>');
                            document.write('<td>${customer.name}</td>');
                            document.write('<td>${customer.email}</td>');
                            document.write('<td>${customer.phone}</td>');
                          </script>

                          <%-- Below anchor tags are provided in the event that JavaScript is disabled --%>
                          <noscript>
                            <td><a href="customerRecord?${customer.id}" class="noDecoration">${customer.id}</a></td>
                            <td><a href="customerRecord?${customer.id}" class="noDecoration">${customer.name}</a></td>
                            <td><a href="customerRecord?${customer.id}" class="noDecoration">${customer.email}</a></td>
                            <td><a href="customerRecord?${customer.id}" class="noDecoration">${customer.phone}</a></td>
                          </noscript>
                        </tr>

                    </c:forEach>

                </table>

            </c:if>

            <%-- orderList is requested --%>
            <c:if test="${!empty requestScope.orderList}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="4">orders</th>
                    </tr>

                    <tr class="tableHeading">
                        <td>order id</td>
                        <td>confirmation number</td>
                        <td>amount</td>
                        <td>date created</td>
                    </tr>

                    <c:forEach var="order" items="${requestScope.orderList}" varStatus="iter">

                        <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                            onclick="document.location.href='orderRecord?${order.id}'">

                          <script type="text/javascript">
                            document.write('<td>${order.id}</td>');
                            document.write('<td>${order.confirmationNumber}</td>');
                            document.write('<td><fmt:formatNumber type="currency"
                                                                  currencySymbol="&euro; "
                                                                  value="${order.amount}"/></td>');

                            document.write('<td><fmt:formatDate value="${order.dateCreated}"
                                                                type="both"
                                                                dateStyle="short"
                                                                timeStyle="short"/></td>');
                          </script>

                          <%-- Below anchor tags are provided in the event that JavaScript is disabled --%>
                          <noscript>
                            <td><a href="orderRecord?${order.id}" class="noDecoration">${order.id}</a></td>

                            <td><a href="orderRecord?${order.id}" class="noDecoration">${order.confirmationNumber}</a></td>

                            <td><a href="orderRecord?${order.id}" class="noDecoration">
                                    <fmt:formatNumber type="currency"
                                                      currencySymbol="&euro; "
                                                      value="${order.amount}"/>
                                </a></td>

                            <td><a href="orderRecord?${order.id}" class="noDecoration">
                                    <fmt:formatDate value="${order.dateCreated}"
                                                type="both"
                                                dateStyle="short"
                                                timeStyle="short"/>
                                </a></td>
                          </noscript>
                        </tr>

                    </c:forEach>

                </table>

            </c:if>

            <%-- customerRecord is requested --%>
            <c:if test="${!empty requestScope.customerRecord}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="2">customer details</th>
                    </tr>
                    <tr>
                        <td style="width: 290px"><strong>customer id:</strong></td>
                        <td>${requestScope.customerRecord.id}</td>
                    </tr>
                    <tr>
                        <td><strong>name:</strong></td>
                        <td>${requestScope.customerRecord.name}</td>
                    </tr>
                    <tr>
                        <td><strong>email:</strong></td>
                        <td>${requestScope.customerRecord.email}</td>
                    </tr>
                    <tr>
                        <td><strong>phone:</strong></td>
                        <td>${requestScope.customerRecord.phone}</td>
                    </tr>
                    <tr>
                        <td><strong>address:</strong></td>
                        <td>${requestScope.customerRecord.address}</td>
                    </tr>
                    <tr>
                        <td><strong>city region:</strong></td>
                        <td>${requestScope.customerRecord.cityRegion}</td>
                    </tr>
                    <tr>
                        <td><strong>credit card number:</strong></td>
                        <td>${requestScope.customerRecord.ccNumber}</td>
                    </tr>

                    <tr><td colspan="2" style="padding: 0 20px"><hr></td></tr>

                    <tr class="tableRow"
                        onclick="document.location.href='orderRecord?${order.id}'">

                        <script type="text/javascript">
                            document.write('<td colspan="2"><strong>view order summary &#x279f;</strong></td>')
                        </script>

                        <%-- Below anchor tag is provided in the event that JavaScript is disabled --%>
                        <noscript>
                            <td colspan="2">
                                <a href="orderRecord?${order.id}" class="noDecoration">
                                    <strong>view order summary &#x279f;</strong></a></td>
                        </noscript>
                    </tr>
                </table>

            </c:if>

            <%-- orderRecord is requested --%>
            <c:if test="${!empty requestScope.orderRecord}">

                <table id="adminTable" class="detailsTable">

                    <tr class="header">
                        <th colspan="2">order summary</th>
                    </tr>
                    <tr>
                        <td><strong>order id:</strong></td>
                        <td>${requestScope.orderRecord.id}</td>
                    </tr>
                    <tr>
                        <td><strong>confirmation number:</strong></td>
                        <td>${requestScope.orderRecord.confirmationNumber}</td>
                    </tr>
                    <tr>
                        <td><strong>date processed:</strong></td>
                        <td>
                            <fmt:formatDate value="${requestScope.orderRecord.dateCreated}"
                                            type="both"
                                            dateStyle="short"
                                            timeStyle="short"/></td>
                    </tr>

                    <tr>
                        <td colspan="2">
                            <table class="embedded detailsTable">
                               <tr class="tableHeading">
                                    <td class="rigidWidth">product</td>
                                    <td class="rigidWidth">quantity</td>
                                    <td>price</td>
                                </tr>

                                <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>

                                <c:forEach var="orderedProduct" items="${requestScope.orderedProducts}" varStatus="iter">

                                    <tr>
                                        <td>
                                            <fmt:message key="${requestScope.products[iter.index].name}"/>
                                        </td>
                                        <td>
                                            ${orderedProduct.quantity}
                                        </td>
                                        <td class="confirmationPriceColumn">
                                            <fmt:formatNumber type="currency" currencySymbol="&euro; "
                                                              value="${requestScope.products[iter.index].price * orderedProduct.quantity}"/>
                                        </td>
                                    </tr>

                                </c:forEach>

                                <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>

                                <tr>
                                    <td colspan="2" id="deliverySurchargeCellLeft"><strong>delivery surcharge:</strong></td>
                                    <td id="deliverySurchargeCellRight">
                                        <fmt:formatNumber type="currency"
                                                          currencySymbol="&euro; "
                                                          value="${initParam.deliverySurcharge}"/></td>
                                </tr>

                                <tr>
                                    <td colspan="2" id="totalCellLeft"><strong>total amount:</strong></td>
                                    <td id="totalCellRight">
                                        <fmt:formatNumber type="currency"
                                                          currencySymbol="&euro; "
                                                          value="${requestScope.orderRecord.amount}"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr><td colspan="3" style="padding: 0 20px"><hr></td></tr>

                    <tr class="tableRow"
                        onclick="document.location.href='customerRecord?${customer.id}'">

                        <script type="text/javascript">
                            document.write('<td colspan="2"><strong>view customer details &#x279f;</strong></td>')
                        </script>

                        <%-- Below anchor tag is provided in the event that JavaScript is disabled --%>
                        <noscript>
                            <td colspan="2">
                                <a href="customerRecord?${customer.id}" class="noDecoration">
                                    <strong>view customer details &#x279f;</strong></a></td>
                        </noscript>
                    </tr>
                </table>

            </c:if>

            <div id="footer"></div>
        </div>
    </body>
</html>