package com.suwiki.feature.myinfo.myevaluation.model

import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import kotlinx.collections.immutable.persistentListOf

val MyExamEvaluationsSample = persistentListOf(
  MyExamEvaluation(
    lectureName = "컴퓨터구조",
    selectedSemester = "2023-2",
    examDifficulty = "hard",
    examInfo = "exam_guides",
    examType = "homework",
    content = "샘플 데이터",
  ),
  MyExamEvaluation(
    lectureName = "인공지능",
    selectedSemester = "2023-1",
    examDifficulty = "hard",
    examInfo = "exam_guides",
    examType = "homework",
    content = "샘플 데이터",
  ),
  MyExamEvaluation(
    lectureName = "ICT개론",
    selectedSemester = "2020-1",
    examDifficulty = "hard",
    examInfo = "exam_guides",
    examType = "homework",
    content = "샘플 데이터",
  ),
)
