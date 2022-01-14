package com.kunize.uswtimetable.ui.lecture_info

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.kunize.uswtimetable.MainActivity.Companion.dp
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.dataclass.ExamInfoType

object LectureInfoBindingAdapter {
    @BindingAdapter("yearSemesterList")
    @JvmStatic
    fun setList(flexBox: FlexboxLayout, items : java.util.ArrayList<String>) {
        for(item in items) {
            val textView = TextView(flexBox.context)
            textView.text = item
            textView.textSize = 12f
            textView.setTextColor(Color.BLACK)
            textView.setBackgroundResource(R.drawable.rounding_light_gray_background)
            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 8.dp,8.dp,0)
            textView.layoutParams = lp
            flexBox.addView(textView)
        }
    }

    @BindingAdapter("customText")
    @JvmStatic
    fun setText(textView: TextView, string: String) {
        textView.text = string
        when (string) {
            in setOf("없음", "잘줌") -> textView.setTextColor(ContextCompat.getColor(textView.context ,R.color.custom_light_gray))
            in setOf("있음", "보통") -> textView.setTextColor(ContextCompat.getColor(textView.context ,R.color.custom_dark_gray))
            else -> textView.setTextColor(ContextCompat.getColor(textView.context ,R.color.custom_red))
        }
    }

    @BindingAdapter("inflateType")
    @JvmStatic
    fun setLayout(constraintLayout: ConstraintLayout, resource: Int) {
        val examInflater = LayoutInflater.from(constraintLayout.context)
        when(resource) {
            ExamInfoType.NOT_INFLATE -> constraintLayout.removeViews(1, constraintLayout.size - 1)
            ExamInfoType.NEED_USE -> {
                val v = examInflater.inflate(R.layout.hide_exam_info, constraintLayout, true)
                val usePointBtn = v.findViewById<AppCompatButton>(R.id.usePointBtn)
                usePointBtn.setOnClickListener{
                    constraintLayout.removeViews(1, constraintLayout.size - 1)
                    Toast.makeText(constraintLayout.context, "포인트 사용!", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                val v =examInflater.inflate(R.layout.no_exam_info, constraintLayout, true)
                val writeExamBtn : AppCompatButton = v.findViewById(R.id.writeExamBtn)
                writeExamBtn.setOnClickListener{
                    //TODO 시험 정보 쓰기 화면으로 이동
                    Toast.makeText(constraintLayout.context, "준비중", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}