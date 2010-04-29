<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing Consultant Items</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Consultant Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Consultant Items Found)<br />" rendered="#{consultant.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{consultant.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{consultant.pagingInfo.firstItem + 1}..#{consultant.pagingInfo.lastItem} of #{consultant.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{consultant.prev}" value="Previous #{consultant.pagingInfo.batchSize}" rendered="#{consultant.pagingInfo.firstItem >= consultant.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{consultant.next}" value="Next #{consultant.pagingInfo.batchSize}" rendered="#{consultant.pagingInfo.lastItem + consultant.pagingInfo.batchSize <= consultant.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{consultant.next}" value="Remaining #{consultant.pagingInfo.itemCount - consultant.pagingInfo.lastItem}"
                                   rendered="#{consultant.pagingInfo.lastItem < consultant.pagingInfo.itemCount && consultant.pagingInfo.lastItem + consultant.pagingInfo.batchSize > consultant.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{consultant.consultantItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
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
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{consultant.editSetup}">
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{consultant.destroy}">
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{consultant.createSetup}" value="New Consultant"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
                
            </h:form>
        </body>
    </html>
</f:view>
