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
import com.kunize.uswtimetable.OpenSourceActivity
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentMyPageBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.login.FindIdActivity
import com.kunize.uswtimetable.ui.login.FindPasswordActivity
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.ui.login.User
import com.kunize.uswtimetable.ui.notice.NoticeActivity
import com.kunize.uswtimetable.ui.signup.SignUpActivity

class MyPageFragment : Fragment() {
    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        incomplete(requireContext())
        initViews(requireContext())

        logInStateView()

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
                    User.logout()
                    viewModel.refresh()
                    true
                }
                else -> {
                    Toast.makeText(requireContext(), "${it.title} : 준비 중입니다", Toast.LENGTH_SHORT)
                        .show()
                    false
                }
            }
        }
        binding.numberOfEvaluations.setOnClickListener {
            findNavController().navigate(R.id.action_more_to_myPostFragment)
        }
        binding.myPostButton.setOnClickListener {
            // TODO 로그인 상태 확인 작업
            findNavController().navigate(R.id.action_more_to_myPostFragment)
        }
    }

    private fun logInStateView() {
        viewModel.user.observe(viewLifecycleOwner) { data ->
            val user = data ?: return@observe

            binding.toolBar.menu.clear()
            if (user.isLoggedIn)
                binding.toolBar.inflateMenu(R.menu.more_menu_after)
            else
                binding.toolBar.inflateMenu(R.menu.more_menu_before)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
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
        }
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
}