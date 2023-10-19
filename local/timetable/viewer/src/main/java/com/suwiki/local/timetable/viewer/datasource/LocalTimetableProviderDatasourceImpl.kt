package com.suwiki.local.timetable.viewer.datasource

import com.suwiki.core.database.TimetableDatabase
import com.suwiki.core.model.timetable.TimetableData
import com.suwiki.data.timetable.viewer.datasource.LocalTimetableProviderDatasource
import com.suwiki.local.timetable.viewer.converter.toModel
import javax.inject.Inject

class LocalTimetableProviderDatasourceImpl @Inject constructor(
    private val timetableDatabase: TimetableDatabase,
) : LocalTimetableProviderDatasource {

    override suspend fun getLocalTimetable(): List<TimetableData> {
        return timetableDatabase.timetableDao().getAll().map { it.toModel() }
    }
}
