package com.kunize.uswtimetable.data.datasource

import com.kunize.uswtimetable.data.model.ApiResult
import com.kunize.uswtimetable.data.model.onFailure
import com.kunize.uswtimetable.data.model.onSuccess
import com.kunize.uswtimetable.dataclass.LoginIdPassword
import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.domain.model.Result
import com.kunize.uswtimetable.domain.model.SuwikiError
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.util.SuwikiApplication

class LoginDataSource(
    @OtherApiService private val apiService: IRetrofit,
) {
    /**
     * 서버에 로그인 후 토큰 저장
     * */
    suspend fun login(id: String, password: String): Result<Unit> {
        apiService.login(LoginIdPassword(id, password))
            .onSuccess { tokens ->
                SuwikiApplication.encryptedPrefs.saveAccessToken(tokens.accessToken)
                SuwikiApplication.encryptedPrefs.saveRefreshToken(tokens.refreshToken)
                return Result.Success(Unit)
            }
            .onFailure { error ->
                return when (error) {
                    is ApiResult.Failure.HttpError -> Result.Failure(
                        SuwikiError.HttpError(error.code, error.message, error.body),
                    )

                    is ApiResult.Failure.NetworkError -> Result.Failure(
                        SuwikiError.NetworkError,
                    )

                    is ApiResult.Failure.UnknownError -> Result.Failure(
                        SuwikiError.CustomError(-1, error.throwable.toString()),
                    )

                    is ApiResult.Failure.CustomError -> Result.Failure(error.error)
                }
            }
        throw RuntimeException("도달할 수 없는 라인")
    }
}
