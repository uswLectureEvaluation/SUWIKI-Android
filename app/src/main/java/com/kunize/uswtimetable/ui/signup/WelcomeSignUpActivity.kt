package com.kunize.uswtimetable.ui.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.ActivityWelcomeSignUpBinding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.Constants.KEY_EMAIL

class WelcomeSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeSignUpBinding
    private val viewModel: WelcomeSignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra(KEY_EMAIL)?.let { viewModel.setEmail(it) }

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.eventLoginClicked.observe(
            this,
            EventObserver { clicked ->
                if (clicked) startLogin()
            },
        )

        viewModel.eventCheckEmailClicked.observe(
            this,
            EventObserver { clicked ->
                if (clicked) onClickEmailCheckButton()
            },
        )
    }

    private fun startLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        this@WelcomeSignUpActivity.finish()
    }

    private fun onClickEmailCheckButton() {
        // 웹뷰 사용 시 포털사이트 -> 웹메일로 이동하지 않는 현상이 있어 수정함
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SCHOOL_HOMEPAGE)))
    }
}
