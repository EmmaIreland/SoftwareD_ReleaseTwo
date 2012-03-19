package surveyor

class GroupAssignment {
    
    static belongsTo = [student: User, team: Team]

    static constraints = {
        student validator: { student, assignment ->
            Enrollment.findByCourseAndStudent(assignment.team.project.course, student) != null
        }
        team validator: { team, assignment ->
            GroupAssignment.findByTeamAndStudent(team, assignment.student) == null
        }
    }
    
    String toString() {
        student.toString() + ' in ' + team.toString()
    }
}
