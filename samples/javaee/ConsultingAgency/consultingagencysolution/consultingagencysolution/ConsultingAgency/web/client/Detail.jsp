<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Client Detail</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Client Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Recruiter:"/>
                    <h:panelGroup>
                        <h:outputText value=" #{client.client.recruiter}"/>
                        <h:panelGroup rendered="#{client.client.recruiter != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{recruiter.detailSetup}">
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="client"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{recruiter.editSetup}">
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="client"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{recruiter.destroy}">
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="client"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="ClientDepartmentNumber:"/>
                    <h:outputText value="#{client.client.clientPK.clientDepartmentNumber}" title="ClientDepartmentNumber" />
                    <h:outputText value="ClientName:"/>
                    <h:outputText value="#{client.client.clientPK.clientName}" title="ClientName" />
                    <h:outputText value="ContactEmail:"/>
                    <h:outputText value="#{client.client.contactEmail}" title="ContactEmail" />
                    <h:outputText value="ContactPassword:"/>
                    <h:outputText value="#{client.client.contactPassword}" title="ContactPassword" />
                    <h:outputText value="BillingAddress:"/>
                    <h:panelGroup>
                        <h:outputText value=" #{client.client.billingAddress}"/>
                        <h:panelGroup rendered="#{client.client.billingAddress != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{address.detailSetup}">
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client.billingAddress][address.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="client"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{address.editSetup}">
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client.billingAddress][address.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="client"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{address.destroy}">
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client.billingAddress][address.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="client"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="ProjectCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty client.client.projectCollection}" value="(No Items)"/>
                        <h:dataTable value="#{client.client.projectCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty client.client.projectCollection}">
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
                                    <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="client" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{project.editSetup}">
                                    <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="client" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{project.destroy}">
                                    <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="client" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ClientController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{client.destroy}" value="Destroy">
                    <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{client.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][client.client][client.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{client.createSetup}" value="New Client" />
                <br />
                <h:commandLink action="#{client.listSetup}" value="Show All Client Items"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
