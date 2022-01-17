package com.kunize.uswtimetable.ui.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.NoticeAdapter
import com.kunize.uswtimetable.databinding.ActivityNoticeBinding
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolBar.setNavigationOnClickListener {
            finish()
        }
    }

}