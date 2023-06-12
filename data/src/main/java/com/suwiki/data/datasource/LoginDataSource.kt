package com.suwiki.data.datasource

import com.suwiki.data.network.ApiResult
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.onFailure
import com.suwiki.data.network.onSuccess
import com.suwiki.domain.di.OtherApiService
import com.suwiki.domain.model.Result
import com.suwiki.domain.model.SuwikiError
import com.suwiki.domain.repository.AuthRepository

class LoginDataSource(
    @OtherApiService private val apiService: ApiService,
    private val authRepository: AuthRepository,
) {
    /**
     * 서버에 로그인 후 토큰 저장
     * */
    suspend fun login(id: String, password: String): Result<Unit> {
        apiService.login(id, password)
            .onSuccess { tokens ->
                authRepository.saveTokens(tokens.refreshToken, tokens.accessToken)
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
