package com.kunize.uswtimetable.ui.common

import android.util.Log
import android.view.View

class OnThrottleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 300L,
) : View.OnClickListener {

    private var clicked = false
    override fun onClick(v: View?) {
        if (!clicked) {
            clicked = true
            v?.run {
                postDelayed(
                    { clicked = false },
                    interval,
                )
                clickListener.onClick(v)
            }
        } else {
            Log.d("로그", "OnThrottleClickListener_onClick: miss!!")
        }
    }
}
