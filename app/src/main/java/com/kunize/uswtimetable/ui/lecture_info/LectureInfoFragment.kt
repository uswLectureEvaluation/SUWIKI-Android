package com.kunize.uswtimetable.ui.lecture_info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentLectureInfoBinding
import com.kunize.uswtimetable.dataclass.LectureProfessorSemester
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.infiniteScrolls
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class LectureInfoFragment : Fragment() {

    lateinit var binding: FragmentLectureInfoBinding
    private val lectureInfoViewModel: LectureInfoViewModel by viewModels {ViewModelFactory(requireContext())}
    var lectureId = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lecture_info, container, false)

        binding.lectureInfoViewModel = lectureInfoViewModel
        binding.lifecycleOwner = this

        val args: LectureInfoFragmentArgs by navArgs()
        lectureId = args.lectureId


        CoroutineScope(IO).launch {
            lectureInfoViewModel.setInfoValue(lectureId)
            lectureInfoViewModel.getEvaluationList(args.lectureId)
            binding.infoRecyclerView.infiniteScrolls {
                lectureInfoViewModel.scrollBottom(args.lectureId)
            }
        }

        with(binding) {
            lectureEvaluationRadioBtn.setOnClickListener {
                lectureInfoViewModel?.lectureInfoRadioBtnClicked()
            }

            hideExamDataLayout.usePointBtn.setOnClickListener {
                lectureInfoViewModel?.usePointBtnClicked()
            }

            noExamDataLayout.writeExamBtn.setOnClickListener {
                goToWriteFragment()
            }

            examInfoRadioBtn.setOnClickListener {
                lectureInfoViewModel?.examInfoRadioBtnClicked(args.lectureId)
            }

            writeBtn.setOnClickListener {
                goToWriteFragment()
            }
        }
        return binding.root
    }

    private fun goToWriteFragment() {
        val action =
            LectureInfoFragmentDirections.actionGlobalWriteFragment(
                lectureProfessorName = LectureProfessorSemester(
                    binding.infoLectureName.text.toString(),
                    binding.infoProfessorName.text.toString(),
                    lectureInfoViewModel.lectureDetailInfoData.value?.data?.semester ?: ""
                ), isEvaluation = !binding.examInfoRadioBtn.isChecked,
                lectureId = lectureId
            )
        findNavController().navigate(action)
    }
}