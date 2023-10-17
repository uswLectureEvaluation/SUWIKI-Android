package com.suwiki.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OpenMajorEntity(
    val name: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
