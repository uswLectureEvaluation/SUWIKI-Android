package com.kunize.uswtimetable.ui.repository.evaluation

import android.util.Log
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureMainDto
import com.kunize.uswtimetable.retrofit.ApiClient
import com.kunize.uswtimetable.retrofit.IRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvaluationRemoteDataSource(): EvaluationDataSource {
    private val retrofit: IRetrofit by lazy { ApiClient.getClientWithNoToken().create(IRetrofit::class.java) }

    override suspend fun getEvaluationDataSource(option: String, page: Int): Response<LectureMainDto> {
        return retrofit.getLectureMainList(option)
    }
}