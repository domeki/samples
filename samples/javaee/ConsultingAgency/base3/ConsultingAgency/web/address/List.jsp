<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing Address Items</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Address Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Address Items Found)<br />" rendered="#{address.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{address.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{address.pagingInfo.firstItem + 1}..#{address.pagingInfo.lastItem} of #{address.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{address.prev}" value="Previous #{address.pagingInfo.batchSize}" rendered="#{address.pagingInfo.firstItem >= address.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{address.next}" value="Next #{address.pagingInfo.batchSize}" rendered="#{address.pagingInfo.lastItem + address.pagingInfo.batchSize <= address.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{address.next}" value="Remaining #{address.pagingInfo.itemCount - address.pagingInfo.lastItem}"
                                   rendered="#{address.pagingInfo.lastItem < address.pagingInfo.itemCount && address.pagingInfo.lastItem + address.pagingInfo.batchSize > address.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{address.addressItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="AddressId"/>
                            </f:facet>
                            <h:outputText value=" #{item.addressId}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Line1"/>
                            </f:facet>
                            <h:outputText value=" #{item.line1}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Line2"/>
                            </f:facet>
                            <h:outputText value=" #{item.line2}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="City"/>
                            </f:facet>
                            <h:outputText value=" #{item.city}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Region"/>
                            </f:facet>
                            <h:outputText value=" #{item.region}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Country"/>
                            </f:facet>
                            <h:outputText value=" #{item.country}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="PostalCode"/>
                            </f:facet>
                            <h:outputText value=" #{item.postalCode}"/>
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
                            <h:commandLink value="Show" action="#{address.detailSetup}">
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][address.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{address.editSetup}">
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][address.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{address.destroy}">
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][address.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>
                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{address.createSetup}" value="New Address"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
                
            </h:form>
        </body>
    </html>
</f:view>
