<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Editing Project</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing Project</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="ProjectName:"/>
                    <h:outputText value="#{project.project.projectPK.projectName}" title="ProjectName" />
                    <h:outputText value="ContactEmail:"/>
                    <h:inputText id="contactEmail" value="#{project.project.contactEmail}" title="ContactEmail" />
                    <h:outputText value="ContactPassword:"/>
                    <h:inputText id="contactPassword" value="#{project.project.contactPassword}" title="ContactPassword" />
                    <h:outputText value="ConsultantCollection:"/>
                    <h:selectManyListbox id="consultantCollection" value="#{project.project.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].consultantCollection}" title="ConsultantCollection" size="6" converter="#{consultant.converter}" >
                        <f:selectItems value="#{consultant.consultantItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                    <h:outputText value="Client:"/>
                    <h:outputText value=" #{project.project.client}" title="Client" />
                    <h:outputText value="BillableCollection:"/>
                    <h:selectManyListbox id="billableCollection" value="#{project.project.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].billableCollection}" title="BillableCollection" size="6" converter="#{billable.converter}" >
                        <f:selectItems value="#{billable.billableItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{project.edit}" value="Save">
                    <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][project.project][project.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{project.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][project.project][project.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{project.listSetup}" value="Show All Project Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
