package com.kunize.uswtimetable.ui.user_info

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityQuitBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory

class QuitActivity: AppCompatActivity() {
    private var _binding: ActivityQuitBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuitViewModel by viewModels { ViewModelFactory() }
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_quit)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}