package com.kunize.uswtimetable.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityLoginBinding
import com.kunize.uswtimetable.dataclass.LoggedInUserView
import com.kunize.uswtimetable.signup.SignUpActivity
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.PreferenceManager
import com.kunize.uswtimetable.util.afterTextChanged
import java.lang.ClassCastException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]
        viewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            binding.login.isEnabled = loginState.isDataValid

            if (loginState.idError != null) {
                binding.userIdContainer.errorContentDescription = getString(loginState.idError)
                binding.userIdContainer.isErrorEnabled = true
            } else {
                binding.userIdContainer.isErrorEnabled = false
            }
            if (loginState.pwError != null) {
                binding.passwordContainer.errorContentDescription = getString(loginState.pwError)
                binding.passwordContainer.isErrorEnabled = true
            } else {
                binding.passwordContainer.isErrorEnabled = false
            }
        })
        viewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            binding.loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            finish()
        })

        initViews(this)

    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName

        Toast.makeText(applicationContext, "$welcome $displayName", Toast.LENGTH_SHORT).show()
    }

    private fun showLoginFailed(@StringRes error: Int) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
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
            } catch(e: ClassCastException) {
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

                login.setOnClickListener {
                    loading.visibility = View.VISIBLE
                    viewModel.login(userId.text.toString(), password.text.toString())
                }
            }
        }
    }

    companion object {
        private const val REMEMBER_LOGIN = "rememberLogin"
    }
}