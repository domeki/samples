<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>ConsultantStatus Detail</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>ConsultantStatus Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="StatusId:"/>
                    <h:outputText value="#{consultantStatus.consultantStatus.statusId}" title="StatusId" />
                    <h:outputText value="Description:"/>
                    <h:outputText value="#{consultantStatus.consultantStatus.description}" title="Description" />
                    <h:outputText value="ConsultantCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty consultantStatus.consultantStatus.consultantCollection}" value="(No Items)"/>
                        <h:dataTable value="#{consultantStatus.consultantStatus.consultantCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty consultantStatus.consultantStatus.consultantCollection}">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="ConsultantId"/>
                                </f:facet>
                                <h:outputText value=" #{item.consultantId}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Email"/>
                                </f:facet>
                                <h:outputText value=" #{item.email}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Password"/>
                                </f:facet>
                                <h:outputText value=" #{item.password}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="HourlyRate"/>
                                </f:facet>
                                <h:outputText value=" #{item.hourlyRate}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="BillableHourlyRate"/>
                                </f:facet>
                                <h:outputText value=" #{item.billableHourlyRate}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="HireDate"/>
                                </f:facet>
                                <h:outputText value="#{item.hireDate}">
                                    <f:convertDateTime pattern="MM/dd/yyyy" />
                                </h:outputText>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Resume"/>
                                </f:facet>
                                <h:outputText value=" #{item.resume}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="RecruiterId"/>
                                </f:facet>
                                <h:outputText value=" #{item.recruiterId}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="StatusId"/>
                                </f:facet>
                                <h:outputText value=" #{item.statusId}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{consultant.detailSetup}">
                                    <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultantStatus.consultantStatus][consultantStatus.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultantStatus" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantStatusController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{consultant.editSetup}">
                                    <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultantStatus.consultantStatus][consultantStatus.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultantStatus" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantStatusController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{consultant.destroy}">
                                    <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultantStatus.consultantStatus][consultantStatus.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultantStatus" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantStatusController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{consultantStatus.destroy}" value="Destroy">
                    <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultantStatus.consultantStatus][consultantStatus.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{consultantStatus.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultantStatus.consultantStatus][consultantStatus.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{consultantStatus.createSetup}" value="New ConsultantStatus" />
                <br />
                <h:commandLink action="#{consultantStatus.listSetup}" value="Show All ConsultantStatus Items"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
