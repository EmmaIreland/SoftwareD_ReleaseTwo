package surveyor

class SurveyAssignment {
	
	static belongsTo = [survey: Survey, student:User]
	
    static constraints = {
           student unique: 'survey'
    }
	
	String toString() {
		student + " assigned to take " + survey
	}
}
