package com.kunize.uswtimetable.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp2Binding
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.afterTextChanged

class SignUpFragment2 : Fragment() {
    private var _binding: FragmentSignUp2Binding? = null
    val binding: FragmentSignUp2Binding get() = _binding!!

    private lateinit var viewModel: SignUpViewModel
    private lateinit var activity: SignUpActivity
    private lateinit var signUpButton: MaterialButton
    private lateinit var backButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up2, container, false)

        activity = requireActivity() as SignUpActivity
        viewModel = activity.viewModel

        backButton = activity.button1
        signUpButton = activity.button2

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        signUpButton.isEnabled = isFullInput()
        signUpButton.setOnClickListener {
            viewModel.signup()
            viewModel.setEmail(binding.etMail.text.toString())
            viewModel.moveToNextPage()
        }
        backButton.setOnClickListener { viewModel.moveToPreviousPage() }
        initViews()
    }

    private fun initViews() {

        binding.etMail.afterTextChanged {
            signUpButton.isEnabled = isFullInput()
        }
    }

    private fun isFullInput(): Boolean = binding.etMail.text.isNullOrBlank().not()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}