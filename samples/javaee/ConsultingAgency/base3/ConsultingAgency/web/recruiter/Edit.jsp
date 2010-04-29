<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Editing Recruiter</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing Recruiter</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="RecruiterId:"/>
                    <h:outputText value="#{recruiter.recruiter.recruiterId}" title="RecruiterId" />
                    <h:outputText value="Email:"/>
                    <h:inputText id="email" value="#{recruiter.recruiter.email}" title="Email" required="true" requiredMessage="The email field is required." />
                    <h:outputText value="Password:"/>
                    <h:inputText id="password" value="#{recruiter.recruiter.password}" title="Password" required="true" requiredMessage="The password field is required." />
                    <h:outputText value="ConsultantCollection:"/>
                    <h:selectManyListbox id="consultantCollection" value="#{recruiter.recruiter.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].consultantCollection}" title="ConsultantCollection" size="6" converter="#{consultant.converter}" >
                        <f:selectItems value="#{consultant.consultantItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="Client:"/>
                    <h:selectOneMenu id="client" value="#{recruiter.recruiter.client}" title="Client" >
                        <f:selectItems value="#{client.clientItemsAvailableSelectOne}"/>
                    </h:selectOneMenu>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{recruiter.edit}" value="Save">
                    <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{recruiter.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{recruiter.listSetup}" value="Show All Recruiter Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
