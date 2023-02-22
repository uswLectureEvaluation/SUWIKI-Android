package com.kunize.uswtimetable.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.ActivityLoginBinding
import com.kunize.uswtimetable.ui.common.User
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.mypage.find_id.FindIdActivity
import com.kunize.uswtimetable.ui.mypage.find_password.FindPasswordActivity
import com.kunize.uswtimetable.ui.signup.SignUpActivity
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.PreferenceManager
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import com.kunize.uswtimetable.util.extensions.toast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        binding.vm = loginViewModel

        User.isLoggedIn.observe(this) {
            if (it) finish()
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
                    toast("이메일 인증을 받지 않은 사용자입니다.")
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
            loginViewModel.eventFlow.collect { event -> handleEvent(this@LoginActivity, event) }
        }

        initViews(this)
    }

    override fun onResume() {
        super.onResume()

        if (User.isLoggedIn.value == true) {
            toast("이미 로그인되어 있습니다")
            finish()
        }
    }

    private fun initViews(context: Context) {
        with(binding) {
            try {
                cbRememberLogin.isChecked = PreferenceManager.getBoolean(context, REMEMBER_LOGIN)
            } catch (e: ClassCastException) {
                Log.d(TAG, "LoginActivity - \"로그인 유지\" 값이 저장되지 않음: $e")
                cbRememberLogin.isChecked = true
            }
        }
    }

    private fun handleEvent(context: Context, event: LoginViewModel.Event) = when (event) {
        is LoginViewModel.Event.CheckRemember -> PreferenceManager.setBoolean(
            context,
            REMEMBER_LOGIN,
            event.checked
        )
        is LoginViewModel.Event.FindId -> startActivity(Intent(context, FindIdActivity::class.java))
        is LoginViewModel.Event.FindPw -> startActivity(
            Intent(
                context,
                FindPasswordActivity::class.java
            )
        )
        is LoginViewModel.Event.SignUp -> {
            startActivity(Intent(context, SignUpActivity::class.java))
            finish()
        }
    }

    companion object {
        const val REMEMBER_LOGIN = "rememberLogin"
    }
}
