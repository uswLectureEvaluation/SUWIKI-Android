package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.base.BaseFragment
import com.kunize.uswtimetable.databinding.FragmentNoticeDetailBinding
import com.kunize.uswtimetable.ui.common.UiEvent
import com.kunize.uswtimetable.ui.common.applyDate
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeDetailFragment : BaseFragment<NoticeDetailState, UiEvent>() {
    private var _binding: FragmentNoticeDetailBinding? = null
    private val binding: FragmentNoticeDetailBinding get() = _binding ?: error("binding is null")
    private val viewModel: NoticeDetailViewModel by viewModels()

    private val navArgs: NoticeDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoticeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getNotice(navArgs.noticeId)
        binding.tvListButton.setOnClickListener {
            findNavController().navigateUp()
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.stateFlow.collect(::render)
        }
    }

    override fun handleEvent(event: UiEvent) {
    }

    override fun render(state: NoticeDetailState) = with(binding) {
        tvTitle.text = state.notice?.title
        applyDate(tvDate, state.notice?.date)
        tvContent.text = state.notice?.content
        pbLoading.isVisible = state.loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.tvListButton.setOnClickListener(null)
        _binding = null
    }
}
