package com.suwiki.data.datasource.local

import com.suwiki.model.TimetableData

interface LocalTimetableDatasource {
    suspend fun loadLocalTimetable(): List<TimetableData>
    suspend fun insert(data: TimetableData)
    suspend fun deleteAllLocalTimetable()
    suspend fun deleteLocalTimetable(data: TimetableData)
    suspend fun updateLocalTimetable(data: TimetableData)
}
