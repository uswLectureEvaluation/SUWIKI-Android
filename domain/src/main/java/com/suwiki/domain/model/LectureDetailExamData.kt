package com.suwiki.domain.model

data class LectureDetailExamData(
    val data: List<EvaluationData>,
    val examDataExist: Boolean,
    val written: Boolean,
)
