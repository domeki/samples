<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Billable Detail</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Billable Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="BillableId:"/>
                    <h:outputText value="#{billable.billable.billableId}" title="BillableId" />
                    <h:outputText value="StartDate:"/>
                    <h:outputText value="#{billable.billable.startDate}" title="StartDate" >
                        <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
                    </h:outputText>
                    <h:outputText value="EndDate:"/>
                    <h:outputText value="#{billable.billable.endDate}" title="EndDate" >
                        <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
                    </h:outputText>
                    <h:outputText value="Hours:"/>
                    <h:outputText value="#{billable.billable.hours}" title="Hours" />
                    <h:outputText value="HourlyRate:"/>
                    <h:outputText value="#{billable.billable.hourlyRate}" title="HourlyRate" />
                    <h:outputText value="BillableHourlyRate:"/>
                    <h:outputText value="#{billable.billable.billableHourlyRate}" title="BillableHourlyRate" />
                    <h:outputText value="Description:"/>
                    <h:outputText value="#{billable.billable.description}" title="Description" />
                    <h:outputText value="Artifacts:"/>
                    <h:outputText value="#{billable.billable.artifacts}" title="Artifacts" />
                    <h:outputText value="Project:"/>
                    <h:panelGroup>
                        <h:outputText value=" #{billable.billable.project}"/>
                        <h:panelGroup rendered="#{billable.billable.project != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{project.detailSetup}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable.project][project.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="billable"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.BillableController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{project.editSetup}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable.project][project.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="billable"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.BillableController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{project.destroy}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable.project][project.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="billable"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.BillableController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="ConsultantId:"/>
                    <h:panelGroup>
                        <h:outputText value=" #{billable.billable.consultantId}"/>
                        <h:panelGroup rendered="#{billable.billable.consultantId != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{consultant.detailSetup}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable.consultantId][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="billable"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.BillableController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{consultant.editSetup}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable.consultantId][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="billable"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.BillableController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{consultant.destroy}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable.consultantId][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="billable"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.BillableController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{billable.destroy}" value="Destroy">
                    <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{billable.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][billable.billable][billable.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{billable.createSetup}" value="New Billable" />
                <br />
                <h:commandLink action="#{billable.listSetup}" value="Show All Billable Items"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
