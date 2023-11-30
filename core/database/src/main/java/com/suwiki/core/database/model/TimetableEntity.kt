package com.suwiki.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimetableEntity(
  @PrimaryKey val createTime: Long,
  var year: String,
  var semester: String,
  var timetableName: String,
  var timetableJsonData: String = "",
)
