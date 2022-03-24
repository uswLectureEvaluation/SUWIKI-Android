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
    private var evaluationResponse = ArrayList<EvaluationData?>()
    private val retrofit: IRetrofit by lazy { ApiClient.getClientWithNoToken().create(IRetrofit::class.java) }

    override suspend fun getEvaluationDataSource(option: String): Response<LectureMainDto> {
        Log.d("apiTest","${retrofit.getLectureMainList(option)}")
        return retrofit.getLectureMainList(option)

//        withContext(CoroutineScope(IO).coroutineContext) {
//            evaluationResponse = callLectureMainList.execute().body()?.convertToEvaluationData() ?: arrayListOf()
////            callLectureMainList.enqueue(object : Callback<LectureMainDto> {
////                override fun onResponse(
////                    call: Call<LectureMainDto>,
////                    response: Response<LectureMainDto>
////                ) {
////                    evaluationResponse = response.body()?.convertToEvaluationData() ?: arrayListOf()
////                    Log.d("apiTest", "onResponse : $evaluationResponse")
////                }
////
////                override fun onFailure(call: Call<LectureMainDto>, t: Throwable) {
////                    Log.d("apiTest", "$t")
////                }
////            })
//        }
//        Log.d("apiTest","리턴 값 : $evaluationResponse")
//        return evaluationResponse
    }
}