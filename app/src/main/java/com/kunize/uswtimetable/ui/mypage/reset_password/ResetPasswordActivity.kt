package com.kunize.uswtimetable.ui.mypage.reset_password

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityResetPasswordBinding
import com.kunize.uswtimetable.ui.mypage.find_password.FindPasswordActivity
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.Result
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel: ResetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.result.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@ResetPasswordActivity, "성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                        delay(1500)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
                is Result.Error -> {
                    Log.d(TAG, "ResetPasswordActivity - reset PW Failed! : ${result.exception.message}")
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener { viewModel.navigateBackEvent() }

        repeatOnStarted {
            viewModel.uiEvent.collect { event ->
                handleEvent(event)
            }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.FindPasswordEvent -> {
                val intent = Intent(this@ResetPasswordActivity, FindPasswordActivity::class.java)
                startActivity(intent)
            }
            is Event.NavigateBackEvent -> this@ResetPasswordActivity.finish()
        }
    }
}
