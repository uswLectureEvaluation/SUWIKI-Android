package com.kunize.uswtimetable.dataclass

data class EvaluationData (
    var name: String = "",
    var professor: String = "",
    var aver: Float = 0f,
    var satisfaction: Float = 0f,
    var honey: Float = 0f,
    var learning: Float = 0f,
    var type: String = ""
) {
    fun floatToStr(f: Float) = f.toString()
}