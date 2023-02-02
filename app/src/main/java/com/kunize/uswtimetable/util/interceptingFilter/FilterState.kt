package com.kunize.uswtimetable.util.interceptingFilter

interface FilterState {
    object Validate: FilterState
    object Nothing: FilterState
}