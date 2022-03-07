package com.kunize.uswtimetable.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyExamInfo (
    val id: String,
    val subject: String,
    val semester: String,
    val professor: String,
    val evaluationType: String,
    val examType: String,
    val examDifficulty: String,
    val content: String
) : Parcelable
