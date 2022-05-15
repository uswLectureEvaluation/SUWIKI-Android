package com.kunize.uswtimetable.ui.signup

import android.os.Bundle
import android.text.InputFilter
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp1Binding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.afterTextChanged
import java.util.regex.Pattern

class SignUpFragment1 : Fragment() {
    private var _binding: FragmentSignUp1Binding? = null
    val binding: FragmentSignUp1Binding get() = _binding!!

    private val viewModel: SignUpViewModel by activityViewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up1, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

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
        }

        viewModel.isIdUnique.observe(viewLifecycleOwner) { isUnique ->
            if (isUnique == false && viewModel.errorMessage.value.isNullOrBlank().not()) {
                viewModel.setToastMessage(viewModel.errorMessage.value!!)
            }
        }

        initViews()
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
            terms.setOnClickListener {
                dataChanged()
            }

            // 아이디: 소문자와 숫자만 입력 가능
            etId.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                val idRegex = """^[a-z0-9]*$"""
                val idPattern = Pattern.compile(idRegex)
                if (source.isNullOrBlank() || idPattern.matcher(source).matches()) {
                    return@InputFilter source
                }
                viewModel.setToastMessage("소문자와 숫자만 입력 가능합니다: $source")
                source.dropLast(1)
            })

            // 패스워드: 알파벳, 숫자, 특정 특수 문자 입력 가능
            etPw.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                val pwRegex = """^[0-9a-zA-Z!@#$%^+\-=]*$"""
                val pwPattern = Pattern.compile(pwRegex)
                if (source.isNullOrBlank() || pwPattern.matcher(source).matches()) {
                    return@InputFilter source
                }
                viewModel.setToastMessage("입력할 수 없는 문자입니다: $source")
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
            viewModel.setToastMessage("입력할 수 있는 최대 길이입니다")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}