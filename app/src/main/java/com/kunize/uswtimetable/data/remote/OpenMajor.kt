package com.kunize.uswtimetable.data.remote

import com.kunize.uswtimetable.data.local.OpenMajorData

data class OpenMajorVersion(
    val version: Float
)

data class OpenMajorList(
    val data: List<String>
) {
    fun convertToOpenMajorData(): MutableList<OpenMajorData> {
        val tmp = mutableListOf<OpenMajorData>()
        data.forEach {
            tmp.add(OpenMajorData(it))
        }
        return tmp
    }
}

data class MajorType(
    val majorType: String
)