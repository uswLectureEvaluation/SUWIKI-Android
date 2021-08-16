package com.kunize.uswtimetable

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.kunize.uswtimetable.MainActivity.Companion.dp
import com.kunize.uswtimetable.dataclass.TimeData

class UswTimeTable@JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0)
    : RelativeLayout(context,attrs,defStyleAttr) {
    var view: View = LayoutInflater.from(context).inflate(R.layout.usw_timetable,this,false)
    private var timeWidth = 0.dp
    private var timeHeight = 50.dp
    var timeTableData = mutableListOf<TimeData>()

    init {
        addView(view)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        timeWidth = (MeasureSpec.getSize(widthMeasureSpec) - 30.dp)/5
        view.findViewById<TextView>(R.id.mon).width = timeWidth
        view.findViewById<TextView>(R.id.tue).width = timeWidth
        view.findViewById<TextView>(R.id.wed).width = timeWidth
        view.findViewById<TextView>(R.id.thu).width = timeWidth
        view.findViewById<TextView>(R.id.fri).width = timeWidth

        Log.d("timetable","custom $timeWidth")
    }



}