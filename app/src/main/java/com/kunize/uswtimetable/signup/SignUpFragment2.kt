package com.kunize.uswtimetable.signup

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentSignUp2Binding
import com.kunize.uswtimetable.util.Constants

class SignUpFragment2 : Fragment() {
    private var _binding: FragmentSignUp2Binding? = null
    val binding: FragmentSignUp2Binding get() = _binding!!

    private lateinit var viewModel: SignUpViewModel
    private val activity by lazy {
        requireActivity() as SignUpActivity
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up2, container, false)
        viewModel = ViewModelProvider(requireActivity(), SignUpViewModelFactory())[SignUpViewModel::class.java]

        activity.progress.progress = 100

        return binding.root
    }


    private fun makeSchoolMailSnackBar() {
        Snackbar.make(binding.root, "학교 메일 홈페이지", Snackbar.LENGTH_INDEFINITE)
            .setAction("이동") {
                val url = viewModel.schoolMailUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }.show()
    }

    private fun floatEmailSendDialog(context: Context, email: String,) {
        MaterialAlertDialogBuilder(context)
            .setTitle("인증 메일 전송")
            .setMessage("$email@${Constants.SCHOOL_DOMAIN} 로 인증 번호를 전송하시겠습니까?")
            .setNeutralButton("취소") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("보내기") { dialog, which ->
//                viewModel.sendEmail(email)
//                sendEmail(email)
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}