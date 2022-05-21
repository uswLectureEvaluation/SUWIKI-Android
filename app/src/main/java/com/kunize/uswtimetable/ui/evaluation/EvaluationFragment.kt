package com.kunize.uswtimetable.ui.evaluation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kunize.uswtimetable.NavGraphDirections
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentEvaluationBinding
import com.kunize.uswtimetable.ui.common.EvaluationListAdapter
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.LectureApiOption.BEST
import com.kunize.uswtimetable.util.LectureApiOption.HONEY
import com.kunize.uswtimetable.util.LectureApiOption.LEARNING
import com.kunize.uswtimetable.util.LectureApiOption.MODIFIED
import com.kunize.uswtimetable.util.LectureApiOption.SATISFACTION
import com.kunize.uswtimetable.util.TextLength.MIN_SEARCH_TEXT_LENGTH
import com.kunize.uswtimetable.util.onItemSelected

class EvaluationFragment : Fragment() {
    lateinit var binding: FragmentEvaluationBinding
    private lateinit var adapter: EvaluationAdapter
    private val evaluationViewModel: EvaluationViewModel by viewModels {ViewModelFactory(requireContext())}
    private var spinnerSel: Int = 0
    private var spinnerTypeList = listOf(MODIFIED, HONEY, SATISFACTION, LEARNING, BEST)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EvaluationAdapter { id ->
            val action = NavGraphDirections.actionGlobalLectureInfoFragment(lectureId = id)
            findNavController().navigate(action)
        }

        binding.recyclerEvaluation.adapter = adapter

        binding.viewModel = evaluationViewModel
        binding.lifecycleOwner = this

        val spinnerTextList = listOf("최근 올라온 강의", "꿀 강의", "만족도가 높은 강의", "배울게 많은 강의", "Best 강의")
        val spinnerImageList = listOf(
            R.drawable.ic_fire_24, R.drawable.ic_thumb_up_24, R.drawable.ic_star_24,
            R.drawable.ic_book_24, R.drawable.ic_best_24
        )

        binding.moreBtn.setOnClickListener {
            goToSearchResult("", spinnerSel)
        }

        binding.btnSearch.setOnClickListener {
            if (isSearchTextLengthNotEnough()) return@setOnClickListener
            goToSearchResult()
        }


        //키보드 검색 클릭 시 프래그먼트 이동 이벤트 구현
        binding.etSearch.setOnEditorActionListener { _, it, _ ->
            var handled = false
            if (it == EditorInfo.IME_ACTION_SEARCH && !isSearchTextLengthNotEnough()) {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                goToSearchResult()
                handled = true
            }
            handled
        }

        //spinner 설정
        val customSpinnerAdapter =
            CustomSpinnerAdapter(requireContext(), spinnerTextList, spinnerImageList)
        binding.spinnerSort.apply {
            adapter = customSpinnerAdapter
            setSelection(0, false)
            onItemSelected { position ->
                spinnerSel = position
                evaluationViewModel.changeType(spinnerTypeList[spinnerSel])
            }
        }

        evaluationViewModel.toastViewModel.toastLiveData.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), evaluationViewModel.toastViewModel.toastMessage, Toast.LENGTH_LONG).show()
        })

    }

    private fun isSearchTextLengthNotEnough(): Boolean {
        if (binding.etSearch.text.toString().length < MIN_SEARCH_TEXT_LENGTH) {
            Toast.makeText(requireContext(), "2글자 이상 입력해주세요!", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun goToSearchResult(
        msg: String = binding.etSearch.text.toString(),
        now: Int = 0
    ) {
        val action =
            EvaluationFragmentDirections.actionNavigationEvaluationToSearchResultFragment(msg, now)
        findNavController().navigate(action)
    }
}