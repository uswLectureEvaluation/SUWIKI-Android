package com.suwiki.core.model.timetable

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
data class OpenLectureData(
  val isLast: Boolean,
  val content: List<OpenLecture>,
)

@Stable
data class OpenLecture(
  val id: Long,
  val name: String,
  val type: String,
  val major: String,
  val grade: Int,
  val professorName: String,
  val originalCellList: List<Cell>
)

@Stable
data class Cell(
  val location: String,
  val day: TimetableDay,
  val startPeriod: Int,
  val endPeriod: Int
)
