<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Editing ConsultantStatus</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing ConsultantStatus</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="StatusId:"/>
                    <h:outputText value="#{consultantStatus.consultantStatus.statusId}" title="StatusId" />
                    <h:outputText value="Description:"/>
                    <h:inputText id="description" value="#{consultantStatus.consultantStatus.description}" title="Description" required="true" requiredMessage="The description field is required." />
                    <h:outputText value="ConsultantCollection:"/>
                    <h:selectManyListbox id="consultantCollection" value="#{consultantStatus.consultantStatus.jsfcrud_transform[jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.collectionToArray][jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method.arrayToList].consultantCollection}" title="ConsultantCollection" size="6" converter="#{consultant.converter}" >
                        <f:selectItems value="#{consultant.consultantItemsAvailableSelectMany}"/>
                    </h:selectManyListbox>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{consultantStatus.edit}" value="Save">
                    <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultantStatus.consultantStatus][consultantStatus.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{consultantStatus.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultantStatus.consultantStatus][consultantStatus.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{consultantStatus.listSetup}" value="Show All ConsultantStatus Items" immediate="true"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
