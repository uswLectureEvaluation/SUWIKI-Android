package com.suwiki.remote.timetable.response

import kotlinx.serialization.Serializable

@Serializable
data class OpenLectureResponse(
  val data: OpenLectureData,
)

@Serializable
data class OpenLectureData(
  val isLast: Boolean,
  val content: List<OpenLecture>,
)

@Serializable
data class OpenLecture(
  val id: Long,
  val name: String,
  val type: String,
  val major: String,
  val grade: Int,
  val professorName: String,
  val originalCellList: List<Cell>,
)

@Serializable
data class Cell(
  val location: String,
  val day: String,
  val startPeriod: Int,
  val endPeriod: Int,
)

internal fun OpenLectureResponse.toModel() = this.data.content.map { openLecture ->
  with(openLecture) {
    com.suwiki.core.model.timetable.OpenLecture(
      id = id,
      name = name,
      type = type,
      major = major,
      grade = grade,
      professorName = professorName,
      originalCellList = originalCellList.map {
        it.toModel()
      },
    )
  }
}

internal fun Cell.toModel() = com.suwiki.core.model.timetable.Cell(
  location = location,
  day = day,
  startPeriod = startPeriod,
  endPeriod = endPeriod,
)
