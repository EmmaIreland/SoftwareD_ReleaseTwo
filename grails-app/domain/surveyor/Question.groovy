package surveyor

class Question {
    
    String question
    
    static belongsTo = [survey: Survey]
    static hasMany = [answers : Answer]
    
    String toString() {
        question
    }
    
    static constraints = {
    }
}
