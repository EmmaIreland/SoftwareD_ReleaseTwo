package surveyor

class TeamController {

	static def post = 'POST'
	def listString = 'list'
	def editString = 'edit'
	def createString = 'create'
	def showString = 'show'
	def defaultNotFoundMessage = 'default.not.found.message'
	
    static allowedMethods = [save: post, update: post, delete: post]

    def index = {
        redirect(action: listString, params: params)
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
            flash.message = 'Please enter a positive integer'
            redirect(action:createString, params:[projectId:params.id])
        } else {
            def groupNum = params.groupNumber.toInteger()
            def i
            for(i = 0; i < groupNum; i++) {
                def projectInstance = new Team(name:"Group ${i}", project: Project.findById(params.id))
                projectInstance.save(flush: true)
            }

            flash.message = i + ' Empty Groups Created'
            redirect(controller: 'project', action: showString, id: params.id)
        }
    }

    private isInteger(num) {
        def bool = false
        if(num.isNumber() && (num.toInteger()>0)){
            bool = true
        }
        bool
    }


    def createAndSaveRandom = {
        def teamsInstance = new Team()
        teamsInstance.properties = params

    //    println 'The value of groupNumber is ' + params.groupNumber
    //    println 'The class of groupNumber is ' + params.groupNumber.class
        

        if(!isInteger(params.groupNumber)) {
            flash.message = 'Please enter a positive integer'
            redirect(action:createString, params:[projectId:params.id])
        }
        
        else{
            def groupNum = params.groupNumber.toInteger()
            def i
            List studentList = new ArrayList(new ArrayList(Project.findById(params.id).course.enrollments*.student))
            List currentStudents = Project.findById(params.id).teams.groupAssignments*.student
            for (int j = 0; j < currentStudents.size(); j++) {
                studentList = studentList - currentStudents[j]
            }


            List groupList = []
            for (i = 0; i < groupNum; i++) {
                def currentTeam = new Team(name:"Group ${i}", project: Project.findById(params.id))
               // currentTeam.randomize = true
                currentTeam.save(flush: true)
                groupList.add(currentTeam)
            }

            for (int k = 0; k < studentList.size(); k++) {
                def groupAssignment = new GroupAssignment(student:studentList.get(k), team:groupList.get(k % groupNum))

                groupAssignment.save(flush: true)
            }

            flash.message = i + ' Groups Created'
            redirect(controller: 'project', action: showString, id: params.id)
        }
    }





    def save = {
        def teamInstance = new Team(params)
        if (teamInstance.save(flush: true)) {
            flash.message = makeMessage('default.created.message', teamInstance.name)
            redirect(action: showString, id: teamInstance.id)
        }
        else {
            render(view: createString, model: [teamInstance: teamInstance])
        }
    }

    def show = {
        def teamInstance = Team.get(params.id)
        if (!teamInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
        else {

            [teamInstance: teamInstance, sortedGroupAssignments: teamInstance.sortedGroupAssignments]
        }
    }

    def edit = {
        def teamInstance = Team.get(params.id)
        if (!teamInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
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

                    teamInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', [
                        message(code: 'team.label', default: 'Team')]
                    as Object[], 'Another user has updated this Team while you were editing')
                    render(view: editString, model: [teamInstance: teamInstance])
                    return
                }
            }
            teamInstance.properties = params
            if (!teamInstance.hasErrors() && teamInstance.save(flush: true)) {
                flash.message = makeMessage('default.updated.message', teamInstance.name)
                redirect(action: showString, id: teamInstance.id)
            }
            else {
                render(view: editString, model: [teamInstance: teamInstance])
            }
        }
        else {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
    }

    def delete = {
        def teamInstance = Team.get(params.id)
        if (teamInstance) {
            try {
                teamInstance.delete(flush: true)
                flash.message = makeMessage('default.deleted.message', teamInstance.name)
                redirect(action: listString)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = makeMessage('default.not.deleted.message', params.id)
                redirect(action: showString, id: params.id)
            }
        }
        else {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
    }

    private makeMessage(code, teamId) {
        return "${message(code: code, args: [teamLabel(), teamId])}"
    }

    private teamLabel() {
        message(code: 'team.label', default: 'Team')
    }
}
