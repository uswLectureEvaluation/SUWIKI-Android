package com.kunize.uswtimetable.signup

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.util.Linkify
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivitySignupBinding
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.afterTextChanged
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignUpViewModel
    private val imm by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, SignUpViewModelFactory())[SignUpViewModel::class.java]

        viewModel.signupFormState.observe(this@SignUpActivity, Observer {
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
                signInButton.isEnabled = true
                Log.d(TAG, "SignUpActivity - $state")
            }
        })

        initViews()
    }

    private fun initViews() {
        with(binding) {
            etId.afterTextChanged {
                dataChanged()
                inputLimitAlert(it.toString(), ID_COUNT_LIMIT)
            }
            etPw.afterTextChanged {
                dataChanged()
                inputLimitAlert(it.toString(), PW_COUNT_LIMIT)
            }
            etPwAgain.afterTextChanged {
                dataChanged()
                inputLimitAlert(it.toString(), PW_COUNT_LIMIT)
            }
            etMail.afterTextChanged {
                dataChanged()
                sendCertificationNumberButton.isEnabled = it.isNullOrBlank().not()
            }
            etCertification.afterTextChanged {
                dataChanged()
                certificationButton.isEnabled = it.isNullOrBlank().not()
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
                makeToast("소문자와 숫자만 입력 가능합니다: $source")
                source.dropLast(1)
            })

            // 패스워드: 알파벳, 숫자, 특정 특수 문자 입력 가능
            etPw.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                val pwRegex = """^[0-9a-zA-Z!@#$%^+\-=]*$"""
                val pwPattern = Pattern.compile(pwRegex)
                if (source.isNullOrBlank() || pwPattern.matcher(source).matches()) {
                    return@InputFilter source
                }
                makeToast("입력할 수 없는 문자입니다: $source")
                ""
            })

            sendCertificationNumberButton.setOnClickListener {
                sendEmail(etMail.text.toString())
            }

            etMail.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendEmail(etMail.text.toString())
                    return@OnEditorActionListener true
                }
                return@OnEditorActionListener false
            })

            certificationButton.setOnClickListener {
                certification(etCertification.text.toString())
                viewModel.emailCheck(binding.etMail.toString())
            }

            etCertification.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    certification(etCertification.text.toString())
                    return@OnEditorActionListener true
                }
                return@OnEditorActionListener false
            })

            signInButton.setOnClickListener {
                val state = viewModel.signupFormState.value
                if (state?.isDataValid == true) {
                    makeToast("회원가입 중")
                    viewModel.signup(
                        etId.text.toString(),
                        etMail.text.toString(),
                        etPw.text.toString()
                    )
                } else {
                    var errMsg = ""
                    when {
                        state?.idError != null -> errMsg = resources.getString(state.idError)
                        state?.pwError != null -> errMsg = resources.getString(state.pwError)
                        state?.pwAgainError != null -> errMsg =
                            resources.getString(state.pwAgainError)
                        state?.certNumError != null -> errMsg =
                            resources.getString(state.certNumError)
                        state?.isTermChecked != null -> errMsg =
                            resources.getString(state.isTermChecked)
                        state?.hasBlank != null -> errMsg = resources.getString(state.hasBlank)
                    }
                    if (errMsg.isNotBlank())
                        Snackbar.make(binding.root, errMsg, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        /* 이용 약관 링크 연결 */
        val link1 = Pattern.compile("이용약관")
        val link2 = Pattern.compile("개인정보처리방침")
        // TODO 이용약관, 개인정보처리방침 링크 연결
        Linkify.addLinks(binding.terms, link1, "")
        Linkify.addLinks(binding.terms, link2, "")
    }

    private fun sendEmail(address: String) {
        // TODO 이메일로 인증 번호 전송
        val schoolMail = address + resources.getString(R.string.school_domain)
        makeToast("$schoolMail: 인증 번호 전송")
        makeSchoolMailSnackBar()

        // TODO Dialog로 한 번 더 확인
        viewModel.sendEmail(address)
        imm.hideSoftInputFromWindow(binding.etMail.windowToken, 0)
    }

    private fun certification(certNum: String) {
//        viewModel.certification(certNum)
        val number = certNum.toInt()
        makeToast("$number: 인증 시도")

        // 키보드 내리기
        imm.hideSoftInputFromWindow(binding.etCertification.windowToken, 0)
    }

    private fun dataChanged() {
        with(binding) {
            viewModel.signUpDataChanged(
                id = etId.text.toString(),
                pw = etPw.text.toString(),
                pwAgain = etPwAgain.text.toString(),
                term = terms.isChecked,
                email = etMail.text.toString(),
                certNum = etCertification.text.toString()
            )
        }
    }

    private fun inputLimitAlert(str: String, limit: Int) {
        if (str.length >= limit) {
            makeToast("입력할 수 있는 최대 길이입니다")
        }
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun makeSchoolMailSnackBar() {
        Snackbar.make(binding.root, "학교 메일 홈페이지", Snackbar.LENGTH_INDEFINITE)
            .setAction("이동") {
                val url = viewModel.schoolMailUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }.show()
    }

    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}