package com.kunize.uswtimetable.dataclass

import com.kunize.uswtimetable.util.LectureItemViewType

data class LectureDetailExamDto(
    val data: List<LectureDetailExam>,
    val examDataExist: Boolean
) {
    fun convertToEvaluationData(): ArrayList<EvaluationData?> {
        val temp = arrayListOf<EvaluationData?>()
        data.forEach {
            temp.add(EvaluationData(
                recyclerViewType = LectureItemViewType.LECTURE,
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
    val semester: String,
    val examInfo: String,
    val examDifficulty: String,
    val content: String
)
