package com.kunize.uswtimetable.util.interceptingFilter

interface Filter {
    fun execute(request: FilterRequest): FilterState
}