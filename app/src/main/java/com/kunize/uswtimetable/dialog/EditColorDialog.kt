package com.kunize.uswtimetable

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.autofit.et.lib.AutoFitEditText
import com.kunize.uswtimetable.ClassInfoActivity.Companion.colorMap

class EditColorDialog(context: Context) : View.OnClickListener {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var Pink: ImageView
    private lateinit var Orange: ImageView
    private lateinit var Purple: ImageView
    private lateinit var Sky: ImageView
    private lateinit var Green: ImageView
    private lateinit var Brown: ImageView
    private lateinit var Gray: ImageView
    private lateinit var Navy: ImageView

    private lateinit var okClickedListener: OKClickedListener

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.color_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        Navy = dlg.findViewById(R.id.imgNavy)
        Pink = dlg.findViewById(R.id.imgPink)
        Orange = dlg.findViewById(R.id.imgOrange)
        Purple = dlg.findViewById(R.id.imgPurple)
        Sky = dlg.findViewById(R.id.imgSky)
        Green = dlg.findViewById(R.id.imgGreen)
        Brown = dlg.findViewById(R.id.imgBrown)
        Gray = dlg.findViewById(R.id.imgGray)

        Navy.imageTintList = ColorStateList.valueOf(colorMap["Navy"]!!)
        Pink.imageTintList = ColorStateList.valueOf(colorMap["Pink"]!!)
        Orange.imageTintList = ColorStateList.valueOf(colorMap["Orange"]!!)
        Purple.imageTintList = ColorStateList.valueOf(colorMap["Purple"]!!)
        Sky.imageTintList = ColorStateList.valueOf(colorMap["Sky"]!!)
        Green.imageTintList = ColorStateList.valueOf(colorMap["Green"]!!)
        Brown.imageTintList = ColorStateList.valueOf(colorMap["Brown"]!!)
        Gray.imageTintList = ColorStateList.valueOf(colorMap["Gray"]!!)


        Navy.setOnClickListener(this)
        Pink.setOnClickListener(this)
        Orange.setOnClickListener(this)
        Purple.setOnClickListener(this)
        Sky.setOnClickListener(this)
        Green.setOnClickListener(this)
        Brown.setOnClickListener(this)
        Gray.setOnClickListener(this)

        dlg.show()
    }

    fun setOnOKClickedListener(okClickedListener: OKClickedListener) {
        this.okClickedListener = okClickedListener
    }

    interface OKClickedListener {
        fun onOKClicked(color: ColorStateList?)
    }

    override fun onClick(v: View?) {
        val img = v as ImageView
        val color = img.imageTintList
        okClickedListener.onOKClicked(color)
        dlg.dismiss()
    }
}