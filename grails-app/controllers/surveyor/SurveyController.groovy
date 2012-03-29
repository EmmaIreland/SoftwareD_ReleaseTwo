package surveyor

class SurveyController {
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
        [surveyInstanceList: Survey.list(params), surveyInstanceTotal: Survey.count()]
    }

    def create = {
        def surveyInstance = new Survey()
        surveyInstance.properties = params
        return [surveyInstance: surveyInstance]
    }

    def save = {
        def surveyInstance = new Survey(params)
        if (surveyInstance.save(flush: true)) {
            flash.message = makeMessage('default.created.message', surveyInstance.name)
            redirect(action: showString, id: surveyInstance.id)
        }
        else {
            render(view: createString, model: [surveyInstance: surveyInstance])
        }
    }

    def show = {
        def surveyInstance = Survey.get(params.id)
        if (!surveyInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
        else {
            [surveyInstance: surveyInstance]
        }
    }

    def edit = {
        def surveyInstance = Survey.get(params.id)
        if (!surveyInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
        else {
            return [surveyInstance: surveyInstance]
        }
    }

    def update = {
        def surveyInstance = Survey.get(params.id)
        if (surveyInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveyInstance.version > version) {
                    
                    surveyInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', [message(code: 'survey.label', default: 'Survey')] as Object[], 'Another user has updated this Survey while you were editing')
                    render(view: editString, model: [surveyInstance: surveyInstance])
                    return
                }
            }
            surveyInstance.properties = params
            if (!surveyInstance.hasErrors() && surveyInstance.save(flush: true)) {
                flash.message = makeMessage('default.updated.message', surveyInstance.name)
                redirect(action: showString, id: surveyInstance.id)
            }
            else {
                render(view: editString, model: [surveyInstance: surveyInstance])
            }
        }
        else {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
    }

    def delete = {
        def surveyInstance = Survey.get(params.id)
        if (surveyInstance) {
            try {
                surveyInstance.delete(flush: true)
                flash.message = makeMessage('default.deleted.message', surveyInstance.name)
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
	private makeMessage(code, surveyId) {
		return "${message(code: code, args: [surveyLabel(), surveyId])}"
	}
 
	private surveyLabel() {
		message(code: 'survey.label', default: 'Survey')
	}
}
