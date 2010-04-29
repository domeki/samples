<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>New Address</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New Address</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{address.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="Line1:"/>
                    <h:inputText id="line1" value="#{address.address.line1}" title="Line1" required="true" requiredMessage="The line1 field is required." />
                    <h:outputText value="Line2:"/>
                    <h:inputText id="line2" value="#{address.address.line2}" title="Line2" />
                    <h:outputText value="City:"/>
                    <h:inputText id="city" value="#{address.address.city}" title="City" required="true" requiredMessage="The city field is required." />
                    <h:outputText value="Region:"/>
                    <h:inputText id="region" value="#{address.address.region}" title="Region" required="true" requiredMessage="The region field is required." />
                    <h:outputText value="Country:"/>
                    <h:inputText id="country" value="#{address.address.country}" title="Country" required="true" requiredMessage="The country field is required." />
                    <h:outputText value="PostalCode:"/>
                    <h:inputText id="postalCode" value="#{address.address.postalCode}" title="PostalCode" required="true" requiredMessage="The postalCode field is required." />
                    <h:outputText value="Client:"/>
                    <h:selectOneMenu id="client" value="#{address.address.client}" title="Client" >
                        <f:selectItems value="#{client.clientItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{address.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{address.listSetup}" value="Show All Address Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
