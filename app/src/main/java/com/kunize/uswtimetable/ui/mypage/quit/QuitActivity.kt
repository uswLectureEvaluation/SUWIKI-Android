package com.kunize.uswtimetable.ui.mypage.quit

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityQuitBinding
import com.kunize.uswtimetable.ui.main.MainActivity
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import com.kunize.uswtimetable.util.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuitBinding

    private val viewModel: QuitViewModel by viewModels()

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

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.NavigateBackEvent -> this@QuitActivity.finish()
            is Event.QuitButtonEvent -> showDialog()
            is Event.Result -> {
                if (event.success) {
                    toast("성공적으로 탈퇴처리 되었습니다.")

                    Intent(this@QuitActivity, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }.also { startActivity(it) }
                } else {
                    toast("탈퇴 처리에 실패하였습니다.")
                }
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setMessage("회원 탈퇴 시 작성한 강의 평가/시험 정보는 전부 삭제됩니다!")
            .setPositiveButton("탈퇴") { _, _ -> viewModel.quit() }
            .setNeutralButton("취소") { _, _ -> }
            .show()
    }
}
