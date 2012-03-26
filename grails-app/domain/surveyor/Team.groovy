package surveyor

class Team {
    String name
    String comments
    
    static belongsTo = [project: Project]
    static hasMany = [groupAssignments: GroupAssignment]

    static constraints = {
        name blank: false
        comments nullable: true
    }
    
    String toString() {
        name
    }
    
    def getSortedGroupAssignments() {
        groupAssignments.sort{it.student.toLastNameFirstName()}
    }
}
