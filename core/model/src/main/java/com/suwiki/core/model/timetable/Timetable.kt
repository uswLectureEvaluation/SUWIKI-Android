package com.suwiki.core.model.timetable

data class Timetable(
  val createTime: Long,
  val year: String,
  val semester: String,
  val name: String,
  val cellList: List<TimetableCell>,
)
