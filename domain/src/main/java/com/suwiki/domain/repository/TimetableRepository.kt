package com.suwiki.domain.repository

import com.suwiki.domain.model.TimetableData

interface TimetableRepository {
    suspend fun loadRemoteTimetable(): List<TimetableData>
    suspend fun loadLocalTimetable(): List<TimetableData>
    suspend fun insert(data: TimetableData)
    suspend fun deleteAllLocalTimetable()
    suspend fun deleteLocalTimetable(data: TimetableData)
    suspend fun updateLocalTimetable(data: TimetableData)
}
