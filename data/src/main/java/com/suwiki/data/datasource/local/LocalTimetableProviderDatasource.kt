package com.suwiki.data.datasource.local

import com.suwiki.core.model.TimetableData

interface LocalTimetableProviderDatasource {
    suspend fun getLocalTimetable(): List<TimetableData>
}
