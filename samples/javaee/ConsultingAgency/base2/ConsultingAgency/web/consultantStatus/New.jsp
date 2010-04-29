<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>New ConsultantStatus</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New ConsultantStatus</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{consultantStatus.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="StatusId:"/>
                    <h:inputText id="statusId" value="#{consultantStatus.consultantStatus.statusId}" title="StatusId" required="true" requiredMessage="The statusId field is required." />
                    <h:outputText value="Description:"/>
                    <h:inputText id="description" value="#{consultantStatus.consultantStatus.description}" title="Description" required="true" requiredMessage="The description field is required." />
                    <h:outputText value="ConsultantCollection:"/>
                    <h:selectManyListbox id="consultantCollection" value="#{consultantStatus.consultantStatus.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].consultantCollection}" title="ConsultantCollection" size="6" converter="#{consultant.converter}" >
                        <f:selectItems value="#{consultant.consultantItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{consultantStatus.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{consultantStatus.listSetup}" value="Show All ConsultantStatus Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
