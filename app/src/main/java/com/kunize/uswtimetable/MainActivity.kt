package com.kunize.uswtimetable

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.getDisplay
import com.kunize.uswtimetable.databinding.ActivityMainBinding
import com.kunize.uswtimetable.dataclass.TimeTableList
import com.kunize.uswtimetable.dao_database.TimeTableListDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main


class MainActivity : AppCompatActivity() {

    lateinit var db: TimeTableListDatabase
    lateinit var timeTableList: List<TimeTableList>
    var timeTableSel: TimeTableList? = null

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = TimeTableListDatabase.getInstance(applicationContext)!!

        binding.createTimeTable.setOnClickListener {
            val intent = Intent(this, CreateTimeTableActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.addClass.setOnClickListener{
            if(timeTableSel == null) {
                Toast.makeText(this,"시간표를 생성해주세요",Toast.LENGTH_SHORT).show()
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
            if(timeTableList.isEmpty()) {
                withContext(Main) {
                    binding.timeTableEmpty.visibility = View.VISIBLE
                    binding.timeTableExist.visibility = View.GONE
                }
            }
            else {
                val createTime = TimeTableSelPref.prefs.getLong("timetableSel",0)
                timeTableSel = timeTableList[0]
                for (empty in timeTableList) {
                    if (empty.createTime == createTime)
                        timeTableSel = empty
                }
                withContext(Main) {
                    binding.timeTableExist.visibility = View.VISIBLE
                    binding.timeTableEmpty.visibility = View.GONE
                    binding.textTitle.text = timeTableSel?.timeTableName
                }
            }
        }
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
        val display = windowManager.defaultDisplay // in case of Activity
        val size = Point()
        display.getRealSize(size) // or getSize(size)
        val width = size.x

        val widthOne = (width - 70.dp)/5
        val heightOne = 45.dp

        var iv = TextView(this)
        var params = RelativeLayout.LayoutParams(widthOne, 90.dp)
        Log.d("Test","너비 = ${binding.timeTable.width.dp} 너비 나누기 5 = ${(binding.timeTable.width / 5).dp}")

        params.leftMargin = widthOne * 3
        params.topMargin = heightOne * 5
        iv.text = "${params.leftMargin}\n${params.topMargin}"
        iv.setTextColor(Color.WHITE)
        iv.setBackgroundColor(Color.BLACK)
        iv.setOnClickListener {
            Log.d("Test", "클릭")
        }
        binding.timeTable.addView(iv, params)
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}