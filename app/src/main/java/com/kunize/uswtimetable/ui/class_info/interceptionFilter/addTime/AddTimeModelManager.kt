package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.data.local.TimeTableList
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.elearning.ELearningValidationFilter
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.elearning.ELearningValidationFilterRequest
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeLocation.TimeLocationValidationFilter
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeLocation.TimeLocationValidationFilterRequest
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeOverlap.OverlapValidationFilter
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeOverlap.OverlapValidationFilterRequest
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeVisible.VisibleValidationFilter
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeVisible.VisibleValidationFilterRequest
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.elearning.ELearningNotValidationStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.timeLocation.TimeLocationNotValidationStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.timeOverlap.OverlapNotValidationStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.timeVisible.TimeVisibleNotValidationStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.timetableValidation.TimetableValidationStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.unknownFailFilterResult.UnknownAddTimeFailStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterChainModel

class AddTimeModelManager(
    private val dayList: List<TextView>,
    private val locationList: List<EditText>,
    private val startTimeList: List<EditText>,
    private val endTimeList: List<EditText>,
    private val timeDataTobeAdded: MutableList<TimeData>,
    private val currentTimeTable: MutableList<TimeData>,
    private val deleteTimeList: List<TextView>,
    private val context: Context,
    private val timetableSel: TimeTableList,
    private val db: TimeTableListDatabase
) {

    fun getFilterChainModels() = listOf(
        FilterChainModel(
            TimeLocationValidationFilter(),
            TimeLocationValidationFilterRequest(
                dayList = dayList,
                locationList = locationList,
                startTimeList = startTimeList,
                endTimeList = endTimeList
            )
        ),
        FilterChainModel(
            ELearningValidationFilter(),
            ELearningValidationFilterRequest(
                timeDataTobeAdded = timeDataTobeAdded,
                currentTimeTable = currentTimeTable
            )
        ),
        FilterChainModel(
            OverlapValidationFilter(),
            OverlapValidationFilterRequest(
                timeDataTobeAdded = timeDataTobeAdded,
                currentTimeTable = currentTimeTable
            )
        ),
        FilterChainModel(
            VisibleValidationFilter(),
            VisibleValidationFilterRequest(
                deleteTimeList = deleteTimeList
            )
        )
    )

    fun getStrategies() = listOf(
        ELearningNotValidationStrategy(),
        TimeLocationNotValidationStrategy(),
        OverlapNotValidationStrategy(),
        TimetableValidationStrategy(
            context = context,
            currentTimeTable = currentTimeTable,
            timeDataTobeAdded = timeDataTobeAdded,
            timetableSel = timetableSel,
            db = db
        ),
        TimeVisibleNotValidationStrategy()
    )

}