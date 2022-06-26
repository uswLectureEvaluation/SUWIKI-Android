package com.kunize.uswtimetable.ui.mypage.suspend_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kunize.uswtimetable.databinding.FragmentSuspensionHistoryBinding

class SuspensionHistoryFragment: Fragment() {
    private var _binding: FragmentSuspensionHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuspensionHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSuspensionHistory.adapter = SuspensionHistoryAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}