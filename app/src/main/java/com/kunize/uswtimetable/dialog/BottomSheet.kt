package com.kunize.uswtimetable.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.TimeTableSelPref
import com.kunize.uswtimetable.dao_database.TimeTableListDatabase
import com.kunize.uswtimetable.dataclass.TimeData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class BottomSheet(data: TimeData, val callback: (Int) -> Unit) : BottomSheetDialogFragment() {

    lateinit var edit: TextView
    lateinit var delete: TextView
    var localData: TimeData = data

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<TextView>(R.id.bottomName)?.text = localData.name
        view?.findViewById<TextView>(R.id.bottomProfessor)?.text = localData.professor
        view?.findViewById<TextView>(R.id.bottomTime)?.text = localData.location + " (" + localData.day + " " + localData.startTime + "~" +localData.endTime + ")"
        edit = view?.findViewById<TextView>(R.id.bottomEdit)!!
        edit.setOnClickListener{
            callback(1)
            dismiss()
        }
        delete = view?.findViewById<TextView>(R.id.bottomDelete)!!
        delete.setOnClickListener {
            callback(2)
            CoroutineScope(IO).launch {
                //TODO 1. 해당 시간에 맞는 TimeTableList DB 불러옴
                val db = TimeTableListDatabase.getInstance(activity?.applicationContext!!)
                val tempTimetableList = db!!.timetableListDao().getAll()
                var timetableSel = tempTimetableList[0]
                val createTime = TimeTableSelPref.prefs.getLong("timetableSel", 0)
                for (empty in tempTimetableList) {
                    if (empty.createTime == createTime)
                        timetableSel = empty
                }
                //TODO 2. DB의 Json String 불러옴
                val jsonStr = timetableSel.timeTableJsonData

                var tempTimeData = mutableListOf<TimeData>()
                var jsonArray: JSONArray

                //TODO 3. Json을 Array로 변환
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
                    tempTimeData.remove(localData)
                }
                //TODO 6. 없을 경우 Array에 추가
                //TODO 7. Array를 Json형식으로 변환
                var newJsonArray = JSONArray()
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
                //TODO 8. DB 업데이트
                timetableSel.timeTableJsonData = newJsonArray.toString()
                db.timetableListDao().update(timetableSel)
                dismiss()
            }
        }//끝
    }
}