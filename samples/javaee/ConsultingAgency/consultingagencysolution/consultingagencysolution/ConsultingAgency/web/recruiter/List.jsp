<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing Recruiter Items</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Recruiter Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Recruiter Items Found)<br />" rendered="#{recruiter.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{recruiter.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{recruiter.pagingInfo.firstItem + 1}..#{recruiter.pagingInfo.lastItem} of #{recruiter.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{recruiter.prev}" value="Previous #{recruiter.pagingInfo.batchSize}" rendered="#{recruiter.pagingInfo.firstItem >= recruiter.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{recruiter.next}" value="Next #{recruiter.pagingInfo.batchSize}" rendered="#{recruiter.pagingInfo.lastItem + recruiter.pagingInfo.batchSize <= recruiter.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{recruiter.next}" value="Remaining #{recruiter.pagingInfo.itemCount - recruiter.pagingInfo.lastItem}"
                                   rendered="#{recruiter.pagingInfo.lastItem < recruiter.pagingInfo.itemCount && recruiter.pagingInfo.lastItem + recruiter.pagingInfo.batchSize > recruiter.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{recruiter.recruiterItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="RecruiterId"/>
                            </f:facet>
                            <h:outputText value=" #{item.recruiterId}"/>
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
                                <h:outputText value="Client"/>
                            </f:facet>
                            <h:outputText value=" #{item.client}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{recruiter.detailSetup}">
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][recruiter.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{recruiter.editSetup}">
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][recruiter.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{recruiter.destroy}">
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][recruiter.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{recruiter.createSetup}" value="New Recruiter"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
                
            </h:form>
        </body>
    </html>
</f:view>
