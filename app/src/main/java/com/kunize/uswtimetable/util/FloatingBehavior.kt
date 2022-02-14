package com.kunize.uswtimetable.util

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlin.math.min
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.kunize.uswtimetable.util.Constants.TAG


class ButtonBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<Button>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: Button,
        dependency: View
    ): Boolean {
        return dependency is SnackbarLayout
    }

    override fun onDependentViewRemoved(
        parent: CoordinatorLayout,
        child: Button,
        dependency: View
    ) {
        super.onDependentViewRemoved(parent, child, dependency)
        child.translationY = 0F
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: Button,
        dependency: View
    ): Boolean {
        val translationY = min(0F, dependency.translationY - dependency.height)
        child.translationY = translationY

        return false
    }
}