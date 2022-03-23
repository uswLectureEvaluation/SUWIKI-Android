package com.kunize.uswtimetable.ui.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp2Binding
import com.kunize.uswtimetable.ui.signup.SignUpViewModel.SignUpState
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
        viewModel.resetEmailResult()

        signUpButton.isEnabled = isFullInput()

        signUpButton.setOnClickListener {
            binding.signupProgress.visibility = View.VISIBLE
            signUpButton.isEnabled = false

            viewModel.setEmail(binding.etMail.text.toString())
            viewModel.checkEmail().observe(viewLifecycleOwner) { state ->
                when (state) {
                    SignUpState.SUCCESS -> {
                        viewModel.signup().observe(viewLifecycleOwner) { result ->
                            when (result) {
                                SignUpState.INVALID_ID -> {
                                    activity.makeToast("아이디를 확인하세요")
                                }
                                SignUpState.INVALID_EMAIL -> {
                                    activity.makeToast("이메일을 확인하세요")
                                }
                                SignUpState.INVALID_PASSWORD -> {
                                    activity.makeToast("비밀번호를 입력하세요")
                                }
                                SignUpState.ERROR -> {
                                    activity.makeToast("서버 통신 에러 발생")
                                }
                                SignUpState.SUCCESS -> {
                                    binding.signupProgress.visibility = View.GONE
                                    viewModel.moveToNextPage()
                                }
                                else -> {
                                    Log.d(TAG, "SignUpFragment2 - onResume() called / result: $result")}
                            }
                        }
                    }
                    SignUpState.INVALID_EMAIL -> {
                        activity.makeToast("중복된 이메일입니다.")
                        Log.d(TAG, "SignUpFragment2 - onResume() called / checkEmail: 중복된 이메일")
                    }
                    SignUpState.ERROR -> {
                        activity.makeToast("서버 통신 에러 발생")
                        Log.d(TAG, "SignUpFragment2 - onResume() called / checkEmail: error")
                    }
                    else -> {
                        Log.d(TAG, "SignUpFragment2 - onResume() called / checkEmail: $state")
                    }
                }
            }
            binding.signupProgress.visibility = View.GONE
            signUpButton.isEnabled = true
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