package com.kunize.uswtimetable.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OpenMajorData(
    val name: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
