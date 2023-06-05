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
import androidx.navigation.fragment.findNavController
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentNoticeListBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.Constants.KEY_NOTICE_ID
import com.kunize.uswtimetable.util.extensions.infiniteScrolls
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import com.kunize.uswtimetable.util.extensions.toast

class NoticeListFragment : Fragment() {

    private var _binding: FragmentNoticeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoticeViewModel by viewModels { ViewModelFactory() }
    private val adapter: NoticeAdapter by lazy { NoticeAdapter(viewModel) }
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoticeListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initRecyclerView()

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Log.d(Constants.TAG, "NoticeListFragment - onCreateView() called / $message")
            toast(message)
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.uiEvent.collect { event ->
                if (event is NoticeViewModel.Event.NoticeClickEvent) {
                    val action = NoticeListFragmentDirections.actionNoticeListFragmentToNoticeDetailFragment(event.notice.id)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.rvNotice
        recyclerView.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) { notices ->
            adapter.submitList(notices)
        }
        recyclerView.infiniteScrolls {
            viewModel.scrollBottomEvent()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toast?.cancel()
        _binding = null
    }
}
