package com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning

import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter.CheckELearningFilter
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter.CheckELearningFilterRequest
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.handleFilterResultStrategy.IsELearningStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.handleFilterResultStrategy.IsNotELearningStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterChainModel

class ELearningModelManager(
    private val isModifiedMode: Boolean,
    private val time: String,
    private val location1: EditText,
    private val day1: TextView,
    private val locationList: List<EditText>,
    private val dayList: List<TextView>,
    private val startTimeList: List<EditText>,
    private val endTimeList: List<EditText>,
    private val deleteTimeList: List<TextView>,
    private val clDayList: List<ConstraintLayout>,
    private val clTimeList: List<ConstraintLayout>
) {

    fun getFilterChainModel() =
        FilterChainModel(
            CheckELearningFilter(),
            CheckELearningFilterRequest(
                isModifiedMode = isModifiedMode,
                time = time,
                location1 = location1,
                day1 = day1,
                locationList = locationList,
                dayList = dayList,
                startTimeList = startTimeList,
                endTimeList = endTimeList,
                deleteTimeList = deleteTimeList,
                clTimeList = clTimeList,
                clDayList = clDayList
            )
        )


    fun getStrategies() = listOf(
        IsELearningStrategy(),
        IsNotELearningStrategy()
    )

}