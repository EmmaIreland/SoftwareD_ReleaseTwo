package surveyor

class TeamController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    def index = {
        redirect(action: 'list', params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [teamInstanceList: Team.list(params), teamInstanceTotal: Team.count()]
    }

    def create = {
        def teamInstance = new Team()
        teamInstance.properties = params
        return [teamInstance: teamInstance]
    }
    
    
    
    def createAndSaveMany = {
        println project.id
        if(!isInteger(params.groupNumber)){
                flash.message = "Please enter a positive integer"
                redirect(action:"create", params:[projectId:params.id])
        }
        else{
                def groupNum = params.groupNumber.toInteger()
                def i
                for(i = 0; i < groupNum; i++){
                        def projectInstance = new Team(name:"Group ${i}", project: Project.findById(params.id))
                        projectInstance.save(flush: true)
                }
                flash.message = i + " Groups Created"
                redirect(controller: "project", action: "show", id: params.id)
        }
}

def isInteger(num) {
        def bool = false
        if(num.isNumber() && (num.toInteger()>0)){
                bool = true
        }
        bool
}
    
    

    def save = {
        def teamInstance = new Team(params)
        if (teamInstance.save(flush: true)) {
            flash.message = "${message(code: 'team.created.message', args: [message(code: 'team.label', default: 'Team'), teamInstance.name])}"
            redirect(action: 'show', id: teamInstance.id)
        }
        else {
            render(view: 'create', model: [teamInstance: teamInstance])
        }
    }

    def show = {
        def teamInstance = Team.get(params.id)
        if (!teamInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'team.label', default: 'Team'), params.id])}"
            redirect(action: 'list')
        }
        else {
            
            [teamInstance: teamInstance, sortedGroupAssignments: teamInstance.sortedGroupAssignments]
        }
    }

    def edit = {
        def teamInstance = Team.get(params.id)
        if (!teamInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'team.label', default: 'Team'), params.id])}"
            redirect(action: 'list')
        }
        else {
            return [teamInstance: teamInstance, sortedGroupAssignments: teamInstance.sortedGroupAssignments]
        }
    }

    def update = {
        def teamInstance = Team.get(params.id)
        if (teamInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (teamInstance.version > version) {
                    
                    teamInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', [message(code: 'team.label', default: 'Team')] as Object[], 'Another user has updated this Team while you were editing')
                    render(view: 'edit', model: [teamInstance: teamInstance])
                    return
                }
            }
            teamInstance.properties = params
            if (!teamInstance.hasErrors() && teamInstance.save(flush: true)) {
                flash.message = "${message(code: 'team.updated.message', args: [message(code: 'team.label', default: 'Team'), teamInstance.name])}"
                redirect(action: 'show', id: teamInstance.id)
            }
            else {
                render(view: 'edit', model: [teamInstance: teamInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'team.label', default: 'Team'), params.id])}"
            redirect(action: 'list')
        }
    }

    def delete = {
        def teamInstance = Team.get(params.id)
        if (teamInstance) {
            try {
                teamInstance.delete(flush: true)
                flash.message = "${message(code: 'team.deleted.message', args: [message(code: 'team.label', default: 'Team'), teamInstance.name])}"
                redirect(action: 'list')
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'team.not.deleted.message', args: [message(code: 'team.label', default: 'Team'), teamInstance.name])}"
                redirect(action: 'show', id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'team.label', default: 'Team'), params.id])}"
            redirect(action: 'list')
        }
    }
}
