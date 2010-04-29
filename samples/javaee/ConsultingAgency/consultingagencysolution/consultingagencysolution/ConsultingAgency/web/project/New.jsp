<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>New Project</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>New Project</h1>
            <h:form>
                <h:inputHidden id="validateCreateField" validator="#{project.validateCreate}" value="value"/>
                <h:panelGrid columns="2">
                    <h:outputText value="ProjectName:"/>
                    <h:inputText id="projectName" value="#{project.project.projectPK.projectName}" title="ProjectName" required="true" requiredMessage="The projectName field is required." />
                    <h:outputText value="ContactEmail:"/>
                    <h:inputText id="contactEmail" value="#{project.project.contactEmail}" title="ContactEmail" />
                    <h:outputText value="ContactPassword:"/>
                    <h:inputText id="contactPassword" value="#{project.project.contactPassword}" title="ContactPassword" />
                    <h:outputText value="ConsultantCollection:"/>
                    <h:selectManyListbox id="consultantCollection" value="#{project.project.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].consultantCollection}" title="ConsultantCollection" size="6" converter="#{consultant.converter}" >
                        <f:selectItems value="#{consultant.consultantItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="Client:"/>
                    <h:selectOneMenu id="client" value="#{project.project.client}" title="Client" required="true" requiredMessage="The client field is required." >
                        <f:selectItems value="#{client.clientItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="BillableCollection:"/>
                    <h:selectManyListbox id="billableCollection" value="#{project.project.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].billableCollection}" title="BillableCollection" size="6" converter="#{billable.converter}" >
                        <f:selectItems value="#{billable.billableItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{project.create}" value="Create"/>
                <br />
                <br />
                <h:commandLink action="#{project.listSetup}" value="Show All Project Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
