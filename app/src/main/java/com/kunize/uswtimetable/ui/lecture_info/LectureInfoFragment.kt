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

        //넘어온 데이터 (강의명, 교수명)
        val args: LectureInfoFragmentArgs by navArgs()

        lectureInfoViewModel.setInfoValue(args.lectureId)

        val tmp = arrayListOf<EvaluationData?>()
        tmp.add(null)
        //lectureInfoViewModel.changeData(tmp)

//        binding.infoRecyclerView.infiniteScrolls {
//            //TODO 추상화 필요
//            //로딩 바 제거, 서버 연동 시 새로운 데이터를 받아 온 후에 제거
//            lectureInfoViewModel.deleteLoading()
//            //스크롤 끝에 도달한 경우 새로운 데이터를 받아옴
//            val newData = if(binding.lectureEvaluationRadioBtn.isChecked)
//                dummyLectureData.subList(0, 12)
//            else
//                dummyExamData.subList(0, 12)
//            lectureInfoViewModel.addData(ArrayList(newData))
//        }


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