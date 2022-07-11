package com.kunize.uswtimetable.ui.mypage.mypost

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kunize.uswtimetable.ui.mypage.mypost.evaluation.MyEvaluationFragment
import com.kunize.uswtimetable.ui.mypage.mypost.examinfo.MyExamInfoFragment

class MyPostPageAdapter(fragment: MyPostFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            MyEvaluationFragment()
        } else {
            MyExamInfoFragment()
        }
    }
}
