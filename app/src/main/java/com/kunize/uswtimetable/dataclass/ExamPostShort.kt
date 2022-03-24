package com.kunize.uswtimetable.dataclass

import java.io.Serializable

data class ExamPostShort(
    val examInfo: String,
    val examDifficulty: String,
    val content: String
): Serializable