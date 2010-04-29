<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing ConsultantStatus Items</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing ConsultantStatus Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No ConsultantStatus Items Found)<br />" rendered="#{consultantStatus.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{consultantStatus.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{consultantStatus.pagingInfo.firstItem + 1}..#{consultantStatus.pagingInfo.lastItem} of #{consultantStatus.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{consultantStatus.prev}" value="Previous #{consultantStatus.pagingInfo.batchSize}" rendered="#{consultantStatus.pagingInfo.firstItem >= consultantStatus.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{consultantStatus.next}" value="Next #{consultantStatus.pagingInfo.batchSize}" rendered="#{consultantStatus.pagingInfo.lastItem + consultantStatus.pagingInfo.batchSize <= consultantStatus.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{consultantStatus.next}" value="Remaining #{consultantStatus.pagingInfo.itemCount - consultantStatus.pagingInfo.lastItem}"
                                   rendered="#{consultantStatus.pagingInfo.lastItem < consultantStatus.pagingInfo.itemCount && consultantStatus.pagingInfo.lastItem + consultantStatus.pagingInfo.batchSize > consultantStatus.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{consultantStatus.consultantStatusItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="StatusId"/>
                            </f:facet>
                            <h:outputText value=" #{item.statusId}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Description"/>
                            </f:facet>
                            <h:outputText value=" #{item.description}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{consultantStatus.detailSetup}">
                                <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultantStatus.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{consultantStatus.editSetup}">
                                <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultantStatus.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{consultantStatus.destroy}">
                                <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultantStatus.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{consultantStatus.createSetup}" value="New ConsultantStatus"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
                
            </h:form>
        </body>
    </html>
</f:view>
