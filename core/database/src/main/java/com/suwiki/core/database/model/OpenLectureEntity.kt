package com.suwiki.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OpenLectureEntity(
  @PrimaryKey val number: Long = 0,
  val major: String = "",
  val grade: String = "",
  val classNumber: String = "",
  val classDivideNumber: String = "",
  val className: String = "",
  val classification: String = "",
  val professor: String = "",
  val time: String = "",
  val credit: String = "",
)
