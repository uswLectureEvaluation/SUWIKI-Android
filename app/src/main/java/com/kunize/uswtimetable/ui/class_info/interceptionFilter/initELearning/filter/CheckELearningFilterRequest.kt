package com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter

import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest

data class CheckELearningFilterRequest(
    val isModifiedMode: Boolean,
    val time: String,
    val location1: EditText,
    val day1: TextView,
    val locationList: List<EditText>,
    val dayList: List<TextView>,
    val startTimeList: List<EditText>,
    val endTimeList: List<EditText>,
    val deleteTimeList: List<TextView>,
    val clDayList: List<ConstraintLayout>,
    val clTimeList: List<ConstraintLayout>
): FilterRequest
