package com.kunize.uswtimetable.ui.home.timetable

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.data.local.TimeTableList

object DBManager {
    val gson = Gson()
    private val convertType = object : TypeToken<MutableList<TimeData>>(){}.type

    fun getCurrentTimetableInfo(db: TimeTableListDatabase): TimeTableList {
        val timeTableList = db.timetableListDao().getAll()
        val createTime = SuwikiApplication.prefs.getLong("timetableSel", 0)
        return timeTableList.find { it.createTime == createTime } ?: timeTableList[0]
    }

    fun deleteTime(context: Context, data: TimeData) {
        val db = TimeTableListDatabase.getInstance(context)
        val timetableSel = getCurrentTimetableInfo(db!!)
        val jsonStr = timetableSel.timeTableJsonData
        val tempTimeData = jsonToArray(jsonStr)
        tempTimeData.remove(data)
        timetableSel.timeTableJsonData = arrayToJson(tempTimeData)
        db.timetableListDao().update(timetableSel)
    }

    fun jsonToArray(jsonStr: String): MutableList<TimeData> {
        val convertType = object : TypeToken<MutableList<TimeData>>(){}.type
        return gson.fromJson(jsonStr.ifBlank { "[]" }, convertType)
    }

    fun arrayToJson(data: MutableList<TimeData>): String {
        return gson.toJson(data, convertType).toString()
    }
}