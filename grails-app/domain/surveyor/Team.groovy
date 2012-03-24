package surveyor

class Team {
    String name
    
    static belongsTo = [project: Project]
    static hasMany = [groupAssignments: GroupAssignment, surveys:Survey]

    static constraints = {
        name blank: false
    }
    
    String toString() {
        name
    }
    
    def getSortedGroupAssignments() {
        groupAssignments.sort{it.student.toLastNameFirstName()}
    }
}
