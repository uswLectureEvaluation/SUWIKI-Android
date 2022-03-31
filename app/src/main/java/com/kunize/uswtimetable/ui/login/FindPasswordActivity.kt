package com.kunize.uswtimetable.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityFindPasswordBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory

class FindPasswordActivity : AppCompatActivity() {
    private var _binding: ActivityFindPasswordBinding? = null
    val binding get() = _binding!!

    private val viewModel: FindPwViewModel by viewModels { ViewModelFactory(this) }
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_find_password)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel


        initViews(this)
    }

    override fun onResume() {
        super.onResume()

        viewModel.successMessage.observe(this) { message ->
            makeToast(message)
            setResult(RESULT_OK)
            finish()
        }
        viewModel.errorMessage.observe(this) { message ->
            makeToast(message)
        }
    }

    private fun initViews(context: Context) {
        with(binding) {
            findIdButton.setOnClickListener {
                startActivity(Intent(context, FindIdActivity::class.java))
            }
        }
    }

    // 뒤로 버튼 누르면 항상 로그인 화면으로 이동
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}