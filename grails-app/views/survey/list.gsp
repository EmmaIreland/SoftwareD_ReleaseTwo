
<%@ page import="surveyor.Survey" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'survey.label', default: 'Survey')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'survey.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'survey.name.label', default: 'Name')}" />
                            
                            <g:sortableColumn property="owner" title="${message(code: 'survey.owner.label', default: 'Owner')}" /> 
                        
                            <g:sortableColumn property="description" title="${message(code: 'survey.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="dueDate" title="${message(code: 'survey.dueDate.label', default: 'Due Date')}" />
                                               
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${surveyInstanceList}" status="i" var="surveyInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${surveyInstance.id}">${fieldValue(bean: surveyInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: surveyInstance, field: "name")}</td>
                            
                            <td>${fieldValue(bean: surveyInstance, field: "owner")}</td>
                        
                            <td>${fieldValue(bean: surveyInstance, field: "description")}</td>
                        
                            <td><g:formatDate date="${surveyInstance.dueDate}" /></td>
                        
                        	    
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${surveyInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
