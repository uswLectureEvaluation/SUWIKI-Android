package com.kunize.uswtimetable.ui.search_result

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSearchResultBinding
import com.kunize.uswtimetable.ui.evaluation.EvaluationFragment.Companion.dummyData
import com.kunize.uswtimetable.ui.evaluation.EvaluationViewModel

class SearchResultFragment : Fragment() {

    lateinit var binding: FragmentSearchResultBinding
    //EvaluationFragment와 동일한 viewModel 사용
    private lateinit var viewModel : EvaluationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[EvaluationViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val args: SearchResultFragmentArgs by navArgs()
        val msg = args.searchLectureName

        viewModel.changeData(ArrayList(dummyData.subList(0, 1)))

        binding.searchLecture.apply {
            setText(msg)
            setSelection(msg.length)
        }
        return binding.root
    }

}