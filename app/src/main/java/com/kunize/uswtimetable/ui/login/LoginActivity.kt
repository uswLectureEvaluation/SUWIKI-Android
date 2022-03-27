package com.kunize.uswtimetable.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kunize.uswtimetable.databinding.ActivityLoginBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.signup.SignUpActivity
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.PreferenceManager
import com.kunize.uswtimetable.util.afterTextChanged

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels { ViewModelFactory(this) }
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        binding.viewModel = loginViewModel

        if (User.isLoggedIn) {
            makeToast("이미 로그인 되어있습니다")
            finish()
        }

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            if (loginState.idError != null) {
                binding.userIdContainer.error = getString(loginState.idError)
            }
            if (loginState.pwError != null) {
                binding.passwordContainer.error = getString(loginState.pwError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity) { loginResult ->

            when (loginResult) {
                LoginState.FAIL -> {
                    makeToast("로그인 실패")
                }
                LoginState.SUCCESS -> {
                    makeToast("로그인 성공!")
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                else -> makeToast("LoginActivity 에러 : $loginResult")
            }
        }

        initViews(this)

    }

    override fun onRestart() {
        super.onRestart()
        if (User.isLoggedIn) {
            finish()
        }
    }

    private fun initViews(context: Context) {
        with(binding) {
            signInButton.setOnClickListener {
                startActivity(Intent(context, SignUpActivity::class.java))
            }
            findIdButton.setOnClickListener {
                startActivity(Intent(context, FindIdActivity::class.java))
            }
            findPasswordButton.setOnClickListener {
                startActivity(Intent(context, FindPasswordActivity::class.java))
            }
            rememberLogin.setOnCheckedChangeListener { _, isChecked ->
                PreferenceManager.setBoolean(context, REMEMBER_LOGIN, isChecked)
            }
            try {
                rememberLogin.isChecked = PreferenceManager.getBoolean(context, REMEMBER_LOGIN)
            } catch (e: ClassCastException) {
                Log.d(TAG, "LoginActivity - \"로그인 유지\" 값이 저장되지 않음: $e")
                rememberLogin.isChecked = false
            }

            userId.afterTextChanged {
                loginViewModel.loginDataChanged(userId.text.toString(), userPassword.text.toString())
            }

            userPassword.apply {
                afterTextChanged {
                    loginViewModel.loginDataChanged(userId.text.toString(), userPassword.text.toString())
                }
            }
        }
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    companion object {
        const val REMEMBER_LOGIN = "rememberLogin"
    }
}