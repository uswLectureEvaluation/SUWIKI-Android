package com.kunize.uswtimetable

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.kunize.uswtimetable.custom_view.timetable.DBManager.arrayToJson
import com.kunize.uswtimetable.custom_view.timetable.DBManager.jsonToArray
import com.kunize.uswtimetable.dao_database.TimeTableListDatabase
import com.kunize.uswtimetable.databinding.ActivityClassInfoBinding
import com.kunize.uswtimetable.dataclass.TimeData
import com.kunize.uswtimetable.dataclass.TimeTableList
import com.kunize.uswtimetable.dialog.EditTimeDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ClassInfoActivity : AppCompatActivity() {
    companion object {
        val colorMap = mapOf<String, Int>(
            "Pink" to Color.rgb(254, 136, 136), //핑크
            "Orange" to Color.rgb(255, 193, 82), //주황
            "Purple" to Color.rgb(204, 154, 243), //보라
            "Sky" to Color.rgb(137, 200, 254), //하늘
            "Green" to Color.rgb(165, 220, 129), //연두
            "Brown" to Color.rgb(194, 143, 98), //갈색
            "Gray" to Color.rgb(194, 193, 189), //회색
            "Navy" to Color.rgb(67, 87, 150), //남색
            "darkGreen" to Color.rgb(107, 143, 84), //진녹색
            "lightBrown" to Color.rgb(255, 187, 128), //연갈색
            "darkPurple" to Color.rgb(161, 121, 192), //진보라색
            "darkGray" to Color.rgb(143, 142, 139) //진회색
        )
    }

    var colorSel: Int = Color.rgb(255, 193, 82)
    private val binding by lazy { ActivityClassInfoBinding.inflate(layoutInflater) }
    private lateinit var jsonStr: String
    private lateinit var db: TimeTableListDatabase
    private lateinit var timetableSel: TimeTableList
    private var tempTimeData = mutableListOf<TimeData>()
    private lateinit var bindingTimeList: List<List<TextView>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindingTimeList = listOf(
            listOf(binding.time1, binding.editTime1, binding.deleteTime1),
            listOf(binding.time2, binding.editTime2, binding.deleteTime2),
            listOf(binding.time3, binding.editTime3, binding.deleteTime3)
        )

        val deleteIdx = intent.getIntExtra("deleteIdx", -1)

        val className = intent.getStringExtra("className")
        val professor = intent.getStringExtra("professor")
        val time = intent.getStringExtra("time")

        binding.editClassName.setText(className)
        binding.editProfessorName.setText(professor)

        CoroutineScope(IO).launch {
            //1. 해당 시간에 맞는 TimeTableList DB 불러옴
            db = TimeTableListDatabase.getInstance(applicationContext)!!
            val tempTimetableList = db.timetableListDao().getAll()
            timetableSel = tempTimetableList[0]
            val createTime = TimeTableSelPref.prefs.getLong("timetableSel", 0)
            for (empty in tempTimetableList) {
                if (empty.createTime == createTime)
                    timetableSel = empty
            }
            //2. DB의 Json String 불러옴
            jsonStr = timetableSel.timeTableJsonData

            tempTimeData = jsonToArray(jsonStr)

            var randomColor = intent.getIntExtra("color", -1)

            if (randomColor == -1) {
                val randomNum = MutableList(12) { i -> i }.shuffled().toMutableList()
                do {
                    randomColor = colorMap.values.toIntArray()[randomNum.removeLast()]
                } while (jsonStr.contains("$randomColor") && (tempTimeData.size < 12))
            }

            colorSel = randomColor

            withContext(Main) {
                binding.imgColor.imageTintList = ColorStateList.valueOf(randomColor)
            }
        }

        setVisibilityTime(View.GONE, 2)
        setVisibilityTime(View.GONE, 3)

        binding.finishButton.setOnClickListener {
            val inputClassName = binding.editClassName.text.toString()
            val inputProfessor = binding.editProfessorName.text.toString()
            CoroutineScope(IO).launch {
                var tempDeleteData = TimeData()
                try {
                    if (deleteIdx != -1) {
                        tempDeleteData = tempTimeData[deleteIdx]
                        tempTimeData.removeAt(deleteIdx)
                    }
                    //3.5 UI에서 정보 추출
                    val extractionList =
                        listOf<TextView>(binding.time1, binding.time2, binding.time3)
                    if (binding.time1.text.toString().isEmpty() && binding.time2.text.toString()
                            .isEmpty() && binding.time3.text.toString().isEmpty()
                    ) {
                        withContext(Main) {
                            Toast.makeText(
                                this@ClassInfoActivity,
                                "시간 및 장소를 입력해주세요!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (deleteIdx != -1)
                            tempTimeData.add(deleteIdx, tempDeleteData)
                        return@launch
                    }
                    val addTimeData = mutableListOf<TimeData>()
                    for (extraction in extractionList) {
                        var tempSplit: List<String>
                        if (extraction.text.toString() == "이러닝") {
                            addTimeData.add(
                                TimeData(
                                    inputClassName,
                                    inputProfessor,
                                    "이러닝",
                                    "",
                                    "",
                                    "",
                                    colorSel
                                )
                            )
                        } else if (extraction.text.toString() != "") {
                            tempSplit = extraction.text.toString().split("(")
                            val location = tempSplit[0]
                            val day = tempSplit[1][0].toString()
                            val onlyTime = tempSplit[1].substring(1).replace(")", "").split(",")
                            val startTime = onlyTime[0]
                            val endTime = onlyTime.last()
                            addTimeData.add(
                                TimeData(
                                    inputClassName,
                                    inputProfessor,
                                    location,
                                    day,
                                    startTime,
                                    endTime,
                                    colorSel
                                )
                            )
                        }
                    }
                    //3.7 이러닝 여부 확인
                    for (newTime in addTimeData) {
                        if ((newTime.name.contains("이러닝") || newTime.location.contains("이러닝") || newTime.location.isEmpty()) && jsonStr.contains(
                                "이러닝"
                            ) && deleteIdx == -1
                        ) {
                            withContext(Main) {
                                Toast.makeText(
                                    this@ClassInfoActivity,
                                    "이러닝은 하나만 들을 수 있어요!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            if (deleteIdx != -1)
                                tempTimeData.add(deleteIdx, tempDeleteData)
                            return@launch
                        }
                    }
                    //4. 추가하려는 요일의 Array를 추출
                    //5. 겹치는 시간이 있는지 확인 -> 있으면 return
                    for (newTime in addTimeData) {
                        for (oldTime in tempTimeData) {
                            if (newTime.day == oldTime.day) {
                                val newStartTime = newTime.startTime.toInt()
                                val newEndTime = newTime.endTime.toInt()
                                val oldStartTime = oldTime.startTime.toInt()
                                val oldEndTime = oldTime.endTime.toInt()
                                if (
                                    (newStartTime in oldStartTime .. oldEndTime) || (newEndTime in oldStartTime .. oldEndTime) ||
                                    (oldStartTime in newStartTime .. newEndTime) || (oldEndTime in newStartTime .. newEndTime)
                                ) {
                                    withContext(Main) {
                                        Toast.makeText(
                                            this@ClassInfoActivity,
                                            "겹치는 시간이 있어요!\n${oldTime.name} (${oldTime.day}${oldTime.startTime} ~ ${oldTime.endTime})",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    if (deleteIdx != -1)
                                        tempTimeData.add(deleteIdx, tempDeleteData)
                                    return@launch
                                }
                            }
                        }
                        //에러 발생 시 여기 수정 addTime내에서도 겹치는지 확인하고 tempTimeDAta.add부분을 밖으로 빼면 될듯?
                    }
                    //6. 없을 경우 Array에 추가
                    for (newTime in addTimeData)
                        tempTimeData.add(newTime)
                    //7. Array를 Json형식으로 변환
                    val newJsonArray = arrayToJson(tempTimeData)
                    //8. DB 업데이트
                    timetableSel.timeTableJsonData = newJsonArray
                    db.timetableListDao().update(timetableSel)
                    //9. 시간표 화면으로 이동
                    val intent = Intent(this@ClassInfoActivity, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //액티비티 스택제거
                    startActivity(intent)
                }
                catch (e: Exception) {
                    Log.d("UnKnownError", "$e")
                    withContext(Main) {
                        Toast.makeText(
                            this@ClassInfoActivity,
                            "문제가 있어 시간표를 추가할 수 없어요!",
                            Toast.LENGTH_SHORT
                        ).show()
                        if (deleteIdx != -1)
                            tempTimeData.add(deleteIdx, tempDeleteData)
                    }
                }
            }  //Coroutine 끝
        } //끝

        //TODO deleteIdx != -1 일 경우 로직 수정해야함 (장소에 띄어쓰기가 있을 경우 장소명이 "이러닝"으로 변함)

        try {
            // deleteIdx가 -1이 아닌 경우에만 문자열 분리
            if (deleteIdx != -1) {
                if (time!! == "" || time == "()")
                    binding.time1.text = "이러닝"
                else
                    binding.time1.text = time
            } else if (time!! == "None") {
                binding.time1.text = "이러닝"
            } else {
                val timeListSplitByDay = splitTime(time)
                for (i in timeListSplitByDay.indices) {
                    setVisibilityTime(View.VISIBLE, i + 1)
                    bindingTimeList[i][0].text = timeListSplitByDay[i]
                }
            }
        } catch (e: Exception) {
            Log.d("timeSplit", "$e")
            binding.time1.text = "이러닝"
        }

        binding.addTime.setOnClickListener {
            if (!binding.time1.isVisible)
                setVisibilityTime(View.VISIBLE, 1)
            else if (!binding.time2.isVisible)
                setVisibilityTime(View.VISIBLE, 2)
            else if (!binding.time3.isVisible)
                setVisibilityTime(View.VISIBLE, 3)
        }

        binding.deleteTime1.setOnClickListener {
            setVisibilityTime(View.GONE, 1)
        }
        binding.deleteTime2.setOnClickListener {
            setVisibilityTime(View.GONE, 2)
        }
        binding.deleteTime3.setOnClickListener {
            setVisibilityTime(View.GONE, 3)
        }

        binding.editTime1.setOnClickListener {
            val dlg = EditTimeDialog(this)
            dlg.setOnOKClickedListener { className, day, time ->
                binding.time1.text = className + "(" + day + time + ")"
            }
            try {
                val tempSplit = binding.time1.text.toString().split("(")
                startDialogWithData(tempSplit, dlg)
            } catch (e: Exception) {
                dlg.start()
            }
        }

        binding.editTime2.setOnClickListener {
            val dlg = EditTimeDialog(this)
            dlg.setOnOKClickedListener { className, day, time ->
                binding.time2.text = className + "(" + day + time + ")"
            }
            try {
                val tempSplit = binding.time2.text.toString().split("(")
                startDialogWithData(tempSplit, dlg)
            } catch (e: Exception) {
                dlg.start()
            }
        }

        binding.editTime3.setOnClickListener {
            val dlg = EditTimeDialog(this)
            dlg.setOnOKClickedListener { className, day, time ->
                binding.time3.text = className + "(" + day + time + ")"
            }
            try {
                val tempSplit = binding.time3.text.toString().split("(")
                startDialogWithData(tempSplit, dlg)
            } catch (e: Exception) {
                dlg.start()
            }
        }

        binding.imgColor.setOnClickListener {
            val dlg = EditColorDialog(this)
            Log.d("color", "클릭함")
            dlg.setOnOKClickedListener(object : EditColorDialog.OKClickedListener {
                override fun onOKClicked(color: Int?) {
                    binding.imgColor.imageTintList = ColorStateList.valueOf(color!!)
                    colorSel = color
                    Log.d("color", "$color")
                }

            })
            dlg.start()
        }
    }

    private fun splitTime(
        time: String?
    ): List<String> {
        val timeListSplitByDay = mutableListOf<String>()
        // 1. 장소 분리 (미술108(월1,2),미술109(월3,4))
        val timeListSplitByLocation = time!!.split("),")
        // 2. 요일 분리 (미래417(화10,11 목10,11,12,13))
        for (timeSplitByLocation in timeListSplitByLocation) {
            val splitLocationDay = timeSplitByLocation.split("(")
            val location = splitLocationDay[0]
            val dayHourList = splitLocationDay[1].replace(")", "").split(" ")
            val dayHourMap = splitContinuousHour(dayHourList)
            dayHourMap.forEach { (day, hourList) ->
                hourList.forEach { hour ->
                    val locationDayHour = "$location($day${hour})"
                    timeListSplitByDay.add(locationDayHour)
                }
            }
        }
        return timeListSplitByDay
    }

    private fun splitContinuousHour(
        dayHourList: List<String>,
    ): MutableMap<String, List<String>> {
        val dayHourMap = mutableMapOf<String, List<String>>()
        for (dayHour in dayHourList) {
            // 3. 시간 분리 (2공학204(화1,2,3,5,6,7))
            val day = dayHour.substring(0, 1)
            val hour = dayHour.substring(1)
            val hourList = checkSeq(hour)
            dayHourMap[day] = hourList
        }
        return dayHourMap
    }

    private fun startDialogWithData(
        tempSplit: List<String>,
        dlg: EditTimeDialog
    ) {
        val location = tempSplit[0]
        val day = tempSplit[1][0].toString()
        val onlyTime = tempSplit[1].substring(1).replace(")", "").split(",")
        val startTime = onlyTime[0]
        val endTime = onlyTime.last()
        dlg.start(location, day, startTime, endTime)
    }


    private fun checkSeq(str: String): List<String> {
        val onlyNumList = str.split(",")
        var tempNumString = ""
        for (idx in onlyNumList.indices) {
            try {
                if ((onlyNumList[idx].toInt() - onlyNumList[idx + 1].toInt()) == -1) {
                    tempNumString = tempNumString + onlyNumList[idx] + ","
                } else {
                    tempNumString = tempNumString + onlyNumList[idx] + "!"
                }
            } catch (e: Exception) {
                tempNumString += onlyNumList[idx]
            }
        }
        return tempNumString.split("!")
    }

    private fun setVisibilityTime(set: Int, idx: Int) {
        if (set == View.GONE) {
            binding.time1.text = ""
        }
        for (textView in bindingTimeList[idx - 1]) {
            textView.visibility = set
        }
    }
}