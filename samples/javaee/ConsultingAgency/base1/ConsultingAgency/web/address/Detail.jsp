<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Address Detail</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Address Detail</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="AddressId:"/>
                    <h:outputText value="#{address.address.addressId}" title="AddressId" />
                    <h:outputText value="Line1:"/>
                    <h:outputText value="#{address.address.line1}" title="Line1" />
                    <h:outputText value="Line2:"/>
                    <h:outputText value="#{address.address.line2}" title="Line2" />
                    <h:outputText value="City:"/>
                    <h:outputText value="#{address.address.city}" title="City" />
                    <h:outputText value="Region:"/>
                    <h:outputText value="#{address.address.region}" title="Region" />
                    <h:outputText value="Country:"/>
                    <h:outputText value="#{address.address.country}" title="Country" />
                    <h:outputText value="PostalCode:"/>
                    <h:outputText value="#{address.address.postalCode}" title="PostalCode" />
                    <h:outputText value="Client:"/>
                    <h:panelGroup>
                        <h:outputText value=" #{address.address.client}"/>
                        <h:panelGroup rendered="#{address.address.client != null}">
                            <h:outputText value=" ("/>
                            <h:commandLink value="Show" action="#{client.detailSetup}">
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][address.address][address.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][address.address.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="address"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.AddressController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{client.editSetup}">
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][address.address][address.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][address.address.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="address"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.AddressController"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{client.destroy}">
                                <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][address.address][address.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.currentClient" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][address.address.client][client.converter].jsfcrud_invoke}"/>
                                <f:param name="jsfcrud.relatedController" value="address"/>
                                <f:param name="jsfcrud.relatedControllerType" value="jsf.AddressController"/>
                            </h:commandLink>
                            <h:outputText value=" )"/>
                        </h:panelGroup>
                    </h:panelGroup>
                </h:panelGrid>
                <br />
                <h:commandLink action="#{address.destroy}" value="Destroy">
                    <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][address.address][address.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{address.editSetup}" value="Edit">
                    <f:param name="jsfcrud.currentAddress" value="#{jsfcrud_class['jsf.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][address.address][address.converter].jsfcrud_invoke}" />
                </h:commandLink>
                <br />
                <h:commandLink action="#{address.createSetup}" value="New Address" />
                <br />
                <h:commandLink action="#{address.listSetup}" value="Show All Address Items"/>
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />
            </h:form>
        </body>
    </html>
</f:view>
