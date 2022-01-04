package com.kunize.uswtimetable.ui.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.NoticeAdapter
import com.kunize.uswtimetable.databinding.ActivityNoticeBinding
import com.kunize.uswtimetable.dataclass.NoticeData
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.ResponseState

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding
    private lateinit var adapter: NoticeAdapter
    private lateinit var viewModel: NoticeViewModel
    private lateinit var noticeList: List<NoticeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice)

        viewModel = ViewModelProvider(this)[NoticeViewModel::class.java]
        noticeList = viewModel.noticeList

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = NoticeAdapter(onItemClicked = { notice ->
            Log.d(TAG, "NoticeActivity - ${notice.title} clicked")
            viewModel.getNotice(notice.id)
        })
        adapter.submitList(viewModel.noticeList) // 리사이클러뷰에 공지 목록 추가

        val decorator = DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL)
        binding.noticeRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@NoticeActivity, LinearLayoutManager.VERTICAL, true)
            this.adapter = adapter
            this.addItemDecoration(decorator) // 리사이클러뷰 구분선 추가
        }
    }
}