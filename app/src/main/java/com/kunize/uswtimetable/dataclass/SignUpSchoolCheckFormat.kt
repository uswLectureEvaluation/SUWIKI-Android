package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName

data class SignUpSchoolCheckFormat(
    @SerializedName("success") val student_certification: String
)