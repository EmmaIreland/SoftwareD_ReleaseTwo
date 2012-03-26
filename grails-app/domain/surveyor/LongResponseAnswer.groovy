package surveyor

class LongResponseAnswer extends Answer{
    
    static belongsTo = [longResponseQuestion: LongResponseQuestion]

    static constraints = {
    }
}
