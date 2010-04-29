<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Consultant Detail</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Consultant Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="ConsultantId:"/>
                    <h:outputText value="#{consultant.consultant.consultantId}" title="ConsultantId" />
                    <h:outputText value="Email:"/>
                    <h:outputText value="#{consultant.consultant.email}" title="Email" />
                    <h:outputText value="Password:"/>
                    <h:outputText value="#{consultant.consultant.password}" title="Password" />
                    <h:outputText value="HourlyRate:"/>
                    <h:outputText value="#{consultant.consultant.hourlyRate}" title="HourlyRate" />
                    <h:outputText value="BillableHourlyRate:"/>
                    <h:outputText value="#{consultant.consultant.billableHourlyRate}" title="BillableHourlyRate" />
                    <h:outputText value="HireDate:"/>
                    <h:outputText value="#{consultant.consultant.hireDate}" title="HireDate" >
                        <f:convertDateTime pattern="MM/dd/yyyy" />
                    </h:outputText>
                    <h:outputText value="Resume:"/>
                    <h:outputText value="#{consultant.consultant.resume}" title="Resume" />
                    <h:outputText value="RecruiterId:"/>
                    <h:panelGroup>
                        <h:outputText value=" #{consultant.consultant.recruiterId}"/>
                        <h:panelGroup rendered="#{consultant.consultant.recruiterId != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{recruiter.detailSetup}">
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant.recruiterId][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="consultant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{recruiter.editSetup}">
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant.recruiterId][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="consultant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{recruiter.destroy}">
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentRecruiter" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant.recruiterId][recruiter.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="consultant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="StatusId:"/>
                    <h:panelGroup>
                        <h:outputText value=" #{consultant.consultant.statusId}"/>
                        <h:panelGroup rendered="#{consultant.consultant.statusId != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{consultantStatus.detailSetup}">
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant.statusId][consultantStatus.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="consultant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{consultantStatus.editSetup}">
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant.statusId][consultantStatus.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="consultant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{consultantStatus.destroy}">
                                <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentConsultantStatus" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant.statusId][consultantStatus.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="consultant"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:outputText value="ProjectCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty consultant.consultant.projectCollection}" value="(No Items)"/>
                        <h:dataTable value="#{consultant.consultant.projectCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty consultant.consultant.projectCollection}">
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
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultant" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{project.editSetup}">
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultant" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{project.destroy}">
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentProject" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][project.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultant" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                    <h:outputText value="BillableCollection:" />
                    <h:panelGroup>
                        <h:outputText rendered="#{empty consultant.consultant.billableCollection}" value="(No Items)"/>
                        <h:dataTable value="#{consultant.consultant.billableCollection}" var="item" 
                                     border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px" 
                                     rendered="#{not empty consultant.consultant.billableCollection}">
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
                                    <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
                                </h:outputText>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="EndDate"/>
                                </f:facet>
                                <h:outputText value="#{item.endDate}">
                                    <f:convertDateTime pattern="MM/dd/yyyy HH:mm:ss" />
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
                                    <h:outputText value="BillableHourlyRate"/>
                                </f:facet>
                                <h:outputText value=" #{item.billableHourlyRate}"/>
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
                                    <h:outputText value="ConsultantId"/>
                                </f:facet>
                                <h:outputText value=" #{item.consultantId}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText escape="false" value="&nbsp;"/>
                                </f:facet>
                                <h:commandLink value="Show" action="#{billable.detailSetup}">
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][billable.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultant" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Edit" action="#{billable.editSetup}">
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][billable.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultant" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController" />
                                </h:commandLink>
                                <h:outputText value=" "/>
                                <h:commandLink value="Destroy" action="#{billable.destroy}">
                                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.currentBillable" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][billable.converter].jsfcrud_invoke}"/>
                                    <f:param name="jsfcrud.relatedController" value="consultant" />
                                    <f:param name="jsfcrud.relatedControllerType" value="jsf.ConsultantController" />
                                </h:commandLink>
                            </h:column>
                        </h:dataTable>
                    </h:panelGroup>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{consultant.destroy}" value="Destroy">
                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{consultant.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentConsultant" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][consultant.consultant][consultant.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{consultant.createSetup}" value="New Consultant" />
                <br />
                <h:commandLink action="#{consultant.listSetup}" value="Show All Consultant Items"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
