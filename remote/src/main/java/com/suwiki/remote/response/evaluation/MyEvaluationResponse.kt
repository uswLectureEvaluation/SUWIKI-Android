package com.suwiki.remote.response.evaluation

import com.suwiki.model.Evaluation
import kotlinx.serialization.Serializable

@Serializable
data class MyEvaluationResponse(
    val id: Long,
    val lectureName: String, // 과목이름
    val professor: String, // 교수이름
    val majorType: String, // 개설학과
    val selectedSemester: String,
    val semesterList: String,
    val totalAvg: Float, // 총점
    val satisfaction: Float, // 만족도
    val learning: Float, // 배움지수
    val honey: Float, // 꿀강지수
    val team: Int, // 조별모임 유무(없음 == 0, 있음 == 1)
    val difficulty: Int, // 학점 잘주는가? (까다로움 == 0, 보통 == 1, 학점느님 ==2)
    val homework: Int, // 과제양 (없음 ==0, 보통 == 1, 많음 == 2)
    val content: String,
)

internal fun MyEvaluationResponse.toModel() = Evaluation(
    id = id,
    lectureName = lectureName,
    professor = professor,
    majorType = majorType,
    selectedSemester = selectedSemester,
    semesterList = semesterList,
    totalAvg = totalAvg,
    satisfaction = satisfaction,
    learning = learning,
    honey = honey,
    team = team,
    difficulty = difficulty,
    homework = homework,
    content = content,
)
