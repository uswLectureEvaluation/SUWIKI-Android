package com.kunize.uswtimetable.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp2Binding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.afterTextChanged

class SignUpFragment2 : Fragment() {
    private var _binding: FragmentSignUp2Binding? = null
    val binding: FragmentSignUp2Binding get() = _binding!!

    private val viewModel: SignUpViewModel by activityViewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up2, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        viewModel.isEmailUnique.observe(viewLifecycleOwner) { unique ->
            if (!unique && viewModel.errorMessage.value?.peekContent().isNullOrBlank().not()) {
                viewModel.setToastMessage(viewModel.errorMessage.value?.peekContent()!!)
            }
        }

        viewModel.signUpResult.observe(viewLifecycleOwner) { success ->
            if (!success && viewModel.errorMessage.value?.peekContent().isNullOrBlank().not()) {
                viewModel.setToastMessage(viewModel.errorMessage.value?.peekContent()!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etInputEmail.afterTextChanged {
            dataChanged()
        }
    }

    private fun dataChanged() {
        viewModel.setNextButtonEnable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}