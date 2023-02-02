package com.kunize.uswtimetable.util.interceptingFilter

import android.util.Log

class FilterExecutor {
    private val filterChainModels = mutableListOf<FilterChainModel>()

    fun addFilter(filterChainModels: List<FilterChainModel>): FilterExecutor {
        this.filterChainModels.addAll(filterChainModels)
        return this
    }

    operator fun invoke(): FilterState {
        return try {
            for (filterChinModel in filterChainModels) {
                with(filterChinModel) {
                    val result = filter(request)
                    if (result != FilterState.Validate) return result
                }
            }
            FilterState.Validate
        } catch (e: Exception) {
            Log.e("TimetableError", e.stackTraceToString())
            UnknownFilterFailState
        }
    }
}