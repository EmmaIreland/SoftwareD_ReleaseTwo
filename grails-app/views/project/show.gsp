
<%@ page import="surveyor.Project"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'project.label', default: 'Project')}" />
<title><g:message code="default.show.label" args="[entityName]" />
</title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /> </a> </span> <span class="menuButton"><g:link
				class="list" action="list">
				<g:message code="default.list.label" args="[entityName]" />
			</g:link> </span> <span class="menuButton"><g:link class="create"
				action="create">
				<g:message code="default.new.label" args="[entityName]" />
			</g:link> </span>
	</div>
	<div class="body">
		<h1>
			<g:message code="Project Details" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="dialog">
			<table>
				<tbody>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="project.name.label" default="Name" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: projectInstance, field: "name")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="project.description.label" default="Description" /></td>

						<td valign="top" class="value">
							${fieldValue(bean: projectInstance, field: "description")}
						</td>

					</tr>

					<tr class="prop">
						<td valign="top" class="name"><g:message
								code="project.course.label" default="Course" /></td>

						<td valign="top" class="value"><g:link controller="course"
								action="show" id="${projectInstance?.course?.id}">
								${projectInstance?.course?.encodeAsHTML()}
							</g:link></td>

					</tr>


				</tbody>
			</table>
		</div>
		<div class="buttons">
			<g:form>
				<g:hiddenField name="id" value="${projectInstance?.id}" />
				<span class="button"><g:actionSubmit class="edit"
						action="edit"
						value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
				</span>
				<span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</span>
			</g:form>
		</div>
		<br></br>
		<h1>Project Groups</h1>
		<div class="list">
			<table>
				<thead>
					<tr>

						<g:sortableColumn property="name"
							title="${message(code: 'project.team.label', default: 'Groups')}" />

						<th><g:message code="project.course.label"
								default="Students in Group" /></th>

						<th><g:message code="project.course.label"
								default="Description" /></th>


					</tr>
				</thead>
				<tbody>
					<g:each in="${projectInstance.teams}" status="i" var="k">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

							<td><g:link controller="team" action="show" id="${k.id}">
									${k?.encodeAsHTML()}
								</g:link>
							</td>

							<td valign="top" style="text-align: left;" class="value">
								<ul>
									<g:each in="${k.groupAssignments.student}" var="s">

										<li><g:link controller="user" action="show" id="${s.id}">
												${s?.encodeAsHTML()}
											</g:link></li>
									</g:each>
								</ul>
							</td>
							<td valign="top" class="value">
								${fieldValue(bean: projectInstance, field: "description")}
							</td>

						</tr>
					</g:each>
				</tbody>
			</table>
			<div class="buttons">						
				<span class="menuButton"><g:link class="create" controller="team" action="create" params="['projectId': projectInstance?.id]"><g:message code="Add Groups" args="[entityName]"/></g:link>
				</span>
			</div>

		</div>


	</div>
</body>
</html>
