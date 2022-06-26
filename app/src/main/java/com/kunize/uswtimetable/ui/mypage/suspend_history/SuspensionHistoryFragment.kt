package com.kunize.uswtimetable.ui.mypage.suspend_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kunize.uswtimetable.databinding.FragmentSuspensionHistoryBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory

class SuspensionHistoryFragment : Fragment() {
    private var _binding: FragmentSuspensionHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SuspendHistoryViewModel by viewModels { ViewModelFactory() }
    private val adapter = SuspensionHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuspensionHistoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSuspensionHistory.adapter = adapter
        viewModel.suspendHistory.observe(viewLifecycleOwner) { history ->
            adapter.submitList(history)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
