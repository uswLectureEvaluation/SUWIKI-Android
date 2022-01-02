package com.kunize.uswtimetable.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivitySignInBinding
import com.kunize.uswtimetable.util.Constants.PasswordAgain
import com.kunize.uswtimetable.util.afterTextChanged
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel
    private val imm by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }
    private var isPwSame = PasswordAgain.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        viewModel.isPwSame.observe(this) {
            isPwSame = it
        }

        initViews(this)
    }

    private fun initViews(context: Context) {
        with(binding) {
            etMail.afterTextChanged {
                activateCertificationButton()
            }

            etCertification.afterTextChanged {
                activateCertificationButton()
            }

            etId.afterTextChanged {
                activateSignInButton()
            }
            etPw.afterTextChanged {
                setPwErrorMsg()
                activateSignInButton()
            }
            etPwAgain.afterTextChanged {
                setPwErrorMsg()
                activateSignInButton()
            }
            termsTextView.setOnCheckedChangeListener { _, _ ->
                activateSignInButton()
            }
            linkToSchoolMailHomepage.setOnClickListener {
                val url = viewModel.schoolMailUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
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

            }
            etCertification.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    certification(etCertification.text.toString())
                    return@OnEditorActionListener true
                }
                return@OnEditorActionListener false
            })
        }
        /* 이용 약관 링크 연결 */
        val link1 = Pattern.compile("이용약관")
        val link2 = Pattern.compile("개인정보처리방침")
        // TODO 이용약관, 개인정보처리방침 링크 연결
        Linkify.addLinks(binding.termsTextView, link1, "")
        Linkify.addLinks(binding.termsTextView, link2, "")
    }

    private fun setPwErrorMsg() {
        with(binding) {
            when {
                etPwAgain.text.toString().isEmpty() -> {
                    viewModel.setPwEmpty()
                }
                etPw.text.toString() == etPwAgain.text.toString() -> {
                    viewModel.setPwSame()
                }
                else -> {
                    viewModel.setPwDifferent()
                }
            }
            passwordAgainEditText.error = if (isPwSame == PasswordAgain.DIFFERENT) "비밀번호와 일치하지 않습니다" else null
        }
    }

    private fun sendEmail(address: String) {
        // TODO 이메일로 인증 번호 전송
        val schoolMail = address + resources.getString(R.string.school_domain)
        Toast.makeText(this@SignInActivity, "$schoolMail: 인증 번호 전송", Toast.LENGTH_SHORT).show()
        binding.linkToSchoolMailHomepage.isGone = false
        imm.hideSoftInputFromWindow(binding.etMail.windowToken, 0)
    }

    private fun certification(certNum: String) {
        // TODO 인증 로직
        val number = certNum.toInt()
        Toast.makeText(this@SignInActivity, "$number: 인증 시도", Toast.LENGTH_SHORT).show()

        // 키보드 내리기
        imm.hideSoftInputFromWindow(binding.etCertification.windowToken, 0)
    }

    private fun activateCertificationButton() {
        binding.sendCertificationNumberButton.isEnabled = binding.etMail.text!!.isNotEmpty()
        binding.certificationButton.isEnabled =
            binding.etCertification.text!!.isNotEmpty()
    }

    private fun activateSignInButton() {
        with(binding) {
            signInButton.isEnabled =
                isPwSame == PasswordAgain.SAME && etId.text!!.isNotEmpty() &&
                        etPw.text!!.isNotEmpty() && etPwAgain.text!!.isNotEmpty() && termsTextView.isChecked
        }
    }
}