package surveyor
class ProjectController {
    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']
    def index = {
        redirect(action: 'list', params: params)
    }
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [projectInstanceList: Project.list(params), projectInstanceTotal: Project.count()]
    }
    def create = {
        def projectInstance = new Project()
        projectInstance.properties = params
        return [projectInstance: projectInstance]
    }
    def save = {
        def projectInstance = new Project(params)
        if (projectInstance.save(flush: true)) {
            flash.message = makeMessage('default.created.message', projectInstance.name)
            redirect(action: 'show', id: projectInstance.id)
        }
        else {
            render(view: 'create', model: [projectInstance: projectInstance])
        }
    }
    def show = {
        def projectInstance = Project.get(params.id)
        if (!projectInstance) {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
        else {
            [projectInstance: projectInstance]
        }
    }
    def edit = {
        def projectInstance = Project.get(params.id)
        if (!projectInstance) {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
        else {
            return [projectInstance: projectInstance]
        }
    }
    def update = {
        def projectInstance = Project.get(params.id)
        if (projectInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (projectInstance.version > version) {
                   
                    projectInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', [message(code: 'project.label', default: 'Project')] as Object[], 'Another user has updated this Project while you were editing')
                    render(view: 'edit', model: [projectInstance: projectInstance])
                    
                }
            }
            projectInstance.properties = params
            if (!projectInstance.hasErrors() && projectInstance.save(flush: true)) {
                flash.message = makeMessage('default.updated.message',projectInstance.name)
                redirect(action: 'show', id: projectInstance.id)
            }
            else {
                render(view: 'edit', model: [projectInstance: projectInstance])
            }
        }
        else {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
    }
    
    
   
//    def reRandomize = {
//        def projectInstance = Project.get(params.id)
//        def groupsToRandomize = 0
//        println 'HERE WE GO'
//        List listOfTeamsToDelete = []
//        // def controller = new TeamController()
//        
//        
//        projectInstance.teams.each {
//            if(it.randomize) {
//                controller.delete().it
//                groupsToRandomize++
//               // println projectInstance.teams.size()
//            }
//        }
//        
//        println groupsToRandomize
//        params.groupNumber = groupsToRandomize.toString().toInteger()
//        params.projectInstance = projectInstance 
//        params.projectID = params.id
//        params.id = null
//        redirect(controller:"team", action:"createAndSaveRandom", params: params)
//    }
    
   
    
    
    
    def delete = {
        def projectInstance = Project.get(params.id)
        if (projectInstance) {
            try {
                projectInstance.delete(flush: true)
                flash.message = makeMessage('default.deleted.message', projectInstance.name)
                redirect(action: 'list')
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = makeMessage('default.not.deleted.message', projectInstance.name)
                redirect(action: 'show', id: params.id)
            }
        }
        else {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
    }
	
	private makeMessage(code, projectId) {
		return "${message(code: code, args: [projectLabel(), projectId])}"
	}
 
	private projectLabel() {
		message(code: 'project.label', default: 'Project')
	}
}