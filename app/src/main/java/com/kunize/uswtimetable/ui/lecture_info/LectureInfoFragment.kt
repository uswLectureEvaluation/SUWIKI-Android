package com.kunize.uswtimetable.ui.lecture_info

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentLectureInfoBinding
import com.kunize.uswtimetable.dataclass.*
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.infiniteScrolls

class LectureInfoFragment : Fragment() {

    lateinit var binding: FragmentLectureInfoBinding
    private val lectureInfoViewModel: LectureInfoViewModel by viewModels {ViewModelFactory(requireContext())}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lecture_info, container, false)

        binding.lectureInfoViewModel = lectureInfoViewModel
        binding.lifecycleOwner = this

        val args: LectureInfoFragmentArgs by navArgs()

        lectureInfoViewModel.setInfoValue(args.lectureId)

        binding.infoRecyclerView.infiniteScrolls {
            lectureInfoViewModel.scrollBottom(args.lectureId)
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
                lectureInfoViewModel?.examInfoRadioBtnClicked()
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
                lectureProfessorName = LectureProfessorName(
                    binding.infoLectureName.text.toString(),
                    binding.infoProfessorName.text.toString()
                ), isEvaluation = !binding.examInfoRadioBtn.isChecked
            )
        findNavController().navigate(action)
    }
}