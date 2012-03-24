package surveyor

class SurveyAssignment {
	
	static belongsTo = [survey: Survey, student:User]
	
    static constraints = {
		//student unique: 'survey'
    }
}
