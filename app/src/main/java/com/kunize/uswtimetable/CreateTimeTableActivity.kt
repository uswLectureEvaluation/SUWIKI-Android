package com.kunize.uswtimetable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.kunize.uswtimetable.databinding.ActivityCreateTimeTableBinding

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

    }
}