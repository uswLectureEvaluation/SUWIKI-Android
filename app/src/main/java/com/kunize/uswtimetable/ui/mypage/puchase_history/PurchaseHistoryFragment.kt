package com.kunize.uswtimetable.ui.mypage.puchase_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.kunize.uswtimetable.databinding.FragmentPurchaseHistoryBinding

class PurchaseHistoryFragment : Fragment() {
    private val viewModel: PurchaseHistoryViewModel by viewModels()
    private var _binding: FragmentPurchaseHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPurchaseHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        initViews()
        initRecyclerView()
    }

    private fun initViews() {
        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        val historyAdapter = PurchaseHistoryAdapter()
        binding.rvPurchaseHistory.adapter = historyAdapter
        viewModel.historyList.observe(viewLifecycleOwner) { histories ->
            historyAdapter.submitList(histories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
