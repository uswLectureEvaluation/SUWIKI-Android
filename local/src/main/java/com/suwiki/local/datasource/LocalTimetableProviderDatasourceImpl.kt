package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalTimetableProviderDatasource
import com.suwiki.local.db.TimetableDatabase
import com.suwiki.local.model.toDomain
import com.suwiki.model.TimetableData
import javax.inject.Inject

class LocalTimetableProviderDatasourceImpl @Inject constructor(
    private val timetableDatabase: TimetableDatabase,
) : LocalTimetableProviderDatasource {

    override suspend fun getLocalTimetable(): List<TimetableData> {
        return timetableDatabase.timetableDao().getAll().map { it.toDomain() }
    }
}
