package com.suwiki.data.timetable.repository

import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.data.timetable.datasource.LocalOpenLectureDatasource
import com.suwiki.data.timetable.datasource.RemoteOpenLectureDataSource
import com.suwiki.domain.timetable.repository.OpenLectureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpenLectureRepositoryImpl @Inject constructor(
  private val remoteOpenLectureDataSource: RemoteOpenLectureDataSource,
  private val localOpenLectureDataSource: LocalOpenLectureDatasource,
) : OpenLectureRepository {
  override suspend fun getOpenLectureList(): Flow<List<OpenLecture>> = flow {
    val localOpenLectureList = localOpenLectureDataSource.getOpenLectureList()
    emit(localOpenLectureList)

    val localVersion = localOpenLectureDataSource.getOpenLectureListVersion().first()
    val remoteVersion = remoteOpenLectureDataSource.getOpenLectureListVersion()

    if (remoteVersion > localVersion) {
      val remoteOpenLectureList = remoteOpenLectureDataSource.getOpenLectureList()
      emit(remoteOpenLectureList)

      with(localOpenLectureDataSource) {
        deleteAllOpenLecture()
        remoteOpenLectureList.forEach {
          insertOpenLecture(it)
        }
        setOpenLectureListVersion(remoteVersion)
      }
    }
  }.flowOn(Dispatchers.IO)
}
