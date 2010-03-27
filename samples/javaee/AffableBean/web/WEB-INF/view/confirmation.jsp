<%--
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
--%>


<div id="singleColumn">

    <p id="confirmationText" class="rounded">
        <strong>Your order has been successfully processed and will be delivered within 24 hours.</strong>
        <br><br>
        Please keep a note of your confirmation number: <strong>${requestScope.order.confirmationNumber}</strong>
        <br>
        If you have a query concerning your order, feel free to <a href="#">contact us</a>.
        <br><br>
        Thank you for shopping at the Affable Bean!
    </p>

    <div class="summaryColumn" >

        <table class="summaryTable">
            <tr class="header">
                <th colspan="3">order summary</th>
            </tr>

            <tr>
                <td class="summaryHeading">product</td>
                <td class="summaryHeading">quantity</td>
                <td class="summaryHeading">price</td>
            </tr>

            <c:forEach var="orderedProduct" items="${requestScope.orderedProducts}" varStatus="iter">

                <tr>
                    <td class="${((iter.index % 2) != 0) ? 'even' : 'odd'}">
                        ${requestScope.products[iter.index].name}
                    </td>
                    <td class="${((iter.index % 2) != 0) ? 'even' : 'odd'}" style="text-align: center">
                        ${orderedProduct.quantity}
                    </td>
                    <td class="${((iter.index % 2) != 0) ? 'even' : 'odd'}">
                        &euro; ${requestScope.products[iter.index].price * orderedProduct.quantity}
                    </td>
                </tr>

            </c:forEach>

            <tr class="even">
                <td colspan="3" style="height: 1px; padding: 0 20px"><hr></td>
            </tr>

            <tr class="even">
                <td colspan="2" style="text-align: right; padding: 0"><strong>delivery surcharge:</strong></td>
                <td style="padding: 0 20px">&euro; ${initParam.deliverySurcharge}</td>
            </tr>
            <tr class="even">
                <td colspan="2" style="text-align: right; padding: 0"><strong>total:</strong></td>
                <td style="padding: 0 20px">&euro; ${requestScope.order.amount}</td>
            </tr>

            <tr class="even">
                <td colspan="3" style="height: 1px; padding: 0 20px"><hr></td>
            </tr>

            <tr class="even">
                <td colspan="3" style="text-align: left; padding: 2px 0 10px 20px"><strong>date processed:</strong>
                    <fmt:formatDate value="${requestScope.order.dateCreated}" type="both" dateStyle="short" timeStyle="short"/></td>
            </tr>
        </table>


    </div>

    <div class="summaryColumn" >

        <table class="summaryTable">
            <tr class="header">
                <th colspan="3">delivery address</th>
            </tr>

            <tr>
                <td class="even">
                    <div id="addressBox">
                        ${requestScope.customer.name}
                        <br>
                        ${requestScope.customer.address}
                        <br>
                        Prague ${requestScope.customer.cityRegion}
                        <br>
                        <hr>
                        <strong>phone:</strong> ${requestScope.customer.phone}
                        <br>
                        <strong>email:</strong> ${requestScope.customer.email}
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>