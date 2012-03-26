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
        if (groupAssignmentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment'), groupAssignmentInstance.id])}"
            redirect(action: 'show', id: groupAssignmentInstance.id)
        }
        else {
            render(view: 'create', model: [groupAssignmentInstance: groupAssignmentInstance])
        }
    }

    def show = {
        def groupAssignmentInstance = GroupAssignment.get(params.id)
        if (!groupAssignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment'), params.id])}"
            redirect(action: 'list')
        }
        else {
            [groupAssignmentInstance: groupAssignmentInstance]
        }
    }

    def edit = {
        def groupAssignmentInstance = GroupAssignment.get(params.id)
        if (!groupAssignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment'), params.id])}"
            redirect(action: 'list')
        }
        else {
            return [groupAssignmentInstance: groupAssignmentInstance]
        }
    }

    def update = {
        def groupAssignmentInstance = GroupAssignment.get(params.id)
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
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment'), groupAssignmentInstance.id])}"
                redirect(action: 'show', id: groupAssignmentInstance.id)
            }
            else {
                render(view: 'edit', model: [groupAssignmentInstance: groupAssignmentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment'), params.id])}"
            redirect(action: 'list')
        }
    }

    def delete = {
        def groupAssignmentInstance = GroupAssignment.get(params.id)
        //def projectInstance = GroupAssignment.get(groupAssignmentInstance.team.project.id)
        if (groupAssignmentInstance) {
            try {
                groupAssignmentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment'), params.id])}"
                redirect(controller: 'project', action: 'show'/*, id: projectInstance.id*/)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment'), params.id])}"
                redirect(action: 'show', id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'groupAssignment.label', default: 'GroupAssignment'), params.id])}"
            redirect(action: 'list')
        }
    }
}
