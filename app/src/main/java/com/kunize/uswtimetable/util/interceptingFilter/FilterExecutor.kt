package com.kunize.uswtimetable.util.interceptingFilter

import android.util.Log

class FilterExecutor {
    private val filterChainModels = mutableListOf<FilterChainModel>()

    fun addFilter(filterChainModel: FilterChainModel): FilterExecutor {
        filterChainModels.add(filterChainModel)
        return this
    }

    fun execute(): FilterState {
        return try {
            for (filterChinModel in filterChainModels) {
                with(filterChinModel) {
                    val result = filter.execute(request)
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