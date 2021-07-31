package com.kunize.uswtimetable

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.ActivityMainBinding
import com.kunize.uswtimetable.dataclass.TimeTableList
import com.kunize.uswtimetable.dao_database.TimeTableListDatabase
import com.kunize.uswtimetable.dataclass.TimeData
import com.kunize.uswtimetable.dialog.BottomSheet
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONArray
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    lateinit var db: TimeTableListDatabase
    lateinit var timeTableList: List<TimeTableList>
    var timeTableSel: TimeTableList? = null
    val timeWidthMap = mapOf("월" to 0, "화" to 1, "수" to 2, "목" to 3, "금" to 4)

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = TimeTableListDatabase.getInstance(applicationContext)!!

        binding.createTimeTable.setOnClickListener {
            val intent = Intent(this, CreateTimeTableActivity::class.java)
            resultLauncher.launch(intent)
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

    override fun onResume() {
        super.onResume()
        CoroutineScope(IO).launch {
            timeTableList = db.timetableListDao().getAll()
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
                val heightOne = 45.dp

                val jsonStr = timeTableSel?.timeTableJsonData

                val tempTimeData = mutableListOf<TimeData>()
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

                for (data in tempTimeData) {
                    if (data.location == "이러닝" || data.day == "토" ||data.location == "") {
                        binding.eLearning.text =
                            data.name + " (" + data.day + " " + data.startTime + "~" + data.endTime + ")"
                        withContext(Main) {
                            binding.eLearning.setOnClickListener {
                                if(binding.eLearning.text.toString() != "")
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
                        intent.putExtra("time", "${data.location}(${data.day}$comma)".replace("이러닝",""))
                    }
                    startActivity(intent)
                }
                2 -> runOnUiThread {
                    if (v != null) {
                        binding.timeTable.removeView(v)
                    } else
                        binding.eLearning.text = ""
                }
            }
        })
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == 5) {
//                CoroutineScope(IO).launch {
//                    timeTableList = db.timetableListDao().getAll()
//                    val createTime = result.data?.getLongExtra("createTime", 0)
//                    if (createTime == 0L)
//                        return@launch
//                    for (empty in timeTableList) {
//                        if (empty.createTime == createTime)
//                            timeTableSel = empty
//                    }
//                    binding.textTitle.text = timeTableSel?.timeTableName
//                }
//            }
        }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)


    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}