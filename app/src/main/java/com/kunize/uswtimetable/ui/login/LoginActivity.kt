package com.kunize.uswtimetable.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
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
    private val viewModel: LoginViewModel by viewModels { ViewModelFactory(this) }
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (viewModel.isLoggedIn) {
            makeToast("이미 로그인 되어있습니다")
            finish()
        }

        viewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            binding.login.isEnabled = loginState.isDataValid

            if (loginState.idError != null) {
                binding.userIdContainer.error = getString(loginState.idError)
                binding.userIdContainer.isErrorEnabled = true
            } else {
                binding.userIdContainer.isErrorEnabled = false
            }
            if (loginState.pwError != null) {
                binding.passwordContainer.error = getString(loginState.pwError)
                binding.passwordContainer.isErrorEnabled = true
            } else {
                binding.passwordContainer.isErrorEnabled = false
            }
        })
        viewModel.loginResult.observe(this@LoginActivity) { loginResult ->

            binding.loading.visibility = View.GONE

            when (loginResult) {
                LoginState.ID_ERROR -> {
                    binding.userIdContainer.isErrorEnabled = true
                }
                LoginState.PW_ERROR -> {
                    binding.passwordContainer.isErrorEnabled = true
                }
                LoginState.UNKNOWN_ERROR -> {
                    makeToast("알 수 없는 에러 발생")
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
                viewModel.loginDataChanged(userId.text.toString(), password.text.toString())
            }

            password.apply {
                afterTextChanged {
                    viewModel.loginDataChanged(userId.text.toString(), password.text.toString())
                }

                setOnEditorActionListener { _, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE ->
                            viewModel.login(userId.text.toString(), password.text.toString())
                    }
                    false
                }
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                userIdContainer.isErrorEnabled = false
                passwordContainer.isErrorEnabled = false

                viewModel.login(userId.text.toString(), password.text.toString())
            }
        }
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    companion object {
        private const val REMEMBER_LOGIN = "rememberLogin"
    }
}