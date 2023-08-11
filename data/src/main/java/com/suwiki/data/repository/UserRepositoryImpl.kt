package com.suwiki.data.repository

import com.suwiki.data.datasource.local.LocalUserDataSource
import com.suwiki.data.datasource.remote.RemoteUserDataSource
import com.suwiki.domain.model.LoggedInUser
import com.suwiki.domain.repository.UserRepository
import com.suwiki.model.Result
import com.suwiki.model.SuwikiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource,
) : UserRepository {

    override val isLoggedIn: Flow<Boolean>
        get() = localUserDataSource.isLoggedIn

    override val userInfo: Flow<Result<LoggedInUser>>
        get() = combine(
            localUserDataSource.userId,
            localUserDataSource.point,
            localUserDataSource.writtenEvaluation,
            localUserDataSource.writtenExam,
            localUserDataSource.viewExam,
            localUserDataSource.email,
        ) { userInfo ->
            if (isLoggedIn.firstOrNull() != true) {
                return@combine Result.Failure(SuwikiError.TokenExpired)
            }

            val userId = (userInfo[0] as? String) ?: return@combine getRemoteUserInfoAndSetLocal()
            val point = (userInfo[1] as? Int) ?: return@combine getRemoteUserInfoAndSetLocal()
            val writtenEvaluation =
                (userInfo[2] as? Int) ?: return@combine getRemoteUserInfoAndSetLocal()
            val writtenExam = (userInfo[3] as? Int) ?: return@combine getRemoteUserInfoAndSetLocal()
            val viewExam = (userInfo[4] as? Int) ?: return@combine getRemoteUserInfoAndSetLocal()
            val email = (userInfo[5] as? String) ?: return@combine getRemoteUserInfoAndSetLocal()

            val loggedInUser = LoggedInUser(
                userId = userId,
                point = point,
                writtenEvaluation = writtenEvaluation,
                writtenExam = writtenExam,
                viewExam = viewExam,
                email = email,
            )

            Result.Success(loggedInUser)
        }

    private suspend fun getRemoteUserInfoAndSetLocal(): Result<LoggedInUser> {
        val result = remoteUserDataSource.getUserData().map { user ->
            LoggedInUser(
                userId = user.userId,
                point = user.point,
                writtenEvaluation = user.writtenEvaluation,
                writtenExam = user.writtenExam,
                viewExam = user.viewExam,
                email = user.email,
            )
        }

        result.getOrNull()?.let {
            localUserDataSource.login(
                userId = it.userId,
                point = it.point,
                writtenEvaluation = it.writtenEvaluation,
                writtenExam = it.writtenExam,
                viewExam = it.viewExam,
                email = it.email,
            )
        }

        return result
    }
}
