package com.kunize.uswtimetable.ui.user_info

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
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResetPasswordActivity: AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel: ResetPasswordViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.result.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    // TODO 모달 창 띄우고 3초 뒤 액티비티 종료
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@ResetPasswordActivity, "성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                        delay(2000)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
                is Result.Error -> {
                    Log.d(TAG, "ResetPasswordActivity - reset PW Failed! : ${result.exception.message}")
                }
            }
        }

        initViews()
    }

    private fun initViews() {
        with(binding) {
            buttonResetPwFindPassword.setOnClickListener {
                val intent = Intent(this@ResetPasswordActivity, FindPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }
}