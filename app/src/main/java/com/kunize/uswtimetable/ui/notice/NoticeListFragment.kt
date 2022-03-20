package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.NoticeAdapter
import com.kunize.uswtimetable.databinding.FragmentNoticeListBinding
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.ConnectionManager

class NoticeListFragment : Fragment() {

    private var _binding: FragmentNoticeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoticeViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var adapter: NoticeAdapter
    private var notices = listOf<NoticeDto>()
    /*private val retrofit: RetrofitManager by lazy {
        RetrofitManager.instance
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoticeListBinding.inflate(inflater, container, false)

        setRecyclerView()

        val isConnected = ConnectionManager.isConnected(requireContext())
        if (isConnected) {
            viewModel.noticeList.observe(viewLifecycleOwner) {
                val data = it?:return@observe
                notices = data
                adapter.submitList(notices)
                binding.loading.isGone = true
                binding.noticeRecyclerView.scrollToPosition(notices.size - 1)
            }
        } else {
            binding.loading.isGone = true
            Toast.makeText(requireContext(), "인터넷이 연결되지 않았습니다", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun setRecyclerView() {
        adapter = NoticeAdapter { notice ->
            val bundle = bundleOf("noticeId" to notice.id.toLong())
            findNavController(this).navigate(
                R.id.action_noticeListFragment_to_noticeDetailFragment,
                bundle
            )
        }
        binding.noticeRecyclerView.adapter = adapter
        binding.noticeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}