package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.NoticeAdapter
import com.kunize.uswtimetable.databinding.FragmentNoticeListBinding
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.ResponseState

class NoticeListFragment : Fragment() {

    private var _binding: FragmentNoticeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoticeListViewModel
    private lateinit var adapter: NoticeAdapter
    private var notices = listOf<NoticeDto>()
    private val retrofit: RetrofitManager by lazy {
        RetrofitManager.instance
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoticeListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoticeAdapter { notice ->
            Log.d(TAG, "NoticeListFragment - ${notice.title} clicked")

            retrofit.getNotice(notice.id.toInt(), completion = { state, result ->
                when(state) {
                    ResponseState.OK -> {
                        Log.d(TAG, "NoticeListFragment - 공지 수신 성공: $result")
                    }
                    ResponseState.FAIL -> {
                        Log.d(TAG, "NoticeListFragment - 공지 수신 실패: $result")
                    }
                }
            })

            // TODO 통신 후 실제 데이터로 교체
            val fakeNotice = NoticeDetailDto(
                notice.id, "임시 제목 데이터", "2022-01-18", "임시 공지 내용입니다. 어쩌고 저쩌고"
            )
            val bundle = bundleOf("noticeData" to fakeNotice)
            view.findNavController()
                .navigate(R.id.action_noticeListFragment_to_noticeDetailFragment, bundle)
        }
        binding.noticeRecyclerView.adapter = adapter
        binding.noticeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[NoticeListViewModel::class.java]

        viewModel.selectedNotice.observe(viewLifecycleOwner, {
        })

        viewModel.noticeList.observe(viewLifecycleOwner, {
            notices = it
            adapter.submitList(notices)
            binding.loading.isGone = true
            binding.noticeRecyclerView.scrollToPosition(notices.size - 1)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = NoticeListFragment()
    }

}