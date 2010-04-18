<%--
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html

 * author: tgiunipero
--%>


<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Controller so that
     users view the same page when switching between EN and CS. --%>
<c:set var="view" value="/confirmation" scope="session"/>

<div id="singleColumn">

    <p id="confirmationText" class="rounded">
        <strong><fmt:message key="successMessage"/></strong>
        <br><br>
        <fmt:message key="confirmationNumberMessage"/> <strong>${requestScope.order.confirmationNumber}</strong>
        <br>
        <fmt:message key="contactMessage"/>
        <br><br>
        <fmt:message key="thankYouMessage"/>
    </p>

    <div class="summaryColumn" >

        <table id="orderSummaryTable" class="detailsTable">
            <tr class="header">
                <th colspan="3"><fmt:message key="orderSummary"/></th>
            </tr>

            <tr>
                <td class="tableHeading"><fmt:message key="product"/></td>
                <td class="tableHeading"><fmt:message key="quantity"/></td>
                <td class="tableHeading"><fmt:message key="price"/></td>
            </tr>

            <c:forEach var="orderedProduct" items="${requestScope.orderedProducts}" varStatus="iter">

                <tr class="${((iter.index % 2) != 0) ? 'lightBlue' : 'white'}">
                    <td>
                        <fmt:message key="${requestScope.products[iter.index].name}"/>
                    </td>
                    <td class="quantityColumn">
                        ${orderedProduct.quantity}
                    </td>
                    <td class="confirmationPriceColumn">
                        <fmt:formatNumber type="currency" currencySymbol="&euro; "
                                          value="${requestScope.products[iter.index].price * orderedProduct.quantity}"/>
                    </td>
                </tr>

            </c:forEach>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="2" id="deliverySurchargeCellLeft"><strong><fmt:message key="surcharge"/>:</strong></td>
                <td id="deliverySurchargeCellRight">
                    <fmt:formatNumber type="currency"
                                      currencySymbol="&euro; "
                                      value="${initParam.deliverySurcharge}"/></td>
            </tr>

            <tr class="lightBlue">
                <td colspan="2" id="totalCellLeft"><strong><fmt:message key="total"/>:</strong></td>
                <td id="totalCellRight">
                    <fmt:formatNumber type="currency"
                                      currencySymbol="&euro; "
                                      value="${requestScope.order.amount}"/></td>
            </tr>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="3" id="dateProcessedRow"><strong><fmt:message key="dateProcessed"/>:</strong>
                    <fmt:formatDate value="${requestScope.order.dateCreated}"
                                    type="both"
                                    dateStyle="short"
                                    timeStyle="short"/></td>
            </tr>
        </table>

    </div>

    <div class="summaryColumn" >

        <table id="deliveryAddressTable" class="detailsTable">
            <tr class="header">
                <th colspan="3"><fmt:message key="deliveryAddress"/></th>
            </tr>

            <tr>
                <td colspan="3" class="lightBlue">
                    ${requestScope.customer.name}
                    <br>
                    ${requestScope.customer.address}
                    <br>
                    Prague ${requestScope.customer.cityRegion}
                    <br>
                    <hr>
                    <strong><fmt:message key="email"/>:</strong> ${requestScope.customer.email}
                    <br>
                    <strong><fmt:message key="phone"/>:</strong> ${requestScope.customer.phone}
                </td>
            </tr>
        </table>
    </div>
</div>