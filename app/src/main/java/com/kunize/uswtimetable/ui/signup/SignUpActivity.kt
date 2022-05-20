package com.kunize.uswtimetable.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.kunize.uswtimetable.databinding.ActivitySignupBinding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.util.BackKeyManager
import com.kunize.uswtimetable.util.Constants.TAG
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    val viewModel: SignUpViewModel by viewModels { ViewModelFactory(this) }
    private var toast: Toast? = null

    lateinit var viewPager: ViewPager2
    private lateinit var indicator: WormDotsIndicator

    private val backKeyManager = BackKeyManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        observeData()
    }

    private fun observeData() {
        viewModel.errorMessage.observe(this) { message ->
            if (message.isNullOrBlank()) return@observe
            makeToast(message)
        }

        viewModel.toastMessage.observe(this) { message ->
            if (message.isNullOrBlank()) return@observe
            makeToast(message)
        }

        viewModel.currentPage.observe(this) { page ->
            toast?.cancel()
            viewPager.currentItem = page
        }

        viewModel.eventLoginButton.observe(this, EventObserver { clicked ->
            if (!clicked) return@EventObserver
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        viewModel.eventCloseButton.observe(this, EventObserver { clicked ->
            if (!clicked) return@EventObserver
            Log.d(TAG, "SignUpActivity - observeData() called / 닫기 버튼 클릭")
            finish()
        })

        viewModel.loading.observe(this) {
            viewModel.setNextButtonEnable()
        }
    }

    private fun initViews() {
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        viewPager = binding.signupViewPager
        viewPager.adapter = SignUpPageAdapter(this@SignUpActivity)
        viewPager.isUserInputEnabled = false
        binding.signUpToolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        indicator = binding.signupIndicator
        indicator.clearFocus()
        indicator.dotsClickable = false
        indicator.setViewPager2(viewPager)
    }

    override fun onBackPressed() {
        backKeyManager.onBackPressed(
            "뒤로 가면 모든 내용이 지워집니다. \n" +
                    "종료하려면 2회 연속 누르세요"
        )
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}