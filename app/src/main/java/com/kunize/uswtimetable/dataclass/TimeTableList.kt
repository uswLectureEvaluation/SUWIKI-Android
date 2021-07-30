package com.kunize.uswtimetable.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeTableList(
    @PrimaryKey val createTime: Long,
    val year: String,
    val semester: String,
    var timeTableName: String,
    var timeTableJsonData: String = ""
)