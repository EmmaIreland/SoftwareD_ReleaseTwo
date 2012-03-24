package surveyor

class Project {
    
    String name
    String description
    
    static belongsTo = [course: Course]
    static hasMany = [teams: Team, surveys:Survey]

    static constraints = {
        name blank: false
        description()
    }
    
    String toString() {
        name
    }
}
