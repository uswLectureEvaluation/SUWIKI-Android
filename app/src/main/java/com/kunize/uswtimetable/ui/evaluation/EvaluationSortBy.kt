package com.kunize.uswtimetable.ui.evaluation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kunize.uswtimetable.R

sealed class EvaluationSortBy {
    @get:DrawableRes
    abstract val imageRes: Int

    @get:StringRes
    abstract val labelRes: Int

    object Recent : EvaluationSortBy() {
        override val imageRes: Int
            get() = R.drawable.ic_fire
        override val labelRes: Int
            get() = R.string.evaluation_category_recent

        override fun toString(): String = "modifiedDate"
    }

    object Honey : EvaluationSortBy() {
        override val imageRes: Int
            get() = R.drawable.ic_honey
        override val labelRes: Int
            get() = R.string.evaluation_category_honey

        override fun toString(): String = "lectureHoneyAvg"
    }

    object Satisfaction : EvaluationSortBy() {
        override val imageRes: Int
            get() = R.drawable.ic_thumbs
        override val labelRes: Int
            get() = R.string.evaluation_category_satisfaction

        override fun toString(): String = "lectureSatisfactionAvg"
    }

    object Learnable : EvaluationSortBy() {
        override val imageRes: Int
            get() = R.drawable.ic_book
        override val labelRes: Int
            get() = R.string.evaluation_category_learnable

        override fun toString(): String = "lectureLearningAvg"
    }

    object Best : EvaluationSortBy() {
        override val imageRes: Int
            get() = R.drawable.ic_1st
        override val labelRes: Int
            get() = R.string.evaluation_category_best

        override fun toString(): String = "lectureTotalAvg"
    }

    companion object {
        fun getCategoryForPosition(position: Int) = when (position) {
            1 -> Honey
            2 -> Satisfaction
            3 -> Learnable
            4 -> Best
            else -> Recent
        }
        fun getPositionFromCategory(category: EvaluationSortBy): Int = when (category) {
            Recent -> 0
            Honey -> 1
            Satisfaction -> 2
            Learnable -> 3
            Best -> 4
        }
    }
}
