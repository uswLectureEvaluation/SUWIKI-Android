package com.kunize.uswtimetable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.kunize.uswtimetable.databinding.ActivityClassInfoBinding

class ClassInfoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityClassInfoBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.time2.visibility = View.GONE
        binding.editTime2.visibility = View.GONE
        binding.deleteTime2.visibility = View.GONE
        binding.time3.visibility = View.GONE
        binding.editTime3.visibility = View.GONE
        binding.deleteTime3.visibility = View.GONE

        binding.addTime.setOnClickListener {
            if(binding.time2.isVisible) {
                binding.time3.visibility = View.VISIBLE
                binding.editTime3.visibility = View.VISIBLE
                binding.deleteTime3.visibility = View.VISIBLE
            }

            binding.time2.visibility = View.VISIBLE
            binding.editTime2.visibility = View.VISIBLE
            binding.deleteTime2.visibility = View.VISIBLE
        }

        binding.deleteTime2.setOnClickListener {
            binding.time2.visibility = View.GONE
            binding.editTime2.visibility = View.GONE
            binding.deleteTime2.visibility = View.GONE
        }
    }
}