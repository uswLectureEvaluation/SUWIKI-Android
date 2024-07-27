package com.suwiki.common.model.enums

import androidx.compose.runtime.Stable

@Stable
enum class LectureAlign(val query: String) {
  RECENT(
    "modifiedDate",
  ),
  HONEY(
    "lectureHoneyAvg",
  ),
  SATISFACTION(
    "lectureSatisfactionAvg",
  ),
  LEARNING(
    "lectureLearningAvg",
  ),
  BEST(
    "lectureTotalAvg",
  ),
}
