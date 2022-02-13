package com.kunize.uswtimetable.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivitySignupBinding
import com.kunize.uswtimetable.util.BackKeyManager
import com.kunize.uswtimetable.util.Constants.TAG

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignUpViewModel
    private val imm by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }
    private var toast: Toast? = null
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private val backKeyManager = BackKeyManager(this)
    val progress: ProgressBar by lazy { findViewById(R.id.pageProgress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, SignUpViewModelFactory())[SignUpViewModel::class.java]

        initNavigation()
        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                Log.d(TAG, "SignUpActivity - onOptionsItemSelected() called")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.signUpFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onBackPressed() {
        backKeyClicked()
    }

    private fun initViews() {
        with(binding) {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.signUpFragment1 -> {
                        signUpToolBar.title = "회원 가입 1/2"
                        pageProgress.progress = 50
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    }
                    R.id.signUpFragment2 -> {
                        signUpToolBar.title = "회원 가입 2/2"
                        pageProgress.progress = 100
                    }
                }
            }
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            signUpToolBar.setupWithNavController(navController, appBarConfiguration)
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

    private fun backKeyClicked() {
        when (navController.currentDestination?.id) {
            R.id.signUpFragment1 -> {
                backKeyManager.onBackPressed(
                    "뒤로 가면 모든 내용이 지워집니다. \n" +
                            "종료하려면 2회 연속 누르세요"
                )
            }
            R.id.signUpFragment2 -> {
                navController.popBackStack()
            }
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