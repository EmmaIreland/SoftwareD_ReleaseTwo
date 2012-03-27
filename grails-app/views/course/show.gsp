
<%@ page import="surveyor.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>${courseInstance.toString()}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.abbreviation.label" default="Abbreviation" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "abbreviation")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.term.label" default="Term" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "term")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.year.label" default="Year" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "year")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.owner.label" default="Owner" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${courseInstance?.owner?.id}">${courseInstance?.owner?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.enrollments.label" default="Enrollments" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${sortedEnrollments}" var="e">
                                    <li><g:link controller="user" action="show" id="${e.student.id}">${e?.student.toLastNameFirstName().encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.projects.label" default="Projects" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${courseInstance.projects}" var="p">
                                    <li><g:link controller="project" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${courseInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
            <br></br>
         <h1>${courseInstance.toString()} Projects</h1>
		<div class="list">
			<table>
				<thead>
					<tr>

						<g:sortableColumn property="name"
							title="${message(code: 'course.project.label', default: 'Projects')}" />

						<th><g:message code="course.project.description.label"
								default="Project Description" /></th>
								
						<th><g:message code="course.project.groups.label"
								default="Groups Assigned to Project" /></th>
								
					</tr>
				</thead>
				<tbody>
					<g:each in="${courseInstance.projects}" status="i" var="k">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

							<td><g:link controller="project" action="show" id="${k.id}">
									${k?.encodeAsHTML()}
								</g:link>
							</td>
							
							<td valign="top" class="value">
								${fieldValue(bean: k, field: "description")}
							</td>

							<td valign="top" style="text-align: left;" class="value">
								<ul>
									<g:each in="${k.teams}" var="s">

										<li><g:link controller="team" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
									</g:each>
								</ul>
							</td>			
						</tr>
					</g:each>
				</tbody>
			</table>
			<div class="buttons">						
				<span class="menuButton"><g:link class="create" controller="project" action="create" params="['courseId': courseInstance?.id]"><g:message code="Add Projects" args="[entityName]"/></g:link>
				</span>
			</div>

		</div>
        </div>
    </body>
</html>
