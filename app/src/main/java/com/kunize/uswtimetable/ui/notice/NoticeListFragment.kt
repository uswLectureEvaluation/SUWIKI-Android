package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.paging.LoadState
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentNoticeListBinding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.Constants.KEY_NOTICE_ID
import com.kunize.uswtimetable.util.repeatOnStarted
import kotlinx.coroutines.flow.collectLatest

class NoticeListFragment : Fragment() {

    private var _binding: FragmentNoticeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoticeViewModel by viewModels { ViewModelFactory() }
    private val adapter: NoticeAdapter by lazy { NoticeAdapter() }
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoticeListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.rvNotice.adapter = adapter

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Log.d(Constants.TAG, "NoticeListFragment - onCreateView() called / $message")
            makeToast(message)
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.items.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            adapter.loadStateFlow.collect {
                binding.progressLoading.isVisible = it.source.refresh is LoadState.Loading
            }
        }

        viewModel.eventNotice.observe(viewLifecycleOwner, EventObserver { noticeId ->
            findNavController(this).navigate(
                R.id.action_noticeListFragment_to_noticeDetailFragment,
                bundleOf(KEY_NOTICE_ID to noticeId)
            )
        })
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