package com.kunize.uswtimetable.ui.more

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.OpenSourceActivity
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.StartActivity
import com.kunize.uswtimetable.databinding.FragmentMoreBinding
import com.kunize.uswtimetable.login.FindIdActivity
import com.kunize.uswtimetable.login.FindPasswordActivity
import com.kunize.uswtimetable.login.LoginActivity
import com.kunize.uswtimetable.login.SignInActivity
import com.kunize.uswtimetable.ui.notice.NoticeActivity

class MoreFragment : Fragment() {
    private lateinit var viewModel: MoreViewModel
    private lateinit var viewModelFactory: MoreViewModelFactory
    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)


        incomplete(requireContext())
        initViews(requireContext())

        viewModelFactory = MoreViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MoreViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.loggedIn.observe(viewLifecycleOwner, { isLoggedIn ->
            if (isLoggedIn) {
                loggedIn()
            } else {
                loggedOut()
            }
        })

        viewModel.myEvaluationPoint.observe(viewLifecycleOwner,  { point ->
            if (point >= 0) {
                binding.myEvaluationsPoint.text = "(+$point)"
                binding.myEvaluationsPoint.setTextColor(Color.RED)
            } else {
                binding.myEvaluationsPoint.text = "($point)"
                binding.myEvaluationsPoint.setTextColor(Color.BLUE)
            }
        })

        viewModel.myExamInfoPoint.observe(viewLifecycleOwner, { point ->
            if (point >= 0) {
                binding.myExamInformationPoint.text = "(+$point)"
                binding.myExamInformationPoint.setTextColor(Color.RED)
            } else {
                binding.myExamInformationPoint.text = "($point)"
                binding.myExamInformationPoint.setTextColor(Color.BLUE)
            }
        })

        viewModel.openedExamPoint.observe(viewLifecycleOwner, { point ->
            if (point >= 0) {
                binding.openedExamPoint.text = "(+$point)"
                binding.openedExamPoint.setTextColor(Color.RED)
            } else {
                binding.openedExamPoint.text = "($point)"
                binding.openedExamPoint.setTextColor(Color.BLUE)
            }
        })

        binding.loginButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    loggedOut()
                    true
                }
                else -> {
                    Toast.makeText(requireContext(), "${it.title} : 준비 중입니다", Toast.LENGTH_SHORT)
                        .show()
                    false
                }
            }
        }
    }

    private fun loggedIn() {
        binding.beforeLoginLayout.isGone = true
        binding.beforeLoginLayoutWithButton.isGone = true
        binding.pointInfoLayout.isGone = false

        /* Test */
        binding.loginOrLogoutButton.text = "로그 아웃 (테스트)"
//        viewModel.login()

        setToolbarMenu()
    }

    private fun setToolbarMenu() {
        binding.toolBar.menu.clear()
        if (viewModel.loggedIn.value == false)
            binding.toolBar.inflateMenu(R.menu.more_menu_before)
        else
            binding.toolBar.inflateMenu(R.menu.more_menu_after)
    }

    private fun loggedOut() {
        binding.beforeLoginLayout.isGone = false
        binding.beforeLoginLayoutWithButton.isGone = false
        binding.pointInfoLayout.isGone = true

        /* Test */
        binding.loginOrLogoutButton.text = "로그인 (테스트)"
//        viewModel.logout()

        setToolbarMenu()
    }

    private fun incomplete(context: Context) {
        with(binding) {
            sendFeedbackButton.setOnClickListener {
                Toast.makeText(context, "준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            questionButton.setOnClickListener {
                Toast.makeText(context, "준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            changePasswordButton.setOnClickListener {
                Toast.makeText(context, "준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            termsOfUseButton.setOnClickListener {
                Toast.makeText(context, "준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            privacyPolicyButton.setOnClickListener {
                Toast.makeText(context, "준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            signOutButton.setOnClickListener {
                Toast.makeText(context, "준비 중입니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews(context: Context) {
        with(binding) {
            noticeButton.setOnClickListener {
                val intent = Intent(context, NoticeActivity::class.java)
                startActivity(intent)
            }
            opensourceLicenceButton.setOnClickListener {
                val intent = Intent(context, OpenSourceActivity::class.java)
                startActivity(intent)
            }
            loginOrLogoutButton.setOnClickListener {
                viewModel?.loginOrOut()
            }
        }
    }

    private fun signIn(context: Context) {
        val intent = Intent(context, SignInActivity::class.java)
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
}