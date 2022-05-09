package com.kunize.uswtimetable.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentContactUsBinding

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


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}