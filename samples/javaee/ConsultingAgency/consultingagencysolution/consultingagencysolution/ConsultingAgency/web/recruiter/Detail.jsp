<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Recruiter Detail</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Recruiter Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="RecruiterId:"/>
                    <h:outputText value="#{recruiter.recruiter.recruiterId}" title="RecruiterId" />
                    <h:outputText value="Email:"/>
                    <h:outputText value="#{recruiter.recruiter.email}" title="Email" />
                    <h:outputText value="Password:"/>
                    <h:outputText value="#{recruiter.recruiter.password}" title="Password" />
                    <h:outputText value="Client:"/>
                    <h:panelGroup>
                        <h:outputText value=" #{recruiter.recruiter.client}"/>
                        <h:panelGroup rendered="#{recruiter.recruiter.client != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{client.detailSetup}">
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="recruiter"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.RecruiterController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{client.editSetup}">
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="recruiter"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.RecruiterController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{client.destroy}">
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="recruiter"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.RecruiterController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="ConsultantCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty recruiter.recruiter.consultantCollection}" value="(No Items)"/>
                        <h:dataTable value="#{recruiter.recruiter.consultantCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty recruiter.recruiter.consultantCollection}">
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
                                    <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="recruiter" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.RecruiterController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{consultant.editSetup}">
                                    <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="recruiter" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.RecruiterController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{consultant.destroy}">
                                    <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="recruiter" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.RecruiterController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{recruiter.destroy}" value="Destroy">
                    <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{recruiter.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][recruiter.recruiter][recruiter.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{recruiter.createSetup}" value="New Recruiter" />
                <br />
                <h:commandLink action="#{recruiter.listSetup}" value="Show All Recruiter Items"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
