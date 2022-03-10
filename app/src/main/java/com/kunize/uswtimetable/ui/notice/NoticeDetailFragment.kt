package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.kunize.uswtimetable.databinding.FragmentNoticeDetailBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeDetailFragment : Fragment() {
    private var _binding: FragmentNoticeDetailBinding? = null
    private val binding: FragmentNoticeDetailBinding get() = _binding!!
    private val viewModel: NoticeDetailViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoticeDetailBinding.inflate(inflater, container, false)

        val noticeId = arguments?.getLong("noticeId")
        Log.d(TAG, "NoticeDetailFragment - noticeId: $noticeId")

        noticeId?.let { viewModel.getNotice(it) }
        viewModel.notice.observe(viewLifecycleOwner) { noticeData ->
            Log.d(TAG, "NoticeDetailFragment - notice: $noticeData")
            binding.noticeTitle.text = noticeData.title
            binding.noticeDate.text = noticeData.date
            binding.noticeDetail.text = noticeData.content
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            Log.d(TAG, "NoticeDetailFragment - Back button Clicked!")
            view.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}