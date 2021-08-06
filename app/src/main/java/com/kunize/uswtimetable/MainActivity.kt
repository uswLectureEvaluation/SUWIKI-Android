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
    private val tempTimeData = mutableListOf<TimeData>()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var edgeList: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        edgeList = listOf(
            binding.edge9,
            binding.edge10,
            binding.edge11,
            binding.edge12,
            binding.edge13,
            binding.edge14,
            binding.edge15
        )

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

        binding.createTimeTable.setOnClickListener {
            val intent = Intent(this, CreateTimeTableActivity::class.java)
            startActivity(intent)
        }

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
            if (timeTableList.isEmpty()) {
                timeTableSel = null
                withContext(Main) {
                    binding.timeTableEmpty.visibility = View.VISIBLE
                    binding.timeTableExist.visibility = View.GONE
                    binding.textTitle.text = ""
                    binding.eLearning.text = ""
                }
            } else {
                val createTime = TimeTableSelPref.prefs.getLong("timetableSel", 0)
                timeTableSel = timeTableList[0]
                for (empty in timeTableList) {
                    if (empty.createTime == createTime)
                        timeTableSel = empty
                }
                Log.d("change", "바뀐 데이터 : $timeTableSel")
                withContext(Main) {
                    binding.timeTable.removeAllViews()
                    binding.eLearning.text = ""
                    binding.timeTableExist.visibility = View.VISIBLE
                    binding.timeTableEmpty.visibility = View.GONE
                    binding.textTitle.text = timeTableSel?.timeTableName
                }
                val outMetrics = DisplayMetrics()
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    val display = this@MainActivity.display
                    display?.getRealMetrics(outMetrics)
                } else {
                    @Suppress("DEPRECATION")
                    val display = this@MainActivity.windowManager.defaultDisplay
                    @Suppress("DEPRECATION")
                    display.getMetrics(outMetrics)
                }

                val width = outMetrics.widthPixels
                val widthOne = (width - 70.dp) / 5
                val heightOne = 50.dp

                val jsonStr = timeTableSel?.timeTableJsonData


                val jsonArray: JSONArray

                //TODO 3. Json을 Array로 변환
                if (jsonStr != "") {
                    jsonArray = JSONArray(jsonStr)
                    for (idx in 0 until jsonArray.length()) {
                        val jsonObj = jsonArray.getJSONObject(idx)
                        val className = jsonObj.getString("name")
                        val professor = jsonObj.getString("professor")
                        val location = jsonObj.getString("location")
                        val day = jsonObj.getString("day")
                        val startTime = jsonObj.getString("startTime")
                        val endTime = jsonObj.getString("endTime")
                        val color = jsonObj.getInt("color")

                        tempTimeData.add(
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
                withContext(Main) {
                    reDrawTimeTable()
                }


                for (data in tempTimeData) {
                    if (data.location == "이러닝" || data.day == "토" || data.location == "") {
                        binding.eLearning.text =
                            data.name + " (" + data.day + " " + data.startTime + "~" + data.endTime + ")"
                        withContext(Main) {
                            binding.eLearning.setOnClickListener {
                                if (binding.eLearning.text.toString() != "")
                                    showBottomSheet(data, tempTimeData, null)
                            }
                        }
                        continue
                    }
                    val timeRect = TextView(this@MainActivity)
                    val drawStart = (data.startTime.toInt() - 1) * heightOne
                    val timeHeight = (data.endTime.toInt() - data.startTime.toInt() + 1) * heightOne
                    val params = RelativeLayout.LayoutParams(widthOne, timeHeight)
                    params.leftMargin = widthOne * timeWidthMap[data.day]!!
                    params.topMargin = drawStart

                    timeRect.text = "${data.name}\n${data.location}"
                    timeRect.setTextColor(Color.WHITE)
                    timeRect.setBackgroundColor(data.color)
                    timeRect.setOnClickListener { v ->
                        Log.d("Test", "클릭")
                        showBottomSheet(data, tempTimeData, v)
                    }
                    withContext(Main) {
                        binding.timeTable.addView(timeRect, params)
                    }
                }
            }
            withContext(Main) {
                delay(200L)
                widgetUpdate()
            }
        }


    }

    private fun widgetUpdate() {
        val timetableBitmap = viewToBitmap(binding.timeTableCard)
        val eLearningBitmap = viewToBitmap(binding.eLearningLayout)
        val totalBitmap = combineImage(timetableBitmap, eLearningBitmap, true)
        val strBit = BitmapToString(totalBitmap!!)
        TimeTableSelPref.prefs.setString("image", strBit)
        val intentAction = Intent(this@MainActivity, TimeTableWidget::class.java)
        intentAction.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        val ids = AppWidgetManager.getInstance(this@MainActivity)
            .getAppWidgetIds(ComponentName(this@MainActivity, TimeTableWidget::class.java))
        intentAction.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        this@MainActivity.sendBroadcast(intentAction)
    }

    private fun reDrawTimeTable() {
        var maxTime = try {
            tempTimeData.maxOf { it ->
                if (it.endTime.isEmpty())
                    0
                else if (it.day == "토")
                    0
                else
                    it.endTime.toInt()
            }
        } catch (e: Exception) {
            0
        }
        for (idx in 0..6) {
            edgeList[idx].visibility = View.GONE
        }
        for (idx in 0..(maxTime - 8)) {
            try {
                edgeList[idx].visibility = View.VISIBLE
            } catch (e: Exception) {
                Log.d("Buggg", "$e")
                edgeList[idx - 1].visibility = View.VISIBLE
            }
        }
    }

    private fun showBottomSheet(
        data: TimeData,
        tempTimeData: MutableList<TimeData>,
        v: View?
    ) {
        val bottomSheet: BottomSheet = BottomSheet(data, callback = {
            when (it) {
                1 -> {
                    val deleteIdx = tempTimeData.indexOf(data)
                    val intent = Intent(this@MainActivity, ClassInfoActivity::class.java)
                    intent.putExtra("deleteIdx", deleteIdx)
                    intent.putExtra("className", data.name)
                    intent.putExtra("professor", data.professor)
                    intent.putExtra("color", data.color)
                    var comma = ""
                    try {
                        for (add in data.startTime.toInt() until data.endTime.toInt()) {
                            comma = comma + "$add" + ","
                        }
                        comma += data.endTime
                        intent.putExtra("time", "${data.location}(${data.day}$comma)")
                    } catch (e: Exception) {
                        intent.putExtra(
                            "time",
                            "${data.location}(${data.day}$comma)".replace("이러닝", "")
                        )
                    }
                    startActivity(intent)
                }
                2 -> CoroutineScope(Main).launch {
                    if (v != null) {
                        binding.timeTable.removeView(v)
                        tempTimeData.remove(data)
                        reDrawTimeTable()
                    } else
                        binding.eLearning.text = ""
                    delay(200L)
                    widgetUpdate()
                }
            }
        })
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    private fun combineImage(first: Bitmap, second: Bitmap, isVerticalMode: Boolean): Bitmap? {
        var bitmap: Bitmap? = null
        bitmap = if (isVerticalMode) Bitmap.createBitmap(
            first.width,
            first.height + second.height,
            Bitmap.Config.ARGB_8888
        ) else Bitmap.createScaledBitmap(first, first.width + second.width, first.height, true)
        val p = Paint()
        p.isDither = true
        p.flags = Paint.ANTI_ALIAS_FLAG
        val c = Canvas(bitmap)
        c.drawBitmap(first, 0f, 0f, p)
        if (isVerticalMode) c.drawBitmap(second, 0f, first.height.toFloat(), p) else c.drawBitmap(
            second,
            first.width.toFloat(),
            0f,
            p
        )
        first.recycle()
        second.recycle()
        return bitmap
    }

    companion object {
        fun BitmapToString(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val byte = baos.toByteArray()
            return Base64.encodeToString(byte, Base64.DEFAULT)
        }

        fun StringToBitmap(encodedString: String): Bitmap? {
            try {
                val byte = Base64.decode(encodedString, Base64.DEFAULT)
                return BitmapFactory.decodeByteArray(byte, 0, byte.size)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}