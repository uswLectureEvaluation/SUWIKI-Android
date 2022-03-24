package com.kunize.uswtimetable.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp2Binding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.afterTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpFragment2 : Fragment() {
    private var _binding: FragmentSignUp2Binding? = null
    val binding: FragmentSignUp2Binding get() = _binding!!

    private val viewModel: SignUpPage2ViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var activity: SignUpActivity
    private lateinit var signUpButton: MaterialButton
    private lateinit var backButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up2, container, false)

        activity = requireActivity() as SignUpActivity

        backButton = activity.button1
        signUpButton = activity.button2

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNullOrBlank()) return@observe
            activity.makeToast(message)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initViews()
        viewModel.setIdPw(activity.getId()!!, activity.getPw()!!)

        signUpButton.setOnClickListener {
            viewModel.setEmail(binding.etMail.text.toString())
            activity.saveEmail(binding.etMail.text.toString())
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.loading.value = true
                viewModel.checkEmail()
                delay(500)

                viewModel.isEmailUnique.observe(viewLifecycleOwner) { emailValid ->
                    if (emailValid) {
                        // TODO 회원가입 시도
                        launch {
                        viewModel.signUp()
                            delay(2000)

                            viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
                                if (result.success) {
                                    if (viewModel.errorMessage.value.isNullOrBlank().not()) {
                                        activity.onNextButtonClicked()
                                    } else {
                                        activity.makeToast(viewModel.errorMessage.value!!)
                                    }
                                } else {
                                    // TODO 회원 가입 실패
                                    if (viewModel.errorMessage.value.isNullOrBlank().not()) {
                                        activity.onNextButtonClicked()
                                    } else {
                                        activity.makeToast(viewModel.errorMessage.value!!)
                                    }
                                }
                            }
                        }
                    } else {
                        // TODO 이메일 중복
                        if (viewModel.errorMessage.value.isNullOrBlank().not()) {
                            activity.makeToast(viewModel.errorMessage.value!!)
                        }
                    }
                }
                viewModel.loading.postValue(false)
            }
        }
        backButton.setOnClickListener { activity.onPreviousButtonClicked() }
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