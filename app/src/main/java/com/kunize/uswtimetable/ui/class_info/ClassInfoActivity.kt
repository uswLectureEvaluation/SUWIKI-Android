package com.kunize.uswtimetable.ui.class_info

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.kunize.uswtimetable.ui.main.MainActivity
import com.kunize.uswtimetable.ui.home.timetable.DBManager.arrayToJson
import com.kunize.uswtimetable.ui.home.timetable.DBManager.getCurrentTimetableInfo
import com.kunize.uswtimetable.ui.home.timetable.DBManager.jsonToArray
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.databinding.ActivityClassInfoBinding
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.data.local.TimeTableList
import com.kunize.uswtimetable.util.TimeStringFormatter
import com.kunize.uswtimetable.util.TimetableCellColor.colorMap
import com.kunize.uswtimetable.util.TimetableColor.APRICOT
import com.kunize.uswtimetable.util.TimetableColor.BROWN
import com.kunize.uswtimetable.util.TimetableColor.DARK_GREEN
import com.kunize.uswtimetable.util.TimetableColor.DARK_PURPLE
import com.kunize.uswtimetable.util.TimetableColor.GRAY
import com.kunize.uswtimetable.util.TimetableColor.GREEN
import com.kunize.uswtimetable.util.TimetableColor.MINT
import com.kunize.uswtimetable.util.TimetableColor.NAVY
import com.kunize.uswtimetable.util.TimetableColor.ORANGE
import com.kunize.uswtimetable.util.TimetableColor.PINK
import com.kunize.uswtimetable.util.TimetableColor.PURPLE
import com.kunize.uswtimetable.util.TimetableColor.SKY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClassInfoActivity : AppCompatActivity() {

    var colorSel: Int = Color.rgb(255, 193, 82)
    private val binding by lazy { ActivityClassInfoBinding.inflate(layoutInflater) }
    private lateinit var jsonStr: String
    private lateinit var db: TimeTableListDatabase
    private lateinit var timetableSel: TimeTableList
    private var tempTimeData = mutableListOf<TimeData>()
    private lateinit var radioHashMap: HashMap<Int, Int>
    private lateinit var timeColorRadioButtonHashMap: HashMap<Int, RadioButton>
    private lateinit var dayList: List<TextView>
    private lateinit var startTimeList: List<EditText>
    private lateinit var endTimeList: List<EditText>
    private lateinit var deleteTimeList: List<TextView>
    private lateinit var locationList: List<EditText>
    private lateinit var clDayList: List<ConstraintLayout>
    private lateinit var clTimeList: List<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            dayList = listOf(tvDay1, tvDay2, tvDay3)
            startTimeList = listOf(etStartClass1, etStartClass2, etStartClass3)
            endTimeList = listOf(etEndClass1, etEndClass2, etEndClass3)
            deleteTimeList = listOf(deleteTime1, deleteTime2, deleteTime3)
            locationList = listOf(location1, location2, location3)
            clDayList = listOf(clDay1, clDay2, clDay3)
            clTimeList = listOf(clClass1, clClass2, clClass3)

            radioHashMap = hashMapOf(
                rbSky.id to SKY, rbNavy.id to NAVY, rbPurple.id to PURPLE,
                rbDarkPurple.id to DARK_PURPLE, rbMint.id to MINT, rbDarkGreen.id to DARK_GREEN,
                rbApricot.id to APRICOT, rbOrange.id to ORANGE, rbPink.id to PINK,
                rbBrown.id to BROWN, rbGreen.id to GREEN, rbGray.id to GRAY
            )

            timeColorRadioButtonHashMap = hashMapOf(
                SKY to rbSky, NAVY to rbNavy, PURPLE to rbPurple,
                DARK_PURPLE to rbPurple, MINT to rbMint, DARK_GREEN to rbDarkGreen,
                APRICOT to rbApricot, ORANGE to rbOrange, PINK to rbPink,
                BROWN to rbBrown, GREEN to rbGreen, GRAY to rbGray
            )
        }

        binding.clDay1.setOnClickListener {
            val dialog = DaySortDialog(this)
            dialog.setDialogListener(object : DaySortDialog.ItemClickListener {
                override fun onClick(text: String) {
                    binding.tvDay1.text = text
                }
            })
            dialog.show()
        }

        binding.clDay2.setOnClickListener {
            val dialog = DaySortDialog(this)
            dialog.setDialogListener(object : DaySortDialog.ItemClickListener {
                override fun onClick(text: String) {
                    binding.tvDay2.text = text
                }
            })
            dialog.show()
        }

        binding.clDay3.setOnClickListener {
            val dialog = DaySortDialog(this)
            dialog.setDialogListener(object : DaySortDialog.ItemClickListener {
                override fun onClick(text: String) {
                    binding.tvDay3.text = text
                }
            })
            dialog.show()
        }

        binding.rgColor.setOnCheckedChangeListener { group, checkedId ->
            colorSel = radioHashMap[checkedId]!!
        }

        val deleteIdx = intent.getIntExtra("deleteIdx", -1)

        val className = intent.getStringExtra("className")
        val professor = intent.getStringExtra("professor")
        val time = intent.getStringExtra("time")

        binding.editClassName.setText(className)
        binding.editProfessorName.setText(professor)

        CoroutineScope(IO).launch {
            db = TimeTableListDatabase.getInstance(applicationContext)!!
            timetableSel = getCurrentTimetableInfo(db)
            jsonStr = timetableSel.timeTableJsonData
            tempTimeData = jsonToArray(jsonStr)

            setTimeTableCellColor()
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
                    if (checkInputDataEmpty(deleteIdx, tempDeleteData)) return@launch
                    if (checkTime1(deleteIdx, tempDeleteData)) return@launch
                    if (checkTime2(deleteIdx, tempDeleteData)) return@launch
                    if (checkTime3(deleteIdx, tempDeleteData)) return@launch
                    val addTimeData = extractData(locationList, inputClassName, inputProfessor)
                    if (checkeLearning(addTimeData, deleteIdx, tempDeleteData)) return@launch
                    if (checkOverlapTime(addTimeData, deleteIdx, tempDeleteData)) return@launch
                    tempTimeData.addAll(addTimeData)
                    timetableSel.timeTableJsonData = arrayToJson(tempTimeData)
                    db.timetableListDao().update(timetableSel)
                    goToMainActivity()
                } catch (e: Exception) {
                    unknownError(e, deleteIdx, tempDeleteData)
                }
            }
        }

        try {
            // deleteIdx가 -1이 아닌 경우에만 문자열 분리
            if (deleteIdx != -1) {
                //시간표 수정 로직
                if (time!! == "" || time == "()" || time == "None") {
                    binding.location1.setText("이러닝")
                    binding.tvDay1.text = "없음"
                }
                else {
                    setTimeLocationView(time)
                }
            } else if (time!! == "None") {
                binding.location1.setText("이러닝")
                binding.tvDay1.text = "없음"
            } else {
                setTimeLocationView(time)
            }
        } catch (e: Exception) {
            Log.d("timeSplit", "$e")
            binding.location1.setText("이러닝")
        }

        binding.addTime.setOnClickListener {
            if (!binding.location1.isVisible)
                setVisibilityTime(View.VISIBLE, 1)
            else if (!binding.location2.isVisible)
                setVisibilityTime(View.VISIBLE, 2)
            else if (!binding.location3.isVisible)
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
    }

    private fun setTimeLocationView(time: String?) {
        val timeListSplitByDay = TimeStringFormatter().splitTimeForClassInfo(time)
        for (i in timeListSplitByDay.indices) {
            setVisibilityTime(View.VISIBLE, i + 1)
            val tempSplit = timeListSplitByDay[i].split("(")
            locationList[i].setText(tempSplit[0])
            dayList[i].text = tempSplit[1][0].toString() + "요일"
            val onlyTime = tempSplit[1].substring(1).replace(")", "").split(",")
            val startTime = onlyTime[0]
            val endTime = onlyTime.last()
            startTimeList[i].setText(startTime)
            endTimeList[i].setText(endTime)
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this@ClassInfoActivity, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //액티비티 스택제거
        startActivity(intent)
    }

    private suspend fun unknownError(
        e: Exception,
        deleteIdx: Int,
        tempDeleteData: TimeData
    ) {
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

    private suspend fun checkOverlapTime(
        addTimeData: MutableList<TimeData>,
        deleteIdx: Int,
        tempDeleteData: TimeData
    ): Boolean {
        for (newTime in addTimeData) {
            for (oldTime in tempTimeData) {
                if (newTime.day == oldTime.day) {
                    val newStartTime = newTime.startTime.toInt()
                    val newEndTime = newTime.endTime.toInt()
                    val oldStartTime = oldTime.startTime.toInt()
                    val oldEndTime = oldTime.endTime.toInt()
                    if (
                        (newStartTime in oldStartTime..oldEndTime) || (newEndTime in oldStartTime..oldEndTime) ||
                        (oldStartTime in newStartTime..newEndTime) || (oldEndTime in newStartTime..newEndTime)
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
                        return true
                    }
                }
            }
        }
        return false
    }

    private suspend fun checkeLearning(
        addTimeData: MutableList<TimeData>,
        deleteIdx: Int,
        tempDeleteData: TimeData
    ): Boolean {
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
                return true
            }
        }
        return false
    }

    private fun extractData(
        extractionList: List<TextView>,
        inputClassName: String,
        inputProfessor: String
    ): MutableList<TimeData> {
        val addTimeData = mutableListOf<TimeData>()
        for (i in extractionList.indices) {
            if (extractionList[i].isVisible && dayList[i].text == "없음") {
                addTimeData.add(
                    TimeData(
                        inputClassName,
                        inputProfessor,
                        "이러닝",
                        color = colorSel
                    )
                )
            } else if (extractionList[i].visibility == View.VISIBLE) {
                addTimeData.add(
                    TimeData(
                        inputClassName,
                        inputProfessor,
                        locationList[i].text.toString(),
                        dayList[i].text.toString().replace("요일", ""),
                        startTimeList[i].text.toString(),
                        endTimeList[i].text.toString(),
                        colorSel
                    )
                )
            }
        }
        return addTimeData
    }

    private suspend fun checkInputDataEmpty(
        deleteIdx: Int,
        tempDeleteData: TimeData
    ): Boolean {
        if (binding.location1.text.toString().isEmpty() && binding.location2.text.toString()
                .isEmpty() && binding.location3.text.toString().isEmpty()
        ) {
            withContext(Main) {
                Toast.makeText(
                    this@ClassInfoActivity,
                    "장소를 입력해주세요!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (deleteIdx != -1)
                tempTimeData.add(deleteIdx, tempDeleteData)
            return true
        }
        return false
    }

    private suspend fun checkTime1(
        deleteIdx: Int,
        tempDeleteData: TimeData
    ): Boolean {
        if(binding.tvDay1.text == "없음") return false
        if (binding.location1.isVisible &&
            (binding.location1.text.toString().isBlank() || binding.etStartClass1.text.toString().isBlank() || binding.etEndClass1.text.toString().isBlank()
                    || (binding.etStartClass1.text.toString().toInt() > binding.etEndClass1.text.toString().toInt()
                    || (binding.etStartClass1.text.toString().toInt() !in 1..15)
                    || (binding.etEndClass1.text.toString().toInt() !in 1..15)))
        ) {
            withContext(Main) {
                Toast.makeText(
                    this@ClassInfoActivity,
                    "시간 또는 장소를 확인해주세요!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (deleteIdx != -1)
                tempTimeData.add(deleteIdx, tempDeleteData)
            return true
        }
        return false
    }

    private suspend fun checkTime2(
        deleteIdx: Int,
        tempDeleteData: TimeData
    ): Boolean {
        if(binding.tvDay2.text == "없음") return false
        if (binding.location2.isVisible &&
            (binding.location2.text.toString().isBlank() || binding.etStartClass2.text.toString().isBlank() || binding.etEndClass2.text.toString().isBlank()
                    || (binding.etStartClass2.text.toString().toInt() > binding.etEndClass2.text.toString().toInt()
                    || (binding.etStartClass2.text.toString().toInt() !in 1..15)
                    || (binding.etEndClass2.text.toString().toInt() !in 1..15)) && binding.tvDay2.text != "없음")
        ) {
            withContext(Main) {
                Toast.makeText(
                    this@ClassInfoActivity,
                    "시간 또는 장소를 확인해주세요!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (deleteIdx != -1)
                tempTimeData.add(deleteIdx, tempDeleteData)
            return true
        }
        return false
    }

    private suspend fun checkTime3(
        deleteIdx: Int,
        tempDeleteData: TimeData
    ): Boolean {
        if(binding.tvDay3.text == "없음") return false
        if (binding.location3.isVisible &&
            (binding.location3.text.toString().isBlank() || binding.etStartClass3.text.toString().isBlank() || binding.etEndClass3.text.toString().isBlank()
                    || (binding.etStartClass3.text.toString().toInt() > binding.etEndClass3.text.toString().toInt()
                    || (binding.etStartClass3.text.toString().toInt() !in 1..15)
                    || (binding.etEndClass3.text.toString().toInt() !in 1..15)) && binding.tvDay3.text != "없음")
        ) {
            withContext(Main) {
                Toast.makeText(
                    this@ClassInfoActivity,
                    "시간 또는 장소를 확인해주세요!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (deleteIdx != -1)
                tempTimeData.add(deleteIdx, tempDeleteData)
            return true
        }
        return false
    }

    private fun setTimeTableCellColor(): Int {
        var randomColor = intent.getIntExtra("color", -1)

        if (randomColor == -1) {
            val randomNum = MutableList(12) { i -> i }.shuffled().toMutableList()
            do {
                randomColor = colorMap.values.toIntArray()[randomNum.removeLast()]
            } while (jsonStr.contains("$randomColor") && (tempTimeData.size < 12))
        }

        timeColorRadioButtonHashMap[randomColor]?.isChecked = true
        colorSel = randomColor
        return randomColor
    }

    private fun setVisibilityTime(set: Int, idx: Int) {
        dayList[idx - 1].apply {
            text = "없음"
            visibility = set
        }
        locationList[idx - 1].apply {
            setText("")
            visibility = set
        }
        startTimeList[idx - 1].apply {
            setText("")
            visibility = set
        }
        endTimeList[idx - 1].apply {
            setText("")
            visibility = set
        }
        deleteTimeList[idx - 1].visibility = set
        clDayList[idx - 1].visibility = set
        clTimeList[idx - 1].visibility = set
    }
}