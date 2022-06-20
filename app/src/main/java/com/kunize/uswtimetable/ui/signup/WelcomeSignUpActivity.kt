package com.kunize.uswtimetable.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.ActivityWelcomeSignUpBinding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.common.WebviewActivity
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.Constants.KEY_EMAIL

class WelcomeSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeSignUpBinding
    private val viewModel: WelcomeSignUpViewModel by viewModels { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra(KEY_EMAIL)?.let { viewModel.setEmail(it) }

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.eventLoginClicked.observe(this, EventObserver { clicked ->
            if (clicked) startLogin()
        })

        viewModel.eventCheckEmailClicked.observe(this, EventObserver { clicked ->
            if (clicked) onClickEmailCheckButton()
        })

    }

    private fun startLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        this@WelcomeSignUpActivity.finish()
    }

    private fun onClickEmailCheckButton() {
        val intent = Intent(this, WebviewActivity::class.java).apply {
            putExtra(Constants.KEY_URL, Constants.SCHOOL_HOMEPAGE)
        }
        startActivity(intent)
    }
}