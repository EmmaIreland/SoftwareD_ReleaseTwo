

<%@ page import="surveyor.GroupAssignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'groupAssignment.label', default: 'GroupAssignment')}" />
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
            <g:hasErrors bean="${groupAssignmentInstance}">
            <div class="errors">
                <g:renderErrors bean="${groupAssignmentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="student"><g:message code="groupAssignment.student.label" default="Student" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: groupAssignmentInstance, field: 'student', 'errors')}">
                                    <g:select name="student.id" from="${surveyor.User.list()}" optionKey="id" value="${groupAssignmentInstance?.student?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="team"><g:message code="groupAssignment.team.label" default="Team" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: groupAssignmentInstance, field: 'team', 'errors')}">
                                    <g:select name="team.id" from="${surveyor.Team.list()}" optionKey="id" value="${groupAssignmentInstance?.team?.id}"  />
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
