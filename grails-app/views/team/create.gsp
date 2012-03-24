

<%@ page import="surveyor.Team" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'team.label', default: 'Team')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="Group List" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="Create Group" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${teamInstance}">
            <div class="errors">
                <g:renderErrors bean="${teamInstance}" as="list" />
            </div>
            </g:hasErrors>
            
            <g:form action="createAndSaveMany" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                            	<td valign="top" class="name">
                            		<label for="groupNumber">Please add number of groups: </label>
                            	</td>
                            	<td valign="top" class="value">
                            		<g:textField name="groupNumber" value="${groupNumber}" />
                            	</td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                	<g:hiddenField name="id" value="${courseID}" />
                    <span class="button"><g:submitButton name="createAndSaveMany" class="save" value="${message(code: 'default.bon.create.label', default: 'Create Groups')}" /></span>
                	<span class="button"><g:actionSubmit name="createAndSaveRandom" class="save" action="createAndSaveRandom" value="${message(code: 'default.buon.cate.label', default: 'Create Groups With Random Students')}" /></span>
                	
                </div>
            </g:form>
        </div>
    </body>
</html>
