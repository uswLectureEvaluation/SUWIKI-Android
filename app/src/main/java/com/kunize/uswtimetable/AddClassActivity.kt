package com.kunize.uswtimetable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kunize.uswtimetable.databinding.ActivityAddClassBinding

class AddClassActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddClassBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}