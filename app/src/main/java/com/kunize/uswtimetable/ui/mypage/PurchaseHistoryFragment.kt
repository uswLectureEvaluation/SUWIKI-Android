package com.kunize.uswtimetable.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.adapter.PurchaseHistoryAdapter
import com.kunize.uswtimetable.databinding.PurchaseHistoryFragmentBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory

class PurchaseHistoryFragment : Fragment() {
    private val viewModel: PurchaseHistoryViewModel by viewModels { ViewModelFactory(requireContext()) }
    private var _binding: PurchaseHistoryFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: PurchaseHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PurchaseHistoryFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        initViews()
        initRecyclerView()

        return binding.root
    }

    private fun initViews() {
        binding.toolBar.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        historyAdapter = PurchaseHistoryAdapter()
        binding.rvPurchaseHistory.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }
        viewModel.historyList.observe(viewLifecycleOwner) { histories ->
            historyAdapter.submitList(histories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}