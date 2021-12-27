package com.kunize.uswtimetable.ui.evaluation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.CustomSpinnerAdapter
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.EvaluationListAdapter
import com.kunize.uswtimetable.databinding.FragmentEvaluationBinding
import com.kunize.uswtimetable.dataclass.EvaluationData

class EvaluationFragment : Fragment() {
    lateinit var binding: FragmentEvaluationBinding
    //리사이클러뷰 어댑터
    lateinit var evaluationAdapter: EvaluationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEvaluationBinding.inflate(inflater, container, false)

        val spinnerTextList = listOf("최근 올라온 강의","꿀 강의","만족도가 높은 강의","배울게 많은 강의","Best 강의")
        val spinnerImageList = listOf(R.drawable.ic_fire_24, R.drawable.ic_thumb_up_24, R.drawable.ic_star_24,
        R.drawable.ic_book_24, R.drawable.ic_best_24)

        val customSpinnerAdapter = CustomSpinnerAdapter(requireContext(), spinnerTextList, spinnerImageList)
        binding.spinner.apply {
            adapter = customSpinnerAdapter
        }

        initRecycler()

        return binding.root
    }
    //리아클러뷰 시작 함수
    private fun initRecycler() {
        evaluationAdapter = EvaluationListAdapter()
        binding.recyclerEvaluation.adapter = evaluationAdapter
        binding.recyclerEvaluation.layoutManager = LinearLayoutManager(activity)
        val dummyData = mutableListOf(
            EvaluationData("올뎃베이직12345678","교양대학",3.2f,0f,0f,0f,"기교"),
            EvaluationData("디지털 사회의 마케팅(비대면 수업)","남아영",3.4f,0f,0f,0f,"기교"),
            EvaluationData("데이터구조","이명원",0.4f,0f,0f,0f,"전핵"),
            EvaluationData("컴퓨터구조","장성태",5f,0f,0f,0f,"전핵"),
            EvaluationData("더미데이터","교양대학",4.9f,0f,0f,0f,"기교"),
            EvaluationData("더미데이터","교양대학",3.2f,0f,0f,0f,"기교"),
            EvaluationData("더미데이터","교양대학",3.2f,0f,0f,0f,"기교"),
            EvaluationData("올뎃베이직","교양대학",3.2f,0f,0f,0f,"기교"),
            EvaluationData("올뎃베이직","교양대학",3.2f,0f,0f,0f,"기교"),
            EvaluationData("올뎃베이직","교양대학",3.2f,0f,0f,0f,"기교")
        )
        evaluationAdapter.evaluationListData = dummyData
        evaluationAdapter.notifyDataSetChanged()
    }
}