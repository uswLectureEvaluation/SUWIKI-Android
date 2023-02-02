package com.kunize.uswtimetable.ui.timetable_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.databinding.ActivityTimeTableListBinding
import com.kunize.uswtimetable.data.local.TimeTableList
import com.kunize.uswtimetable.ui.create_timetable.CreateTimeTableActivity
import com.kunize.uswtimetable.util.TimeTableListClickType
import com.kunize.uswtimetable.util.SuwikiApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimeTableListActivity : AppCompatActivity() {
    private val binding by lazy {ActivityTimeTableListBinding.inflate(layoutInflater)}
    lateinit var tableList: MutableList<TimeTableList>
    lateinit var db: TimeTableListDatabase
    var adapter = TimeTableListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        db = TimeTableListDatabase.getInstance(applicationContext)!!


        binding.ivAdd.setOnClickListener {
            val intent = Intent(this, CreateTimeTableActivity::class.java)
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        CoroutineScope(IO).launch {
            tableList = db.timetableListDao().getAll() as MutableList<TimeTableList>
            adapter.timeTableListData = tableList
            withContext(Main) {
                binding.timetableList.adapter = adapter
                binding.timetableList.layoutManager = LinearLayoutManager(this@TimeTableListActivity)
            }
        }

        adapter.setItemClickListener(object : TimeTableListAdapter.ItemClickListener {
            override fun onClick(view: View, data: TimeTableList, type: TimeTableListClickType) {
                when(type) {
                    TimeTableListClickType.ITEM_CLICK -> {
                        SuwikiApplication.prefs.setLong("timetableSel",data.createTime)
                        finish()
                    }
                    TimeTableListClickType.EDIT_CLICK -> {
                        val intent = Intent(this@TimeTableListActivity, CreateTimeTableActivity::class.java)
                        intent.putExtra("edit_data",data)
                        startActivity(intent)
                    }
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(IO).launch {
            tableList = db.timetableListDao().getAll() as MutableList<TimeTableList>
            adapter.timeTableListData = tableList
            withContext(Main) {
                binding.timetableList.adapter = adapter
                binding.timetableList.layoutManager = LinearLayoutManager(this@TimeTableListActivity)
            }
        }
    }
}