package com.suwiki.lectureevaluation.viewer.response.exam

import com.suwiki.core.model.LectureDetailExam
import com.suwiki.core.model.LectureDetailExamData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LectureDetailExamDataResponse(
    val data: List<LectureDetailExamResponse>,
    val examDataExist: Boolean,
    val written: Boolean,
)

internal fun LectureDetailExamDataResponse.toModel() = com.suwiki.core.model.LectureDetailExamData(
    data = data.map { it.toModel() },
    examDataExist = examDataExist,
    written = written,
)

@Serializable
data class LectureDetailExamResponse(
    val id: Long,
    @SerialName("selectedSemester") val semester: String,
    val examInfo: String,
    val examType: String?,
    val examDifficulty: String,
    val content: String,
)

internal fun LectureDetailExamResponse.toModel() = com.suwiki.core.model.LectureDetailExam(
    id = id,
    semester = semester,
    examInfo = examInfo,
    examType = examType,
    examDifficulty = examDifficulty,
    content = content,
)