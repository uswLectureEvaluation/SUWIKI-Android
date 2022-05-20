package com.kunize.uswtimetable.ui.common

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("group", "content")
fun applyExpandOrFoldLayout(view: View, group: ConstraintLayout, content: TextView) {
    view.setOnClickListener {
        if (group.isVisible) {
            group.visibility = View.GONE
            content.maxLines = 2
        } else {
            group.visibility = View.VISIBLE
            content.maxLines = 100
        }
    }
}

@BindingAdapter("expandable")
fun applyExpandOrFoldText(view: View, content: TextView) {
    view.setOnClickListener {
        content.maxLines = if (content.maxLines == 2) 100 else 2
    }
}