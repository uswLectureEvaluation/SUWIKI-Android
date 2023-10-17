package com.suwiki.timetable.editor.datasource

import com.suwiki.data.datasource.local.LocalTimetableStorageDatasource
import com.suwiki.database.TimetableDatabase
import com.suwiki.model.TimetableData
import com.suwiki.timetable.editor.converter.toEntity
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
