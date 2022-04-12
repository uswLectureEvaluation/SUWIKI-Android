package com.kunize.uswtimetable.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.button.MaterialButton
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp2Binding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.afterTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SignUpFragment2 : Fragment() {
    private var _binding: FragmentSignUp2Binding? = null
    val binding: FragmentSignUp2Binding get() = _binding!!

    private val viewModel: SignUpViewModel by activityViewModels { ViewModelFactory(requireContext()) }
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
        binding.viewmodel = viewModel

        signUpButton.setOnClickListener {
            viewModel.loading.value = true
            CoroutineScope(Dispatchers.Main).launch {
                async { viewModel.checkEmail() }.await()
                async { viewModel.signUp() }.await()
                viewModel.loading.postValue(false)
            }
        }

        viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
            if (result.success) {
                if (viewModel.errorMessage.value.isNullOrBlank().not()) {
                    activity.onNextButtonClicked()
                } else {
                    activity.makeToast(viewModel.errorMessage.value!!)
                }
            } else {
                if (viewModel.errorMessage.value.isNullOrBlank().not()) {
                    activity.onNextButtonClicked()
                } else {
                    activity.makeToast(viewModel.errorMessage.value!!)
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initViews()

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