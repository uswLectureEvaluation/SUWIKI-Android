package com.kunize.uswtimetable.ui.evaluation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kunize.uswtimetable.adapter.CustomSpinnerAdapter
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentEvaluationBinding
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.LectureApiOption.HONEY
import com.kunize.uswtimetable.util.LectureApiOption.LEARNING
import com.kunize.uswtimetable.util.LectureApiOption.MODIFIED
import com.kunize.uswtimetable.util.LectureApiOption.SATISFACTION
import com.kunize.uswtimetable.util.LectureItemViewType
import com.kunize.uswtimetable.util.TextLength.MIN_SEARCH_TEXT_LENGTH

class EvaluationFragment : Fragment() {
    lateinit var binding: FragmentEvaluationBinding

    private val evaluationViewModel: EvaluationViewModel by viewModels {ViewModelFactory(requireContext())}
    private var spinnerSel: Int = 0

    companion object {
        val dummyShortData = arrayListOf<EvaluationData?>(
            EvaluationData(
                LectureItemViewType.SHORT, "올뎃베이직12345678", "교양대학", 3.2f, 3f, 2.6f, 4.2f, "기교",
                "2020-1", "없음", "많음", "까다로움", "내용이 없습니다.", "매우 쉬움", "족보, PPT"
            ),
            EvaluationData(
                LectureItemViewType.SHORT,
                "디지털 사회의 마케팅(비대면 수업)",
                "남아영",
                3.4f,
                4f,
                2.3f,
                3.9f,
                "기교"
            ),
            EvaluationData(LectureItemViewType.SHORT,"데이터구조", "이명원", 0.4f, 0.1f, 0.2f, 0.5f, "전핵"),
            EvaluationData(LectureItemViewType.SHORT,"컴퓨터구조", "장성태", 5f, 0f, 0f, 0f, "전핵"),
            EvaluationData(LectureItemViewType.SHORT,"더미데이터", "교양대학", 4.9f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.SHORT,"더미데이터", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.SHORT,"더미데이터", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.SHORT,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.SHORT,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.SHORT,"TEN", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.SHORT,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.SHORT,"LAST", "교양대학", 3.2f, 0f, 0f, 0f, "기교")
        )

        val dummyLectureData = arrayListOf<EvaluationData?>(
            EvaluationData(
                LectureItemViewType.LECTURE, "올뎃베이직12345678", "교양대학", 3.2f, 3f, 2.6f, 4.2f, "기교",
                "2020-1", "없음", "많음", "까다로움", "내용이 없습니다.", "매우 쉬움", "족보, PPT"
            ),
            EvaluationData(
                LectureItemViewType.LECTURE,
                "디지털 사회의 마케팅(비대면 수업)",
                "남아영",
                3.4f,
                4f,
                2.3f,
                3.9f,
                "기교"
            ),
            EvaluationData(LectureItemViewType.LECTURE,"데이터구조", "이명원", 0.4f, 0.1f, 0.2f, 0.5f, "전핵"),
            EvaluationData(LectureItemViewType.LECTURE,"컴퓨터구조", "장성태", 5f, 0f, 0f, 0f, "전핵"),
            EvaluationData(LectureItemViewType.LECTURE,"더미데이터", "교양대학", 4.9f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.LECTURE,"더미데이터", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.LECTURE,"더미데이터", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.LECTURE,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.LECTURE,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.LECTURE,"TEN", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.LECTURE,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.LECTURE,"LAST", "교양대학", 3.2f, 0f, 0f, 0f, "기교")
        )

        val dummyExamData = arrayListOf<EvaluationData?>(
            EvaluationData(
                LectureItemViewType.EXAM, "올뎃베이직12345678", "교양대학", 3.2f, 3f, 2.6f, 4.2f, "기교",
                "2020-1", "없음", "많음", "까다로움", "내용이 없습니다.", "매우 쉬움", "족보, PPT"
            ),
            EvaluationData(
                LectureItemViewType.EXAM,
                "디지털 사회의 마케팅(비대면 수업)",
                "남아영",
                3.4f,
                4f,
                2.3f,
                3.9f,
                "기교"
            ),
            EvaluationData(LectureItemViewType.EXAM,"데이터구조", "이명원", 0.4f, 0.1f, 0.2f, 0.5f, "전핵"),
            EvaluationData(LectureItemViewType.EXAM,"컴퓨터구조", "장성태", 5f, 0f, 0f, 0f, "전핵"),
            EvaluationData(LectureItemViewType.EXAM,"더미데이터", "교양대학", 4.9f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.EXAM,"더미데이터", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.EXAM,"더미데이터", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.EXAM,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.EXAM,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.EXAM,"TEN", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.EXAM,"올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData(LectureItemViewType.EXAM,"LAST", "교양대학", 3.2f, 0f, 0f, 0f, "기교")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation, container, false)

        //evaluationViewModel = ViewModelProvider(this)[TempEvaluationViewModel::class.java]
        binding.viewModel = evaluationViewModel
        binding.lifecycleOwner = this

        val spinnerTextList = listOf("최근 올라온 강의", "꿀 강의", "만족도가 높은 강의", "배울게 많은 강의", "Best 강의")
        val spinnerImageList = listOf(
            R.drawable.ic_fire_24, R.drawable.ic_thumb_up_24, R.drawable.ic_star_24,
            R.drawable.ic_book_24, R.drawable.ic_best_24
        )

        binding.moreBtn.setOnClickListener {
            goToSearchResult("tags:ALL", spinnerSel)
        }

        binding.searchBtn.setOnClickListener {
            if (isSearchTextLengthNotEnough()) return@setOnClickListener
            goToSearchResult()
        }


        //키보드 검색 클릭 시 프래그먼트 이동 이벤트 구현
        binding.searchLecture.setOnEditorActionListener { _, it, _ ->
            var handled = false
            if (it == EditorInfo.IME_ACTION_SEARCH && !isSearchTextLengthNotEnough()) {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.searchLecture.windowToken, 0)
                goToSearchResult()
                handled = true
            }
            handled
        }

        //spinner 설정
        val customSpinnerAdapter =
            CustomSpinnerAdapter(requireContext(), spinnerTextList, spinnerImageList)
        binding.spinner.apply {
            adapter = customSpinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinnerSel = position
                    when (position) {
                        0 -> evaluationViewModel.changeType(MODIFIED)
                        1 -> evaluationViewModel.changeType(HONEY)
                        2 -> evaluationViewModel.changeType(SATISFACTION)
                        3 -> evaluationViewModel.changeType(LEARNING)
                        4 -> evaluationViewModel.changeType(HONEY)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
        return binding.root
    }

    private fun isSearchTextLengthNotEnough(): Boolean {
        if (binding.searchLecture.text.toString().length < MIN_SEARCH_TEXT_LENGTH) {
            Toast.makeText(requireContext(), "2글자 이상 입력해주세요!", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun goToSearchResult(
        msg: String = binding.searchLecture.text.toString(),
        now: Int = 0
    ) {
        val action =
            EvaluationFragmentDirections.actionNavigationEvaluationToSearchResultFragment(msg, now)
        findNavController().navigate(action)
    }
}