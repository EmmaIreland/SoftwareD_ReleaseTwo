package surveyor

class QuestionController {

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
        [questionInstanceList: Question.list(params), questionInstanceTotal: Question.count()]
    }

    def create = {
        def questionInstance = new Question()
        questionInstance.properties = params
        return [questionInstance: questionInstance]
    }

    def save = {
        def questionInstance = new Question(params)
        if (questionInstance.save(flush: true)) {
            flash.message = makeMessage('default.created.message', questionInstance.id)
            redirect(action: showString, id: questionInstance.id)
        }
        else {
            render(view: createString, model: [questionInstance: questionInstance])
        }
    }

    def show = {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
        else {
            [questionInstance: questionInstance]
        }
    }

    def edit = {
        def questionInstance = Question.get(params.id)
        if (!questionInstance) {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
        else {
            return [questionInstance: questionInstance]
        }
    }

    def update = {
        def questionInstance = Question.get(params.id)
        if (questionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (questionInstance.version > version) {
                    
                    questionInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', [message(code: 'question.label', default: 'Question')] as Object[], 'Another user has updated this Question while you were editing')
                    render(view: editString, model: [questionInstance: questionInstance])
                    return
                }
            }
            questionInstance.properties = params
            if (!questionInstance.hasErrors() && questionInstance.save(flush: true)) {
                flash.message = makeMessage('default.updated.message', questionInstance.id)
                redirect(action: showString, id: questionInstance.id)
            }
            else {
                render(view: editString, model: [questionInstance: questionInstance])
            }
        }
        else {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
    }

    def delete = {
        def questionInstance = Question.get(params.id)
        if (questionInstance) {
            try {
                questionInstance.delete(flush: true)
                flash.message = makeMessage('default.deleted.message', questionInstance.question)
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
	private makeMessage(code, questionId) {
		return "${message(code: code, args: [questionLabel(), questionId])}"
	}
 
	private questionLabel() {
		message(code: 'question.label', default: 'question')
	}
}
