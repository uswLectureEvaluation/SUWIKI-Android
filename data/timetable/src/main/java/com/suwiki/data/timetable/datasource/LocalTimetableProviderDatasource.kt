package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.Timetable

interface LocalTimetableProviderDatasource {
  suspend fun getLocalTimetable(): List<Timetable>
}
