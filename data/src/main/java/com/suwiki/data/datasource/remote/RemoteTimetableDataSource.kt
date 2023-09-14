package com.suwiki.data.datasource.remote

import com.suwiki.model.TimetableData

interface RemoteTimetableDataSource {
    suspend fun fetchRemoteTimetableVersion(): Long
    suspend fun fetchRemoteTimetable(): List<TimetableData>
}
