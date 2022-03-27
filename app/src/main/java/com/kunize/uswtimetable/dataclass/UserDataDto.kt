package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDataDto(
    @SerializedName("loginId") val userId: String,
    val point: Int,
    @SerializedName("written_evaluation") val writtenEvaluation: Int,
    @SerializedName("written_exam") val writtenExam: Int,
    @SerializedName("view_exam") val viewExam: Int
): Serializable