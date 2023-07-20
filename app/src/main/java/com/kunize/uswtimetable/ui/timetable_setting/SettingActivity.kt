package com.kunize.uswtimetable.ui.timetable_setting

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.ui.home.timetable.BitmapConverter.stringToBitmap
import com.kunize.uswtimetable.ui.home.timetable.UswTimeTable.Companion.CLASSNAME
import com.kunize.uswtimetable.ui.home.timetable.UswTimeTable.Companion.CLASSNAME_LOCATION
import com.kunize.uswtimetable.ui.home.timetable.UswTimeTable.Companion.CLASSNAME_PROFESSOR
import com.kunize.uswtimetable.ui.home.timetable.UswTimeTable.Companion.CLASSNAME_PROFESSOR_LOCATION
import com.kunize.uswtimetable.databinding.ActivitySettingBinding
import com.kunize.uswtimetable.SuwikiApplication
import java.io.*


class SettingActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.shareKakaoBtn.setOnClickListener {
            try {
                val strBit = SuwikiApplication.prefs.getString("image", "")
                val bitmap = stringToBitmap(strBit)

                val dir = getImageUri(this, bitmap!!)

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, dir)
                intent.`package` = "com.kakao.talk"
                startActivity(intent)
            } catch (e: Exception) { Toast.makeText(this, "카카오톡이 설치되어 있지 않습니다!", Toast.LENGTH_LONG).show() }
        }

        binding.classInfoBtn.apply {
            setOnClickListener {
                val list = arrayOf("수업명","수업명, 장소", "수업명, 교수명", "수업명, 교수명, 장소")
                showDialog(it ,list,"infoFormat")
            }
            text = getPrefInfo("infoFormat", 1)
        }
    }

    private fun getPrefInfo(type: String, defValue: Int): String {
        return when (SuwikiApplication.prefs.getInt(type, defValue)) {
            CLASSNAME -> "수업명"
            CLASSNAME_LOCATION -> "수업명, 장소"
            CLASSNAME_PROFESSOR -> "수업명, 교수명"
            CLASSNAME_PROFESSOR_LOCATION -> "수업명, 교수명, 장소"
            else -> ""
        }
    }

    private fun showDialog(v: View, list: Array<String>, setType: String) {
        val dlg = AlertDialog.Builder(this)
        dlg.setItems(list
        ) { _, which ->
            val temp = when (list[which]) {
                "수업명" -> CLASSNAME
                "수업명, 장소" -> CLASSNAME_LOCATION
                "수업명, 교수명" -> CLASSNAME_PROFESSOR
                "수업명, 교수명, 장소" -> CLASSNAME_PROFESSOR_LOCATION
                else -> 0
            }
            (v as TextView).text = list[which]
            SuwikiApplication.prefs.setInt(setType, temp)
        }
        dlg.show()
    }

    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}