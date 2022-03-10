package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.kunize.uswtimetable.databinding.FragmentNoticeDetailBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory

class NoticeDetailFragment : Fragment() {
    private var _binding: FragmentNoticeDetailBinding? = null
    private val binding: FragmentNoticeDetailBinding get() = _binding!!
    private val viewModel: NoticeDetailViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoticeDetailBinding.inflate(inflater, container, false)

        arguments?.getLong("noticeId")?.let { viewModel.getNotice(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}