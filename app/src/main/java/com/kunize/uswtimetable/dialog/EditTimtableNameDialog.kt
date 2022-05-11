package com.kunize.uswtimetable.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.autofit.et.lib.AutoFitEditText
import com.kunize.uswtimetable.ui.create_timetable.CreateTimeTableActivity.Companion.semesterList
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.util.DateUtil.Companion.getYearList
import com.kunize.uswtimetable.data.local.TimeTableList

class EditTimetableDialog(context: Context) {
    private val localContext = context
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK: TextView
    private lateinit var btnCancel: TextView
    private lateinit var dialogEditName: AutoFitEditText
    private lateinit var listener: OKClickedListener
    private lateinit var semester: Spinner
    private lateinit var year: Spinner

    private val yearList = getYearList()
    private val yearAdapter = ArrayAdapter(context,R.layout.item_spinner, yearList)
    private val semesterAdapter = ArrayAdapter(context,R.layout.item_spinner,semesterList)

    fun start(data: TimeTableList) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.edit_timetable_name_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        year = dlg.findViewById(R.id.editYearSpinner)
        semester =  dlg.findViewById(R.id.editSemesterSpinner)

        val yearList = getYearList()
        val yearIdx = yearList.indexOf(data.year)
        val yearSel = if (yearIdx == -1) 0 else yearIdx

//        val yearSel = when(data.year) {
//            "2021" -> 0
//            "2022" -> 1
//            "2023" -> 2
//            else -> 0
//        }


        val semesterSel = when(data.semester) {
            "1" -> 0
            else -> 1
        }

        year.apply {
            adapter = yearAdapter
            setSelection(yearSel)
        }

        semester.apply {
            adapter = semesterAdapter
            setSelection(semesterSel)
        }


        dialogEditName = dlg.findViewById(R.id.editTimeTableName)
        dialogEditName.setText(data.timeTableName)

        btnOK = dlg.findViewById(R.id.timetableOk)
        btnOK.setOnClickListener {

            if(dialogEditName.text.toString().isBlank()) {
                Toast.makeText(localContext,"시간표 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            listener.onOKClicked(
                dialogEditName.text.toString(),
                yearList[year.selectedItemPosition],
                semesterList[semester.selectedItemPosition]
            )
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.timetableCancel)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnOKClickedListener(listener: (String, String, String) -> Unit) {
        this.listener = object : OKClickedListener {
            override fun onOKClicked(name: String, year: String, semester: String) {
                listener(name, year, semester)
            }
        }
    }

    interface OKClickedListener {
        fun onOKClicked(name: String, year: String, semester: String)
    }

}