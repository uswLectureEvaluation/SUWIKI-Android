package com.suwiki.feature.myinfo.myevaluation.model

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import kotlinx.collections.immutable.persistentListOf

val MyExamEvaluationsSample = persistentListOf(
  MyExamEvaluation(
    lectureName = "컴퓨터구조",
    selectedSemester = "2023-2",
    examDifficulty = "어려움",
    examInfo = "대면",
    examType = "과제",
    content = "샘플 데이터",
  ),
  MyExamEvaluation(
    lectureName = "인공지능",
    selectedSemester = "2023-1",
    examDifficulty = "보통",
    examInfo = "대면",
    examType = "PPT",
    content = "샘플 데이터",
  ),
  MyExamEvaluation(
    lectureName = "ICT개론",
    selectedSemester = "2020-1",
    examDifficulty = "쉬움",
    examInfo = "비대면",
    examType = "필기",
    content = "샘플 데이터",
  ),
)
