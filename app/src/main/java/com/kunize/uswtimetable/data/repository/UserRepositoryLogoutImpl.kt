package com.kunize.uswtimetable.data.repository

import com.kunize.uswtimetable.data.datastore.UserPreference
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.domain.repository.UserRepository
import com.kunize.uswtimetable.util.SuwikiApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryLogoutImpl @Inject constructor(
    private val userPreference: UserPreference,
) : UserRepository {
    override val isLoggedIn: Flow<Boolean>
        get() = userPreference.isLoggedIn

    override val userInfo: Flow<LoggedInUser?> = flow {
        combine(
            userPreference.userId,
            userPreference.point,
            userPreference.writtenEvaluation,
            userPreference.writtenExam,
            userPreference.viewExam,
            userPreference.email,
        ) {
            val userId = (it[0] as? String) ?: run {
                emit(null)
                return@combine
            }
            val point = (it[1] as? Int) ?: run {
                emit(null)
                return@combine
            }
            val writtenEvaluation = (it[2] as? Int) ?: run {
                emit(null)
                return@combine
            }
            val writtenExam = (it[3] as? Int) ?: run {
                emit(null)
                return@combine
            }
            val viewExam = (it[4] as? Int) ?: run {
                emit(null)
                return@combine
            }
            val email = (it[5] as? String) ?: run {
                emit(null)
                return@combine
            }
            emit(
                LoggedInUser(
                    userId = userId,
                    point = point,
                    writtenEvaluation = writtenEvaluation,
                    writtenExam = writtenExam,
                    viewExam = viewExam,
                    email = email,
                ),
            )
        }
    }

    override suspend fun login() {
    }

    override suspend fun logout() {
        userPreference.logout()
        SuwikiApplication.encryptedPrefs.saveAccessToken("") // TODO DataStore 로 마이그레이션
        SuwikiApplication.encryptedPrefs.saveRefreshToken("")
    }
}
