package com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter

import android.widget.EditText
import android.widget.TextView
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

data class IsELearningState(
    val location1: EditText,
    val day1: TextView
) : FilterState