package com.suwiki.data.network.dto

import com.google.gson.annotations.SerializedName
import com.suwiki.domain.model.EvaluationData

data class LectureDetailExamDto(
    val data: MutableList<LectureDetailExam>,
    val examDataExist: Boolean,
    val written: Boolean,
) {
    fun convertToEvaluationData(): MutableList<EvaluationData?> {
        val temp = mutableListOf<EvaluationData?>() // TODO Presentation 의 converter 로 이동
        data.forEach {
            temp.add(
                EvaluationData(
                    recyclerViewType = 4, // LectureItemViewType.EXAM,
                    lectureId = it.id,
                    yearSemester = it.semester,
                    testMethod = it.examInfo,
                    examType = it.examType,
                    difficulty = it.examDifficulty,
                    content = it.content,
                ),
            )
        }
        return temp
    }
}

data class LectureDetailExam(
    val id: Long,
    @SerializedName("selectedSemester") val semester: String,
    val examInfo: String,
    val examType: String?,
    val examDifficulty: String,
    val content: String,
)
