package com.kunize.uswtimetable.util.interceptingFilter

interface Filter {
    operator fun invoke(request: FilterRequest): FilterState
}