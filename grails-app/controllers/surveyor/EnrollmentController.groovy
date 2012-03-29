package surveyor

class EnrollmentController {

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
        [enrollmentInstanceList: Enrollment.list(params), enrollmentInstanceTotal: Enrollment.count()]
    }

    def create = {
        def enrollmentInstance = new Enrollment()
        enrollmentInstance.properties = params
        return [enrollmentInstance: enrollmentInstance]
    }

    def save = {
        def enrollmentInstance = new Enrollment(params)
        if (enrollmentInstance.save(flush: true)) {
            flash.message = makeMessage('default.created.message', params.id)
            redirect(action: showString, id: enrollmentInstance.id)
        }
        else {
            render(view: createString, model: [enrollmentInstance: enrollmentInstance])
        }
    }

    def show = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (!enrollmentInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
        else {
            [enrollmentInstance: enrollmentInstance]
        }
    }

    def edit = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (!enrollmentInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
        else {
            return [enrollmentInstance: enrollmentInstance]
        }
    }

    def update = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (enrollmentInstance.version > version) {
                    
                    enrollmentInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', 
						[message(code: 'enrollment.label', default: 'Enrollment')] as Object[], 
						'Another user has updated this Enrollment while you were editing')
                    render(view: editString, model: [enrollmentInstance: enrollmentInstance])
                    return
                }
            }
            enrollmentInstance.properties = params
            if (!enrollmentInstance.hasErrors() && enrollmentInstance.save(flush: true)) {
            flash.message = makeMessage('default.updated.message', params.id)
                redirect(action: showString, id: enrollmentInstance.id)
            }
            else {
                render(view: editString, model: [enrollmentInstance: enrollmentInstance])
            }
        }
        else {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
    }

    def delete = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
            try {
                enrollmentInstance.delete(flush: true)
				flash.message = makeMessage('default.deleted.message', params.id)
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
	
	private makeMessage(code, enrollmentId) {
		return "${message(code: code, args: [enrollmentLabel(), enrollmentId])}"
	}
 
	private enrollmentLabel() {
		message(code: 'enrollment.label', default: 'Enrollment')
	}
}
