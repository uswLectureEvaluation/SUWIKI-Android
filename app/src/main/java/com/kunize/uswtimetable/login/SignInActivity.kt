package com.kunize.uswtimetable.login

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.util.Linkify
import android.widget.CompoundButton
import androidx.core.widget.addTextChangedListener
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.StartActivity
import com.kunize.uswtimetable.databinding.ActivitySignInBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                activateCertificationButton()
            }
        })
        binding.certificationNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                activateCertificationButton()
            }
        })

        binding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                activateSignInButton()
            }
        })
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                activateSignInButton()
            }
        })
        binding.passwordAgainEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                activateSignInButton()
            }
        })
        binding.termsTextView.setOnCheckedChangeListener { p0, isChecked ->
            activateSignInButton()
        }

        binding.linkToSchoolMailHomepage.setOnClickListener {
            val url = "http://mail.suwon.ac.kr"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val link1 = Pattern.compile("이용약관")
        val link2 = Pattern.compile("개인정보처리방침")
        // TODO 이용약관, 개인정보처리방침 링크 연결
        Linkify.addLinks(binding.termsTextView, link1, "")
        Linkify.addLinks(binding.termsTextView, link2, "")
    }

    private fun activateCertificationButton() {
        binding.sendCertificationNumberButton.isEnabled = binding.emailEditText.text.isNotEmpty()
        binding.certificationButton.isEnabled =
            binding.certificationNumberEditText.text.isNotEmpty()
    }

    private fun activateSignInButton() {
        with(binding) {
            signInButton.isEnabled =
                idEditText.text.isNotEmpty() &&
                passwordEditText.text.isNotEmpty() &&
                passwordAgainEditText.text.isNotEmpty() &&
                termsTextView.isChecked
        }
    }
}