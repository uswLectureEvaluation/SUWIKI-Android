package com.kunize.uswtimetable.dataclass

import android.graphics.Color

data class TimeData(
    var location: String = "",
    var day: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var color: Int = Color.rgb(255, 193, 82), //주황색
    var credit: String = ""
)