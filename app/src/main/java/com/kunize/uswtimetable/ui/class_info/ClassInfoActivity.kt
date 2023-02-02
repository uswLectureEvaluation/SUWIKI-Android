package com.kunize.uswtimetable.ui.class_info

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.kunize.uswtimetable.ui.home.timetable.DBManager.getCurrentTimetableInfo
import com.kunize.uswtimetable.ui.home.timetable.DBManager.jsonToArray
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.databinding.ActivityClassInfoBinding
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.data.local.TimeTableList
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.ModelManager
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.HandleFilterResultStrategy
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
import com.kunize.uswtimetable.util.interceptingFilter.FilterExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClassInfoActivity : AppCompatActivity() {

    private var colorSel: Int = Color.rgb(255, 193, 82)
    private val binding by lazy { ActivityClassInfoBinding.inflate(layoutInflater) }
    private lateinit var jsonStr: String
    private lateinit var db: TimeTableListDatabase
    private lateinit var timetableSel: TimeTableList
    private var currentTimeTable = mutableListOf<TimeData>()
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

        initDefaultData()

        val deleteIdx = intent.getIntExtra("deleteIdx", -1)
        val className = intent.getStringExtra("className")
        val professor = intent.getStringExtra("professor")
        val time = intent.getStringExtra("time")

        binding.editClassName.setText(className)
        binding.editProfessorName.setText(professor)

        setVisibilityTime(View.GONE, 2)
        setVisibilityTime(View.GONE, 3)

        try {
            if (modeEditTime(deleteIdx)) {
                //시간표 수정 로직
                if (time == "" || time == "()" || time == "None") {
                    binding.location1.setText("이러닝")
                    binding.tvDay1.text = "없음"
                } else {
                    setTimeLocationView(time)
                }
            } else if (time == "None") {
                binding.location1.setText("이러닝")
                binding.tvDay1.text = "없음"
            } else {
                setTimeLocationView(time)
            }
        } catch (e: Exception) {
            Log.d("timeSplit", "$e")
            binding.location1.setText("이러닝")
            binding.tvDay1.text = "없음"
        }

        CoroutineScope(IO).launch {
            db = TimeTableListDatabase.getInstance(applicationContext)!!
            timetableSel = getCurrentTimetableInfo(db)
            jsonStr = timetableSel.timeTableJsonData
            currentTimeTable = jsonToArray(jsonStr)

            setTimeTableCellColor()
        }

        clDayList.zip(dayList).forEach { (clDay, tvDay) ->
            clDay.setOnClickListener {
                showDaySortDialog(tvDay)
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.addTime.setOnClickListener {
            if (!binding.location1.isVisible)
                setVisibilityTime(View.VISIBLE, 1)
            else if (!binding.location2.isVisible)
                setVisibilityTime(View.VISIBLE, 2)
            else if (!binding.location3.isVisible)
                setVisibilityTime(View.VISIBLE, 3)
        }

        deleteTimeList.forEachIndexed { index, deleteTime ->
            deleteTime.setOnClickListener {
                setVisibilityTime(View.GONE, index + 1)
            }
        }

        binding.rgColor.setOnCheckedChangeListener { group, checkedId ->
            colorSel = radioHashMap[checkedId]!!
        }

        binding.finishButton.setOnClickListener {
            val inputClassName = binding.editClassName.text.toString()
            val inputProfessor = binding.editProfessorName.text.toString()
            CoroutineScope(IO).launch {
                var tempDeleteData = TimeData()

                if (modeEditTime(deleteIdx)) {
                    tempDeleteData = currentTimeTable[deleteIdx]
                    currentTimeTable.removeAt(deleteIdx)
                }

                val modelManager = ModelManager(
                    dayList = dayList,
                    locationList = locationList,
                    startTimeList = startTimeList,
                    endTimeList = endTimeList,
                    timeDataTobeAdded = extractData(locationList, inputClassName, inputProfessor),
                    currentTimeTable = currentTimeTable,
                    deleteTimeList = deleteTimeList,
                    context = this@ClassInfoActivity,
                    timetableSel = timetableSel,
                    db = db
                )

                val timetableValidationFilter = FilterExecutor()
                val filterResult = timetableValidationFilter
                    .addFilter(modelManager.getFilterChainModels())
                    .invoke()

                val handleAddTimeFilterResultStrategy =
                    HandleFilterResultStrategy(doWhenNotInvalidation = {
                        if (modeEditTime(deleteIdx))
                            currentTimeTable.add(deleteIdx, tempDeleteData)
                    })

                withContext(Main) {
                    handleAddTimeFilterResultStrategy
                        .addStrategy(modelManager.getStrategies())
                        .invoke(filterResult)
                }
            }
        }
    }

    private fun showDaySortDialog(textView: TextView) {
        val dialog = DaySortDialog(this)
        dialog.setDialogListener(object : DaySortDialog.ItemClickListener {
            override fun onClick(text: String) {
                textView.text = text
            }
        })
        dialog.show()
    }

    private fun initDefaultData() {
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

    private fun extractData(
        extractionList: List<TextView>,
        inputClassName: String,
        inputProfessor: String
    ): MutableList<TimeData> {
        val addTimeData = mutableListOf<TimeData>()
        for (i in extractionList.indices) {
            if (extractionList[i].isVisible && (dayList[i].text == "없음")) {
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

    private fun modeEditTime(deleteIdx: Int) = deleteIdx != -1

    private fun setTimeTableCellColor(): Int {
        var randomColor = intent.getIntExtra("color", -1)

        if (randomColor == -1) {
            val randomNum = MutableList(12) { i -> i }.shuffled().toMutableList()
            do {
                randomColor = colorMap.values.toIntArray()[randomNum.removeLast()]
            } while (jsonStr.contains("$randomColor") && (currentTimeTable.size < 12))
        }

        timeColorRadioButtonHashMap[randomColor]?.isChecked = true
        colorSel = randomColor
        return randomColor
    }

    private fun setVisibilityTime(set: Int, idx: Int) {
        dayList[idx - 1].apply {
            text = "월요일"
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