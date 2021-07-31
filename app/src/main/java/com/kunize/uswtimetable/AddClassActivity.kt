package com.kunize.uswtimetable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.adapter.ClassSearchAdapter
import com.kunize.uswtimetable.databinding.ActivityAddClassBinding
import com.kunize.uswtimetable.dataclass.TimeTableData
import com.kunize.uswtimetable.dao_database.TimeTableDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddClassActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddClassBinding.inflate(layoutInflater) }
    private lateinit var timetableData: MutableList<TimeTableData>
    private var spinnerData = listOf(
        "전부",
        "간호학과",
        "건설환경에너지공학부",
        "건축도시부동산학부",
        "경영학부",
        "경제학부",
        "관현악과",
        "교양",
        "국악과",
        "데이터과학부",
        "디자인학부",
        "문화예술학부",
        "미디어커뮤니케이션학과",
        "바이오화학산업학부",
        "법·행정학부",
        "산업및기계공학부",
        "성악과",
        "소방행정학과",
        "스포츠과학부",
        "식품영양학과",
        "아동가족복지학과",
        "외국어학부",
        "의류학과",
        "인문학부",
        "자유전공학부",
        "작곡과",
        "전기전자공학부",
        "전자재료공학부",
        "정보통신학부",
        "조형예술학부",
        "컴퓨터학부",
        "클라우드융복합전공",
        "피아노과",
        "행정학과",
        "호텔관광학부",
        "화학공학·신소재공학부"
    )
    private lateinit var searchAdapter: ClassSearchAdapter
    private val gradeList = listOf("전부", "1학년", "2학년", "3학년", "4학년")
    var majorSel = "전부"
    var gradeSel = "전부"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        searchAdapter = ClassSearchAdapter()
        val db = TimeTableDatabase.getInstance(applicationContext)


        CoroutineScope(IO).launch {
            timetableData = db!!.timetableDao().getAll().toMutableList()
            searchAdapter.filteredData = timetableData
            searchAdapter.unfilteredData = timetableData

            val majorSpinnerAdapter = ArrayAdapter<String>(
                this@AddClassActivity, R.layout.item_spinner_major,
                spinnerData
            )
            val gradeSpinnerAdapter = ArrayAdapter<String>(
                this@AddClassActivity, R.layout.item_spinner_major,
                gradeList
            )

            withContext(Main) {
                binding.majorSpinner.adapter = majorSpinnerAdapter
                binding.gradeSpinner.adapter = gradeSpinnerAdapter
                binding.recyclerClass.adapter = searchAdapter
                binding.recyclerClass.layoutManager = LinearLayoutManager(this@AddClassActivity)
            }
        }


        binding.searchClass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do Nothing
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchAdapter.filter?.filter(charSequence)
            }

            override fun afterTextChanged(charSequence: Editable?) {
                //
            }
        })

        binding.majorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                majorSel = spinnerData[position]
                filterData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.gradeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                gradeSel = gradeList[position]
                filterData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        searchAdapter.setItemClickListener(object : ClassSearchAdapter.ItemClickListener {
            override fun onClick(view: View, data: TimeTableData) {
                val intent = Intent(this@AddClassActivity, ClassInfoActivity::class.java)
                intent.putExtra("className", data.className)
                intent.putExtra("professor", data.professor)
                intent.putExtra("time", data.time)
                startActivity(intent)
            }
        })
    }

    private fun filterData() {
        val changeData = mutableListOf<TimeTableData>()
        if (majorSel == "전부" && gradeSel == "전부") {
            searchAdapter.filteredData = timetableData as MutableList<TimeTableData>
            searchAdapter.unfilteredData = timetableData as MutableList<TimeTableData>
        } else if (majorSel != "전부" && gradeSel == "전부") {
            for (data in timetableData) {
                if (majorSel == data.major)
                    changeData.add(data)
            }
            searchAdapter.filteredData = changeData
            searchAdapter.unfilteredData = changeData
        } else if (majorSel == "전부" && gradeSel != "전부") {
            for (data in timetableData) {
                if (gradeSel == data.grade)
                    changeData.add(data)
            }
            searchAdapter.filteredData = changeData
            searchAdapter.unfilteredData = changeData
        } else {
            for (data in timetableData) {
                if (majorSel == data.major && gradeSel == data.grade)
                    changeData.add(data)
            }
            searchAdapter.filteredData = changeData
            searchAdapter.unfilteredData = changeData
        }
        searchAdapter.notifyDataSetChanged()
        binding.searchClass.setText("")
    }

}