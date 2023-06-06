package com.kunize.uswtimetable.data.repository

import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.domain.repository.AuthRepository
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.util.SuwikiApplication
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @OtherApiService private val apiService: IRetrofit,
) : AuthRepository {
    override fun requestRefreshToken(token: String): Boolean {
        val response = apiService.requestRefresh(token).execute()

        return if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                SuwikiApplication.encryptedPrefs.saveAccessToken(it.accessToken)
                SuwikiApplication.encryptedPrefs.saveRefreshToken(it.refreshToken)
                true
            } ?: run {
                false
            }
        } else {
            false
        }
    }
}
