package com.kunize.uswtimetable.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LectureProfessorName(
    val subject: String,
    val professor: String
) : Parcelable
