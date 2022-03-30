package com.kunize.uswtimetable.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LectureProfessorSemester(
    val subject: String,
    val professor: String,
    val semester: String
) : Parcelable
