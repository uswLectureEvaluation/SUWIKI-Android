package com.suwiki.data.timetable.repository

import com.suwiki.core.model.exception.TimetableCellOverlapException
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.data.timetable.datasource.LocalTimetableDataSource
import com.suwiki.domain.timetable.repository.TimetableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

  override suspend fun insertTimetable(data: Timetable) {
    localTimetableDataSource.insertTimetable(data)
  }

  override suspend fun updateTimetable(
    createTime: Long,
    year: String,
    semester: String,
    name: String,
  ) = with(localTimetableDataSource) {
    val timetable = getTimetable(createTime)
    updateTimetable(
      timetable!!.copy(
        year = year,
        semester = semester,
        name = name,
      ),
    )
  }


  override suspend fun insertTimetableCell(cellList: List<TimetableCell>) {
    val timetable = getMainTimetable()!!
    checkCellOverlap(cellList, timetable.cellList)
    localTimetableDataSource.updateTimetable(
      timetable.copy(
        cellList = timetable.cellList.plus(cellList)
      )
    )
  }

  override suspend fun deleteTimetableCell(cell: TimetableCell) {
    val timetable = getMainTimetable()!!
    localTimetableDataSource.updateTimetable(
      timetable.copy(
        cellList = timetable.cellList.minus(cell)
      )
    )
  }

  override suspend fun updateTimetableCell(cell: TimetableCell) {
    val timetable = getMainTimetable()!!
    val oldCell = timetable.cellList.find { it.id == cell.id }!!
    val tempCellList = timetable.cellList.minus(oldCell)
    checkCellOverlap(listOf(cell), tempCellList)
    localTimetableDataSource.updateTimetable(
      timetable.copy(
        cellList = tempCellList.plus(cell)
      )
    )
  }

  private suspend fun getMainTimetable() = with(localTimetableDataSource) {
    val createTime = getMainTimetableCreateTime().first()
    getTimetable(createTime)
  }

  private fun checkCellOverlap(
    toInsertCellList: List<TimetableCell>,
    currentCellList: List<TimetableCell>,
  ) {
    toInsertCellList.forEach { toInsertCell ->
      currentCellList
        .filter { it.day == toInsertCell.day }
        .forEach { sameDayCell ->
          val isOverlap = toInsertCell.startPeriod in sameDayCell.startPeriod..sameDayCell.endPeriod ||
            toInsertCell.endPeriod in sameDayCell.startPeriod..sameDayCell.endPeriod

          if (isOverlap) {
            throw TimetableCellOverlapException(
              name = sameDayCell.name,
              startPeriod = sameDayCell.startPeriod,
              endPeriod = sameDayCell.endPeriod,
            )
          }
        }
    }
  }
}
