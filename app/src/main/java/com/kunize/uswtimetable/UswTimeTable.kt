package com.kunize.uswtimetable

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kunize.uswtimetable.MainActivity.Companion.dp
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
    var view: View = LayoutInflater.from(context).inflate(R.layout.usw_timetable, this, false)
    private var timeWidth = 0.dp
    private var timeHeight = 50.dp
    private var isMeasured = false
    var timeTableData = mutableListOf<TimeData>()

    init {
        addView(view)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("seqence", "onMeasure")

        timeWidth = (MeasureSpec.getSize(widthMeasureSpec) - 30.dp) / 5

        view.findViewById<TextView>(R.id.mon).width = timeWidth
        view.findViewById<TextView>(R.id.tue).width = timeWidth
        view.findViewById<TextView>(R.id.wed).width = timeWidth
        view.findViewById<TextView>(R.id.thu).width = timeWidth
        view.findViewById<TextView>(R.id.fri).width = timeWidth

        isMeasured = true
        Log.d("timetable", "custom $timeWidth")
    }

    fun drawTable() {
        CoroutineScope(IO).launch {
            do {
                //onMeasure가 실행될 때 까지 기다립니다.
            } while (!isMeasured)

            Log.d("seqence", "drawTable")
            val timeRect = TextView(context)
            val drawStart = timeHeight * 2
            val params = LayoutParams(timeWidth, timeHeight * 3)
            params.leftMargin = timeWidth * 3 + 30.dp
            params.topMargin = drawStart + 30.dp
            timeRect.setBackgroundColor(Color.BLUE)
            withContext(Main) {
                view.findViewById<RelativeLayout>(R.id.customTimeTable).addView(timeRect, params)
            }
        }
    }


}