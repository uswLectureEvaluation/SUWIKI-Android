package com.kunize.uswtimetable.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyEvaluation (
    val id: String,
    @SerializedName("lectureName") val subject: String, // 과목 이름
    //val subjectType: String,
    val professor: String,
    val semester: String,
    @SerializedName("totalAvg") val totalValue: Float, // 총점
    val satisfaction: Float, // 만족도
    val learning: Float, // 배움 지수
    val honey: Float, // 꿀강 지수
    val team: Int, // 조별모임 유무 (없음: 0, 있음: 1)
    val difficulty: Int, // 학점 (까다로움: 0, 보통: 1, 잘 줌: 2)
    val homework: Int, // 과제 양 (없음: 0, 보통: 1, 많음: 2)
    val grade: String,
    val content: String
) : Parcelable
