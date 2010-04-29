<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Editing Billable</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing Billable</h1>
            <h:form id="form1">
                <h:panelGrid columns="2">
                    <h:outputText value="BillableId:"/>
                    <h:outputText value="#{billable.billable.billableId}" title="BillableId" />
                    <h:outputText value="StartDate (MM/dd/yyyy):"/>
                    <h:inputText id="startDate" value="#{billable.billable.startDate}" title="StartDate" converterMessage="Invalid start date.">
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:inputText>
                    <h:outputText value="EndDate (MM/dd/yyyy):"/>
                    <h:inputText id="endDate" value="#{billable.billable.endDate}" title="EndDate" validator="#{billable.validateEndDate}" converterMessage="Invalid end date.">
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:inputText>
                    <h:outputText value="Hours:"/>
                    <h:inputText id="hours" value="#{billable.billable.hours}" title="Hours" required="true" requiredMessage="The hours field is required." converterMessage="The hours field must be an integer." />
                    <h:outputText value="HourlyRate:"/>
                    <h:outputText id="hourlyRate" value="#{billable.billable.hourlyRate}" title="HourlyRate" />
                    <h:outputText value="Description:"/>
                    <h:inputText id="description" value="#{billable.billable.description}" title="Description" />
                    <h:outputText value="Artifacts:"/>
                    <h:inputTextarea rows="4" cols="30" id="artifacts" value="#{billable.billable.artifacts}" title="Artifacts" />
                    <h:outputText value="Project:"/>
                    <h:selectOneMenu id="project" value="#{billable.billable.project}" title="Project" required="true" requiredMessage="The project field is required." >
                        <f:selectItems value="#{project.assignedProjectsForLoggedInConsultantSelectOne}"/>
                    </h:selectOneMenu>
                    
                </h:panelGrid>
                <br />
                <h:commandLink action="#{billable.edit}" value="Save">
                    <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{billable.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{billable.searchSetup}" value="Search Billable Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
