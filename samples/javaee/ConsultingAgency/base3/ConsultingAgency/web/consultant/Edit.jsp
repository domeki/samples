<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Editing Consultant</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing Consultant</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="ConsultantId:"/>
                    <h:outputText value="#{consultant.consultant.consultantId}" title="ConsultantId" />
                    <h:outputText value="Email:"/>
                    <h:inputText id="email" value="#{consultant.consultant.email}" title="Email" required="true" requiredMessage="The email field is required." />
                    <h:outputText value="Password:"/>
                    <h:inputText id="password" value="#{consultant.consultant.password}" title="Password" required="true" requiredMessage="The password field is required." />
                    <h:outputText value="HourlyRate:"/>
                    <h:inputText id="hourlyRate" value="#{consultant.consultant.hourlyRate}" title="HourlyRate" required="true" requiredMessage="The hourlyRate field is required." />
                    <h:outputText value="BillableHourlyRate:"/>
                    <h:inputText id="billableHourlyRate" value="#{consultant.consultant.billableHourlyRate}" title="BillableHourlyRate" required="true" requiredMessage="The billableHourlyRate field is required." />
                    <h:outputText value="HireDate (MM/dd/yyyy):"/>
                    <h:inputText id="hireDate" value="#{consultant.consultant.hireDate}" title="HireDate" >
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:inputText>
                    <h:outputText value="Resume:"/>
                    <h:inputTextarea rows="4" cols="30" id="resume" value="#{consultant.consultant.resume}" title="Resume" />
                    <h:outputText value="ProjectCollection:"/>
                    <h:selectManyListbox id="projectCollection" value="#{consultant.consultant.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].projectCollection}" title="ProjectCollection" size="6" converter="#{project.converter}" >
                        <f:selectItems value="#{project.projectItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="RecruiterId:"/>
                    <h:selectOneMenu id="recruiterId" value="#{consultant.consultant.recruiterId}" title="RecruiterId" >
                        <f:selectItems value="#{recruiter.recruiterItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="StatusId:"/>
                    <h:selectOneMenu id="statusId" value="#{consultant.consultant.statusId}" title="StatusId" required="true" requiredMessage="The statusId field is required." >
                        <f:selectItems value="#{consultantStatus.consultantStatusItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                    <h:outputText value="BillableCollection:"/>
                    <h:selectManyListbox id="billableCollection" value="#{consultant.consultant.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].billableCollection}" title="BillableCollection" size="6" converter="#{billable.converter}" >
                        <f:selectItems value="#{billable.billableItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{consultant.edit}" value="Save">
                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{consultant.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{consultant.listSetup}" value="Show All Consultant Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
