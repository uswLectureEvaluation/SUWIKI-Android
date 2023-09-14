package com.suwiki.data.datasource.local

import com.suwiki.model.TimetableData

interface LocalTimetableStorageDatasource {
    suspend fun insert(data: TimetableData)
    suspend fun deleteAllLocalTimetable()
    suspend fun deleteLocalTimetable(data: TimetableData)
    suspend fun updateLocalTimetable(data: TimetableData)
}
