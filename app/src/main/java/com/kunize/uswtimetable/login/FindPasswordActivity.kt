package com.kunize.uswtimetable.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityFindPasswordBinding

class FindPasswordActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFindPasswordBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews(this)
    }

    private fun initViews(context: Context) {
        with(binding) {
            findIdButton.setOnClickListener {
                startActivity(Intent(context, FindIdActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}