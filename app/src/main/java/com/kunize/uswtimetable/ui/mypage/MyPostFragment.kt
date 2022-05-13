package com.kunize.uswtimetable.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kunize.uswtimetable.databinding.FragmentMyPostBinding

class MyPostFragment : Fragment() {
    private var _binding: FragmentMyPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var tab: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPostBinding.inflate(inflater, container, false)

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = MyPostPageAdapter(this)

        tab = binding.tabLayout

        TabLayoutMediator(tab, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "강의 평가"
                1 -> tab.text = "시험 정보"
            }
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}