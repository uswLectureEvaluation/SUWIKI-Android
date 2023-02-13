package com.kunize.uswtimetable.ui.lecture_info

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.LectureProfessorSemester
import com.kunize.uswtimetable.databinding.FragmentLectureInfoBinding
import com.kunize.uswtimetable.ui.common.EvaluationListAdapter
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.extensions.infiniteScrolls
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LectureInfoFragment : Fragment() {

    lateinit var binding: FragmentLectureInfoBinding
    private lateinit var adapter: EvaluationListAdapter
    private val lectureInfoViewModel: LectureInfoViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lecture_info, container, false)

        binding.lectureInfoViewModel = lectureInfoViewModel
        binding.lifecycleOwner = this

        adapter = EvaluationListAdapter { id ->
            showAlertDialog(
                "신고하기",
                "신고하시겠어요? \n" +
                    "\n" +
                    "*허위 신고 시 제재가 가해질 수 있습니다!"
            ) {
                if (binding.examInfoRadioBtn.isChecked) lectureInfoViewModel.reportExam(id)
                else lectureInfoViewModel.reportLecture(id)
            }
        }
        binding.infoRecyclerView.adapter = adapter

        val args: LectureInfoFragmentArgs by navArgs()
        lectureInfoViewModel.pageViewModel.lectureId = args.lectureId

        binding.ivPrev.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnClose.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_evaluation, false)
        }

        lifecycleScope.launch {
            loadInitData()
            with(binding) {
                infoRecyclerView.infiniteScrolls {
                    lectureInfoViewModel?.scrollBottomEvent()
                }
                lectureEvaluationRadioBtn.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) lectureInfoViewModel?.lectureInfoRadioBtnClicked()
                }
                examInfoRadioBtn.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) lectureInfoViewModel?.examInfoRadioBtnClicked()
                }
            }
        }

        lectureInfoViewModel.toastViewModel.toastLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                Toast.makeText(
                    requireContext(),
                    lectureInfoViewModel.toastViewModel.toastMessage,
                    Toast.LENGTH_LONG
                ).show()
                if (lectureInfoViewModel.toastViewModel.toastMessage != "포인트가 부족해요!")
                    findNavController().popBackStack()
            }
        )

        with(binding) {
            hideExamDataLayout.usePointBtn.setOnClickListener {
                lectureInfoViewModel?.usePointBtnClicked()
            }

            writeBtn.setOnClickListener {
                goToWriteFragment()
            }
        }
        return binding.root
    }

    private suspend fun loadInitData() {
        lectureInfoViewModel.fetchLectureIntegratedInfo {
            lectureInfoViewModel.fetchLectureList()
        }
    }

    private fun goToWriteFragment() {
        val action =
            LectureInfoFragmentDirections.actionGlobalWriteFragment(
                lectureProfessorName = LectureProfessorSemester(
                    binding.tvLectureName.text.toString(),
                    binding.tvProfessorName.text.toString(),
                    lectureInfoViewModel.lectureDetailInfoData.value?.semester ?: ""
                ),
                isEvaluation = !binding.examInfoRadioBtn.isChecked,
                lectureId = lectureInfoViewModel.pageViewModel.lectureId
            )
        findNavController().navigate(action)
    }

    private fun showAlertDialog(title: String, message: String, positiveClick: () -> Unit) {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인") { _, _ ->
                positiveClick()
            }
            .setNegativeButton("취소") { _, _ ->
            }
        builder.show()
    }
}
