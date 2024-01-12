package com.suwiki.feature.myinfo.myevaluation.model

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import kotlinx.collections.immutable.persistentListOf

val MyExamEvaluationsSample = persistentListOf(
  MyExamEvaluation(
    id = 0,
    lectureName = "컴퓨터구조",
    selectedSemester = "2023-2",
    semesterList = "2023-2, 2023-1, 2022-2",
    examDifficulty = "어려움",
    examInfo = listOf("족보"),
    examType = "중간고사",
    content = "샘플 데이터",
  ),
  MyExamEvaluation(
    id = 1,
    lectureName = "인공지능",
    selectedSemester = "2023-1",
    semesterList = "2023-1, 2022-2, 2022-1",
    examDifficulty = "보통",
    examInfo = listOf("PPT"),
    examType = "기말고사",
    content = "샘플 데이터",
  ),
  MyExamEvaluation(
    id = 2,
    lectureName = "ICT개론",
    selectedSemester = "2020-1",
    semesterList = "2020-2, 2020-1, 2019-2",
    examDifficulty = "쉬움",
    examInfo = listOf("필기"),
    examType = "쪽지",
    content = "샘플 데이터",
  ),
)
