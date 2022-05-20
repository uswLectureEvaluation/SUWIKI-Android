package com.kunize.uswtimetable.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kunize.uswtimetable.databinding.FragmentSignUp3Binding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.common.WebviewActivity
import com.kunize.uswtimetable.util.Constants.KEY_URL
import com.kunize.uswtimetable.util.Constants.SCHOOL_HOMEPAGE

class SignUpFragment3 : Fragment() {
    private var _binding: FragmentSignUp3Binding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by activityViewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUp3Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        viewModel.eventCheckMail.observe(viewLifecycleOwner, EventObserver { clicked ->
            if (clicked) onClickEmailCheckButton()
        })
    }

    private fun onClickEmailCheckButton() {
        val intent = Intent(requireContext(), WebviewActivity::class.java).apply {
            putExtra(KEY_URL, SCHOOL_HOMEPAGE)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}