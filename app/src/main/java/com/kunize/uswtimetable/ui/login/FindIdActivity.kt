package com.kunize.uswtimetable.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.ActivityFindIdBinding

class FindIdActivity : AppCompatActivity() {
    private val binding: ActivityFindIdBinding by lazy {
        ActivityFindIdBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews(this)
    }

    private fun initViews(context: Context) {
        with(binding) {
            findPasswordButton.setOnClickListener {
                startActivity(Intent(context, FindPasswordActivity::class.java))
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