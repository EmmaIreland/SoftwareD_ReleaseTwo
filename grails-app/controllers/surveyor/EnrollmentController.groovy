package surveyor

class EnrollmentController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    def index = {
        redirect(action: 'list', params: params)
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
            redirect(action: 'show', id: enrollmentInstance.id)
        }
        else {
            render(view: 'create', model: [enrollmentInstance: enrollmentInstance])
        }
    }

    def show = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (!enrollmentInstance) {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
        else {
            [enrollmentInstance: enrollmentInstance]
        }
    }

    def edit = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (!enrollmentInstance) {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
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
                    render(view: 'edit', model: [enrollmentInstance: enrollmentInstance])
                    return
                }
            }
            enrollmentInstance.properties = params
            if (!enrollmentInstance.hasErrors() && enrollmentInstance.save(flush: true)) {
            flash.message = makeMessage('default.updated.message', params.id)
                redirect(action: 'show', id: enrollmentInstance.id)
            }
            else {
                render(view: 'edit', model: [enrollmentInstance: enrollmentInstance])
            }
        }
        else {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
    }

    def delete = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
            try {
                enrollmentInstance.delete(flush: true)
				flash.message = makeMessage('default.deleted.message', params.id)
                redirect(action: 'list')
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = makeMessage('default.not.deleted.message', params.id)
                redirect(action: 'show', id: params.id)
            }
        }
        else {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
    }
	
	private makeMessage(code, enrollmentId) {
		return "${message(code: code, args: [enrollmentLabel(), enrollmentId])}"
	}
 
	private enrollmentLabel() {
		message(code: 'enrollment.label', default: 'Enrollment')
	}
}
