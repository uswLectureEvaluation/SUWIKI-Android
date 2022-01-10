package com.kunize.uswtimetable.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kunize.uswtimetable.databinding.ActivityLoginBinding
import com.kunize.uswtimetable.ui.evaluation.PreferenceManager
import com.kunize.uswtimetable.util.Constants.TAG
import java.lang.ClassCastException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews(this)

    }

    private fun initViews(context: Context) {
        with(binding) {
            signInButton.setOnClickListener {
                startActivity(Intent(context, SignInActivity::class.java))
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
        }
    }

    companion object {
        private const val REMEMBER_LOGIN = "rememberLogin"
    }
}