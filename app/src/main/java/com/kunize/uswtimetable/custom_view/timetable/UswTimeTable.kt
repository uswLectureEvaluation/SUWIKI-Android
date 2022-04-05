package com.kunize.uswtimetable.custom_view.timetable

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.size
import com.kunize.uswtimetable.ClassInfoActivity
import com.kunize.uswtimetable.CreateTimeTableActivity
import com.kunize.uswtimetable.MainActivity.Companion.dp
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.TimeData
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
    companion object {
        const val CLASSNAME = 0
        const val CLASSNAME_LOCATION = 1
        const val CLASSNAME_PROFESSOR = 2
        const val CLASSNAME_PROFESSOR_LOCATION = 3
    }

    var view: View = LayoutInflater.from(context).inflate(R.layout.usw_timetable, this, false)

    //속성값
    var isEmpty: Boolean
    var infoFormat: Int

    private var timeWidth = 0.dp
    private var timeHeight = 50.dp

    private val customTimeTable: ConstraintLayout
    private val existTimeTable: ConstraintLayout
    private val emptyTimeTable: ConstraintLayout
    private val timeColumnList: List<TextView>
    private val eLearningText: TextView
    private val createTimetableBtn: CardView

    private val mon: TextView
    private val tue: TextView
    private val wed: TextView
    private val thu: TextView
    private val fri: TextView

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
            infoFormat = typedArray.getInt(R.styleable.UswTimeTable_infoFormat, 1)
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

        mon = findViewById(R.id.mon)
        tue = findViewById(R.id.tue)
        wed = findViewById(R.id.wed)
        thu = findViewById(R.id.thu)
        fri = findViewById(R.id.fri)

        createTimetableBtn.setOnClickListener {
            val intent = Intent(context, CreateTimeTableActivity::class.java)
            startActivity(context, intent, null)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        timeWidth = (MeasureSpec.getSize(widthMeasureSpec) - 34.dp) / 5
    }

    private fun showBottomSheet(
        data: TimeData,
        tempTimeData: MutableList<TimeData>,
        v: View?
    ) {
        val bottomSheet = BottomSheet(data, callback = {
            when (it) {
                1 -> setOnClickEditButton(tempTimeData, data)
                2 -> setOnClickRemoveButton(v, tempTimeData, data)
            }
        })
        bottomSheet.show((context as AppCompatActivity).supportFragmentManager, bottomSheet.tag)
    }

    private fun setOnClickRemoveButton(
        v: View?,
        tempTimeData: MutableList<TimeData>,
        data: TimeData
    ) {
        if (v != null) {
            customTimeTable.removeView(v)
            tempTimeData.remove(data)
            reDrawColumn()
        } else
            eLearningText.text = ""
    }

    private fun setOnClickEditButton(
        tempTimeData: MutableList<TimeData>,
        data: TimeData
    ) {
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
        eLearningText.text = ""
        if (isEmpty) {
            emptyTimeTable.visibility = VISIBLE
            existTimeTable.visibility = GONE
            return
        }

        val topMargin = 55.dp

        emptyTimeTable.visibility = GONE
        existTimeTable.visibility = VISIBLE

        CoroutineScope(IO).launch {
            withContext(Main) {
                customTimeTable.removeViews(22, customTimeTable.size - 22)
                eLearningText.text = ""
                reDrawColumn()
            }
            val set = ConstraintSet()
            for (data in timeTableData) {
                if (addeLearningView(data)) continue
                val timeText = setTimeText(data)
                val params = setParams(data, topMargin)
                val timeRect = setTimeRect(timeText, data)
                timeRect.layoutParams = params
                withContext(Main) {
                    customTimeTable.addView(timeRect, -1)
                    set.clone(customTimeTable)
                    set.connect(timeRect.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    val startId = when(data.day) {
                        "월" -> mon.id
                        "화" -> tue.id
                        "수" -> wed.id
                        "목" -> thu.id
                        else -> fri.id
                    }
                    set.connect(timeRect.id, ConstraintSet.START, startId, ConstraintSet.START)
                    set.connect(timeRect.id, ConstraintSet.END, startId, ConstraintSet.END, 1)
                    set.constrainDefaultWidth(timeRect.id, ConstraintSet.MATCH_CONSTRAINT_SPREAD)
                    set.applyTo(customTimeTable)
                }
            }
        }

    }

    private fun setTimeRect(
        timeText: SpannableString,
        data: TimeData
    ): TextView {
        val timeRect = TextView(context)
        timeRect.id = View.generateViewId()
        timeRect.text = timeText
        timeRect.setTextColor(Color.WHITE)
        timeRect.setBackgroundColor(data.color)
        timeRect.setOnClickListener { v ->
            showBottomSheet(data, timeTableData, v)
        }
        return timeRect
    }

    private fun setParams(
        data: TimeData,
        topMargin: Int
    ): ConstraintLayout.LayoutParams {
        val drawStart = (data.startTime.toInt() - 1) * timeHeight
        val timeHeight = (data.endTime.toInt() - data.startTime.toInt() + 1) * timeHeight
        val params = ConstraintLayout.LayoutParams(0, timeHeight)
        //params.leftMargin = timeWidth * timeWidthMap[data.day]!! + (timeWidthMap[data.day]!!.dp) - 1.dp
        params.topMargin = drawStart + topMargin
        return params
    }

    private fun setTimeText(data: TimeData): SpannableString {
        val tempText = when (infoFormat) {
            CLASSNAME -> data.name
            CLASSNAME_LOCATION -> "${data.name}\n${data.location}"
            CLASSNAME_PROFESSOR -> "${data.name}\n${data.professor}"
            CLASSNAME_PROFESSOR_LOCATION -> "${data.name}\n${data.professor}\n${data.location}"
            else -> "${data.name}\n${data.location}"
        }
        val timeText = SpannableString(tempText)
        timeText.setSpan(
            StyleSpan(android.graphics.Typeface.BOLD),
            0,
            data.name.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        timeText.setSpan(
            AbsoluteSizeSpan(12, true),
            data.name.length,
            tempText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return timeText
    }

    private suspend fun addeLearningView(data: TimeData): Boolean {
        if ((data.location == "이러닝" && data.day == "") || data.day == "토") {
            eLearningText.text =
                "${data.name} (${data.day} ${data.startTime}~${data.endTime})"
            withContext(Main) {
                eLearningText.setOnClickListener {
                    if (eLearningText.text.toString() != "")
                        showBottomSheet(data, timeTableData, null)
                }
            }
            return true
        }
        return false
    }
}