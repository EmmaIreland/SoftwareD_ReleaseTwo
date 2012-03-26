package surveyor

class Question {
    
    String question
    
    static belongsTo = [survey: Survey]
    
    String toString() {
        question
    }
    
    
    static constraints = {
    }
}

