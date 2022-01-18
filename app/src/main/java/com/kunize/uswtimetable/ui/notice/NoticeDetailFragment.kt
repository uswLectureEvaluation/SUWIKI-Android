package com.kunize.uswtimetable.ui.notice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.NoticeDetailFragmentBinding
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.util.Constants.TAG

class NoticeDetailFragment : Fragment() {
    private var _binding: NoticeDetailFragmentBinding? = null
    private val binding: NoticeDetailFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoticeDetailFragmentBinding.inflate(inflater, container, false)

        val noticeData = arguments?.getSerializable("noticeData") as NoticeDetailDto
        with(binding) {
            noticeTitle.text = noticeData.title
            noticeDate.text = noticeData.date
            noticeDetail.text = noticeData.contents
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