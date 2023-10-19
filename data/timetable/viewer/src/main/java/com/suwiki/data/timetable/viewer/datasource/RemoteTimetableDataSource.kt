package com.suwiki.data.timetable.viewer.datasource

import com.suwiki.core.model.timetable.TimetableData

interface RemoteTimetableDataSource {
    suspend fun fetchRemoteTimetableVersion(): Long
    suspend fun fetchRemoteTimetable(): List<TimetableData>
}