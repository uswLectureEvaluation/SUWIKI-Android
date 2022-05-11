package com.kunize.uswtimetable.data.local

import android.graphics.Color

data class TimeData(
    var name: String = "",
    var professor: String = "",
    var location: String = "",
    var day: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var color: Int = Color.rgb(255, 193, 82), //주황색
    var credit: String = ""
)