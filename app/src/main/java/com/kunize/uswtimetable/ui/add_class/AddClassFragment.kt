package com.kunize.uswtimetable.ui.add_class

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.data.local.TimeTableData
import com.kunize.uswtimetable.data.local.TimeTableDatabase
import com.kunize.uswtimetable.databinding.FragmentAddClassBinding
import com.kunize.uswtimetable.ui.class_info.ClassInfoActivity
import com.kunize.uswtimetable.util.FragmentType
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.extensions.onTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddClassFragment : Fragment() {
    lateinit var binding: FragmentAddClassBinding
    private lateinit var timetableData: MutableList<TimeTableData>
    private lateinit var spinnerData: MutableList<String>
    private lateinit var searchAdapter: ClassSearchAdapter
    private val gradeList = listOf("전체", "1학년", "2학년", "3학년", "4학년")
    var majorSel = "전체"
    var gradeSel = "전체"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddClassBinding.inflate(inflater, container, false)

        searchAdapter = ClassSearchAdapter()
        val db = TimeTableDatabase.getInstance(requireActivity())

        binding.searchClass.visibility = View.INVISIBLE

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.clOpenMajor.setOnClickListener {
            val action = AddClassFragmentDirections.globalOpenMajor(FragmentType.ADD_CLASS)
            findNavController().navigate(action)
        }

        binding.clSort.setOnClickListener {
            val dialog = GradeSortDialog(context as AppCompatActivity)
            dialog.setDialogListener(object : GradeSortDialog.ItemClickListener {
                override fun onClick(text: String) {
                    binding.tvSortSelected.text = text
                    gradeSel = text
                    SuwikiApplication.prefs.setInt("gradeSel", gradeList.indexOf(text))
                    filterData()
                }
            })
            dialog.show()
        }

        CoroutineScope(IO).launch {
            timetableData = db!!.timetableDao().getAll().toMutableList()
            searchAdapter.filteredData = timetableData
            searchAdapter.unfilteredData = timetableData

            val tempSpinnerData = mutableSetOf<String>()
            for (data in timetableData)
                tempSpinnerData.add(data.major)
            spinnerData = tempSpinnerData.sorted().toMutableList()
            spinnerData.add(0, "전체")

            withContext(Main) {
                binding.recyclerClass.adapter = searchAdapter
                binding.recyclerClass.layoutManager = LinearLayoutManager(requireActivity())
                binding.searchClass.visibility = View.VISIBLE
                majorSel = SuwikiApplication.prefs.getString("openMajorSel", "전체")
                majorSel = majorSel.replace("-", "·")
                gradeSel = gradeList[SuwikiApplication.prefs.getInt("gradeSel", 0)]
                binding.tvSortSelected.text = gradeSel
                binding.tvSelectedOpenMajor.text = majorSel
                filterData()
            }
        }

        binding.searchClass.onTextChanged { searchAdapter.filter.filter(it) }

        searchAdapter.setItemClickListener(object : ClassSearchAdapter.ItemClickListener {
            override fun onClick(view: View, data: TimeTableData) {
                goToClassInfoActivity(data.className, data.professor, data.time)
            }
        })

        binding.btnDirectlyAddClass.setOnClickListener {
            goToClassInfoActivity("", "", "미정(월,1,2)")
        }

        return binding.root
    }

    private fun goToClassInfoActivity(
        className: String,
        professor: String,
        time: String
    ) {
        val intent = Intent(requireActivity(), ClassInfoActivity::class.java)
        intent.putExtra("className", className)
        intent.putExtra("professor", professor)
        intent.putExtra("time", time)
        startActivity(intent)
    }

    private fun filterData() {
        val changeData = mutableListOf<TimeTableData>()
        when {
            majorSel == "전체" && gradeSel == "전체" -> changeFilterData(timetableData)
            majorSel != "전체" && gradeSel == "전체" -> setFilterDataByMajor(changeData)
            majorSel == "전체" && gradeSel != "전체" -> setFilterDataByGrade(changeData)
            else -> setFilterDataByAll(changeData)
        }
        searchAdapter.notifyDataSetChanged()
        binding.searchClass.setText("")
    }

    private fun setFilterDataByAll(changeData: MutableList<TimeTableData>) {
        addSameGradeMajor(changeData)
        changeFilterData(changeData)
    }

    private fun setFilterDataByGrade(changeData: MutableList<TimeTableData>) {
        addSameGrade(changeData)
        changeFilterData(changeData)
    }

    private fun setFilterDataByMajor(changeData: MutableList<TimeTableData>) {
        addSameMajor(changeData)
        changeFilterData(changeData)
    }

    private fun addSameGradeMajor(changeData: MutableList<TimeTableData>) {
        for (data in timetableData) {
            if (majorSel == data.major && gradeSel == data.grade)
                changeData.add(data)
        }
    }

    private fun addSameGrade(changeData: MutableList<TimeTableData>) {
        for (data in timetableData) {
            if (gradeSel == data.grade)
                changeData.add(data)
        }
    }

    private fun addSameMajor(changeData: MutableList<TimeTableData>) {
        for (data in timetableData) {
            if (majorSel == data.major)
                changeData.add(data)
        }
    }

    private fun changeFilterData(changeData: MutableList<TimeTableData>) {
        searchAdapter.filteredData = changeData
        searchAdapter.unfilteredData = changeData
    }
}
