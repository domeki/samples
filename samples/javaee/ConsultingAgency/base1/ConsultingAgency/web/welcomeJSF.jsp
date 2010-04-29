<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%--
    This file is an entry point for JavaServer Faces application.
--%>
<f:view>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
<link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
    </head>
    <body>
        <h:form>
<h1><h:outputText value="JavaServer Faces" /></h1>
    <br/>
<h:commandLink action="#{recruiter.listSetup}" value="Show All Recruiter Items"/>

    <br/>
<h:commandLink action="#{project.listSetup}" value="Show All Project Items"/>

    <br/>
<h:commandLink action="#{consultantStatus.listSetup}" value="Show All ConsultantStatus Items"/>

    <br/>
<h:commandLink action="#{consultant.listSetup}" value="Show All Consultant Items"/>

    <br/>
<h:commandLink action="#{client.listSetup}" value="Show All Client Items"/>

    <br/>
<h:commandLink action="#{billable.listSetup}" value="Show All Billable Items"/>

    <br/>
<h:commandLink action="#{address.listSetup}" value="Show All Address Items"/>
</h:form>

    </body>
</html>
</f:view>
