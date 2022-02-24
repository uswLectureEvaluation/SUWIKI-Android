package com.kunize.uswtimetable.ui.more

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.MyPostPageAdapter
import com.kunize.uswtimetable.databinding.FragmentMyPostBinding

class MyPostFragment : Fragment() {
    private var _binding: FragmentMyPostBinding? = null
    private val binding get() = _binding!!
    private val tab by lazy { binding.tabLayout }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPostBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = MyPostPageAdapter(this)

        TabLayoutMediator(tab, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "강의 평가"
                1 -> tab.text = "시험 정보"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}