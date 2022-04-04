package com.kunize.uswtimetable.custom_view.timetable

import android.content.Context
import android.util.Log
import com.kunize.uswtimetable.TimeTableSelPref
import com.kunize.uswtimetable.dao_database.TimeTableListDatabase
import com.kunize.uswtimetable.dataclass.TimeData
import org.json.JSONArray
import org.json.JSONObject

object DBManager {
    fun deleteTime(context: Context, data: TimeData) {
        //1. 해당 시간에 맞는 TimeTableList DB 불러옴
        val db = TimeTableListDatabase.getInstance(context)
        val tempTimetableList = db!!.timetableListDao().getAll()
        var timetableSel = tempTimetableList[0]
        val createTime = TimeTableSelPref.prefs.getLong("timetableSel", 0)
        for (empty in tempTimetableList) {
            if (empty.createTime == createTime)
                timetableSel = empty
        }
        //2. DB의 Json String 불러옴
        val jsonStr = timetableSel.timeTableJsonData

        val tempTimeData = mutableListOf<TimeData>()
        val jsonArray: JSONArray

        //3. Json을 Array로 변환
        if (jsonStr != "") {
            jsonArray = JSONArray(jsonStr)
            for (idx in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(idx)
                val name = jsonObj.getString("name")
                val professor = jsonObj.getString("professor")
                val location = jsonObj.getString("location")
                val day = jsonObj.getString("day")
                val startTime = jsonObj.getString("startTime")
                val endTime = jsonObj.getString("endTime")
                val color = jsonObj.getInt("color")

                tempTimeData.add(
                    TimeData(
                        name,
                        professor,
                        location,
                        day,
                        startTime,
                        endTime,
                        color
                    )
                )
            }
            tempTimeData.remove(data)
        }
        // 6. 없을 경우 Array에 추가
        // 7. Array를 Json형식으로 변환
        val newJsonArray = JSONArray()
        for (addData in tempTimeData) {
            val addJsonObj = JSONObject()
            addJsonObj.put("name", addData.name)
            addJsonObj.put("professor", addData.professor)
            addJsonObj.put("location", addData.location)
            addJsonObj.put("day", addData.day)
            addJsonObj.put("startTime", addData.startTime)
            addJsonObj.put("endTime", addData.endTime)
            addJsonObj.put("color", addData.color)
            Log.d("jsonAdd", "추가 될 데이터 ${addJsonObj.toString()}")
            newJsonArray.put(addJsonObj)
        }
        // 8. DB 업데이트
        timetableSel.timeTableJsonData = newJsonArray.toString()
        db.timetableListDao().update(timetableSel)
    }
}