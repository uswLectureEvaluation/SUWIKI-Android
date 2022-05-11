package com.kunize.uswtimetable.ui.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentContactUsBinding
import com.kunize.uswtimetable.util.afterTextChanged

class ContactUsFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentContactUsBinding? = null
    private val binding get() = _binding!!
    private lateinit var category: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentContactUsBinding.inflate(inflater, container, false)
        category = binding.spCategory

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.contact_us, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            category.adapter = adapter
        }

        initViews()


        return binding.root
    }

    private fun initViews() {
        with(binding) {
            etTitle.afterTextChanged {
                btnSend.isEnabled = onDataChanged()
            }
            etContent.afterTextChanged {
                btnSend.isEnabled = onDataChanged()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.setOnClickListener {
            sendEmail()
        }
    }

    private fun onDataChanged(): Boolean {
        with(binding) {
            return (etTitle.text.isNotBlank() && etContent.text.isNotBlank())
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
//        intent.type = "message/rfc822"
//        intent.putExtra(Intent.EXTRA_EMAIL, "uswin_official@naver.com")
        intent.data = Uri.parse("mailto:uswin_official@naver.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, binding.etTitle.text)
        intent.putExtra(Intent.EXTRA_TEXT, binding.etContent.text)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(Intent.createChooser(intent, "문의하기"))
        } catch(except: android.content.ActivityNotFoundException) {
            Toast.makeText(requireContext(), "이메일을 보낼 수 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}