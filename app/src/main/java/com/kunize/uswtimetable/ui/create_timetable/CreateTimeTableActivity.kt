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
import com.kunize.uswtimetable.SuwikiApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.Serializable

class CreateTimeTableActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreateTimeTableBinding.inflate(layoutInflater) }

    companion object {
        val semesterList = listOf("1", "2")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val yearList = getYearList()
        var thisYear = getYear()
        var thisSemester = getSemester()
        val yearAdapter = ArrayAdapter(this, R.layout.item_spinner, yearList)
        val semesterAdapter = ArrayAdapter(this, R.layout.item_spinner, semesterList)
        val editData = intent.getSerializableExtra("edit_data")
        editData?.let {
            binding.finishButton.text = "수정"
            binding.textView.text = "시간표 수정"
            binding.editName.setText((it as TimeTableList).timeTableName)
            thisYear = it.year.toInt()
            thisSemester = it.semester.toInt()
        }

        binding.yearSpinner.adapter = yearAdapter
        binding.semesterSpinner.adapter = semesterAdapter

        // 년도, 학기 자동 설정
        val yearIndex = yearList.indexOf(thisYear.toString())
        if (yearIndex != -1) binding.yearSpinner.setSelection(yearIndex) // yearList에 찾는 값(thisYear)이 없다면 -1을 반환하므로 if문으로 체크
        binding.semesterSpinner.setSelection(thisSemester - 1) // 현재 학기에서 1을 뺀 것이 스피너에서의 위치

        binding.ivClose.setOnClickListener {
            finish()
        }

        val db = TimeTableListDatabase.getInstance(applicationContext)!!
        binding.finishButton.setOnClickListener {
            if (binding.editName.text.toString() == "") {
                Toast.makeText(this, "시간표 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CoroutineScope(IO).launch {
                if (editData == null)
                    createTimetable(yearList, db)
                else {
                    editTimetable(editData, yearList, db)
                }
            }
        }
    }

    private fun editTimetable(
        editData: Serializable?,
        yearList: List<String>,
        db: TimeTableListDatabase
    ) {
        (editData as TimeTableList).semester =
            semesterList[binding.semesterSpinner.selectedItemPosition]
        editData.year = yearList[binding.yearSpinner.selectedItemPosition]
        editData.timeTableName = binding.editName.text.toString()
        db.timetableListDao().update(editData)
        finish()
    }

    private fun createTimetable(
        yearList: List<String>,
        db: TimeTableListDatabase
    ) {

        val currentTime = System.currentTimeMillis()
        val selectedYear = yearList[binding.yearSpinner.selectedItemPosition]
        val selectedSemester = semesterList[binding.semesterSpinner.selectedItemPosition]

        // SharedPreference에 저장
        SuwikiApplication.prefs.setLong("timetableSel", currentTime)

        val tempData = TimeTableList(
            currentTime,
            selectedYear,
            selectedSemester,
            binding.editName.text.toString()
        )
        db.timetableListDao().insert(tempData)
        finish()

    }
}