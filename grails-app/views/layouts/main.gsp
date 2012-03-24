<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Qwertyuiop Survey Tool" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.png')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="sideBarNav">
        	<a href="${createLink(uri: '/')}"><img id="appLogo" src="${resource(dir:'images',file:'ccs_logo.png')}" alt="Grails" border="0" /></a>
        	<br>
        	<a href="${createLink(uri: '/')}"><div id="appName">Qwertyuiop</div></a>
        	<div id="controllers">
        		<div id="title">Manage</div>
        		<div id="list">
	        		<ul>
					<li class="controller"><g:link controller="course">Courses</g:link></li>
					<li class="controller"><g:link controller="project">Projects</g:link></li>
					<li class="controller"><g:link controller="team">Groups</g:link></li>
					<li class="controller"><g:link controller="survey">Surveys</g:link></li>
					<li class="controller"><g:link controller="user">Users</g:link></li>
                </ul>
                </div>
        	</div>
        </div>
        <div style="float: left">
        	<g:layoutBody />
        </div>
    </body>
</html>