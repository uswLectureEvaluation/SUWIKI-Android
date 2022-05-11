package com.kunize.uswtimetable.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeTableList(
    @PrimaryKey val createTime: Long,
    var year: String,
    var semester: String,
    var timeTableName: String,
    var timeTableJsonData: String = ""
)