package com.suwiki.data.datasource.local

import com.suwiki.core.model.timetable.TimetableData

interface LocalTimetableProviderDatasource {
  suspend fun getLocalTimetable(): List<TimetableData>
}
