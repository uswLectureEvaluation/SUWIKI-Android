package com.kunize.uswtimetable.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class MyExamInfo(
    val id: String,
    val subject: String,
    val semester: String,
    val professor: String,
    val evaluationType: String,
    val examType: String,
    val examDifficulty: String,
    val content: String
) : Parcelable

data class MyExamListDto(
    @SerializedName("data") val data: List<MyExamDto>
) {
    fun toExamInfoList(): List<MyExamInfo> {
        val temp = mutableListOf<MyExamInfo>()
        data.forEach {
            temp.add(MyExamInfo(
                id = it.id,
                subject = it.subject,
                semester = it.semester,
                professor = it.professor,
                evaluationType = it.evaluationType,
                examType = it.examType,
                examDifficulty = it.examDifficulty,
                content = it.content
            ))
        }
        return temp.toList()
    }
}

data class MyExamDto(
    @SerializedName("id") val id: String,
    @SerializedName("lectureName") val subject: String,
    @SerializedName("semester") val semester: String,
    @SerializedName("professor") val professor: String,
    @SerializedName("evaluationType") val evaluationType: String,
    @SerializedName("examType") val examType: String,
    @SerializedName("examDifficulty") val examDifficulty: String,
    @SerializedName("content") val content: String
) : Serializable