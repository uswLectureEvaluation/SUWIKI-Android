package com.kunize.uswtimetable.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeTableList(
    @PrimaryKey val createTime: Long,
    val year: String,
    val semester: String,
    val timeTableName: String,
    val timeTableJsonData: String = ""
)