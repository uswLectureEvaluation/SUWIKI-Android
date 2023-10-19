package com.suwiki.data.timetable.editor.datasource

import com.suwiki.core.model.timetable.TimetableData

interface LocalTimetableStorageDatasource {
    suspend fun insert(data: TimetableData)
    suspend fun deleteAllLocalTimetable()
    suspend fun deleteLocalTimetable(data: TimetableData)
    suspend fun updateLocalTimetable(data: TimetableData)
}
