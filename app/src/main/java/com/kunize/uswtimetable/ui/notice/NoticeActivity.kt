package com.kunize.uswtimetable.ui.notice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.NoticeAdapter
import com.kunize.uswtimetable.databinding.ActivityNoticeBinding
import com.kunize.uswtimetable.dataclass.NoticeData
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding
    private lateinit var adapter: NoticeAdapter
    private lateinit var viewModel: NoticeViewModel
    private lateinit var noticeList: List<NoticeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice)

        binding.toolBar.setNavigationOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this)[NoticeViewModel::class.java]
        // TODO 실제에는 dumpData가 아닌 noticeList로 대체
//        noticeList = viewModel.noticeList
        noticeList = viewModel.dumpData

        initRecyclerView()
        Log.d(TAG, "NoticeActivity - noticeList: $noticeList")
    }

    private fun initRecyclerView() {
        adapter = NoticeAdapter(onItemClicked = { notice ->
            Log.d(TAG, "NoticeActivity - ${notice.title} clicked")
            viewModel.getNotice(notice.id)
        })
        adapter.submitList(noticeList) // 리사이클러뷰에 공지 목록 추가

        val decorator = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.noticeRecyclerView.adapter = adapter
        binding.noticeRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        binding.noticeRecyclerView.addItemDecoration(decorator)
    }
}