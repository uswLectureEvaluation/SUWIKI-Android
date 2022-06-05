package com.kunize.uswtimetable.ui.home.timetable

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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.size
import com.kunize.uswtimetable.ui.class_info.ClassInfoActivity
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.UswTimetableBinding
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.ui.create_timetable.CreateTimeTableActivity
import com.kunize.uswtimetable.util.TimetableCellColor
import com.kunize.uswtimetable.util.TimetableColor
import com.kunize.uswtimetable.util.dp
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
    private val binding = UswTimetableBinding.inflate(LayoutInflater.from(context), this, true)

    //속성값
    var isEmpty: Boolean
    var infoFormat: Int

    private var timeHeight = 50.dp
    private val timeColumnList: List<TextView>

    var timeTableData = mutableListOf<TimeData>()

    init {
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
        with(binding) {
            timeColumnList = listOf(nine, ten, eleven, twelve, thirteen, fourteen, fifteen)

            customTimeTable.visibility = View.INVISIBLE
            emptyTimeTable.visibility = View.INVISIBLE

            createTimeTableBtn.setOnClickListener {
                val intent = Intent(context, CreateTimeTableActivity::class.java)
                startActivity(context, intent, null)
            }
        }

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
            binding.customTimeTable.removeView(v)
            tempTimeData.remove(data)
            reDrawColumn()
        } else
            binding.eLearningText.text = ""
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
        val maxTime = if(timeTableData.isEmpty()) 0
        else
            timeTableData.maxOf {
            if(it.day == "토") 0
            else it.endTime.toIntOrNull() ?: 0
        }

        val params = binding.customTimeTable.layoutParams
        params.height = if(maxTime < 8)  425.dp
                        else 25.dp + maxTime * 50.dp + 50.dp
        binding.customTimeTable.layoutParams = params
    }

    fun drawTable() {
        binding.eLearningText.text = ""
        if (isEmpty) {
            binding.emptyTimeTable.visibility = VISIBLE
            binding.customTimeTable.visibility = GONE
            return
        }

        val topMargin = 55.dp

        CoroutineScope(IO).launch {
            withContext(Main) {
                binding.customTimeTable.removeViews(28, binding.customTimeTable.size - 28)
                binding.eLearningText.text = ""
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
                    binding.customTimeTable.addView(timeRect, -1)
                    set.clone(binding.customTimeTable)
                    set.connect(timeRect.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    val idDp = when(data.day) {
                        "월" -> Pair(binding.mon.id, 1.dp)
                        "화" -> Pair(binding.tue.id, 1.dp)
                        "수" -> Pair(binding.wed.id, 1.dp)
                        "목" -> Pair(binding.thu.id, 1.dp)
                        else -> Pair(binding.fri.id, 0)
                    }
                    set.connect(timeRect.id, ConstraintSet.START, idDp.first, ConstraintSet.START)
                    set.connect(timeRect.id, ConstraintSet.END, idDp.first, ConstraintSet.END, idDp.second)
                    set.constrainDefaultWidth(timeRect.id, ConstraintSet.MATCH_CONSTRAINT_SPREAD)
                    set.applyTo(binding.customTimeTable)
                }
            }
            withContext(Main) {
                binding.emptyTimeTable.visibility = GONE
                binding.customTimeTable.visibility = VISIBLE
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
        setBackgroundColor(timeRect, data.color)
        timeRect.setOnClickListener { v ->
            showBottomSheet(data, timeTableData, v)
        }
        return timeRect
    }

    private fun setBackgroundColor(timeRect: TextView, color: Int) {
        when(color) {
            TimetableColor.APRICOT -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_apricot))
            TimetableColor.BROWN -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_brown))
            TimetableColor.DARK_GREEN -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_dark_green))
            TimetableColor.DARK_PURPLE -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_dark_purple))
            TimetableColor.GRAY -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_gray))
            TimetableColor.GREEN -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_green))
            TimetableColor.MINT -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_mint))
            TimetableColor.NAVY -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_navy))
            TimetableColor.PURPLE -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_purple))
            TimetableColor.SKY -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_sky))
            TimetableColor.PINK -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_pink))
            else -> timeRect.setBackgroundColor(ContextCompat.getColor(context, R.color.suwiki_timetable_orange))
        }
    }

    private fun setParams(
        data: TimeData,
        topMargin: Int
    ): LayoutParams {
        val drawStart = (data.startTime.toInt() - 1) * timeHeight
        val timeHeight = (data.endTime.toInt() - data.startTime.toInt() + 1) * timeHeight
        val params = LayoutParams(0, timeHeight)
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
            binding.eLearningText.text =
                "${data.name} (${data.day} ${data.startTime}~${data.endTime})"
            withContext(Main) {
                binding.eLearningText.setOnClickListener {
                    if (binding.eLearningText.text.toString() != "")
                        showBottomSheet(data, timeTableData, null)
                }
            }
            return true
        }
        return false
    }
}