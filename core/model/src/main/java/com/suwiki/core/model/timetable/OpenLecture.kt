package com.suwiki.core.model.timetable

data class OpenLecture(
  val id: Long,
  val name: String,
  val type: String,
  val major: String,
  val grade: Int,
  val professorName: String,
  val originalCellList: List<Cell>
)

data class Cell(
  val location: String,
  val day: String,
  val startPeriod: Int,
  val endPeriod: Int
)
