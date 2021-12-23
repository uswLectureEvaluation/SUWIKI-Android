package com.kunize.uswtimetable

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun getToday(): String {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            return dateFormat.format(System.currentTimeMillis())
        }

        fun getYear():Int {
            val today = getToday()
            return today.split("/")[0].toInt()
        }

        fun getYearList(): List<String> {
            val today = getToday()
            var year = today.split("/")[0].toInt()
            val yearList = mutableListOf<String>()
            for (i in 1.. NUMBER_OF_YEAR) {
                yearList.add(year++.toString())
            }
            return yearList.toList()
        }

        fun getSemester(): Int {
            val today = getToday()
            val month = today.split("/")[1].toInt()
            return if (month in SEMESTER_1_START..SEMESTER_1_END) 1 else 2
        }

        private const val NUMBER_OF_YEAR = 6
        private const val SEMESTER_1_START = 3
        private const val SEMESTER_1_END = 6
    }
}