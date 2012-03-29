package surveyor

class IntegerScaleAnswer extends Answer{

    static belongsTo = [integerScaleQuestion: IntegerScaleQuestion]
       
    static constraints = {
    }
	
	String toString() {
		answer
	}
	
}
