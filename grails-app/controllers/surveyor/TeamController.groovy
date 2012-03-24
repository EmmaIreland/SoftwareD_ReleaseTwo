package surveyor

class TeamController {


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [teamsInstanceList: Team.list(params), teamsInstanceTotal: Team.count()]
    }

    def create = {
        def teamsInstance = new Team()
        teamsInstance.properties = params
        return [teamsInstance: teamsInstance, courseID: params.projectId]
    }
        
        def createAndSaveMany = {
                if (!isInteger(params.groupNumber)){
                        flash.message = "Please enter a positive integer"	
						List studentList = Project.findById(params.id).course.enrollments*.student
						println studentList
						println studentList.size()
						
                        redirect(action:"create", params:[projectId:params.id])
                
				} else {
                        def groupNum = params.groupNumber.toInteger()
                        def i
                        for(i = 0; i < groupNum; i++) {
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
		
		
		def createAndSaveRandom = {
			if(!isInteger(params.groupNumber)) {
					flash.message = "Please enter a positive integer"
					redirect(action:"create", params:[projectId:params.id])
			}
			else{
					def groupNum = params.groupNumber.toInteger()
					List studentList = Project.findById(params.id).course.enrollments*.student
					
					//List currentGroups = Project.findById(params.id).teams.list()					
					def i
					List groupList = []
					for (i = 0; i < groupNum; i++) {
						def currentTeam = new Team(name:"Group ${i}", project: Project.findById(params.id))	
							currentTeam.save(flush: true)
						groupList.add(currentTeam)
					}
					
					
					
					for (int k = 0; k < studentList.size(); k++) {	
//						def result = true
//						for (int j = 0; j < currentGroups.size(); j++) {
//							if (GroupAssignment.findByUserAndTeam(studentList.get(k), currentGroups.get(j))) {
//								result = false
//							}								
//						}								

							def groupAssignment = new GroupAssignment(student:studentList.get(k), team:groupList.get(k % groupNum))
							groupAssignment.save(flush: true)
						}
					
					flash.message = i + " Groups Created"
					redirect(controller: "project", action: "show", id: params.id)
			}	
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
