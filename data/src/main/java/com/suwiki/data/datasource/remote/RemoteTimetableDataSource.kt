package com.suwiki.data.datasource.remote

import com.suwiki.core.model.timetable.OpenLecture

interface RemoteTimetableDataSource {
  suspend fun fetchRemoteTimetableVersion(): Long
  suspend fun fetchRemoteTimetable(): List<OpenLecture>
}
