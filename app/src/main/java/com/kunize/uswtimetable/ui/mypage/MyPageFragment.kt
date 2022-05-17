package com.kunize.uswtimetable.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentMyPageBinding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.ui.notice.NoticeActivity
import com.kunize.uswtimetable.ui.open_source.OpenSourceActivity
import com.kunize.uswtimetable.ui.signup.SignUpActivity
import com.kunize.uswtimetable.ui.user_info.*
import com.kunize.uswtimetable.util.MyPageViewType.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyPageFragment : Fragment() {
    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentMyPageBinding
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.user = User
        binding.executePendingBindings()

        logInStateView()
        setOnViewClicked()
        setOnMenuClicked()
    }

    private fun setOnMenuClicked() {
        binding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_login -> {
                    logIn(requireContext())
                    true
                }
                R.id.action_sign_in -> {
                    signIn(requireContext())
                    true
                }
                R.id.action_find_id -> {
                    findId(requireContext())
                    true
                }
                R.id.action_find_password -> {
                    findPw(requireContext())
                    true
                }
                R.id.action_log_out -> {
                    User.logout()
                    true
                }
                R.id.action_change_password -> {
                    resetPassword(requireContext())
                    true
                }
                R.id.action_quit -> {
                    quit(requireContext())
                    true
                }
                R.id.menu_purchase_history -> {
                    showPurchaseHistory()
                    true
                }
                else -> {
                    makeToast("${it.title} : 준비 중입니다")
                    false
                }
            }
        }
    }

    private fun setOnViewClicked() {
        viewModel.eventClick.observe(viewLifecycleOwner, EventObserver { type ->
            val context = requireContext()
            when (type) {
                BTN_LOGIN -> logIn(context)
                BTN_MY_POST -> showMyPosts()
                MENU_NOTICE -> showNoticePage(context)
                MENU_FEEDBACK -> makeToast("준비 중입니다.")
                MENU_QUESTION -> showQuestionPage()
                MENU_CHANGE_PW -> resetPassword(context)
                MENU_TERMS -> makeToast("준비 중입니다.")
                MENU_PRIVACY_POLICY -> makeToast("준비 중입니다.")
                MENU_SIGN_OUT -> quit(context)
                MENU_OPENSOURCE -> showOpenSourcePage(context)
            }
        })
    }

    private fun showMyPosts() {
        if (User.isLoggedIn.value == true) findNavController().navigate(R.id.action_more_to_myPostFragment)
    }

    private fun logInStateView() {
        User.isLoggedIn.observe(viewLifecycleOwner) { loggedIn ->
            binding.toolBar.menu.clear()
            if (loggedIn) {
                binding.toolBar.inflateMenu(R.menu.more_menu_after)
            } else {
                binding.toolBar.inflateMenu(R.menu.more_menu_before)
            }
        }
    }

    private fun showQuestionPage() {
        val dialogFragment = ContactUsFragment()
        dialogFragment.show(parentFragmentManager, dialogFragment.tag)
    }

    private fun showNoticePage(context: Context) {
        val intent = Intent(context, NoticeActivity::class.java)
        startActivity(intent)
    }

    private fun showOpenSourcePage(context: Context) {
        val intent = Intent(context, OpenSourceActivity::class.java)
        startActivity(intent)
    }

    private fun signIn(context: Context) {
        val intent = Intent(context, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun findId(context: Context) {
        val intent = Intent(context, FindIdActivity::class.java)
        startActivity(intent)
    }

    private fun findPw(context: Context) {
        val intent = Intent(context, FindPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun logIn(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun resetPassword(context: Context) {
        if (User.isLoggedIn.value == true) {
            val intent = Intent(context, ResetPasswordActivity::class.java)
            startActivity(intent)
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                makeToast("로그인 후 가능합니다")
                delay(2000)
            }
        }
    }

    private fun quit(context: Context) {
        if (User.isLoggedIn.value == true) {
            val intent = Intent(context, QuitActivity::class.java)
            startActivity(intent)
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                makeToast("로그인 후 가능합니다")
                delay(2000)
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showPurchaseHistory() {
        findNavController().navigate(R.id.action_navigation_my_page_to_purchaseHistoryFragment)
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}