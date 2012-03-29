package surveyor

class GroupAssignmentController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    def index = {
        redirect(action: 'list', params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [groupAssignmentInstanceList: GroupAssignment.list(params), groupAssignmentInstanceTotal: GroupAssignment.count()]
    }

    def create = {
        def groupAssignmentInstance = new GroupAssignment()
        groupAssignmentInstance.properties = params
        return [groupAssignmentInstance: groupAssignmentInstance]
    }

    def save = {
        def groupAssignmentInstance = new GroupAssignment(params)
		def project = GroupAssignment.get(groupAssignmentInstance.team.project.id)
        if (groupAssignmentInstance.save(flush: true)) {
            flash.message = makeMessageStudentTeam('groupAssignment.created.message',  groupAssignmentInstance.id, groupAssignmentInstance.student, groupAssignmentInstance.team)
            redirect(action: 'show', id: project.id)
        }
        else {
            render(view: 'create', model: [groupAssignmentInstance: groupAssignmentInstance])
        }
    }

    def show = {
        def groupAssignmentInstance = GroupAssignment.get(params.id)
        if (!groupAssignmentInstance) {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
        else {
            [groupAssignmentInstance: groupAssignmentInstance]
        }
    }

    def edit = {
        def groupAssignmentInstance = GroupAssignment.get(params.id)
        if (!groupAssignmentInstance) {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
        else {
            def teamInstance = Team.findById(params.teamId)
            def projectInstance = teamInstance.project
            List groupsInProject = new ArrayList(projectInstance.teams)
            
            List studentList = new ArrayList(new ArrayList(projectInstance.course.enrollments*.student))

            return [groupAssignmentInstance: groupAssignmentInstance, 
                    groupsInProject: groupsInProject, studentList: studentList]
        }
    }

    def update = {
        def groupAssignmentInstance = GroupAssignment.get(params.id)
	def project = GroupAssignment.get(groupAssignmentInstance.team.project.id)
        if (groupAssignmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (groupAssignmentInstance.version > version) {
                    
                    groupAssignmentInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', [message(code: 'groupAssignment.label', default: 'GroupAssignment')] as Object[], 'Another user has updated this GroupAssignment while you were editing')
                    render(view: 'edit', model: [groupAssignmentInstance: groupAssignmentInstance])
                    return
                }
            }
            groupAssignmentInstance.properties = params
            if (!groupAssignmentInstance.hasErrors() && groupAssignmentInstance.save(flush: true)) {
            flash.message = makeMessageStudentTeam('groupAssignment.updated.message', groupAssignmentInstance.id, groupAssignmentInstance.student, groupAssignmentInstance.team)
                redirect(controller: 'project', action: 'show', id: project.id)
            }
            else {
                List groupsInProject = new ArrayList(Project.findById(project.id).teams)               
                List studentList = new ArrayList(new ArrayList(Project.findById(project.id).course.enrollments*.student))
               
                 render(view: 'edit', model: [groupAssignmentInstance: groupAssignmentInstance, 
                     groupsInProject: groupsInProject,studentList: studentList])
            }
        }
        else {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
    }

    def delete = {
        def groupAssignmentInstance = GroupAssignment.get(params.id)
        def projectInstance = GroupAssignment.get(groupAssignmentInstance.team.project.id)
        if (groupAssignmentInstance) {
            try {
                groupAssignmentInstance.delete(flush: true)
            flash.message = makeMessageStudentTeam('groupAssignment.deleted.message', groupAssignmentInstance.id,groupAssignmentInstance.student, groupAssignmentInstance.team)
                redirect(controller: 'project', action: 'show', id: projectInstance.id)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = makeMessage('default.not.deleted.message', groupAssignmentInstance.id)
                redirect(action: 'show', id: params.id)
            }
        }
        else {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
    }
	
	private makeMessage(code, groupAssignmentId) {
		return "${message(code: code, args: [groupAssignmentLabel(), groupAssignmentId])}"
	}
    
        private makeMessageStudentTeam(code, groupAssignmentId,
            groupAssignmentStudent, groupAssignmentTeam) {
            return "${message(code: code, args: [groupAssignmentLabel(), groupAssignmentId,groupAssignmentStudent, groupAssignmentTeam])}"
        }
 
	private groupAssignmentLabel() {
		message(code: 'groupAssignment.label', default: 'GroupAssignment')
	}
}
