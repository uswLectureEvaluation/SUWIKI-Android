package com.suwiki.data.datasource.local

import com.suwiki.model.TimetableData

interface LocalTimetableProviderDatasource {
    suspend fun getLocalTimetable(): List<TimetableData>
}
