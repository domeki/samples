<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing Project Items</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Project Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Project Items Found)<br />" rendered="#{project.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{project.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{project.pagingInfo.firstItem + 1}..#{project.pagingInfo.lastItem} of #{project.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{project.prev}" value="Previous #{project.pagingInfo.batchSize}" rendered="#{project.pagingInfo.firstItem >= project.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{project.next}" value="Next #{project.pagingInfo.batchSize}" rendered="#{project.pagingInfo.lastItem + project.pagingInfo.batchSize <= project.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{project.next}" value="Remaining #{project.pagingInfo.itemCount - project.pagingInfo.lastItem}"
                                   rendered="#{project.pagingInfo.lastItem < project.pagingInfo.itemCount && project.pagingInfo.lastItem + project.pagingInfo.batchSize > project.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{project.projectItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="ProjectName"/>
                            </f:facet>
                            <h:outputText value=" #{item.projectPK.projectName}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="ContactEmail"/>
                            </f:facet>
                            <h:outputText value=" #{item.contactEmail}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="ContactPassword"/>
                            </f:facet>
                            <h:outputText value=" #{item.contactPassword}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Client"/>
                            </f:facet>
                            <h:outputText value=" #{item.client}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{project.detailSetup}">
                                <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{project.editSetup}">
                                <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{project.destroy}">
                                <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{project.createSetup}" value="New Project"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
                
            </h:form>
        </body>
    </html>
</f:view>
