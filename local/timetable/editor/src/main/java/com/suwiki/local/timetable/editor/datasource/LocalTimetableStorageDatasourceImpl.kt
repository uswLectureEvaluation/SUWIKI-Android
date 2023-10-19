package com.suwiki.local.timetable.editor.datasource

import com.suwiki.core.database.TimetableDatabase
import com.suwiki.core.model.timetable.TimetableData
import com.suwiki.data.timetable.editor.datasource.LocalTimetableStorageDatasource
import com.suwiki.local.timetable.editor.converter.toEntity
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
