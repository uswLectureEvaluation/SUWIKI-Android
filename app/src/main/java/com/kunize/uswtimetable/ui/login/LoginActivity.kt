package com.kunize.uswtimetable.ui.login

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.ActivityLoginBinding
import com.kunize.uswtimetable.ui.mypage.find_id.FindIdActivity
import com.kunize.uswtimetable.ui.mypage.find_password.FindPasswordActivity
import com.kunize.uswtimetable.ui.signup.SignUpActivity
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import com.kunize.uswtimetable.util.extensions.toast
import com.mangbaam.presentation.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        binding.vm = loginViewModel

        repeatOnStarted {
            loginViewModel.loggedIn.collect {
                if (it) {
                    finish()
                }
            }
        }

        loginViewModel.loginFormState.observe(this@LoginActivity) {
            val loginState = it ?: return@observe

            if (loginState.idError != null) {
                binding.layoutInputId.error = getString(loginState.idError)
            }
            if (loginState.pwError != null) {
                binding.layoutInputPw.error = getString(loginState.pwError)
            }
        }

        loginViewModel.loginResult.observe(this@LoginActivity) { loginResult ->

            when (loginResult) {
                LoginState.REQUIRE_AUTH -> {
                    toast("이메일 인증을 받지 않은 사용자입니다.") // TODO 문자열 추출
                }

                LoginState.FAIL -> {
                    toast("로그인 실패")
                }

                LoginState.SUCCESS -> {
                    toast("로그인 성공!")
                    setResult(Activity.RESULT_OK)
                    finish()
                }

                else -> toast("LoginActivity 에러 : $loginResult")
            }
        }

        repeatOnStarted {
            loginViewModel.eventFlow.collect(::handleEvent)
        }

        initViews()
    }

    private fun initViews() {
        repeatOnStarted {
            loginViewModel.rememberLogin.collect {
                binding.cbRememberLogin.isChecked = it
            }
        }
    }

    private fun handleEvent(event: LoginViewModel.Event) = when (event) {
        is LoginViewModel.Event.FindId -> startActivity<FindIdActivity> {}
        is LoginViewModel.Event.FindPw -> startActivity<FindPasswordActivity> {}
        is LoginViewModel.Event.SignUp -> startActivity<SignUpActivity>(true) {}
    }
}
