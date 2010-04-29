<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Consultant Timecard Application - Login</title>
            <link rel="stylesheet" type="text/css" href="/ConsultingAgency/faces/jsfcrud.css" /><%@ include file="/WEB-INF/jspf/AjaxScripts.jspf" %><script type="text/javascript" src="/ConsultingAgency/faces/jsfcrud.js"></script>
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Consultant Timecard Application - Login</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Email:"/>
                    <h:inputText id="email" value="#{consultant.consultant.email}" title="Email" required="true" requiredMessage="The email field is required." />
                    <h:outputText value="Password:"/>
                    <h:inputSecret id="password" value="#{consultant.consultant.password}" title="Password" required="true" requiredMessage="The password field is required." />
                </h:panelGrid>
                <br />
                <h:commandLink action="#{consultant.login}" value="Login"/>
            </h:form>
        </body>
    </html>
</f:view>
