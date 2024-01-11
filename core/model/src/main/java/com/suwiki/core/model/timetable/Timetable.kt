package com.suwiki.core.model.timetable

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Timetable(
  val createTime: Long,
  val year: String,
  val semester: String,
  val name: String,
  val cellList: List<TimetableCell>,
)
