package com.kunize.uswtimetable.ui.signup

import android.os.Bundle
import android.text.InputFilter
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp1Binding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.afterTextChanged
import java.util.regex.Pattern

class SignUpFragment1 : Fragment() {
    private var _binding: FragmentSignUp1Binding? = null
    val binding: FragmentSignUp1Binding get() = _binding!!

    private lateinit var activity: SignUpActivity
    private val viewModel: SignUpPage1ViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var nextButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up1, container, false)

        activity = requireActivity() as SignUpActivity

        nextButton = activity.button2

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNullOrBlank()) return@observe
            activity.makeToast(message)
        }
        viewModel.nextButtonEnable.observe(viewLifecycleOwner) { enable ->
            nextButton.isEnabled = enable
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.onResume()
        viewModel.signupFormState.observe(viewLifecycleOwner) {
            val state = it ?: return@observe

            state.idError?.let { errMsg ->
                binding.idEditText.error = resources.getString(errMsg)
            }
            state.pwError?.let { errMsg ->
                binding.passwordEditText.error = resources.getString(errMsg)
            }
            state.pwAgainError?.let { errMsg ->
                binding.passwordAgainEditText.error = resources.getString(errMsg)
            }

            with(binding) {
                idEditText.isErrorEnabled = state.idError != null
                passwordEditText.isErrorEnabled = state.pwError != null
                passwordAgainEditText.isErrorEnabled = state.pwAgainError != null
            }
        }

        initViews()
        initButton()
    }

    private fun initButton() {
        nextButton.setOnClickListener {
            val state = viewModel.signupFormState.value ?: return@setOnClickListener
            viewModel.saveUserIdAndPw(
                binding.etId.text.toString(),
                binding.etPw.text.toString()
            )
            activity.saveIdPw(
                binding.etId.text.toString(),
                binding.etPw.text.toString()
            )
            if (state.isDataValid) {
                viewModel.checkId()
                if (viewModel.isIdUnique.value == true) {
                    activity.changePage(1)
                } else {
                    if (viewModel.errorMessage.value.isNullOrBlank().not()) {
                        activity.makeToast(viewModel.errorMessage.value!!)
                    }
                }
            } else {
                val msg: String = when {
                    state.hasBlank != null -> {
                        resources.getString(state.hasBlank)
                    }
                    state.idError != null -> {
                        resources.getString(state.idError)
                    }
                    state.pwError != null -> {
                        resources.getString(state.pwError)
                    }
                    state.pwAgainError != null -> {
                        resources.getString(state.pwAgainError)
                    }
                    state.isTermChecked != null -> {
                        resources.getString(state.isTermChecked)
                    }
                    else -> {
                        "알 수 없는 에러 발생"
                    }
                }
                activity.makeToast(msg)
            }
        }
    }

    private fun initViews() {

        with(binding) {
            etId.afterTextChanged {
                dataChanged()
                inputLimitAlert(it.toString(), Constants.ID_COUNT_LIMIT)
            }
            etPw.afterTextChanged {
                dataChanged()
                inputLimitAlert(it.toString(), Constants.PW_COUNT_LIMIT)
            }
            etPwAgain.afterTextChanged {
                dataChanged()
                inputLimitAlert(it.toString(), Constants.PW_COUNT_LIMIT)
            }
            terms.setOnCheckedChangeListener { _, _ ->
                dataChanged()
            }

            // 아이디: 소문자와 숫자만 입력 가능
            etId.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                val idRegex = """^[a-z0-9]*$"""
                val idPattern = Pattern.compile(idRegex)
                if (source.isNullOrBlank() || idPattern.matcher(source).matches()) {
                    return@InputFilter source
                }
                getParentActivity().makeToast("소문자와 숫자만 입력 가능합니다: $source")
                source.dropLast(1)
            })

            // 패스워드: 알파벳, 숫자, 특정 특수 문자 입력 가능
            etPw.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                val pwRegex = """^[0-9a-zA-Z!@#$%^+\-=]*$"""
                val pwPattern = Pattern.compile(pwRegex)
                if (source.isNullOrBlank() || pwPattern.matcher(source).matches()) {
                    return@InputFilter source
                }
                getParentActivity().makeToast("입력할 수 없는 문자입니다: $source")
                ""
            })
        }
        /* 이용 약관 링크 연결 */
        val link1 = Pattern.compile("이용약관")
        val link2 = Pattern.compile("개인정보처리방침")
        // TODO 이용약관, 개인정보처리방침 링크 연결
        Linkify.addLinks(binding.terms, link1, "")
        Linkify.addLinks(binding.terms, link2, "")
    }

    private fun dataChanged() {
        viewModel.signUpDataChanged(
            id = binding.etId.text.toString(),
            pw = binding.etPw.text.toString(),
            pwAgain = binding.etPwAgain.text.toString(),
            term = binding.terms.isChecked
        )

    }

    private fun inputLimitAlert(str: String, limit: Int) {
        if (str.length >= limit) {
            val activity = requireActivity() as SignUpActivity
            activity.makeToast("입력할 수 있는 최대 길이입니다")
        }
    }

    private fun getParentActivity(): SignUpActivity {
        return requireActivity() as SignUpActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}