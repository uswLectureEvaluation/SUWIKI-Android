package com.kunize.uswtimetable.ui.evaluation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.kunize.uswtimetable.CustomSpinnerAdapter
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentEvaluationBinding

class EvaluationFragment : Fragment() {
    lateinit var binding: FragmentEvaluationBinding

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

        return binding.root
    }
}