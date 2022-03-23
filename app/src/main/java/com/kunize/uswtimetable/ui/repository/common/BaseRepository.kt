package com.kunize.uswtimetable.ui.repository.common

import android.util.Log
import com.kunize.uswtimetable.util.Constants.TAG
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T: Any>safeApiCall(call: suspend() -> Response<T>, error: String): T? {
        val result = apiCall(call, error)
        var output: T? = null

        when (result) {
            is Output.Success ->
                output = result.output
            is Output.Error ->
                Log.e(TAG, "BaseRepository - safeApiCall() called / error: $error, ${result.exception}")
        }
        return output
    }

    private suspend fun <T : Any> apiCall(
        call: suspend () -> Response<T>,
        error: String
    ): Output<T> {
        val response = call.invoke()

        return if (response.isSuccessful)
            Output.Success(response.body()!!)
        else
            Output.Error(IOException("error: $error"))
    }
}