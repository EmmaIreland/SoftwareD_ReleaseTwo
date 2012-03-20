

<%@ page import="surveyor.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
	    <g:javascript>
	    	window.onload = function() {
	    		generateLastStudentsFromError();
	    	}
	    </g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${courseInstance}">
            <div class="errors">
                <g:renderErrors bean="${courseInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${courseInstance?.id}" />
                <g:hiddenField name="version" value="${courseInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="abbreviation"><g:message code="course.abbreviation.label" default="Abbreviation" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'abbreviation', 'errors')}">
                                    <g:textField name="abbreviation" value="${courseInstance?.abbreviation}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="course.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${courseInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="term"><g:message code="course.term.label" default="Term" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'term', 'errors')}">
                                    <g:select name="term" from="${courseInstance.constraints.term.inList}" value="${courseInstance?.term}" valueMessagePrefix="course.term"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="year"><g:message code="course.year.label" default="Year" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'year', 'errors')}">
                                    <g:textField name="year" value="${courseInstance?.year}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="enrollments"><g:message code="course.enrollments.label" default="Enrollments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'enrollments', 'errors')}">
                                    
									<ul id="enrollmentList">
									<g:each in="${sortedEnrollments?}" var="e">
									    <li><g:link controller="user" action="show" id="${e.student.id}">${e?.student.toLastNameFirstName().encodeAsHTML()}</g:link></li>
									</g:each>
									</ul>
									
									<g:javascript>
										var newStudentCount = 0;
										
										function generateLastStudentsFromError() {
											<g:each in="${newStudents?}" var="id">
												var studentName = '${fieldValue(bean: User.get(id), field: 'name')}';
												
												addStudent();
												var addedElement = document.getElementById('studentIds' + (newStudentCount-1))
												addedElement.value = "${id}";
												<g:if test="${id.toLong() in enrollmentErrorIds}">
													addedElement.setAttribute('style', 'border-color: red');
												</g:if>
											</g:each>
										}
										
										function addStudent() {
											var newSelect = document.createElement('select');
											newSelect.setAttribute('id', 'studentIds' + newStudentCount);
											newSelect.setAttribute('name', 'studentIds');
											
											<g:each in="${availableStudents}" var="user">
												var newOption = document.createElement('option');
												newOption.setAttribute('value', "${fieldValue(bean: user, field: 'id')}");
												var textNode = document.createTextNode("${user.toLastNameFirstName()}");
												newOption.appendChild(textNode);
												newSelect.appendChild(newOption);
											</g:each>
											
											var deleteIcon = document.createElement('img');
											deleteIcon.setAttribute('src', "${resource(dir:'images',file:'skin/delete.png')}");
											deleteIcon.setAttribute('style', "position: relative; top: 3px; left: 6px;");
											deleteIcon.setAttribute('onClick', "deleteStudent(" + newStudentCount + ");");
											
											var newItem = document.createElement('li');
											newItem.appendChild(newSelect);
											newItem.appendChild(deleteIcon);
											document.getElementById('enrollmentList').appendChild(newItem);
											
											newStudentCount++;
										}
										
										function deleteStudent(n) {
											var select = document.getElementById('studentIds' + n);
											var item = select.parentNode;
											item.parentNode.removeChild(item);
										}
									</g:javascript>
									
									<style type="text/css">
										#addStudentButton {
											border: 1px solid #ccc;
											width: 112px;
											background: #eed;
											border-radius: 6px;
											padding: 1px 4px 0px 4px;
											cursor: pointer;
											margin-top: 6px;
										}
										#addStudentButton #addButtonText {
											position: relative;
											bottom: 4px;
										}
									</style>
									<div id='addStudentButton' onClick='addStudent()' title="Enroll a student in this class.">
										<img src="${resource(dir:'images', file:'skin/add.png')}" alt="add Button" />
										<span id="addButtonText">Enroll a student</span>
									</div>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="owner"><g:message code="course.owner.label" default="Owner" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'owner', 'errors')}">
                                    <g:select name="owner.id" from="${surveyor.User.list()}" optionKey="id" value="${courseInstance?.owner?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="projects"><g:message code="course.projects.label" default="Projects" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: courseInstance, field: 'projects', 'errors')}">
                                    
<ul>
<g:each in="${courseInstance?.projects?}" var="p">
    <li><g:link controller="project" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="project" action="create" params="['course.id': courseInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'project.label', default: 'Project')])}</g:link>

                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
