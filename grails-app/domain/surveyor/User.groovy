package surveyor

class User {
    
    String name
    String email
    
    static hasMany = [courses: Course, enrollments: Enrollment, groupAssignments: GroupAssignment]

    static constraints = {
        name blank:false
        email email:true, blank:false
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
        nameParts[nameParts.size()-1]
    }
}
