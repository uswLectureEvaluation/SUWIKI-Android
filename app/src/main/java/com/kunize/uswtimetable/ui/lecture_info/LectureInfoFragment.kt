package com.kunize.uswtimetable.ui.lecture_info

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentLectureInfoBinding
import com.kunize.uswtimetable.databinding.FragmentLectureInfoBindingImpl
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.ExamInfoType
import com.kunize.uswtimetable.dataclass.LectureInfoData
import com.kunize.uswtimetable.dataclass.LectureItemViewType
import com.kunize.uswtimetable.ui.evaluation.EvaluationFragment.Companion.dummyData
import com.kunize.uswtimetable.util.infiniteScrolls
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LectureInfoFragment : Fragment() {

    lateinit var binding: FragmentLectureInfoBinding
    private lateinit var lectureInfoViewModel: LectureInfoViewModel
    private var isEvaluation = true

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
        lectureInfoViewModel.changeData(ArrayList(dummyData.subList(0, 12)))

        binding.infoRecyclerView.infiniteScrolls {
            //로딩 바 제거, 서버 연동 시 새로운 데이터를 받아 온 후에 제거
            lectureInfoViewModel.deleteLoading()
            //스크롤 끝에 도달한 경우 새로운 데이터를 받아옴
            val newData = dummyData.subList(0, 12)
            lectureInfoViewModel.addData(ArrayList(newData))
        }

        //넘어온 데이터 (강의명, 교수명)
        val args: LectureInfoFragmentArgs by navArgs()

        with(binding) {
            infoLectureName.text = args.lectureName
            infoProfessorName.text = args.professor


            lectureEvaluationRadioBtn.setOnClickListener {
                isEvaluation = true
                //TODO 서버로 부터 강의평가 데이터 받아오기
                val tmp = arrayListOf<EvaluationData?>()
                tmp.add(null)
                lectureInfoViewModel?.changeData(tmp)
                lectureInfoViewModel?.setViewType(LectureItemViewType.LECTURE)
                lectureInfoContainer.removeViews(2, lectureInfoContainer.size - 2)
                lectureInfoViewModel?.changeWriteBtnText(R.string.write_evaluation)
            }


            examInfoRadioBtn.setOnClickListener {
                isEvaluation = false
                lectureInfoViewModel?.setViewType(LectureItemViewType.HIDE_EXAM)
                lectureInfoViewModel?.changeData(arrayListOf())
                //TODO 서버로 부터 시험 정보 데이터 받아오기
                val type = ExamInfoType.NEED_USE
                val examInflater = LayoutInflater.from(context)
                when (type) {
                    ExamInfoType.NOT_INFLATE -> {
                        lectureInfoContainer.removeViewAt(lectureInfoContainer.size - 1)
                        val newData = dummyData.subList(0, 12)
                        lectureInfoViewModel?.changeData(arrayListOf())
                        lectureInfoViewModel?.setViewType(LectureItemViewType.EXAM)
                    }
                    ExamInfoType.NEED_USE -> {
                        val v = examInflater.inflate(R.layout.hide_exam_info, lectureInfoContainer, true)
                        val usePointBtn = v.findViewById<AppCompatButton>(R.id.usePointBtn)
                        usePointBtn.setOnClickListener {
                            lectureInfoContainer.removeViewAt(lectureInfoContainer.size - 1)
                            lectureInfoViewModel?.setViewType(LectureItemViewType.EXAM)
                            val tmp = arrayListOf<EvaluationData?>()
                            tmp.add(null)
                            lectureInfoViewModel?.changeData(tmp)
                            Toast.makeText(activity, "포인트 사용!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> {
                        val v =
                            examInflater.inflate(R.layout.no_exam_info, lectureInfoContainer, true)
                        val writeExamBtn: AppCompatButton = v.findViewById(R.id.writeExamBtn)
                        writeExamBtn.setOnClickListener {
                            //TODO 시험 정보 쓰기 화면으로 이동
                            Toast.makeText(activity, "준비중", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                lectureInfoViewModel?.changeWriteBtnText(R.string.write_exam)
            }

            writeBtn.setOnClickListener {
                goToWriteFragment()
            }
        }
        return binding.root
    }

    private fun goToWriteFragment() {
        val action =
            LectureInfoFragmentDirections.actionGlobalWriteFragment(lectureName = binding.infoLectureName.text.toString(),professorName =  binding.infoProfessorName.text.toString(), isEvaluation = isEvaluation)
        findNavController().navigate(action)
    }
}