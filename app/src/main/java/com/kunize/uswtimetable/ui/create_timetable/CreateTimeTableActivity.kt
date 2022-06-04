package com.kunize.uswtimetable.ui.create_timetable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kunize.uswtimetable.util.DateUtil.Companion.getSemester
import com.kunize.uswtimetable.util.DateUtil.Companion.getYear
import com.kunize.uswtimetable.util.DateUtil.Companion.getYearList
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityCreateTimeTableBinding
import com.kunize.uswtimetable.data.local.TimeTableList
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.util.TimeTableSelPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CreateTimeTableActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreateTimeTableBinding.inflate(layoutInflater)}

    companion object {
        val semesterList = listOf("1", "2")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val yearList = getYearList()
        val yearAdapter = ArrayAdapter<String>(this, R.layout.item_spinner,yearList)
        val semesterAdapter = ArrayAdapter<String>(this, R.layout.item_spinner, semesterList)

        binding.yearSpinner.adapter = yearAdapter
        binding.semesterSpinner.adapter = semesterAdapter

        val thisYear = getYear()
        val thisSemester = getSemester()

        // 년도, 학기 자동 설정
        val yearIndex = yearList.indexOf(thisYear.toString())
        if (yearIndex != -1) binding.yearSpinner.setSelection(yearIndex) // yearList에 찾는 값(thisYear)이 없다면 -1을 반환하므로 if문으로 체크
        binding.semesterSpinner.setSelection(thisSemester-1) // 현재 학기에서 1을 뺀 것이 스피너에서의 위치

        binding.ivClose.setOnClickListener {
            finish()
        }

        val db = TimeTableListDatabase.getInstance(applicationContext)!!
        binding.finishButton.setOnClickListener {
            if(binding.editName.text.toString() == "") {
                Toast.makeText(this,"시간표 이름을 입력해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CoroutineScope(IO).launch {
                val currentTime = System.currentTimeMillis()
                val selectedYear = yearList[binding.yearSpinner.selectedItemPosition]
                val selectedSemester = semesterList[binding.semesterSpinner.selectedItemPosition]

                // SharedPreference에 저장
                TimeTableSelPref.prefs.setLong("timetableSel",currentTime)

                val tempData = TimeTableList(currentTime,selectedYear, selectedSemester, binding.editName.text.toString())
                db.timetableListDao().insert(tempData)
                finish()
            }
        }
    }
}