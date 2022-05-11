package com.kunize.uswtimetable.util

class TimeStringFormatter {
    fun splitTime(
        time: String?
    ): List<String> {
        val timeListSplitByDay = mutableListOf<String>()
        // 1. 장소 분리 (미술108(월1,2),미술109(월3,4))
        val timeListSplitByLocation = time!!.split("),")
        // 2. 요일 분리 (미래417(화10,11 목10,11,12,13))
        for (timeSplitByLocation in timeListSplitByLocation) {
            val splitLocationDay = timeSplitByLocation.split("(")
            val location = splitLocationDay[0]
            val dayHourList = splitLocationDay[1].replace(")", "").split(" ")
            val dayHourMap = splitContinuousHour(dayHourList)
            dayHourMap.forEach { (day, hourList) ->
                hourList.forEach { hour ->
                    val locationDayHour = "$location($day${hour})"
                    timeListSplitByDay.add(locationDayHour)
                }
            }
        }
        return timeListSplitByDay
    }

    private fun splitContinuousHour(
        dayHourList: List<String>,
    ): MutableMap<String, List<String>> {
        val dayHourMap = mutableMapOf<String, List<String>>()
        for (dayHour in dayHourList) {
            // 3. 시간 분리 (2공학204(화1,2,3,5,6,7))
            val day = dayHour.substring(0, 1)
            val hour = dayHour.substring(1)
            val hourList = checkSeq(hour)
            dayHourMap[day] = hourList
        }
        return dayHourMap
    }

    private fun checkSeq(str: String): List<String> {
        val onlyNumList = str.split(",")
        var tempNumString = ""
        for (idx in onlyNumList.indices) {
            try {
                if ((onlyNumList[idx].toInt() - onlyNumList[idx + 1].toInt()) == -1) {
                    tempNumString = tempNumString + onlyNumList[idx] + ","
                } else {
                    tempNumString = tempNumString + onlyNumList[idx] + "!"
                }
            } catch (e: Exception) {
                tempNumString += onlyNumList[idx]
            }
        }
        return tempNumString.split("!")
    }
}