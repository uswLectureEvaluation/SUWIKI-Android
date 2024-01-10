package com.suwiki.core.model.enums

import androidx.compose.runtime.Stable

@Stable
enum class GradeLevel(val value: Int) {
  EASY(2),
  NORMAL(1),
  DIFFICULT(0),
}

@Stable
enum class HomeworkLevel(val value: Int) {
  NONE(0),
  NORMAL(1),
  MANY(2),
}

@Stable
enum class TeamLevel(val value: Int) {
  NOT_EXIST(0),
  EXIST(1),
}
