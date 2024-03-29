

<%@ page import="surveyor.Survey" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'survey.label', default: 'Survey')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${surveyInstance}">
            <div class="errors">
                <g:renderErrors bean="${surveyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="survey.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: surveyInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${surveyInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="survey.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: surveyInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${surveyInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dueDate"><g:message code="survey.dueDate.label" default="Due Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: surveyInstance, field: 'dueDate', 'errors')}">
                                    <g:datePicker name="dueDate" precision="hour" value="${surveyInstance?.dueDate}" default="none" noSelection="['': '']" years="[2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021]"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="owner"><g:message code="survey.owner.label" default="Owner" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: surveyInstance, field: 'owner', 'errors')}">
                                    <g:select name="owner.id" from="${surveyor.User.list()}" optionKey="id" value="${surveyInstance?.owner?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
