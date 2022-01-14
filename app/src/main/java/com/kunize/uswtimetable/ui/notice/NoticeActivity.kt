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
    private lateinit var adapter: NoticeAdapter
    private lateinit var viewModel: NoticeViewModel
    private var noticeList = listOf<NoticeDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice)

        binding.toolBar.setNavigationOnClickListener {
            finish()
        }

        viewModel = ViewModelProvider(this)[NoticeViewModel::class.java]
        // TODO 실제에는 dumpData가 아닌 noticeList로 대체

        initRecyclerView()
        viewModel.noticeList.observe(this) {
            noticeList = it
            adapter.submitList(noticeList)
            binding.loading.isGone = true
        }
    }

    private fun initRecyclerView() {
        adapter = NoticeAdapter(onItemClicked = { notice ->
            Log.d(TAG, "NoticeActivity - ${notice.title} clicked")
//            viewModel.getNotice(notice.id)
        })
        //adapter.submitList(noticeList) // 리사이클러뷰에 공지 목록 추가
        Log.d(TAG, "NoticeActivity - submitList(noticeList) -> $noticeList")

        val decorator = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.noticeRecyclerView.adapter = adapter
        binding.noticeRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        binding.noticeRecyclerView.addItemDecoration(decorator)
    }
}