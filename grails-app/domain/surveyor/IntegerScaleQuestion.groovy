package surveyor

class IntegerScaleQuestion extends Question{

    static hasMany = [integerScaleAnswer: IntegerScaleAnswer]
    
    static constraints = {
    }
}
