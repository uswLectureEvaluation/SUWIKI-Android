package com.kunize.uswtimetable

import android.R.id.button1
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.UswTimeTable.Companion.CLASSNAME
import com.kunize.uswtimetable.UswTimeTable.Companion.CLASSNAME_LOCATION
import com.kunize.uswtimetable.UswTimeTable.Companion.CLASSNAME_PROFESSOR
import com.kunize.uswtimetable.UswTimeTable.Companion.CLASSNAME_PROFESSOR_LOCATION
import com.kunize.uswtimetable.UswTimeTable.Companion.PERIOD
import com.kunize.uswtimetable.UswTimeTable.Companion.TIME
import com.kunize.uswtimetable.databinding.ActivitySettingBinding


class SettingActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.classInfoBtn.apply {
            setOnClickListener {
                val list = arrayOf("수업명","수업명, 장소", "수업명, 교수명", "수업명, 교수명, 장소")
                showDialog(it ,list,"infoFormat")
            }
            text = getPrefInfo("infoFormat", 1)
        }

        binding.timeFormatBtn.apply {
            setOnClickListener {
                val list = arrayOf("교시", "시간")
                showDialog(it, list,"yaxisType")
            }
            text = getPrefInfo("yaxisType", 5)
        }
    }

    private fun getPrefInfo(type: String, defValue: Int): String {
        return when (TimeTableSelPref.prefs.getInt(type, defValue)) {
            CLASSNAME -> "수업명"
            CLASSNAME_LOCATION -> "수업명, 장소"
            CLASSNAME_PROFESSOR -> "수업명, 교수명"
            CLASSNAME_PROFESSOR_LOCATION -> "수업명, 교수명, 장소"
            PERIOD -> "교시"
            TIME -> "시간"
            else -> ""
        }
    }

    private fun showDialog(v: View, list: Array<String>, setType: String) {
        val dlg = AlertDialog.Builder(this)
        dlg.setItems(list
        ) { _, which ->
            val temp = when (list[which]) {
                "수업명" -> CLASSNAME
                "수업명, 장소" -> CLASSNAME_LOCATION
                "수업명, 교수명" -> CLASSNAME_PROFESSOR
                "수업명, 교수명, 장소" -> CLASSNAME_PROFESSOR_LOCATION
                "교시" -> PERIOD
                "시간" -> TIME
                else -> 0
            }
            (v as TextView).text = list[which]
            TimeTableSelPref.prefs.setInt(setType, temp)
        }
        dlg.show()
    }
}