package com.kunize.uswtimetable.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    lateinit var binding: FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write, container, false)

        binding.finishButton.setOnClickListener {
            goToLectureInfoFragment()
        }


        return binding.root
    }

    private fun goToLectureInfoFragment() {
        val action =
            WriteFragmentDirections.actionWriteFragmentToLectureInfoFragment()
        findNavController().navigate(action)
    }
}