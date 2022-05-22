package com.kunize.uswtimetable.ui.signup

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SignUpPageAdapter(activity: SignUpActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SignUpFragment1()
            1 -> SignUpFragment2()
            else -> throw IllegalArgumentException("Cannot create fragment at page: $position")
        }
    }
}