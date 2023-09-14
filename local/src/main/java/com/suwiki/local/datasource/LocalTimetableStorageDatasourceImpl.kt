package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalTimetableStorageDatasource
import com.suwiki.local.db.TimetableDatabase
import com.suwiki.local.model.toEntity
import com.suwiki.model.TimetableData
import javax.inject.Inject

class LocalTimetableStorageDatasourceImpl @Inject constructor(
    private val timetableDatabase: TimetableDatabase,
) : LocalTimetableStorageDatasource {

    override suspend fun insert(data: TimetableData) {
        timetableDatabase.timetableDao().insert(data.toEntity())
    }

    override suspend fun deleteAllLocalTimetable() {
        timetableDatabase.timetableDao().deleteAll()
    }

    override suspend fun deleteLocalTimetable(data: TimetableData) {
        timetableDatabase.timetableDao().delete(data.toEntity())
    }

    override suspend fun updateLocalTimetable(data: TimetableData) {
        timetableDatabase.timetableDao().update(data.toEntity())
    }
}
