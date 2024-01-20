package com.suwiki.core.model.enums

enum class LectureEvaluationTab(
  val position: Int,
  val title: String,
) {
  LECTURE_EVALUATION(
    position = 0,
    title = "강의평가",
  ),
  EXAM_INFO(
    position = 1,
    title = "시험정보",
  ),
}
