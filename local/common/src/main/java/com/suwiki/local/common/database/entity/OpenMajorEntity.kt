package com.suwiki.local.common.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OpenMajorEntity(
  val name: String,
) {
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0
}
