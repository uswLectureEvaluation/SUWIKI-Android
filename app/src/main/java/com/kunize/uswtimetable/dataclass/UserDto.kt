package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id") val userId: String,
    val point: Int,
    @SerializedName("written_lecture") val writtenLecture: Int,
    @SerializedName("written_exam") val writtenExam: Int,
    @SerializedName("view_exam") val viewExam: Int
)