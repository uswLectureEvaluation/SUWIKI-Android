package com.kunize.uswtimetable.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kunize.uswtimetable.databinding.FragmentSignUp3Binding
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
        binding.viewModel = viewModel

        return binding.root
    }

    fun onClickEmailCheckButton(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SCHOOL_HOMEPAGE))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}