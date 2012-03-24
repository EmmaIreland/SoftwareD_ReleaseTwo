package surveyor

class Survey {
	String name
	String description
	Date dueDate

	static hasMany = [studentAssignments: SurveyAssignment]
	static belongsTo = [owner:User]

	static constraints = {
		name blank: false
		description blank: true
		dueDate nullable: true, validator: {dueDate ->
			if(dueDate != null) {
				dueDate.after(new Date().previous())
			}
		}
		studentAssignments nullable: true
	}

	String toString() {
		name
	}
}
