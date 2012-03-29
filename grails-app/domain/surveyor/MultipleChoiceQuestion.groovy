package surveyor

class MultipleChoiceQuestion extends Question {

    static hasMany = [multipleChoiceAnswer: MultipleChoiceAnswer]  
    
    static constraints = {
    }
	
	String toString() {
		question
	}
}
