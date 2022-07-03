package com.kunize.uswtimetable.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navigationFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navigationFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        binding.bottomNav.setOnItemReselectedListener { }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home || destination.id == R.id.navigation_evaluation || destination.id == R.id.navigation_my_page)
                binding.bottomNav.visibility = View.VISIBLE
            else
                binding.bottomNav.visibility = View.GONE
        }

        initAdView()
    }

    private fun initAdView() {
        MobileAds.initialize(this) {}
        loadBanner()
    }

    private fun loadBanner() {
        val adRequest = AdRequest
            .Builder()
            .build()

        binding.adBottom.loadAd(adRequest)
    }
}
