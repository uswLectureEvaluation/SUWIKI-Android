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
import com.kunize.uswtimetable.login.LoginActivity

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

        viewModel.loggedIn.observe(viewLifecycleOwner, Observer { isLoggedIn ->
            if (isLoggedIn) {
                loggedIn()
            } else {
                loggedOut()
            }
        })

        viewModel.myEvaluationPoint.observe(viewLifecycleOwner, Observer { point ->
            if (point >= 0) {
                binding.myEvaluationsPoint.text = "(+$point)"
                binding.myEvaluationsPoint.setTextColor(Color.RED)
            } else {
                binding.myEvaluationsPoint.text = "($point)"
                binding.myEvaluationsPoint.setTextColor(Color.BLUE)
            }
        })

        viewModel.myExamInfoPoint.observe(viewLifecycleOwner, Observer { point ->
            if (point >= 0) {
                binding.myExamInformationPoint.text = "(+$point)"
                binding.myExamInformationPoint.setTextColor(Color.RED)
            } else {
                binding.myExamInformationPoint.text = "($point)"
                binding.myExamInformationPoint.setTextColor(Color.BLUE)
            }
        })

        viewModel.openedExamPoint.observe(viewLifecycleOwner, Observer { point ->
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

    private fun loggedIn() {
        binding.beforeLoginLayout.isGone = true
        binding.beforeLoginLayoutWithButton.isGone = true
        binding.pointInfoLayout.isGone = false

        /* Test */
        binding.loginOrLogoutButton.text = "로그 아웃 (테스트)"
    }

    private fun loggedOut() {
        binding.beforeLoginLayout.isGone = false
        binding.beforeLoginLayoutWithButton.isGone = false
        binding.pointInfoLayout.isGone = true

        /* Test */
        binding.loginOrLogoutButton.text = "로그인 (테스트)"
    }

    private fun incomplete(context: Context) {
        with(binding) {
            noticeButton.setOnClickListener {
                Toast.makeText(context, "준비 중입니다", Toast.LENGTH_SHORT).show()
            }
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
        binding.opensourceLicenceButton.setOnClickListener {
            val intent = Intent(context, OpenSourceActivity::class.java)
            startActivity(intent)
        }
        binding.loginOrLogoutButton.setOnClickListener {
            viewModel.loginOrOut()
        }
    }
}