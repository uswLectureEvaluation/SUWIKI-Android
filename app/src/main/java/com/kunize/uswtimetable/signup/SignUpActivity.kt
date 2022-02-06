package com.kunize.uswtimetable.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignUpViewModel
    private val imm by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }
    private var toast: Toast? = null
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    val progress: ProgressBar by lazy { findViewById(R.id.pageProgress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, SignUpViewModelFactory())[SignUpViewModel::class.java]

        initNavigation()
        initViews()
    }

    private fun initNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.signUpFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initViews() {
        with(binding) {
            /*button.setOnClickListener {
                val state = viewModel.signupFormState.value
                if (state?.isDataValid == true) {
                    makeToast("회원가입 중")
                    viewModel.signup(
//                        etId.text.toString(),
//                        etMail.text.toString(),
//                        etPw.text.toString()
                    "", "", ""
                    )
                } else {
                    var errMsg = ""
                    when {
                        state?.idError != null -> errMsg = resources.getString(state.idError)
                        state?.pwError != null -> errMsg = resources.getString(state.pwError)
                        state?.pwAgainError != null -> errMsg =
                            resources.getString(state.pwAgainError)
                        state?.certNumError != null -> errMsg =
                            resources.getString(state.certNumError)
                        state?.isTermChecked != null -> errMsg =
                            resources.getString(state.isTermChecked)
                        state?.hasBlank != null -> errMsg = resources.getString(state.hasBlank)
                    }
                    if (errMsg.isNotBlank())
                        Snackbar.make(binding.root, errMsg, Snackbar.LENGTH_SHORT).show()
                }
            }*/
        }
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