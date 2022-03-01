package com.kunize.uswtimetable.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp3Binding
import com.kunize.uswtimetable.login.LoginActivity
import com.kunize.uswtimetable.util.Constants.SCHOOL_HOMEPAGE

class SignUpFragment3 : Fragment() {
    private var _binding: FragmentSignUp3Binding? = null
    private val binding get() = _binding!!
    private lateinit var activity: SignUpActivity
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUp3Binding.inflate(inflater, container, false)

        activity = requireActivity() as SignUpActivity
        viewModel = activity.viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.tvInfo.text = getString(R.string.signup_welcome_info, viewModel.email)

        binding.btnCheckMail.setOnClickListener {
            onClickEmailCheckButton()
        }

        activity.button1.setOnClickListener {
            activity.finish()
        }

        activity.button2.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity.finish()
        }
    }

    private fun onClickEmailCheckButton() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SCHOOL_HOMEPAGE))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}