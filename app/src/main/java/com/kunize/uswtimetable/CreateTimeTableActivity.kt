package com.kunize.uswtimetable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kunize.uswtimetable.databinding.ActivityCreateTimeTableBinding
import com.kunize.uswtimetable.model.TimeTableList
import com.kunize.uswtimetable.model.TimeTableListDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CreateTimeTableActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreateTimeTableBinding.inflate(layoutInflater)}
    private val yearList = listOf("2021","2022","2023")
    private val semesterList = listOf("1","2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val yearAdapter = ArrayAdapter<String>(this,R.layout.item_spinner,yearList)
        val semesterAdapter = ArrayAdapter<String>(this,R.layout.item_spinner,semesterList)

        binding.yearSpinner.adapter = yearAdapter
        binding.semesterSpinner.adapter = semesterAdapter

        val db = TimeTableListDatabase.getInstance(applicationContext)!!
        binding.finishButton.setOnClickListener {
            if(binding.editName.text.toString() == "") {
                Toast.makeText(this,"시간표 이름을 입력해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CoroutineScope(IO).launch {
                val currentTime = System.currentTimeMillis()
                val tempData = TimeTableList(currentTime,yearList[binding.yearSpinner.selectedItemPosition],
                    semesterList[binding.semesterSpinner.selectedItemPosition],binding.editName.text.toString())
                db.timetableListDao().insert(tempData)
                val returnIntent = Intent()
                returnIntent.putExtra("createTime",tempData.createTime)
                setResult(5,returnIntent)
                finish()
            }
        }

    }
}