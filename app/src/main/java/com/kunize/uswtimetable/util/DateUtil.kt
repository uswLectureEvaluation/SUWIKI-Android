package com.kunize.uswtimetable.util

import com.kunize.uswtimetable.util.Constants.NUMBER_OF_YEAR
import com.kunize.uswtimetable.util.Constants.SEMESTER_1_END
import com.kunize.uswtimetable.util.Constants.SEMESTER_1_START
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        private fun getToday(): String {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            return dateFormat.format(System.currentTimeMillis())
        }

        fun getYear():Int {
            val today = getToday()
            return today.split("/")[0].toInt()
        }

        fun getYearList(): MutableList<String> {
            val today = getToday()
            var year = today.split("/")[0].toInt()
            val yearList = mutableListOf<String>()
            for(i in NUMBER_OF_YEAR / 2 downTo 1) {
                yearList.add((year - i).toString())
            }
            for (i in 1.. NUMBER_OF_YEAR/2) {
                yearList.add(year++.toString())
            }
            return yearList
        }

        fun getSemester(): Int {
            val today = getToday()
            val month = today.split("/")[1].toInt()
            return if (month in SEMESTER_1_START..SEMESTER_1_END) 1 else 2
        }
    }
}