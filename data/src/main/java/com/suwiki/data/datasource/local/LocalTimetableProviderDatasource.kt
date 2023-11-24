package com.suwiki.data.datasource.local

import com.suwiki.core.model.timetable.Timetable

interface LocalTimetableProviderDatasource {
  suspend fun getLocalTimetable(): List<Timetable>
}
