package surveyor

class CheckBoxAnswer extends Answer{

    static belongsTo = [checkBoxQuestion: CheckBoxQuestion]
    
    static constraints = {
    }
}
