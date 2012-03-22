package surveyor

class CourseController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
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
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'course.label', default: 'Course'), courseInstance.toString()])}"
			redirect(action: "show", id: courseInstance.id)
		}
		else {
			render(view: "create", model: [courseInstance: courseInstance])
		}
	}

	def show = {
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "list")
		}
		else {
			[courseInstance: courseInstance, sortedEnrollments: courseInstance.sortedEnrollments]
		}
	}

	def edit = {
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "list")
		}
		else {
			List allUsers = new ArrayList(User.list())
			List allUsersExceptOwner = new ArrayList()


			allUsers = allUsers - courseInstance.enrollments*.student

			for(int i = 0; i < allUsers.size; i++){
				if(allUsers.get(i).id!= courseInstance.owner.id){
					allUsersExceptOwner.add(allUsers.get(i))
				}
			}

			allUsersExceptOwner.sort{it.toLastNameFirstName()}

			[courseInstance: courseInstance, sortedEnrollments: courseInstance.sortedEnrollments, availableStudents: allUsersExceptOwner, hasAvailableStudents: (allUsers.size() > 0), User: User]
		}
	}

	def update = {
		def courseInstance = Course.get(params.id)
		if (courseInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (courseInstance.version > version) {

					courseInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'course.label', default: 'Course')]
					as Object[], "Another user has updated this Course while you were editing")
					render(view: "edit", model: [courseInstance: courseInstance])
					return
				}
			}
			courseInstance.properties = params

			// do new Enrollments
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
			// end do new Enrollments

			if (!courseInstance.hasErrors() && enrollmentErrorIds.isEmpty() && courseInstance.save(flush: true)) {
				enrollmentsToBeChecked.each { enrollment ->
					enrollment.save(failOnError: true)
				}

				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'course.label', default: 'Course'), courseInstance.toString()])}"
				redirect(action: "show", id: courseInstance.id)
			}
			else {
				render(view: "edit", model: [courseInstance: courseInstance,
							sortedEnrollments: courseInstance.sortedEnrollments,
							newStudents: studentIds,
							enrollmentErrorIds: enrollmentErrorIds,
							User: User])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def courseInstance = Course.get(params.id)
		if (courseInstance) {
			try {
				courseInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'course.label', default: 'Course'), courseInstance.toString()])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'course.label', default: 'Course'), courseInstance.toString()])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "list")
		}
	}
}
