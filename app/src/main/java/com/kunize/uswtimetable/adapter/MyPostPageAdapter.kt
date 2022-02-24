package com.kunize.uswtimetable.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kunize.uswtimetable.ui.more.MyEvaluationFragment
import com.kunize.uswtimetable.ui.more.MyExamInfoFragment
import com.kunize.uswtimetable.ui.more.MyPostFragment

class MyPostPageAdapter(fragment: MyPostFragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            MyEvaluationFragment()
        } else {
            MyExamInfoFragment()
        }
    }
}