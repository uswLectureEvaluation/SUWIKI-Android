package com.suwiki.remote.timetable.response

import kotlinx.serialization.Serializable

enum class TimetableDay(val idx: Int) {
  MON(0), TUE(1), WED(2), THU(3), FRI(4), SAT(5), E_LEARNING(6)
}

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
  val day: TimetableDay,
  val startPeriod: Int,
  val endPeriod: Int,
)

internal fun OpenLectureResponse.toModel(): com.suwiki.common.model.timetable.OpenLectureData {
  val content = this.data.content.map { openLecture ->
    with(openLecture) {
      com.suwiki.common.model.timetable.OpenLecture(
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

  return com.suwiki.common.model.timetable.OpenLectureData(
    isLast = data.isLast,
    content = content,
  )
}

internal fun Cell.toModel() = com.suwiki.common.model.timetable.Cell(
  location = location,
  day = when (day) {
    TimetableDay.MON -> com.suwiki.common.model.timetable.TimetableDay.MON
    TimetableDay.TUE -> com.suwiki.common.model.timetable.TimetableDay.TUE
    TimetableDay.WED -> com.suwiki.common.model.timetable.TimetableDay.WED
    TimetableDay.THU -> com.suwiki.common.model.timetable.TimetableDay.THU
    TimetableDay.FRI -> com.suwiki.common.model.timetable.TimetableDay.FRI
    TimetableDay.SAT -> com.suwiki.common.model.timetable.TimetableDay.SAT
    TimetableDay.E_LEARNING -> com.suwiki.common.model.timetable.TimetableDay.E_LEARNING
  },
  startPeriod = startPeriod,
  endPeriod = endPeriod,
)
