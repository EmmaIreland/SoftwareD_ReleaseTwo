package surveyor

class Survey {
    String name
    String description
    Date dueDate
    
    static belongsTo = [course: Course, project: Project, team: Team]

    static constraints = {
        name blank: false
        description blank: true
        dueDate nullable: true
        course nullable: true
        project nullable: true
        team nullable: true, validator: { team, survey ->
            def course = survey.course
            def project = survey.project
            def compatible = true
            
            if (course && project) {
                compatible &= project.course == course
            }
            if (project && team) {
                compatible &= team.project == project
            }
            if (team && course) {
                compatible &= team.project.course == course
            }
            
            compatible
        }
    }
    
    String toString() {
        name
    }
}
