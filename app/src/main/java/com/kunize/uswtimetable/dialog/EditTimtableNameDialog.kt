package com.kunize.uswtimetable.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.autofit.et.lib.AutoFitEditText
import com.kunize.uswtimetable.R

class EditTimetableDialog(context: Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK: TextView
    private lateinit var btnCancel: TextView
    private lateinit var dialogEditName: AutoFitEditText
    private lateinit var listener: OKClickedListener

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.edit_timetable_name_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogEditName = dlg.findViewById(R.id.editTimeTableName)

        btnOK = dlg.findViewById(R.id.timetableOk)
        btnOK.setOnClickListener {

            listener.onOKClicked(
                dialogEditName.text.toString()
            )
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.timetableCancel)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object : OKClickedListener {
            override fun onOKClicked(name: String) {
                listener(name)
            }
        }
    }

    interface OKClickedListener {
        fun onOKClicked(name: String)
    }

}