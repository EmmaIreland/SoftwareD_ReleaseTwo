package surveyor

class MultipleChoiceAnswer extends Answer {

    static belongsTo = [multipleChoiceQuestion: MultipleChoiceQuestion]
    
    
    static constraints = {
    }
}
