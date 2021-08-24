package com.kunize.uswtimetable

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import com.kunize.uswtimetable.MainActivity.Companion.dp
import com.kunize.uswtimetable.dataclass.TimeData
import com.kunize.uswtimetable.dialog.BottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UswTimeTable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var view: View = LayoutInflater.from(context).inflate(R.layout.usw_timetable, this, false)

    //속성값
    var isEmpty: Boolean
    var showProfessor: Boolean
    var yaxisType: String?

    private var timeWidth = 0.dp
    private var timeHeight = 50.dp
    private var isMeasured = false

    private val customTimeTable: RelativeLayout
    private val existTimeTable: ConstraintLayout
    private val emptyTimeTable: ConstraintLayout
    private val timeColumnList: List<TextView>
    private val eLearningText: TextView
    private val createTimetableBtn: CardView

    var timeTableData = mutableListOf<TimeData>()

    private val timeWidthMap = mapOf("월" to 0, "화" to 1, "수" to 2, "목" to 3, "금" to 4)

    init {
        addView(view)
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.UswTimeTable,
            0,
            0
        )
        try {
            isEmpty = typedArray.getBoolean(R.styleable.UswTimeTable_isEmpty, false)
            showProfessor = typedArray.getBoolean(R.styleable.UswTimeTable_showProfessor, false)
            yaxisType = typedArray.getString(R.styleable.UswTimeTable_yaxisType)
        } finally {
            typedArray.recycle()
        }

        timeColumnList = listOf(
            findViewById(R.id.nine),
            findViewById(R.id.ten),
            findViewById(R.id.eleven),
            findViewById(R.id.twelve),
            findViewById(R.id.thirteen),
            findViewById(R.id.fourteen),
            findViewById(R.id.fifteen)
        )

        customTimeTable = findViewById(R.id.customTimeTable)
        existTimeTable = findViewById(R.id.existTimeTable)
        emptyTimeTable = findViewById(R.id.emptyTimeTable)
        eLearningText = findViewById(R.id.eLearningText)
        createTimetableBtn = findViewById(R.id.createTimeTableBtn)
        existTimeTable.visibility = View.INVISIBLE
        emptyTimeTable.visibility = View.INVISIBLE

        createTimetableBtn.setOnClickListener {
            val intent = Intent(context, CreateTimeTableActivity::class.java)
            startActivity(context,intent,null)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("seqence", "onMeasure")

        timeWidth = (MeasureSpec.getSize(widthMeasureSpec) - 30.dp) / 5

        isMeasured = true
        Log.d("timetable", "custom $timeWidth")
    }

    private fun showBottomSheet(
        data: TimeData,
        tempTimeData: MutableList<TimeData>,
        v: View?
    ) {
        val bottomSheet: BottomSheet = BottomSheet(data, callback = {
            when (it) {
                1 -> {
                    val deleteIdx = tempTimeData.indexOf(data)
                    val intent = Intent(context, ClassInfoActivity::class.java)
                    intent.putExtra("deleteIdx", deleteIdx)
                    intent.putExtra("className", data.name)
                    intent.putExtra("professor", data.professor)
                    intent.putExtra("color", data.color)
                    var comma = ""
                    try {
                        for (add in data.startTime.toInt() until data.endTime.toInt()) {
                            comma = comma + "$add" + ","
                        }
                        comma += data.endTime
                        intent.putExtra("time", "${data.location}(${data.day}$comma)")
                    } catch (e: Exception) {
                        intent.putExtra(
                            "time",
                            "${data.location}(${data.day}$comma)".replace("이러닝", "")
                        )
                    }
                    startActivity(context, intent, null)
                }
                2 ->
                     {
                        if (v != null) {
                            customTimeTable.removeView(v)
                            tempTimeData.remove(data)
                            reDrawColumn()
                        } else
                            eLearningText.text = ""
                    }
            }
        })
        bottomSheet.show((context as AppCompatActivity).supportFragmentManager, bottomSheet.tag)
    }

    private fun reDrawColumn() {
        val maxTime = try {
            timeTableData.maxOf { it ->
                when {
                    it.endTime.isEmpty() -> 0
                    it.day == "토" -> 0
                    else -> it.endTime.toInt()
                }
            }
        } catch (e: Exception) {
            0
        }
        for (idx in 0..6) {
            timeColumnList[idx].visibility = View.GONE
        }
        for (idx in 0..(maxTime - 8)) {
            try {
                timeColumnList[idx].visibility = View.VISIBLE
            } catch (e: Exception) {
                timeColumnList[idx - 1].visibility = View.VISIBLE
            }
        }
    }

    fun drawTable() {
        if (isEmpty) {
            emptyTimeTable.visibility = VISIBLE
            existTimeTable.visibility = GONE
        } else {
            emptyTimeTable.visibility = GONE
            existTimeTable.visibility = VISIBLE

            Log.d("dataIn","$timeTableData")

            CoroutineScope(IO).launch {
                Log.d("dataIn","그리기 시작")
                do {
                    //onMeasure가 실행될 때 까지 기다립니다.
                } while (!isMeasured)

                withContext(Main) {
                    customTimeTable.removeAllViews()
                    eLearningText.text = ""
                    reDrawColumn()
                }

                for (data in timeTableData) {
                    if ((data.location == "이러닝" && data.day == "") || data.day == "토" || data.location == "") {
                        eLearningText.text =
                            data.name + " (" + data.day + " " + data.startTime + "~" + data.endTime + ")"
                        withContext(Main) {
                            eLearningText.setOnClickListener {
                                if (eLearningText.text.toString() != "")
                                    showBottomSheet(data, timeTableData, null)
                            }
                        }
                        continue
                    }

                    val timeRect = TextView(context)
                    val drawStart = (data.startTime.toInt() - 1) * timeHeight
                    val timeHeight =
                        (data.endTime.toInt() - data.startTime.toInt() + 1) * timeHeight
                    val params = RelativeLayout.LayoutParams(timeWidth, timeHeight)
                    params.leftMargin = timeWidth * timeWidthMap[data.day]!! + 30.dp
                    params.topMargin = drawStart + 30.dp

                    val tempText = "${data.name}\n${data.location}"
                    val timeText = SpannableString(tempText)
                    timeText.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0,data.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    timeText.setSpan(AbsoluteSizeSpan(12,true),data.name.length,tempText.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    timeRect.text = timeText
                    timeRect.setTextColor(Color.WHITE)
                    timeRect.setBackgroundColor(data.color)
                    timeRect.setOnClickListener { v ->
                        Log.d("Test", "클릭")
                        showBottomSheet(data, timeTableData, v)
                    }
                    withContext(Main) {
                        customTimeTable.addView(timeRect, params)
                    }
                }
            }
        }
    }
}