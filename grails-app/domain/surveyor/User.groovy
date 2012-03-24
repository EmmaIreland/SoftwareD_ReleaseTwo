package surveyor

class User {
    
    String name
    String email
    
    static hasMany = [courses: Course, enrollments: Enrollment,
		 groupAssignments: GroupAssignment, surveyAssignments: SurveyAssignment]

    static constraints = {
        name blank:false
        email email:true
    }
    
    String toString() {
        name
    }
    
    String toLastNameFirstName() { 
        lastName + ', ' + firstName
    }
    
    def getFirstName() {
        name.split()[0]
    }
    
    def getLastName() {
        def nameParts = name.split()
        nameParts.last()
    }
}
