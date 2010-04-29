package billable;

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Search Billable Items</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Search Billable Items</h1>
            <h:form id="form1">
                <h:panelGrid columns="2">
                    <h:outputText value="BillableId:"/>
                    <h:inputText value="#{billable.billable.billableId}" title="BillableId" converterMessage="The billable id field must be an integer."/>
                    <h:outputText value="StartDate (MM/dd/yyyy):"/>
                    <h:inputText id="startDate" value="#{billable.billable.startDate}" title="StartDate" converterMessage="Invalid start date.">
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:inputText>
                    <h:outputText value="EndDate (MM/dd/yyyy):"/>
                    <h:inputText id="endDate" value="#{billable.billable.endDate}" title="EndDate" validator="#{billable.validateEndDate}" converterMessage="Invalid end date.">
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:inputText>
                    <h:outputText value="Hours:"/>
                    <h:inputText id="hours" value="#{billable.searchHours}" title="Hours" converterMessage="The hours field must be an integer." />
                    <h:outputText value="HourlyRate:"/>
                    <h:inputText id="hourlyRate" value="#{billable.billable.hourlyRate}" title="HourlyRate" converterMessage="The hourly rate field must be a monetary figure."/>
                    <h:outputText value="Description:"/>
                    <h:inputText id="description" value="#{billable.billable.description}" title="Description" />
                    <h:outputText value="Project:"/>
                    <h:selectOneMenu id="project" value="#{billable.billable.project}" title="Project" >
                        <f:selectItems value="#{project.billedProjectsForLoggedInConsultantSelectOne}"/>
                    </h:selectOneMenu>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{billable.search}" value="Search" />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
