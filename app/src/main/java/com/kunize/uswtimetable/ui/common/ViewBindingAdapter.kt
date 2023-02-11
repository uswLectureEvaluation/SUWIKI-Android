package com.kunize.uswtimetable.ui.common

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.kunize.uswtimetable.R

@BindingAdapter("group", "detailOrShortButton")
fun applyExpandOrFoldLayout(view: View, group: ConstraintLayout, content: TextView) {
    view.setOnClickListener {
        if (group.isVisible) {
            group.visibility = View.GONE
            content.text = content.context.resources.getString(R.string.see_detail)
        } else {
            group.visibility = View.VISIBLE
            content.text = content.context.resources.getString(R.string.see_short)
        }
    }
}

@BindingAdapter("expandable")
fun applyExpandOrFoldText(view: View, content: TextView) {
    view.setOnClickListener {
        content.maxLines = if (content.maxLines == 2) 100 else 2
    }
}

@BindingAdapter("onThrottleClick", "duration", requireAll = false)
fun applyThrottleClick(view: View, listener: View.OnClickListener, interval: Long? = 300L) {
    val throttleListener = interval?.let { time ->
        OnThrottleClickListener(listener, time)
    } ?: OnThrottleClickListener(listener)
    view.setOnClickListener(throttleListener)
}
