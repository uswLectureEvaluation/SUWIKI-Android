package com.kunize.uswtimetable.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LectureProfessorSemester(
    val subject: String,
    val professor: String,
    val semester: String
) : Parcelable
