package surveyor

class ShortResponseAnswer extends Answer {
    
    static belongsTo = [shortResponseQuestion: ShortResponseQuestion]

    static constraints = {
    }
}
