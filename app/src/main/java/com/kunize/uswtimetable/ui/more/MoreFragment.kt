package com.kunize.uswtimetable.ui.more

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    private lateinit var viewModel: MoreViewModel
    private lateinit var viewModelFactory: MoreViewModelFactory
    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)

        viewModelFactory = MoreViewModelFactory()
        viewModel = ViewModelProvider(this,viewModelFactory)[MoreViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.loggedIn.observe(viewLifecycleOwner, Observer { isLoggedIn ->
            if(isLoggedIn) {
                loggedIn()
            } else {
                loggedOut()
            }
        })

        viewModel.myEvaluationPoint.observe(viewLifecycleOwner, Observer { point ->
            if(point>=0) {
                binding.myEvaluationsPoint.text = "(+$point)"
                binding.myEvaluationsPoint.setTextColor(Color.RED)
            } else {
                binding.myEvaluationsPoint.text = "($point)"
                binding.myEvaluationsPoint.setTextColor(Color.BLUE)
            }
        })

        viewModel.myExamInfoPoint.observe(viewLifecycleOwner, Observer { point ->
            if(point>=0) {
                binding.myExamInformationPoint.text = "(+$point)"
                binding.myExamInformationPoint.setTextColor(Color.RED)
            } else {
                binding.myExamInformationPoint.text = "($point)"
                binding.myExamInformationPoint.setTextColor(Color.BLUE)
            }
        })

        viewModel.openedExamPoint.observe(viewLifecycleOwner, Observer { point ->
            if(point>=0) {
                binding.openedExamPoint.text = "(+$point)"
                binding.openedExamPoint.setTextColor(Color.RED)
            } else {
                binding.openedExamPoint.text = "($point)"
                binding.openedExamPoint.setTextColor(Color.BLUE)
            }
        })

        binding.loginButton.setOnClickListener {
            // TODO 로그인 액티비티 띄우기
        }

        return binding.root
    }

    private fun loggedIn() {
        binding.beforeLoginLayout.isGone = true
        binding.beforeLoginLayoutWithButton.isGone = true
        binding.pointInfoLayout.isGone = false
    }

    private fun loggedOut() {
        binding.beforeLoginLayout.isGone = false
        binding.beforeLoginLayoutWithButton.isGone = false
        binding.pointInfoLayout.isGone = true
    }
}