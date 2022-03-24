package com.kunize.uswtimetable.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp2Binding
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

        viewModel.errorMessage2.observe(viewLifecycleOwner) { message ->
            activity.makeToast(message)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initViews()

        signUpButton.setOnClickListener {
            viewModel.setEmail(binding.etMail.text.toString())
            viewModel.checkEmail()
            viewModel.isEmailUnique.observe(viewLifecycleOwner) { emailValid ->
                if (emailValid) {
                    // TODO 회원가입 시도
                    viewModel.signUp()
                    viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
                        if (result.success) {
                            viewModel.moveToNextPage()
                        } else {
                            // TODO 회원 가입 실패
                        }
                    }
                } else {
                    // TODO 이메일 중복
                }
            }
        }
        backButton.setOnClickListener { viewModel.moveToPreviousPage() }
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