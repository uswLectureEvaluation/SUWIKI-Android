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
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.common.WebviewActivity
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.ui.mypage.MyPageViewModel.Event
import com.kunize.uswtimetable.ui.notice.NoticeActivity
import com.kunize.uswtimetable.ui.open_source.OpenSourceActivity
import com.kunize.uswtimetable.ui.user_info.QuitActivity
import com.kunize.uswtimetable.ui.user_info.ResetPasswordActivity
import com.kunize.uswtimetable.ui.user_info.User
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.repeatOnStarted
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyPageFragment : Fragment() {
    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory() }
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
        binding.executePendingBindings()

        viewLifecycleOwner.repeatOnStarted {
            viewModel.eventFlow.collect { event -> handleUiEvent(event) }
        }
    }

    override fun onResume() {
        super.onResume()
        User.login()
        binding.user = User
    }

    private fun handleUiEvent(event: Event) {
        val context = requireContext()
        when(event) {
            is Event.LoginEvent -> logIn(context)
            is Event.LogoutEvent -> logOut()
            is Event.MyPostEvent -> showMyPosts()
            is Event.NoticeEvent -> showNoticePage(context)
            is Event.FeedbackEvent -> showFeedbackPage(context)
            is Event.QuestionEvent -> showQuestionPage()
            is Event.TermsEvent -> showTermsPage(context)
            is Event.PrivacyPolicyEvent -> showPrivacyPolicyPage(context)
            is Event.ChangePwEvent -> resetPassword(context)
            is Event.SignOutEvent -> quit(context)
            is Event.OpenSourceEvent -> showOpenSourcePage(context)
            is Event.PurchaseHistoryEvent -> showPurchaseHistory()
            is Event.LimitHistoryEvent -> showSuspensionHistory()
            else -> makeToast("준비 중입니다.")
        }
    }

    private fun showMyPosts() {
        if (User.isLoggedIn.value == true) findNavController().navigate(R.id.action_navigation_my_page_to_myPostFragment)
    }

    private fun showQuestionPage() {
        val dialogFragment = ContactUsFragment()
        dialogFragment.show(parentFragmentManager, dialogFragment.tag)
    }

    private fun showNoticePage(context: Context) {
        val intent = Intent(context, NoticeActivity::class.java)
        startActivity(intent)
    }

    private fun showFeedbackPage(context: Context) {
        showWebView(context, Constants.FEEDBACK_SITE)
    }

    private fun showTermsPage(context: Context) {
        showWebView(context, Constants.TERMS_SITE)
    }

    private fun showPrivacyPolicyPage(context: Context) {
        showWebView(context, Constants.PRIVACY_POLICY_SITE)
    }

    private fun showOpenSourcePage(context: Context) {
        val intent = Intent(context, OpenSourceActivity::class.java)
        startActivity(intent)
    }

    private fun showSuspensionHistory() {
        findNavController().navigate(R.id.action_navigation_my_page_to_suspensionHistoryFragment)
    }

    private fun logIn(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun logOut() {
        User.logout()
        binding.user = User
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
        findNavController().navigate(R.id.action_navigation_my_page_to_purchaseHistoryFragment )
    }

    private fun showWebView(context: Context, url: String) {
        val intent = Intent(context, WebviewActivity::class.java).apply {
            putExtra(Constants.KEY_URL, url)
        }
        startActivity(intent)
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}