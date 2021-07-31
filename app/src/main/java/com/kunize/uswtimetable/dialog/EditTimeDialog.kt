package com.kunize.uswtimetable.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.autofit.et.lib.AutoFitEditText
import com.kunize.uswtimetable.R

class EditTimeDialog(context: Context) {
    private val localContext = context
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK: TextView
    private lateinit var btnCancel: TextView
    private lateinit var dialogLocation: AutoFitEditText
    private lateinit var Mon: TextView
    private lateinit var Tue: TextView
    private lateinit var Wed: TextView
    private lateinit var Thu: TextView
    private lateinit var Fri: TextView
    private lateinit var dialogStartTime: AutoFitEditText
    private lateinit var dialogEndTime: AutoFitEditText
    private lateinit var listener: OKClickedListener
    var day = ""
    companion object {
        val customRed = Color.rgb(234, 81, 95)
        val customGray = Color.rgb(207, 207, 207)
    }

    fun start(inputLocation: String = "", inputDay: String = "", inputStartTime: String = "", inputEndTime: String = "") {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.edit_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogLocation = dlg.findViewById(R.id.dialogLocation)
        Mon = dlg.findViewById(R.id.Monday)
        Tue = dlg.findViewById(R.id.Tuesday)
        Wed = dlg.findViewById(R.id.Wednesday)
        Thu = dlg.findViewById(R.id.Thursday)
        Fri = dlg.findViewById(R.id.Friday)
        dialogStartTime = dlg.findViewById(R.id.dialogTime1)
        dialogEndTime = dlg.findViewById(R.id.dialogTime2)

        when(inputDay) {
            "월" -> Mon.setTextColor(customRed)
            "화" -> Tue.setTextColor(customRed)
            "수" -> Wed.setTextColor(customRed)
            "목" -> Thu.setTextColor(customRed)
            "금" -> Fri.setTextColor(customRed)
        }

        day = inputDay

        dialogLocation.setText(inputLocation)
        dialogStartTime.setText(inputStartTime)
        dialogEndTime.setText(inputEndTime)

        Mon.setOnClickListener {
            nomal()
            Mon.setTextColor(customRed)
            day = Mon.text.toString()
        }
        Tue.setOnClickListener {
            nomal()
            Tue.setTextColor(customRed)
            day = Tue.text.toString()
        }
        Wed.setOnClickListener {
            nomal()
            Wed.setTextColor(customRed)
            day = Wed.text.toString()
        }
        Thu.setOnClickListener {
            nomal()
            Thu.setTextColor(customRed)
            day = Thu.text.toString()
        }
        Fri.setOnClickListener {
            nomal()
            Fri.setTextColor(customRed)
            day = Fri.text.toString()
        }

        btnOK = dlg.findViewById(R.id.ok)
        btnOK.setOnClickListener {
            if(dialogLocation.text.toString() == "") {
                Toast.makeText(localContext,"장소를 입력해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(day == "") {
                Toast.makeText(localContext,"요일을 선택해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(dialogStartTime.text.toString() == "" || dialogEndTime.text.toString() == "") {
                Toast.makeText(localContext,"교시를 입력해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(dialogStartTime.text.toString().toInt() > dialogEndTime.text.toString().toInt()) {
                Toast.makeText(localContext,"시작 교시를 확인해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(dialogStartTime.text.toString().toInt() < 1 || dialogEndTime.text.toString().toInt() > 15) {
                Toast.makeText(localContext,"1~15사이의 숫자만 들어갈 수 있습니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var time = ""
            for(i in dialogStartTime.text.toString().toInt() .. dialogEndTime.text.toString().toInt() - 1) {
                time = time + "$i" + ","
            }
            time += "${dialogEndTime.text.toString().toInt()}"
            listener.onOKClicked(
                dialogLocation.text.toString(),
                day,
                time
            )
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.cancel)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnOKClickedListener(listener: (String, String, String) -> Unit) {
        this.listener = object : OKClickedListener {
            override fun onOKClicked(className: String, day: String, time: String) {
                listener(className, day, time)
            }
        }
    }


    interface OKClickedListener {
        fun onOKClicked(className: String, day: String, time: String)
    }

    private fun nomal() {
        Mon.setTextColor(customGray)
        Tue.setTextColor(customGray)
        Wed.setTextColor(customGray)
        Thu.setTextColor(customGray)
        Fri.setTextColor(customGray)
    }


}