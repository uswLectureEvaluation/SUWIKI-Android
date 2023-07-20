package com.kunize.uswtimetable.ui.lecture_info

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.base.ErrorHandler
import com.kunize.uswtimetable.data.local.LectureProfessorSemester
import com.kunize.uswtimetable.databinding.FragmentLectureInfoBinding
import com.kunize.uswtimetable.ui.common.BindingAdapter.setList
import com.kunize.uswtimetable.ui.common.EvaluationListAdapter
import com.kunize.uswtimetable.ui.lecture_info.LectureInfoBindingAdapter.setList
import com.kunize.uswtimetable.util.extensions.infiniteScrolls
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import com.mangbaam.presentation.extension.toast
import com.suwiki.domain.model.SuwikiError
import kotlin.math.roundToInt

class LectureInfoFragment : Fragment(), ErrorHandler {

    private var _binding: FragmentLectureInfoBinding? = null
    private val binding get() = _binding ?: error("binding is null")

    private val adapter: EvaluationListAdapter = EvaluationListAdapter(::showAlertDialog)
    private val viewModel: LectureInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lecture_info, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lectureInfoViewModel = viewModel
        binding.lifecycleOwner = this

        initViews()
        observe()
    }

    private fun initViews() {
        val args: LectureInfoFragmentArgs by navArgs()
        viewModel.pageViewModel.lectureId = args.lectureId

        with(binding) {
            infoRecyclerView.adapter = adapter

            ivPrev.setOnClickListener {
                findNavController().popBackStack()
            }

            btnClose.setOnClickListener {
                findNavController().popBackStack(R.id.navigation_evaluation, false)
            }

            infoRecyclerView.infiniteScrolls {
                viewModel.scrollBottomEvent()
            }

            lectureEvaluationRadioBtn.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) viewModel.lectureInfoRadioBtnClicked()
            }

            examInfoRadioBtn.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) viewModel.examInfoRadioBtnClicked()
            }

            hideExamDataLayout.usePointBtn.setOnClickListener {
                viewModel.usePointBtnClicked()
            }

            writeBtn.setOnClickListener {
                goToWriteFragment()
            }
        }
    }

    private fun observe() {
        repeatOnStarted {
            viewModel.lectureInfoState.collect(::render)
        }
    }

    private fun goToWriteFragment() {
        val action =
            LectureInfoFragmentDirections.actionGlobalWriteFragment(
                lectureProfessorName = LectureProfessorSemester(
                    binding.tvLectureName.text.toString(),
                    binding.tvProfessorName.text.toString(),
                    viewModel.state.lectureInfo.semester,
                ),
                isEvaluation = !binding.examInfoRadioBtn.isChecked,
                lectureId = viewModel.pageViewModel.lectureId,
            )
        findNavController().navigate(action)
    }

    private fun showAlertDialog(id: Long) {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("신고하기")
            .setMessage(
                "신고하시겠어요? \n" +
                    "\n" +
                    "*허위 신고 시 제재가 가해질 수 있습니다!",
            )
            .setPositiveButton("확인") { _, _ ->
                if (binding.examInfoRadioBtn.isChecked) {
                    viewModel.reportExam(id)
                } else {
                    viewModel.reportLecture(id)
                }
            }
            .setNegativeButton("취소") { _, _ ->
            }
        builder.show()
    }

    private fun render(state: LectureInfoState) = with(binding) {
        tvLectureName.text = state.lectureInfo.lectureName
        tvProfessorName.text = state.lectureInfo.professor
        tvMajorName.text = state.lectureInfo.majorType
        yearSemesterLayout.setList(state.lectureInfo.semester)
        infoEvaluationType.text = state.lectureInfo.lectureType

        val totalAvg = state.lectureInfo.lectureTotalAvg
        infoHoneyScore.text =
            getStringIfPositiveOrDash(
                R.string.floatToStr,
                totalAvg,
                state.lectureInfo.lectureHoneyAvg,
            )
        infoLearningScore.text =
            getStringIfPositiveOrDash(
                R.string.floatToStr,
                totalAvg,
                state.lectureInfo.lectureLearningAvg,
            )
        infoSatisfactionScore.text =
            getStringIfPositiveOrDash(
                R.string.floatToStr,
                totalAvg,
                state.lectureInfo.lectureTotalAvg,
            )

        noExamDataLayout.isVisible = state.evaluationsState.items.isEmpty() && !state.showNoExamInfo
        noExamDataLayout.isVisible = state.showNoExamInfo
        hideExamDataLayout.root.isVisible = state.showHideExamInfo
        writeBtn.setText(state.writeButtonText)
        writeBtn.isEnabled = !state.written

        infoMeeting.text = getStringIfPositiveOrDash(
            if (state.lectureInfo.lectureTeamAvg.roundToInt() == 0) R.string.not_exist else R.string.exist,
            totalAvg,
        )
        infoMeeting.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (state.lectureInfo.lectureTeamAvg.roundToInt() == 0) R.color.suwiki_blue_900 else R.color.suwiki_purple,
            ),
        )

        infoTask.text = getStringIfPositiveOrDash(
            when (state.lectureInfo.lectureHomeworkAvg.roundToInt()) {
                0 -> R.string.not_exist
                1 -> R.string.normal
                else -> R.string.many
            },
            totalAvg,
        )
        infoTask.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                when (state.lectureInfo.lectureHomeworkAvg.roundToInt()) {
                    0 -> R.color.suwiki_blue_900
                    1 -> R.color.suwiki_black_900
                    else -> R.color.suwiki_purple
                },
            ),
        )

        infoGrade.text = getStringIfPositiveOrDash(
            when (state.lectureInfo.lectureDifficultyAvg.roundToInt()) {
                0 -> R.string.good
                1 -> R.string.normal
                else -> R.string.picky
            },
            totalAvg,
        )
        infoGrade.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                when (state.lectureInfo.lectureDifficultyAvg.roundToInt()) {
                    0 -> R.color.suwiki_blue_900
                    1 -> R.color.suwiki_black_900
                    else -> R.color.suwiki_purple
                },
            ),
        )

        infoRecyclerView.setList(state.evaluationsState.items)

        state.error?.let { handleError(requireContext(), it) }
    }

    override fun handleError(context: Context, error: SuwikiError) {
        when (error) {
            SuwikiError.RequestFailure -> toast("포인트가 부족해요!")
            SuwikiError.RestrictApproach -> toast("권한이 없어요! 이용 제한 내역을 확인하거나 문의해주세요!")
            SuwikiError.NetworkError -> toast("인터넷 연결을 확인해주세요!")
            is SuwikiError.HttpError -> toast(error.message)
            is SuwikiError.CustomError -> toast(error.message)
            else -> {}
        }
    }

    private fun getStringIfPositiveOrDash(
        @StringRes resId: Int,
        totalAvg: Float,
        value: Float? = null,
    ): String {
        return if (totalAvg == 0f) {
            getString(R.string.hyphen)
        } else {
            value?.let { getString(resId, it) } ?: getString(resId)
        }
    }

    override fun onDestroyView() {
        binding.infoRecyclerView.adapter = null
        binding.unbind()
        super.onDestroyView()
    }
}
