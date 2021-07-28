package com.kunize.uswtimetable

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
import com.kunize.uswtimetable.model.TimeTableData
import com.kunize.uswtimetable.model.TimeTableDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddClassActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddClassBinding.inflate(layoutInflater) }
    private lateinit var timetableList: List<TimeTableData>
    private lateinit var spinnerData: MutableList<String>
    private lateinit var searchAdapter: ClassSearchAdapter
    private val gradeList = listOf("전부","1학년","2학년","3학년","4학년")
    var majorSel = "전부"
    var gradeSel = "전부"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        searchAdapter = ClassSearchAdapter()
        val db = TimeTableDatabase.getInstance(applicationContext)

        runBlocking {
            CoroutineScope(IO).launch {
                timetableList = db?.timetableDao()?.getAll()!!
                searchAdapter.filteredData = timetableList.toMutableList()
                searchAdapter.unfilteredData = timetableList.toMutableList()
            }.join()
            CoroutineScope(IO).launch {
                val tempSpinnerData = mutableSetOf<String>()
                for(data in timetableList) {
                    tempSpinnerData.add(data.major)
                }
                spinnerData = tempSpinnerData.sorted().toMutableList()
                spinnerData.add(0,"전부")
                val majorSpinnerAdapter = ArrayAdapter<String>(this@AddClassActivity,R.layout.item_spinner_major,
                    spinnerData
                )
                val gradeSpinnerAdapter = ArrayAdapter<String>(this@AddClassActivity,R.layout.item_spinner_major,
                    gradeList
                )
                binding.majorSpinner.adapter = majorSpinnerAdapter
                binding.gradeSpinner.adapter = gradeSpinnerAdapter
            }
            binding.recyclerClass.adapter = searchAdapter
            binding.recyclerClass.layoutManager = LinearLayoutManager(this@AddClassActivity)
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

        binding.majorSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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

        binding.gradeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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
            override fun onClick(view: View, position: Int) {
                //여기에서 거래 액티비티 실행
            }
        })
    }

    private fun filterData() {
        val changeData = mutableListOf<TimeTableData>()
        if (majorSel == "전부" && gradeSel == "전부") {
            searchAdapter.filteredData = timetableList as MutableList<TimeTableData>
            searchAdapter.unfilteredData = timetableList as MutableList<TimeTableData>
        } else if (majorSel != "전부" && gradeSel == "전부") {
            for (data in timetableList) {
                if (majorSel == data.major)
                    changeData.add(data)
            }
            searchAdapter.filteredData = changeData
            searchAdapter.unfilteredData = changeData
        } else if (majorSel == "전부" && gradeSel != "전부") {
            for (data in timetableList) {
                if (gradeSel == data.grade)
                    changeData.add(data)
            }
            searchAdapter.filteredData = changeData
            searchAdapter.unfilteredData = changeData
        } else {
            for (data in timetableList) {
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