package com.kunize.uswtimetable

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.kunize.uswtimetable.databinding.ActivityMainBinding
import com.kunize.uswtimetable.model.TimeTableData
import com.kunize.uswtimetable.model.TimeTableDatabase
import com.kunize.uswtimetable.model.TimeTableList
import com.kunize.uswtimetable.model.TimeTableListDatabase
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

        //TODO 선택된 시간표가 무엇인지 sharedRef 만들어야함

        db = TimeTableListDatabase.getInstance(applicationContext)!!

        binding.createTimeTable.setOnClickListener {
            val intent = Intent(this, CreateTimeTableActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.addClass.setOnClickListener{
            val intent = Intent(this, AddClassActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(IO).launch {
            timeTableList = db.timetableListDao().getAll()
            if(timeTableList.isEmpty()) {
                withContext(Main) {
                    binding.timeTableEmpty.visibility = View.VISIBLE
                    binding.timeTableExist.visibility = View.GONE
                }
            }
            else {
                withContext(Main) {
                    binding.timeTableExist.visibility = View.VISIBLE
                    binding.timeTableEmpty.visibility = View.GONE
                }
            }
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 5) {
                CoroutineScope(IO).launch {
                    timeTableList = db.timetableListDao().getAll()
                    val createTime = result.data?.getLongExtra("createTime", 0)
                    if (createTime == 0L)
                        return@launch
                    for (empty in timeTableList) {
                        if (empty.createTime == createTime)
                            timeTableSel = empty
                    }
                    binding.textTitle.text = timeTableSel?.timeTableName
                }
            }
        }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        var location = IntArray(2)
        var iv = TextView(this)
        val r = Rect()
        val bot = binding.button4.bottom
        val x = binding.button4.left
        var params = RelativeLayout.LayoutParams(binding.timeTable.width / 5, 300)
        params.leftMargin = 0
        params.topMargin = 0
        iv.text = "${params.leftMargin}\n${params.topMargin}"
        iv.setTextColor(Color.WHITE)
        iv.setBackgroundColor(Color.BLACK)
        iv.setOnClickListener {
            Log.d("Test", "클릭")
        }
        binding.timeTable.addView(iv, params)
    }
}