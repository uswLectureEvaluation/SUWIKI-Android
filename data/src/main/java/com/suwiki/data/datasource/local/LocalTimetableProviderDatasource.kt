package com.suwiki.data.datasource.local

import com.suwiki.core.model.timetable.OpenLecture

interface LocalTimetableProviderDatasource {
  suspend fun getLocalTimetable(): List<OpenLecture>
}
