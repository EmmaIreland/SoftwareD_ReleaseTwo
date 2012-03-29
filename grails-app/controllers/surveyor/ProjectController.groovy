package surveyor
class ProjectController {
	
	static def post = 'POST'
	def listString = 'list'
	def editString = 'edit'
	def createString = 'create'
	def showString = 'show'
	def projectString = 'Project'
	def defaultNotFoundMessage = 'default.not.found.message'
	
    static allowedMethods = [save: post, update: post, delete: post]
	
    def index = {
        redirect(action: listString, params: params)
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
            redirect(action: showString, id: projectInstance.id)
        }
        else {
            render(view: createString, model: [projectInstance: projectInstance])
        }
    }
    def show = {
        def projectInstance = Project.get(params.id)
        if (!projectInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
        else {
            [projectInstance: projectInstance]
        }
    }
    def edit = {
        def projectInstance = Project.get(params.id)
        if (!projectInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
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
                   
                    projectInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', [message(code: 'project.label', default: projectString)] as Object[], 'Another user has updated this Project while you were editing')
                    render(view: editString, model: [projectInstance: projectInstance])
                    
                }
            }
            projectInstance.properties = params
            if (!projectInstance.hasErrors() && projectInstance.save(flush: true)) {
                flash.message = makeMessage('default.updated.message',projectInstance.name)
                redirect(action: showString, id: projectInstance.id)
            }
            else {
                render(view: editString, model: [projectInstance: projectInstance])
            }
        }
        else {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
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
                redirect(action: listString)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = makeMessage('default.not.deleted.message', projectInstance.name)
                redirect(action: showString, id: params.id)
            }
        }
        else {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
    }
	
	private makeMessage(code, projectId) {
		return "${message(code: code, args: [projectLabel(), projectId])}"
	}
 
	private projectLabel() {
		message(code: 'project.label', default: projectString)
	}
}