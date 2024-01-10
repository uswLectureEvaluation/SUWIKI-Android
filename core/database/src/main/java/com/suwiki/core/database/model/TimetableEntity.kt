package com.suwiki.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimetableEntity(
  @PrimaryKey val createTime: Long,
  var year: String,
  var semester: String,
  var name: String,
  var cellList: String = "",
)
