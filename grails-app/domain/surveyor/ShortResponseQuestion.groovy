package surveyor

class ShortResponseQuestion extends Question {

    static hasMany = [shortResponseAnswer: ShortResponseAnswer]

    static constraints = {
    }
	
	String toString() {
		question
	}
	
}
