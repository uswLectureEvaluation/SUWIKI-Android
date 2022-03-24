package com.kunize.uswtimetable.ui.repository.common

import android.util.Log
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.Result
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T: Any>safeApiCall(call: suspend() -> Response<T>, error: String): T? {
        val result = apiCall(call, error)
        var output: T? = null

        when (result) {
            is Result.Success ->
                output = result.data
            is Result.Error ->
                Log.e(TAG, "BaseRepository - safeApiCall() called / error: $error, ${result.exception}")
        }
        return output
    }

    private suspend fun <T : Any> apiCall(
        call: suspend () -> Response<T>,
        error: String
    ): Result<T> {
        val response = call.invoke()

        return if (response.isSuccessful)
            Result.Success(response.body()!!)
        else
            Result.Error(IOException("error: $error"))
    }
}