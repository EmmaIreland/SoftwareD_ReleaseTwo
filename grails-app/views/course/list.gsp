
<%@ page import="surveyor.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
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
                        
                            <g:sortableColumn property="abbreviation" title="${message(code: 'course.abbreviation.label', default: 'Abbreviation')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'course.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="term" title="${message(code: 'course.term.label', default: 'Term')}" />
                        
                            <g:sortableColumn property="year" title="${message(code: 'course.year.label', default: 'Year')}" />
                        
                            <th><g:message code="course.owner.label" default="Owner" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${courseInstanceList}" status="i" var="courseInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${courseInstance.id}">${fieldValue(bean: courseInstance, field: "abbreviation")}</g:link></td>
                        
                            <td>${fieldValue(bean: courseInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: courseInstance, field: "term")}</td>
                        
                            <td>${fieldValue(bean: courseInstance, field: "year")}</td>
                        
                            <td>${fieldValue(bean: courseInstance, field: "owner")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${courseInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
