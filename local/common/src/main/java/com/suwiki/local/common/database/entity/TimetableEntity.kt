package com.suwiki.local.common.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suwiki.core.model.timetable.TimetableCell

@Entity
data class TimetableEntity(
  @PrimaryKey val createTime: Long,
  var year: String,
  var semester: String,
  var name: String,
  var cellList: List<TimetableCell>,
)
