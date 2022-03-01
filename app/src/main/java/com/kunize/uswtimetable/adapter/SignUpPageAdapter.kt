package com.kunize.uswtimetable.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kunize.uswtimetable.signup.SignUpActivity
import com.kunize.uswtimetable.signup.SignUpFragment1
import com.kunize.uswtimetable.signup.SignUpFragment2
import com.kunize.uswtimetable.signup.SignUpFragment3
import com.kunize.uswtimetable.ui.more.MyPostFragment

class SignUpPageAdapter(activity: SignUpActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SignUpFragment1()
            1 -> SignUpFragment2()
            else -> SignUpFragment3()
        }
    }
}