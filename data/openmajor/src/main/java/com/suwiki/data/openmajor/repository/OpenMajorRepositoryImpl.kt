package com.suwiki.data.openmajor.repository

import com.suwiki.core.model.openmajor.OpenMajor
import com.suwiki.data.openmajor.datasource.LocalOpenMajorDataSource
import com.suwiki.data.openmajor.datasource.RemoteOpenMajorDataSource
import com.suwiki.domain.openmajor.repository.OpenMajorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class OpenMajorRepositoryImpl @Inject constructor(
    private val localOpenMajorDataSource: LocalOpenMajorDataSource,
    private val remoteOpenMajorDataSource: RemoteOpenMajorDataSource,
) : OpenMajorRepository {
    override suspend fun getOpenMajorList(): Flow<List<String>> = flow {
        Timber.d("local Major List emit = ${localOpenMajorDataSource.getLocalOpenMajorList().map { it.name }}")
        emit(localOpenMajorDataSource.getLocalOpenMajorList().map { it.name })

        val localVersion = localOpenMajorDataSource.getLocalOpenMajorVersion().firstOrNull() ?: 0f
        val remoteVersion = remoteOpenMajorDataSource.getOpenMajorVersion()

        Timber.d("localVersion = $localVersion, remoteVersion = $remoteVersion")

        if (remoteVersion > localVersion) {
            val remoteOpenMajorList = remoteOpenMajorDataSource.getOpenMajorList()
            Timber.d("local Major List emit = $remoteOpenMajorList")

            emit(remoteOpenMajorList)

            with(localOpenMajorDataSource) {
                deleteAllOpenMajors()
                saveAllOpenMajors(
                    remoteOpenMajorList.mapIndexed { index, major ->
                        OpenMajor(id = index, name = major)
                    },
                )
                setLocalOpenMajorVersion(remoteVersion)
            }
        }
    }

    override suspend fun bookmarkMajor(majorName: String) {
        remoteOpenMajorDataSource.bookmarkMajor(majorName)
    }

    override suspend fun removeBookmarkMajor(majorName: String) {
        remoteOpenMajorDataSource.removeBookmarkMajor(majorName)
    }
}
