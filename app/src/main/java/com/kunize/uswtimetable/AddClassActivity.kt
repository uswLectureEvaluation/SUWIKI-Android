package com.kunize.uswtimetable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakao.adfit.ads.AdListener
import com.kakao.adfit.ads.ba.BannerAdView
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
    private lateinit var spinnerData: MutableList<String>
    private lateinit var searchAdapter: ClassSearchAdapter
    private val gradeList = listOf("전부", "1학년", "2학년", "3학년", "4학년")
    var majorSel = "전부"
    var gradeSel = "전부"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bannerAdView.setClientId(getString(R.string.kakaoAdfitID))  // 할당 받은 광고단위 ID 설정
        binding.bannerAdView.loadAd()

        searchAdapter = ClassSearchAdapter()
        val db = TimeTableDatabase.getInstance(applicationContext)

        binding.searchClass.visibility = View.INVISIBLE


        CoroutineScope(IO).launch {
            timetableData = db!!.timetableDao().getAll().toMutableList()
            searchAdapter.filteredData = timetableData
            searchAdapter.unfilteredData = timetableData

            val tempSpinnerData = mutableSetOf<String>()
            for(data in timetableData)
                tempSpinnerData.add(data.major)
            spinnerData = tempSpinnerData.sorted().toMutableList()
            spinnerData.add(0, "전부")

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
                binding.majorSpinner.setSelection(TimeTableSelPref.prefs.getInt("majorSel", 0))
                binding.gradeSpinner.setSelection(TimeTableSelPref.prefs.getInt("gradeSel", 0))
                binding.recyclerClass.adapter = searchAdapter
                binding.recyclerClass.layoutManager = LinearLayoutManager(this@AddClassActivity)
                binding.searchClass.visibility = View.VISIBLE
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
                TimeTableSelPref.prefs.setInt("majorSel", position)
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
                TimeTableSelPref.prefs.setInt("gradeSel", position)
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

    override fun onResume() {
        super.onResume()
        binding.bannerAdView.resume()
    }


    override fun onPause() {
        super.onPause()
        binding.bannerAdView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.bannerAdView.destroy()
    }

}