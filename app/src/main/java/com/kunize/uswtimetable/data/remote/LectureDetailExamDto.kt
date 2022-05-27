package com.kunize.uswtimetable.data.remote

import com.google.gson.annotations.SerializedName
import com.kunize.uswtimetable.data.local.EvaluationData
import com.kunize.uswtimetable.util.LectureItemViewType

data class LectureDetailExamDto(
    val data: MutableList<LectureDetailExam>,
    val examDataExist: Boolean
) {
    fun convertToEvaluationData(): MutableList<EvaluationData?> {
        val temp = mutableListOf<EvaluationData?>()
        data.forEach {
            temp.add(
                EvaluationData(
                    recyclerViewType = LectureItemViewType.EXAM,
                    lectureId = it.id,
                    yearSemester = it.semester,
                    testMethod = it.examInfo,
                    examType = it.examType,
                    difficulty = it.examDifficulty,
                    content = it.content
                )
            )
        }
        return temp
    }
}

data class LectureDetailExam(
    val id: Long,
    @SerializedName("selectedSemester") val semester: String,
    val examInfo: String,
    val examType: String,
    val examDifficulty: String,
    val content: String
)
