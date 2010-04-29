<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Billable Results</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Billable Results</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Billable Items Found)<br />" rendered="#{billable.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{billable.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{billable.pagingInfo.firstItem + 1}..#{billable.pagingInfo.lastItem} of #{billable.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{billable.prev}" value="Previous #{billable.pagingInfo.batchSize}" rendered="#{billable.pagingInfo.firstItem >= billable.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{billable.next}" value="Next #{billable.pagingInfo.batchSize}" rendered="#{billable.pagingInfo.lastItem + billable.pagingInfo.batchSize <= billable.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{billable.next}" value="Remaining #{billable.pagingInfo.itemCount - billable.pagingInfo.lastItem}"
                                   rendered="#{billable.pagingInfo.lastItem < billable.pagingInfo.itemCount && billable.pagingInfo.lastItem + billable.pagingInfo.batchSize > billable.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{billable.billableItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="BillableId"/>
                            </f:facet>
                            <h:outputText value=" #{item.billableId}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="StartDate"/>
                            </f:facet>
                            <h:outputText value="#{item.startDate}">
                                <f:convertDateTime pattern="MM/dd/yyyy" />
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="EndDate"/>
                            </f:facet>
                            <h:outputText value="#{item.endDate}">
                                <f:convertDateTime pattern="MM/dd/yyyy" />
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Hours"/>
                            </f:facet>
                            <h:outputText value=" #{item.hours}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="HourlyRate"/>
                            </f:facet>
                            <h:outputText value=" #{item.hourlyRate}"/>
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Description"/>
                            </f:facet>
                            <h:outputText value=" #{item.description}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Artifacts"/>
                            </f:facet>
                            <h:outputText value=" #{item.artifacts}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Project"/>
                            </f:facet>
                            <h:outputText value=" #{item.project}"/>
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{billable.detailSetup}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][billable.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{billable.editSetup}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][billable.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{billable.destroy}">
                                <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][billable.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.timecard.consultantOfCurrentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item.consultantId][consultant.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <h:commandLink action="#{billable.searchSetup}" value="Modify Criteria"/>
                <br />
                <h:commandLink action="#{billable.createSetup}" value="New Billable"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
