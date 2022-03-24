package com.kunize.uswtimetable.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.SignUpPageAdapter
import com.kunize.uswtimetable.databinding.ActivitySignupBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.BackKeyManager
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    val viewModel: SignUpViewModel by viewModels { ViewModelFactory(this) }
    private var toast: Toast? = null

    lateinit var viewPager: ViewPager2
    private lateinit var adapter: SignUpPageAdapter
    private lateinit var indicator: WormDotsIndicator
    lateinit var button1: MaterialButton
    lateinit var button2: MaterialButton

    private val backKeyManager = BackKeyManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        initViews()

        viewModel.currentPage.observe(this) { page ->
            toast?.cancel()
            when (page) {
                0 -> {
                    button1.visibility = View.INVISIBLE
                    button1.text = getString(R.string.btn_previous)
                    button2.text = getString(R.string.btn_next)
                }
                1 -> {
                    button1.visibility = View.VISIBLE
                    button1.text = getString(R.string.btn_previous)
                    button2.text = getString(R.string.sign_in)
                }
                2 -> {
                    button1.visibility = View.VISIBLE
                    button1.text = getString(R.string.btn_exit)
                    button2.text = getString(R.string.login)
                }
            }
            viewPager.currentItem = page
        }
    }

    fun onNextButtonClicked() = viewModel.moveToNextPage()
    fun onPreviousButtonClicked() = viewModel.moveToPreviousPage()
    fun changePage(page: Int) = viewModel.movePage(page)
    fun saveIdPw(id: String, pw: String) = viewModel.saveIdPw(id, pw)
    fun saveEmail(email: String) = viewModel.saveEmail(email)
    fun getId() = viewModel.id
    fun getPw() = viewModel.pw

    private fun initViews() {
        button1 = binding.btnSignup1
        button2 = binding.btnSignup2

        viewPager = binding.signupViewPager
        adapter = SignUpPageAdapter(this@SignUpActivity)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false

        indicator = binding.signupIndicator
        indicator.clearFocus()
        indicator.dotsClickable = false
        indicator.setViewPager2(viewPager)
    }

    override fun onBackPressed() {
        backKeyManager.onBackPressed("뒤로 가면 모든 내용이 지워집니다. \n" +
                "종료하려면 2회 연속 누르세요")
    }

    fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}