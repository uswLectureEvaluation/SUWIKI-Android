package com.kunize.uswtimetable.dataclass

import com.kunize.uswtimetable.util.LectureItemViewType
import com.kunize.uswtimetable.util.LectureItemViewType.SHORT


data class LectureMainDto(
    val data: List<LectureMain>
) {
    fun convertToEvaluationData(): ArrayList<EvaluationData?> {
        val temp = arrayListOf<EvaluationData?>()
        data.forEach {
            temp.add(EvaluationData(
                recyclerViewType = SHORT,
                lectureId = it.id,
                yearSemester = it.semester,
                professor = it.professor,
                type = it.lectureType,
                name = it.lectureName,
                aver = it.lectureTotalAvg,
                satisfaction = it.lectureSatisfactionAvg,
                honey = it.lectureHoneyAvg,
                learning = it.lectureLearningAvg
            ))
        }
        return temp
    }
}

data class LectureMain(
    val id: Long,
    val semester: String,
    val professor: String,
    val lectureType: String,
    val lectureName: String,
    val lectureTotalAvg: Float,
    val lectureSatisfactionAvg: Float,
    val lectureHoneyAvg: Float,
    val lectureLearningAvg: Float
)
