package surveyor

class LongResponseQuestion extends Question{

    static hasMany = [longResponseAnswer: LongResponseAnswer]
    
    
    static constraints = {
    }
}
