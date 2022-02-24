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
import com.google.android.material.tabs.TabLayout
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentMyPostBinding

class MyPostFragment: Fragment() {
    private var _binding: FragmentMyPostBinding? = null
    private val binding get() = _binding!!
    private val tab by lazy { binding.tabLayout }
    private val myEvaluationFragment by lazy { MyEvaluationFragment() }
    private val myExamInfoFragment by lazy { MyExamInfoFragment() }

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

        tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        childFragmentManager.beginTransaction().replace(R.id.my_post_container, myEvaluationFragment, "my_evaluation").commit()
                    }
                    1 -> {
                        childFragmentManager.beginTransaction().replace(R.id.my_post_container, myExamInfoFragment, "my_exam_info").commit()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}