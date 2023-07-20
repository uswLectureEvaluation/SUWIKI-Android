package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kunize.uswtimetable.base.BaseFragment
import com.kunize.uswtimetable.databinding.FragmentNoticeListBinding
import com.kunize.uswtimetable.util.extensions.infiniteScrolls
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeListFragment : BaseFragment<NoticeListState, NoticeViewModel.Event>() {

    private var _binding: FragmentNoticeListBinding? = null
    private val binding get() = _binding ?: error("binding is null")

    private val viewModel: NoticeViewModel by viewModels()
    private val adapter: NoticeAdapter = NoticeAdapter {
        viewModel.noticeClicked(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoticeListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()

        viewLifecycleOwner.repeatOnStarted {
            viewModel.uiEvent.collect(::handleEvent)
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.stateFlow.collect(::render)
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.stateFlow.collect { state ->
                render(state)
                state.error?.let { handleError(requireContext(), it) }
            }
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.rvNotice
        recyclerView.adapter = adapter
        recyclerView.infiniteScrolls {
            viewModel.scrollBottomEvent()
        }
    }

    override fun render(state: NoticeListState) = with(binding) {
        progressLoading.isVisible = state.loading
        tvEmptyLabel.isVisible = state.data.isEmpty()
        adapter.submitList(state.data)
    }

    override fun handleEvent(event: NoticeViewModel.Event) {
        if (event is NoticeViewModel.Event.NoticeClickEvent) {
            val action =
                NoticeListFragmentDirections.actionNoticeListFragmentToNoticeDetailFragment(
                    event.notice.id,
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
