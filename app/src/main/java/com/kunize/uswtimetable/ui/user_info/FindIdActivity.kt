package com.kunize.uswtimetable.ui.user_info

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityFindIdBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.login.LoginActivity

class FindIdActivity : AppCompatActivity() {
    private var _binding: ActivityFindIdBinding? = null
    val binding get() = _binding!!

    private val viewModel: FindIdViewModel by viewModels { ViewModelFactory(this) }
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_find_id)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.successMessage.observe(this) { message ->
            makeToast(message)
            finish()
        }
        viewModel.errorMessage.observe(this) { message ->
            makeToast(message)
        }
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}