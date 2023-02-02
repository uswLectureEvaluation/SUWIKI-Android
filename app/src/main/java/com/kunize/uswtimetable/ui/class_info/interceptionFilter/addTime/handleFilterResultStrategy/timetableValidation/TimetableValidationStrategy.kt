package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.timetableValidation

import android.content.Context
import android.content.Intent
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.data.local.TimeTableList
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.ui.home.timetable.DBManager
import com.kunize.uswtimetable.ui.main.MainActivity
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TimetableValidationStrategy(
    val context: Context, private var currentTimeTable: MutableList<TimeData>,
    private val timeDataTobeAdded: MutableList<TimeData>,
    private var timetableSel: TimeTableList,
    val db: TimeTableListDatabase
) : FilterResultStrategy {
    override fun identifyFilterState(state: FilterState): Boolean = state is FilterState.Validate

    override suspend fun invoke(request: FilterState) {
        withContext(IO) {
            currentTimeTable.addAll(timeDataTobeAdded)
            timetableSel.timeTableJsonData = DBManager.arrayToJson(currentTimeTable)
            db.timetableListDao().update(timetableSel)
        }
        val intent = Intent(context, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //액티비티 스택제거
        context.startActivity(intent)
    }
}