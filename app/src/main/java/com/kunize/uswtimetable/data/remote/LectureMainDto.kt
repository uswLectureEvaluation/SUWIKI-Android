package com.kunize.uswtimetable.data.remote

import com.google.gson.annotations.SerializedName
import com.kunize.uswtimetable.data.local.EvaluationData
import com.kunize.uswtimetable.util.LectureItemViewType.SHORT


data class LectureMainDto(
    val data: MutableList<LectureMain>
) {
    fun convertToEvaluationData(): MutableList<EvaluationData?> {
        val temp = mutableListOf<EvaluationData?>()
        data.forEach {
            temp.add(
                EvaluationData(
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
            )
            )
        }
        return temp
    }
}

data class LectureMain(
    val id: Long,
    @SerializedName("selectedSemester") val semester: String,
    val professor: String,
    val majorType: String,
    val lectureType: String,
    val lectureName: String,
    val lectureTotalAvg: Float,
    val lectureSatisfactionAvg: Float,
    val lectureHoneyAvg: Float,
    val lectureLearningAvg: Float
)
