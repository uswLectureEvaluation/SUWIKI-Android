package com.kunize.uswtimetable.ui.mypage.quit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityQuitBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.repeatOnStarted

class QuitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuitBinding

    private val viewModel: QuitViewModel by viewModels { ViewModelFactory() }
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quit)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.toolbar.setNavigationOnClickListener { viewModel.navigateBackEvent() }

        repeatOnStarted {
            viewModel.uiEvent.collect { event ->
                handleEvent(event)
            }
        }
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.NavigateBackEvent -> this@QuitActivity.finish()
            is Event.SuccessMessage -> makeToast(event.message)
            is Event.ErrorMessage -> makeToast(event.message)
        }
    }
}