package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import com.kunize.uswtimetable.util.LectureItemViewType

data class LectureDetailEvaluationDto(
    val data: MutableList<LectureDetailEvaluation>
) {
    fun convertToEvaluationData(): MutableList<EvaluationData?> {
        val temp = mutableListOf<EvaluationData?>()
        data.forEach {
            temp.add(EvaluationData(
                recyclerViewType = LectureItemViewType.LECTURE,
                lectureId = it.id,
                yearSemester = it.semester,
                aver = it.totalAvg,
                satisfaction = it.satisfaction,
                learning = it.learning,
                honey = it.honey,
                teamMeeting = it.team,
                grade = it.difficulty,
                task = it.homework,
                content = it.content
            ))
        }
        return temp
    }
}

data class LectureDetailEvaluation(
    val id: Long,
    @SerializedName("selectedSemester") val semester: String,
    val totalAvg: Float,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String
)
