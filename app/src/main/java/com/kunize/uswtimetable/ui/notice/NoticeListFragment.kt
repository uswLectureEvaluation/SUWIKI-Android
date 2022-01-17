package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.adapter.NoticeAdapter
import com.kunize.uswtimetable.databinding.NoticeListFragmentBinding
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeListFragment : Fragment() {

    private var _binding: NoticeListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoticeListViewModel
    private lateinit var adapter: NoticeAdapter
    private var notices = listOf<NoticeDto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoticeListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoticeAdapter { notice ->
            Log.d(TAG, "NoticeListFragment - ${notice.title} clicked")

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
            binding.noticeRecyclerView.scrollToPosition(notices.size-1)
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