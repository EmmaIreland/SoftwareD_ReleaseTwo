
<%@ page import="surveyor.Team" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'team.label', default: 'Team')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            
        </div>
        <div class="body">
            <h1><g:message code="Group List" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                                                
                            <g:sortableColumn property="name" title="${message(code: 'team.name.label', default: 'Name')}" />
                        
                            <th><g:message code="team.project.label" default="Project" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${teamsInstanceList}" status="i" var="teamsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${teamsInstance.id}">${fieldValue(bean: teamsInstance, field: "name")}</g:link></td>
                                                
                            <td>${fieldValue(bean: teamsInstance, field: "project")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${teamsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
