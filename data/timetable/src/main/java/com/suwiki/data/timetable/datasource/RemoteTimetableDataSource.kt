package com.suwiki.data.timetable.datasource

import com.suwiki.core.model.timetable.Timetable

interface RemoteTimetableDataSource {
  suspend fun fetchRemoteTimetableVersion(): Long
  suspend fun fetchRemoteTimetable(): List<Timetable>
}
