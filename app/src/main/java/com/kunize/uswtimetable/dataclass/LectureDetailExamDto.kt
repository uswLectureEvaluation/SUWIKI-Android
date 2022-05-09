package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import com.kunize.uswtimetable.util.LectureItemViewType

data class LectureDetailExamDto(
    val data: MutableList<LectureDetailExam>,
    val examDataExist: Boolean
) {
    fun convertToEvaluationData(): MutableList<EvaluationData?> {
        val temp = mutableListOf<EvaluationData?>()
        data.forEach {
            temp.add(EvaluationData(
                recyclerViewType = LectureItemViewType.EXAM,
                lectureId = it.id,
                yearSemester = it.semester,
                testMethod = it.examInfo,
                difficulty = it.examDifficulty,
                content = it.content
            ))
        }
        return temp
    }
}

data class LectureDetailExam(
    val id: Long,
    @SerializedName("selectedSemester") val semester: String,
    val examInfo: String,
    val examDifficulty: String,
    val content: String
)
