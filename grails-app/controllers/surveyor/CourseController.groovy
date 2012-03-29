package surveyor

class CourseController {
	
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
		[courseInstanceList: Course.list(params), courseInstanceTotal: Course.count()]
	}

	def create = {
		def courseInstance = new Course()
		courseInstance.properties = params
		return [courseInstance: courseInstance]
	}

	def save = {
		def courseInstance = new Course(params)
		if (courseInstance.save(flush: true)) {
				flash.message = makeMessage('default.created.message', courseInstance.toString())
			redirect(action: showString, id: courseInstance.id)
		}
		else {
			render(view: createString, model: [courseInstance: courseInstance])
		}
	}

	def show = {
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
		else {
			[courseInstance: courseInstance, sortedEnrollments: courseInstance.sortedEnrollments]
		}
	}

	def edit = {
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
		else {
			List allUsers = new ArrayList(User.list())
			List allUsersExceptOwner = []

			allUsers = allUsers - courseInstance.enrollments*.student

			for(int i = 0; i < allUsers.size; i++){
				if(allUsers.get(i).id!= courseInstance.owner.id){
					allUsersExceptOwner.add(allUsers.get(i))
				}
			}

			allUsersExceptOwner.sort{
				it.toLastNameFirstName()
			}

			[courseInstance: courseInstance, sortedEnrollments: courseInstance.sortedEnrollments, availableStudents: allUsersExceptOwner, hasAvailableStudents: (allUsers.size() > 0), User: User]
		}
	}

	def update = {
		def courseInstance = Course.get(params.id)
		if (courseInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (courseInstance.version > version) {

					courseInstance.errors.rejectValue('version', 'default.optimistic.locking.failure', [
						message(code: 'course.label', default: 'Course')]
					as Object[], 'Another user has updated this Course while you were editing')
					render(view: editString, model: [courseInstance: courseInstance])
					return
				}
			}
			courseInstance.properties = params

			def studentIds = params.list('studentIds')

			List students = []
			studentIds.each { studentId ->
				students.add(User.get(studentId)) // TODO verify valid id
			}
			students.unique()

			List enrollmentsToBeChecked = []
			students.each { student ->
				enrollmentsToBeChecked.add( new Enrollment(student: student, course: courseInstance) )
			}

			List enrollmentErrorIds = []
			enrollmentsToBeChecked.each { enrollment ->
				if ( !enrollment.validate() ) {
					enrollmentErrorIds.add(enrollment.student.id)
				}
			}
			if (!enrollmentErrorIds.isEmpty()) {
				courseInstance.errors.reject('course.newenrollments.failed')
			}

			
			if (!courseInstance.hasErrors() && enrollmentErrorIds.isEmpty() && courseInstance.save(flush: true)) {
				enrollmentsToBeChecked.each { enrollment ->
					enrollment.save(failOnError: true)
				}

				flash.message = makeMessage('default.updated.message', courseInstance.toString())
				redirect(action: showString, id: courseInstance.id)
			}
			else {
				render(view: editString, model: [courseInstance: courseInstance,
					sortedEnrollments: courseInstance.sortedEnrollments,
					newStudents: studentIds,
					enrollmentErrorIds: enrollmentErrorIds,
					User: User])
			}
		}
		else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
	}

	def delete = {
		def courseInstance = Course.get(params.id)
		if (courseInstance) {
			try {
				courseInstance.delete(flush: true)
				flash.message = makeMessage('default.deleted.message', courseInstance.toString())
				redirect(action: listString)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = makeMessage('default.not.deleted.message', courseInstance.toString())
				redirect(action: showString, id: params.id)
			}
		}
		else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
	}

	
	private makeMessage(code, courseId) {
		return "${message(code: code, args: [courseLabel(), courseId])}"
	}
 
	private courseLabel() {
		message(code: 'course.label', default: 'Course')
	}
}
