

<%@ page import="surveyor.Team"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'team.label', default: 'Team')}" />
<title><g:message code="default.edit.label" args="[entityName]" />
</title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" />
		</a>
		</span> <span class="menuButton"><g:link class="list" action="list">
				<g:message code="Group List" args="[entityName]" />
			</g:link>
		</span> <span class="menuButton"><g:link class="create"
				action="create">
				<g:message code="New Group" args="[entityName]" />
			</g:link>
		</span>
	</div>
	<div class="body">
		<h1>
			<g:message code="Edit Group" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${teamInstance}">
			<div class="errors">
				<g:renderErrors bean="${teamInstance}" as="list" />
			</div>
		</g:hasErrors>
		<g:form method="post">
			<g:hiddenField name="id" value="${teamInstance?.id}" />
			<g:hiddenField name="version" value="${teamInstance?.version}" />
			<div class="dialog">
				<table>
					<tbody>

						<tr class="prop">
							<td valign="top" class="name"><label for="name"><g:message
										code="team.name.label" default="Name" />
							</label></td>
							<td valign="top"
								class="value ${hasErrors(bean: teamInstance, field: 'name', 'errors')}">
								<g:textField name="name" value="${teamInstance?.name}" /></td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="comments"><g:message
										code="team.comments.label" default="Comments" />
							</label></td>
							<td valign="top"
								class="value ${hasErrors(bean: teamInstance, field: 'comments', 'errors')}">
								<g:textField name="comments" value="${teamInstance?.comments}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="groupAssignments"><g:message
										code="team.groupAssignments.label" default="Students in Group" />
							</label></td>
							<td valign="top"
								class="value ${hasErrors(bean: teamInstance, field: 'groupAssignments', 'errors')}">

								<ul>
									<g:each in="${teamInstance?.groupAssignments?}" var="g">
										<li><g:link controller="user" action="show" id="${g.student.id}">${g?.student.toLastNameFirstName().encodeAsHTML()}	</g:link> 
										(<g:link controller="groupAssignment" action="edit"	params="['teamId': teamInstance?.id]" id="${g.id}">Update or Delete</g:link>)


										</li>
									</g:each>
								</ul> <g:link controller="groupAssignment" action="create"
									params="['team.id': teamInstance?.id]">
									${message(code: 'default.add.label', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment')])}
								</g:link></td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="project"><g:message
										code="team.project.label" default="Project" />
							</label></td>
							<td valign="top"
								class="value ${hasErrors(bean: teamInstance, field: 'project', 'errors')}">
								<g:select name="project.id" from="${surveyor.Project.list()}"
									optionKey="id" value="${teamInstance?.project?.id}" /></td>
						</tr>

					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"><g:actionSubmit class="save"
						action="update"
						value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</span> <span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</span>
			</div>
		</g:form>
	</div>
</body>
</html>
