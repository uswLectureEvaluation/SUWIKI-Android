package com.kunize.uswtimetable.util.interceptingFilter

data class FilterChainModel(
    val filter: Filter,
    val request: FilterRequest
)
