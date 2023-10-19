package com.suwiki.data.timetable.viewer.datasource

import com.suwiki.core.model.timetable.TimetableData

interface LocalTimetableProviderDatasource {
    suspend fun getLocalTimetable(): List<TimetableData>
}
