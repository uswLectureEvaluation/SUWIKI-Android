package com.kunize.uswtimetable.ui.repository.evaluation

import androidx.lifecycle.LiveData
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureMainDto
import retrofit2.Call
import retrofit2.Response

interface EvaluationDataSource {
    suspend fun getEvaluationDataSource(option: String, page: Int = 1): Response<LectureMainDto>
}