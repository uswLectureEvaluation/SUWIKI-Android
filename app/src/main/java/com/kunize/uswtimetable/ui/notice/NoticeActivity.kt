package com.kunize.uswtimetable.ui.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityNoticeBinding

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.toolBar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        navHostFragment.childFragmentManager.addOnBackStackChangedListener {
            if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
                binding.toolBar.setNavigationOnClickListener {
                    finish()
                }
            } else {
                binding.toolBar.setNavigationOnClickListener {
                    navController.popBackStack()
                }
            }
        }
    }

}