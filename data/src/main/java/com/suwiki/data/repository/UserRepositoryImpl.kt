package com.suwiki.data.repository

import com.suwiki.data.datasource.local.LocalAuthDataSource
import com.suwiki.data.datasource.local.LocalUserDataSource
import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.domain.model.LoggedInUser
import com.suwiki.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authDataSource: LocalAuthDataSource,
    private val localUserDataSource: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource,
) : UserRepository {

    override val isLoggedIn: Flow<Boolean>
        get() = localUserDataSource.isLoggedIn

    override val userInfo: Flow<LoggedInUser?>
        get() = combine(
            localUserDataSource.userId,
            localUserDataSource.point,
            localUserDataSource.writtenEvaluation,
            localUserDataSource.writtenExam,
            localUserDataSource.viewExam,
            localUserDataSource.email,
        ) {
            val userId = (it[0] as? String) ?: return@combine null
            val point = (it[1] as? Int) ?: return@combine null
            val writtenEvaluation = (it[2] as? Int) ?: return@combine null
            val writtenExam = (it[3] as? Int) ?: return@combine null
            val viewExam = (it[4] as? Int) ?: return@combine null
            val email = (it[5] as? String) ?: return@combine null
            LoggedInUser(
                userId = userId,
                point = point,
                writtenEvaluation = writtenEvaluation,
                writtenExam = writtenExam,
                viewExam = viewExam,
                email = email,
            )
        }

    override suspend fun setUserInfo(): Boolean {
        return false
//        remoteUserDataSource.getUserData().onSuccess { user ->
//            return@set
//        }
//        return try {
//
//
//            val response = remoteUserDataSource.getUserData()
//            return if (response.isSuccessful) {
//                response.body()?.apply {
//                    userPreference.login(
//                        userId = userId,
//                        point = point,
//                        writtenEvaluation = writtenEvaluation,
//                        writtenExam = writtenExam,
//                        viewExam = viewExam,
//                        email = email,
//                    )
//                } ?: return@withContext false
//                true
//            } else {
//                false
//            }
//        } catch (_: Exception) {
//            false
//        }
    }
}
