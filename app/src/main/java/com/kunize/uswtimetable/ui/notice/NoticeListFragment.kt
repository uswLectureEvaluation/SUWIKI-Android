package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentNoticeListBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeListFragment : Fragment() {

    private var _binding: FragmentNoticeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoticeViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var adapter: NoticeAdapter
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoticeListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setRecyclerView()

        viewModel.notices.observe(viewLifecycleOwner) { notices ->
            adapter.submitList(notices)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Log.d(TAG, "NoticeListFragment - onCreateView() called / $message")
            makeToast(message)
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

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toast?.cancel()
        _binding = null
    }
}