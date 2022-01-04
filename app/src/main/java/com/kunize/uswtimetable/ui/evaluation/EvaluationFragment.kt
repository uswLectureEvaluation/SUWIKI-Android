package com.kunize.uswtimetable.ui.evaluation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.adapter.CustomSpinnerAdapter
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.EvaluationListAdapter
import com.kunize.uswtimetable.databinding.FragmentEvaluationBinding
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureItemViewType

class EvaluationFragment : Fragment() {
    lateinit var binding: FragmentEvaluationBinding

    private lateinit var evaluationViewModel : EvaluationViewModel

    companion object {
        val dummyData = arrayListOf<EvaluationData?>(
            EvaluationData("올뎃베이직12345678", "교양대학", 3.2f, 3f, 2.6f, 4.2f, "기교"),
            EvaluationData("디지털 사회의 마케팅(비대면 수업)", "남아영", 3.4f, 4f, 2.3f, 3.9f, "기교"),
            EvaluationData("데이터구조", "이명원", 0.4f, 0.1f, 0.2f, 0.5f, "전핵"),
            EvaluationData("컴퓨터구조", "장성태", 5f, 0f, 0f, 0f, "전핵"),
            EvaluationData("더미데이터", "교양대학", 4.9f, 0f, 0f, 0f, "기교"),
            EvaluationData("더미데이터", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData("더미데이터", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData("올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData("올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData("올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData("올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교"),
            EvaluationData("올뎃베이직", "교양대학", 3.2f, 0f, 0f, 0f, "기교")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEvaluationBinding.inflate(inflater, container, false)

        evaluationViewModel = ViewModelProvider(this)[EvaluationViewModel::class.java]
        binding.viewModel = evaluationViewModel
        binding.lifecycleOwner = this

        evaluationViewModel.setViewType(LectureItemViewType.SHORT)

        val spinnerTextList = listOf("최근 올라온 강의","꿀 강의","만족도가 높은 강의","배울게 많은 강의","Best 강의")
        val spinnerImageList = listOf(R.drawable.ic_fire_24, R.drawable.ic_thumb_up_24, R.drawable.ic_star_24,
        R.drawable.ic_book_24, R.drawable.ic_best_24)

        //TODO 더보기 버튼 클릭 시 "정렬할 기준", "tags:ALL" 데이터를 가지고 프래그먼트 이동

        binding.searchBtn.setOnClickListener {
            goToSearchResult()
        }

        //키보드 검색 클릭 시 프래그먼트 이동 이벤트 구현
        binding.searchLecture.setOnEditorActionListener { _, it, _ ->
            var handled = false
            if(it == EditorInfo.IME_ACTION_SEARCH) {
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.searchLecture.windowToken, 0)
                goToSearchResult()
                handled = true
            }
            handled
        }

        //spinner 설정
        val customSpinnerAdapter = CustomSpinnerAdapter(requireContext(), spinnerTextList, spinnerImageList)
        binding.spinner.apply {
            adapter = customSpinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when(position) {
                        0 -> evaluationViewModel.changeData(dummyData)
                        1 -> evaluationViewModel.changeData(ArrayList(dummyData.subList(1, 4)))
                        2 -> evaluationViewModel.changeData(ArrayList(dummyData.subList(3, 4)))
                        3 -> evaluationViewModel.changeData(ArrayList(dummyData.subList(5, 8)))
                        4 -> evaluationViewModel.changeData(ArrayList(dummyData.subList(7, 9)))
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
        return binding.root
    }

    private fun goToSearchResult() {
        val action =
            EvaluationFragmentDirections.actionNavigationEvaluationToSearchResultFragment(binding.searchLecture.text.toString())
        findNavController().navigate(action)
    }
}