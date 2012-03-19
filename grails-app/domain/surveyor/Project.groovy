package surveyor

class Project {
    
    String name
    String description
    
    static belongsTo = [course: Course]
    static hasMany = [teams: Team]

    static constraints = {
        name blank: false
        description() // TODO should project description be able to be blank
    }
    
    String toString() {
        name
    }
}
