package com.suwiki.timetable.viewer.datasource

import com.suwiki.data.datasource.local.LocalTimetableProviderDatasource
import com.suwiki.core.database.TimetableDatabase
import com.suwiki.core.model.TimetableData
import com.suwiki.timetable.viewer.converter.toModel
import javax.inject.Inject

class LocalTimetableProviderDatasourceImpl @Inject constructor(
    private val timetableDatabase: TimetableDatabase,
) : LocalTimetableProviderDatasource {

    override suspend fun getLocalTimetable(): List<TimetableData> {
        return timetableDatabase.timetableDao().getAll().map { it.toModel() }
    }
}
