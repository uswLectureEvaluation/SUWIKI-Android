package com.kunize.uswtimetable

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.adfit.ads.AdListener
import com.kunize.uswtimetable.dao_database.TimeTableListDatabase
import com.kunize.uswtimetable.databinding.ActivityMainBinding
import com.kunize.uswtimetable.dataclass.TimeData
import com.kunize.uswtimetable.dataclass.TimeTableList
import com.kunize.uswtimetable.dialog.BottomSheet
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONArray
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var db: TimeTableListDatabase
    lateinit var timeTableList: List<TimeTableList>
    private var timeTableSel: TimeTableList? = null
    private val timeWidthMap = mapOf("월" to 0, "화" to 1, "수" to 2, "목" to 3, "금" to 4)
    private var tempTimeData = mutableListOf<TimeData>()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var edgeList: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bannerAdView.setClientId(getString(R.string.kakaoAdfitID))  // 할당 받은 광고단위 ID 설정
        binding.bannerAdView.setAdListener(object : AdListener {  // optional :: 광고 수신 리스너 설정

            override fun onAdLoaded() {
                // 배너 광고 노출 완료 시 호출
            }

            override fun onAdFailed(errorCode: Int) {
                // 배너 광고 노출 실패 시 호출
            }

            override fun onAdClicked() {
                // 배너 광고 클릭 시 호출
            }

        })

        binding.bannerAdView.loadAd()

        db = TimeTableListDatabase.getInstance(applicationContext)!!

        binding.addClass.setOnClickListener {
            if (timeTableSel == null) {
                Toast.makeText(this, "시간표를 생성해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, AddClassActivity::class.java)
            startActivity(intent)

        }

        binding.showTimeTableList.setOnClickListener {
            val intent = Intent(this, TimeTableListActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onPause() {
        super.onPause()
        Log.d("AddView", "멈춤")
        binding.bannerAdView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.bannerAdView.destroy()
    }


    override fun onResume() {
        super.onResume()
        Log.d("AddView", "재개")
        binding.bannerAdView.resume()
        CoroutineScope(IO).launch {
            timeTableList = db.timetableListDao().getAll()
            tempTimeData.clear()
            if (timeTableList.isEmpty()) { //시간표가 없을 경우
                timeTableSel = null
                binding.uswTimeTable.isEmpty = true
                withContext(Main) {
                    binding.textTitle.text = ""
                    binding.uswTimeTable.drawTable()
                }
            } else { //시간표가 있을 경우
                val createTime = TimeTableSelPref.prefs.getLong("timetableSel", 0)
                timeTableSel = timeTableList[0]
                for (empty in timeTableList) {
                    if (empty.createTime == createTime)
                        timeTableSel = empty
                }
                Log.d("change", "바뀐 데이터 : $timeTableSel")
                binding.uswTimeTable.isEmpty = false
                withContext(Main) {
                    binding.textTitle.text = timeTableSel?.timeTableName
                }

                val jsonStr = timeTableSel?.timeTableJsonData
                //3. Json을 Array로 변환
                tempTimeData = jsonToArray(jsonStr)
                withContext(Main) {
                    binding.uswTimeTable.timeTableData = tempTimeData
                    binding.uswTimeTable.drawTable()
                }
            }
            withContext(Main) {
                delay(200L)
                widgetUpdate()
            }
        }
    }

    private fun widgetUpdate() {
        val timetableBitmap = viewToBitmap(binding.uswTimeTable)
        val strBit = bitmapToString(timetableBitmap)
        TimeTableSelPref.prefs.setString("image", strBit)
        val intentAction = Intent(this@MainActivity, TimeTableWidget::class.java)
        intentAction.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(this@MainActivity)
            .getAppWidgetIds(ComponentName(this@MainActivity, TimeTableWidget::class.java))
        intentAction.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        this@MainActivity.sendBroadcast(intentAction)
    }

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    companion object {
        val Int.dp: Int
            get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

        val Float.dp: Int
            get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

        fun jsonToArray(jsonStr: String?): MutableList<TimeData> {
            val returnTimeData = mutableListOf<TimeData>()
            if (jsonStr != "") {
                val jsonArray = JSONArray(jsonStr)
                for (idx in 0 until jsonArray.length()) {
                    val jsonObj = jsonArray.getJSONObject(idx)
                    val className = jsonObj.getString("name")
                    val professor = jsonObj.getString("professor")
                    val location = jsonObj.getString("location")
                    val day = jsonObj.getString("day")
                    val startTime = jsonObj.getString("startTime")
                    val endTime = jsonObj.getString("endTime")
                    val color = jsonObj.getInt("color")

                    returnTimeData.add(
                        TimeData(
                            className,
                            professor,
                            location,
                            day,
                            startTime,
                            endTime,
                            color
                        )
                    )
                }
            }
            return returnTimeData
        }

        fun bitmapToString(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val byte = baos.toByteArray()
            return Base64.encodeToString(byte, Base64.DEFAULT)
        }

        fun stringToBitmap(encodedString: String): Bitmap? {
            return try {
                val byte = Base64.decode(encodedString, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(byte, 0, byte.size)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}