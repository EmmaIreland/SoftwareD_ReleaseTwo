package surveyor

class SurveyController {

    static allowedMethods = [save: 'POST', update: 'POST', delete: 'POST']

    def index = {
        redirect(action: 'list', params: params)
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
            redirect(action: 'show', id: surveyInstance.id)
        }
        else {
            render(view: 'create', model: [surveyInstance: surveyInstance])
        }
    }

    def show = {
        def surveyInstance = Survey.get(params.id)
        if (!surveyInstance) {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
        else {
            [surveyInstance: surveyInstance]
        }
    }

    def edit = {
        def surveyInstance = Survey.get(params.id)
        if (!surveyInstance) {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
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
                    render(view: 'edit', model: [surveyInstance: surveyInstance])
                    return
                }
            }
            surveyInstance.properties = params
            if (!surveyInstance.hasErrors() && surveyInstance.save(flush: true)) {
                flash.message = makeMessage('default.updated.message', surveyInstance.name)
                redirect(action: 'show', id: surveyInstance.id)
            }
            else {
                render(view: 'edit', model: [surveyInstance: surveyInstance])
            }
        }
        else {
            flash.message = makeMessage('default.not.found.message', params.id)
            redirect(action: 'list')
        }
    }

    def delete = {
        def surveyInstance = Survey.get(params.id)
        if (surveyInstance) {
            try {
                surveyInstance.delete(flush: true)
                flash.message = makeMessage('default.deleted.message', surveyInstance.name)
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
	private makeMessage(code, surveyId) {
		return "${message(code: code, args: [surveyLabel(), surveyId])}"
	}
 
	private surveyLabel() {
		message(code: 'survey.label', default: 'Survey')
	}
}
