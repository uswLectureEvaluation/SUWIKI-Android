package com.kunize.uswtimetable.signup

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.util.Linkify
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivitySignupBinding
import com.kunize.uswtimetable.util.Constants.ID_COUNT_LIMIT
import com.kunize.uswtimetable.util.Constants.PW_COUNT_LIMIT
import com.kunize.uswtimetable.util.afterTextChanged
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignUpViewModel
    private val imm by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, SignUpViewModelFactory())[SignUpViewModel::class.java]

        viewModel.signupFormState.observe(this@SignUpActivity, Observer {
            val state = it ?: return@Observer

            binding.signInButton.isEnabled = state.isDataValid
        })
        initViews(this)
    }

    private fun initViews(context: Context) {
        with(binding) {
            etMail.afterTextChanged {
                dataChanged()
            }

            etCertification.afterTextChanged {
                dataChanged()
            }

            etPw.afterTextChanged {
                dataChanged()
            }
            etPwAgain.afterTextChanged {
                dataChanged()
            }
            etId.afterTextChanged {
                dataChanged()
            }

            // 아이디: 소문자와 숫자만 입력 가능
            etId.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                val alphaNum = Pattern.compile(viewModel.alphaNumPattern)
                if (source.length > ID_COUNT_LIMIT) {
                    Toast.makeText(context, "${ID_COUNT_LIMIT}개까지 입력 가능합니다", Toast.LENGTH_SHORT).show()
                    return@InputFilter source
                }
                if (source.equals("") || alphaNum.matcher(source).matches()) {
                    return@InputFilter source
                }
                idEditText.isHelperTextEnabled = true
                Toast.makeText(context, "소문자와 숫자만 입력 가능합니다. $source", Toast.LENGTH_SHORT).show()
                val lowercaseId = source.toString().lowercase()
                if (alphaNum.matcher(lowercaseId).matches()) {
                    source.toString().lowercase()
                }
                else {
                    lowercaseId.filter {
                        it.isLowerCase() || it.isDigit()
                    }
                }
            }, InputFilter.LengthFilter(ID_COUNT_LIMIT))

            // 패스워드:
            etPw.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
//                val tmpPattern = """^(?=.[a-zA-Z])(?=.[!@#\$%^+=-])(?=.[0-9])*$"""
//                val regex = Pattern.compile(tmpPattern)
//                if (source.equals("") || regex.matcher(source).matches()) {
//                    return@InputFilter source
//                }
                val pattern = Pattern.compile("^[a-z0-9]*$")
                if (source.isNullOrBlank() || pattern.matcher(source).matches()){
                    return@InputFilter source
                }
                Toast.makeText(context, "비밀번호를 확인하세요", Toast.LENGTH_SHORT).show()
                ""
            }, InputFilter.LengthFilter(PW_COUNT_LIMIT))

            termsTextView.setOnCheckedChangeListener { _, _ ->
                dataChanged()
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
                viewModel.emailCheck(binding.etMail.toString())
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

    private fun sendEmail(address: String) {
        // TODO 이메일로 인증 번호 전송
        val schoolMail = address + resources.getString(R.string.school_domain)
        Toast.makeText(this@SignUpActivity, "$schoolMail: 인증 번호 전송", Toast.LENGTH_SHORT).show()
        binding.linkToSchoolMailHomepage.isGone = false
        imm.hideSoftInputFromWindow(binding.etMail.windowToken, 0)
    }

    private fun certification(certNum: String) {
//        viewModel.certification(certNum)
        val number = certNum.toInt()
        Toast.makeText(this@SignUpActivity, "$number: 인증 시도", Toast.LENGTH_SHORT).show()

        // 키보드 내리기
        imm.hideSoftInputFromWindow(binding.etCertification.windowToken, 0)
    }

    private fun dataChanged() {
        with(binding) {
            viewModel.signUpDataChanged(etId.toString(), etPw.toString(), etPwAgain.toString(), termsTextView.isChecked)
        }
    }
}