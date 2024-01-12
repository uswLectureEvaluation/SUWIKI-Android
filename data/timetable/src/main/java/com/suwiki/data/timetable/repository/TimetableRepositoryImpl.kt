package com.suwiki.data.timetable.repository

import com.suwiki.core.model.timetable.Timetable
import com.suwiki.data.timetable.datasource.LocalTimetableDataSource
import com.suwiki.domain.timetable.repository.TimetableRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimetableRepositoryImpl @Inject constructor(
  private val localTimetableDataSource: LocalTimetableDataSource,
) : TimetableRepository {
  override suspend fun getAllTimetable(): List<Timetable> {
    return localTimetableDataSource.getAllTimetable()
  }

  override suspend fun getTimetable(createTime: Long): Timetable? {
    return localTimetableDataSource.getTimetable(createTime)
  }

  override suspend fun setMainTimetableCreateTime(createTime: Long) {
    localTimetableDataSource.setMainTimetableCreateTime(createTime)
  }

  override suspend fun getMainTimetableCreateTime(): Flow<Long> {
    return localTimetableDataSource.getMainTimetableCreateTime()
  }

  override suspend fun deleteTimetable(data: Timetable) {
    localTimetableDataSource.deleteTimetable(data)
  }

  override suspend fun updateTimetable(data: Timetable) {
    localTimetableDataSource.updateTimetable(data)
  }

  override suspend fun insertTimetable(data: Timetable) {
    localTimetableDataSource.insertTimetable(data)
  }
}
