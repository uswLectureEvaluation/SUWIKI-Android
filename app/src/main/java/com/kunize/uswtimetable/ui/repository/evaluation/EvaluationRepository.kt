package com.kunize.uswtimetable.ui.repository.evaluation

import android.util.Log
import androidx.lifecycle.LiveData
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureMainDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvaluationRepository(
    private val remoteDataSource: EvaluationRemoteDataSource
) {
    suspend fun getLectureMainList(option: String): Response<LectureMainDto> {
        return remoteDataSource.getEvaluationDataSource(option)
    }
}

