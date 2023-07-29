package com.suwiki.model

data class LectureDetailExamData(
    val data: List<Evaluation>,
    val examDataExist: Boolean,
    val written: Boolean,
)
