package com.kunize.uswtimetable.signup

import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp1Binding
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.afterTextChanged
import java.util.regex.Pattern

class SignUpFragment1 : Fragment() {
    private var _binding: FragmentSignUp1Binding? = null
    val binding: FragmentSignUp1Binding get() = _binding!!
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up1, container, false)

        viewModel = ViewModelProvider(
            requireActivity(),
            SignUpViewModelFactory()
        )[SignUpViewModel::class.java]
        viewModel.signupFormState.observe(requireActivity(), Observer {
            val state = it ?: return@Observer

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
                nextButton.isEnabled = state.isDataValid
            }
        })

        initViews()
        initButton()

        return binding.root
    }

    private fun initButton() {
        binding.nextButton.setOnClickListener {
            // TODO 아이디, 비밀번호, 체크 확인
            if (viewModel.signupFormState.value?.isDataValid == null) {
                val userId = binding.etId.text.toString()
                val userPw = binding.etPw.text.toString()
                val action =
                    SignUpFragment1Directions.actionSignUpFragment1ToSignUpFragment2(userId, userPw, true)
                findNavController().navigate(action)
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
        with(binding) {
            viewModel.signUpDataChanged(
                id = etId.text.toString(),
                pw = etPw.text.toString(),
                pwAgain = etPwAgain.text.toString(),
                term = terms.isChecked
            )
        }
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