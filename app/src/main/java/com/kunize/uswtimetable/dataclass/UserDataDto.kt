package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDataDto(
    @SerializedName("loginId") val userId: String,
    val email: String,
    val point: Int,
    @SerializedName("writtenLecture") val writtenEvaluation: Int,
    @SerializedName("writtenExam") val writtenExam: Int,
    @SerializedName("viewExam") val viewExam: Int
): Serializable