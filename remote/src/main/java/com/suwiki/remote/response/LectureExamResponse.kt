package com.suwiki.remote.response

import com.suwiki.model.LectureExam
import kotlinx.serialization.Serializable

@Serializable
data class LectureExamResponse(
    val id: Long? = null,
    val lectureName: String? = null, // 과목 이름
    val professor: String? = null, // 교수이름
    val majorType: String? = null, // 개설학과
    val selectedSemester: String? = null,
    val semesterList: String? = null,
    val examInfo: String, // 시험 방식
    val examType: String? = null,
    val examDifficulty: String, // 시험 난이도
    val content: String,
)

internal fun LectureExamResponse.toModel() = LectureExam(
    id = id,
    lectureName = lectureName,
    professor = professor,
    majorType = majorType,
    selectedSemester = selectedSemester,
    semesterList = semesterList,
    examInfo = examInfo,
    examType = examType,
    examDifficulty = examDifficulty,
    content = content,
)
