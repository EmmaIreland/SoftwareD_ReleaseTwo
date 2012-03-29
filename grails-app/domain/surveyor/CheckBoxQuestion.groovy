package surveyor

class CheckBoxQuestion extends Question {
    
    static hasMany = [checkBoxAnswer: CheckBoxAnswer]
    
    static constraints = {
    }
	
	String toString() {
		question
	}
	
}
