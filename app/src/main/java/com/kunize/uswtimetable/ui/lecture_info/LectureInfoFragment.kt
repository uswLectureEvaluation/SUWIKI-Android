package com.kunize.uswtimetable.ui.lecture_info

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentLectureInfoBinding
import com.kunize.uswtimetable.databinding.FragmentLectureInfoBindingImpl
import com.kunize.uswtimetable.dataclass.LectureInfoData
import com.kunize.uswtimetable.dataclass.LectureItemViewType
import com.kunize.uswtimetable.ui.evaluation.EvaluationFragment.Companion.dummyData

class LectureInfoFragment : Fragment() {

    lateinit var binding: FragmentLectureInfoBinding
    private lateinit var lectureInfoViewModel : LectureInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lecture_info, container, false)

        lectureInfoViewModel = ViewModelProvider(this)[LectureInfoViewModel::class.java]
        binding.lectureInfoViewModel = lectureInfoViewModel
        binding.lifecycleOwner = this

        lectureInfoViewModel.setInfoValue(LectureInfoData("전핵", arrayListOf("2021-2","2021-1","2020-2","2020-1","2018-1","2018-2"), 4.2f,2.4f, 3.5f, "없음","보통","까다로움"))

        //recyclerview 설정
        lectureInfoViewModel.setViewType(LectureItemViewType.LECTURE)
        lectureInfoViewModel.changeData(dummyData)

        //넘어온 데이터 (강의명, 교수명)
        val args: LectureInfoFragmentArgs by navArgs()

        with(binding) {
            infoLectureName.text = args.lectureName
            infoProfessorName.text = args.professor
        }

        return binding.root
    }


}