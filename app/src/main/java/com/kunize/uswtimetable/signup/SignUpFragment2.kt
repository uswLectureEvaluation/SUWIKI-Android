package com.kunize.uswtimetable.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp2Binding
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.Constants.SCHOOL_DOMAIN_AT
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.afterTextChanged

class SignUpFragment2 : Fragment() {
    private var _binding: FragmentSignUp2Binding? = null
    private val args: SignUpFragment2Args by navArgs()
    val binding: FragmentSignUp2Binding get() = _binding!!

    private lateinit var viewModel: SignUpViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up2, container, false)
        viewModel = ViewModelProvider(requireActivity(), SignUpViewModelFactory())[SignUpViewModel::class.java]

        initViews()

        return binding.root
    }

    private fun initViews() {
        with(binding) {
            etMail.afterTextChanged {
                signUpButton.isEnabled = isFullInput()
            }
            /*etMail.setOnEditorActionListener { textView, actionId, keyEvent ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEND -> {
                        floatEmailSendDialog(requireContext(), textView.text.toString())
                        true
                    }
                    else -> false
                }
            }*/
            signUpButton.setOnClickListener {
                viewModel.signup(args.userId, args.userPw, etMail.text.toString() + SCHOOL_DOMAIN_AT)
                // TODO 이메일 중복 확인
                // TODO 이메일 전송 성공 시 다음 페이지로 이동
                viewModel.setEmail(etMail.text.toString())
                val action = SignUpFragment2Directions.actionSignUpFragment2ToSignUpFragment3(etMail.text.toString())
                findNavController().navigate(action)
            }
        }

    }

    /*private fun makeSchoolMailSnackBar() {
        Snackbar.make(binding.root, "학교 메일 홈페이지", Snackbar.LENGTH_INDEFINITE)
            .setAction("이동") {
                val url = viewModel.schoolMailUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }.show()
    }*/

    /*private fun floatEmailSendDialog(context: Context, email: String) {
        hideKeyboard(context, binding.emailEditText)
        MaterialAlertDialogBuilder(context)
            .setTitle("인증 메일 전송")
            .setMessage("$email@${Constants.SCHOOL_DOMAIN} 로 인증 번호를 전송하시겠습니까?")
            .setNeutralButton("취소") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("보내기") { dialog, which ->
                viewModel.sendEmail(email)
                makeSchoolMailSnackBar()
            }.show()
    }*/

    private fun isFullInput(): Boolean = binding.etMail.text.isNullOrBlank().not()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }*/
}