package com.suwiki.data.repository

import com.suwiki.data.model.UserPreference
import com.suwiki.data.network.ApiService
import com.suwiki.domain.di.IoDispatcher
import com.suwiki.domain.di.OtherApiService
import com.suwiki.domain.model.LoggedInUser
import com.suwiki.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @OtherApiService private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userPreference: UserPreference,
) : UserRepository {

    override val isLoggedIn: Flow<Boolean>
        get() = userPreference.isLoggedIn

    override val userInfo: Flow<LoggedInUser?>
        get() = combine(
            userPreference.userId,
            userPreference.point,
            userPreference.writtenEvaluation,
            userPreference.writtenExam,
            userPreference.viewExam,
            userPreference.email,
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
        return try {
            withContext(ioDispatcher) {
                val response = apiService.getUserData()
                return@withContext if (response.isSuccessful) {
                    response.body()?.apply {
                        userPreference.login(
                            userId = userId,
                            point = point,
                            writtenEvaluation = writtenEvaluation,
                            writtenExam = writtenExam,
                            viewExam = viewExam,
                            email = email,
                        )
                    } ?: return@withContext false
                    true
                } else {
                    false
                }
            }
        } catch (_: Exception) {
            false
        }
    }
}
