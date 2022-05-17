package com.kunize.uswtimetable.ui.common

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("group", "content")
fun applyExpandOrFoldView(view: View, group: ConstraintLayout, content: TextView) {
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