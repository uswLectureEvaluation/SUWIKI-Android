package com.kunize.uswtimetable.ui.write

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.LectureEvaluationEditDto
import com.kunize.uswtimetable.dataclass.LectureEvaluationPostDto
import com.kunize.uswtimetable.dataclass.LectureExamPostDto
import com.kunize.uswtimetable.ui.repository.write.WriteRepository
import kotlinx.coroutines.withContext
import retrofit2.Response

class WriteViewModel(private val writeRepository: WriteRepository) : ViewModel() {
    private val _honeyScore = MutableLiveData<Float>()
    val honeyScore: LiveData<Float>
        get() = _honeyScore

    fun changeHoneyScore(value: Float) {
        _honeyScore.value = value
    }

    private val _satisfactionScore = MutableLiveData<Float>()
    val satisfactionScore: LiveData<Float>
        get() = _satisfactionScore

    fun changeSatisfactionScore(value: Float) {
        _satisfactionScore.value = value
    }

    private val _learningScore = MutableLiveData<Float>()
    val learningScore: LiveData<Float>
        get() = _learningScore

    fun changeLearningScore(value: Float) {
        _learningScore.value = value
    }

    suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Boolean {
        val response: Response<String>
        withContext(viewModelScope.coroutineContext) {
            response = writeRepository.postLectureEvaluation(lectureId, info)
        }
        return response.isSuccessful
    }

    suspend fun postLectureExam(lectureId: Long, info: LectureExamPostDto): Boolean {
        val response: Response<String>
        withContext(viewModelScope.coroutineContext) {
            response = writeRepository.postLectureExam(lectureId, info)
        }
        return response.isSuccessful
    }

    suspend fun updateLectureEvaluation(lectureId: Long, info: LectureEvaluationEditDto): Boolean {
        val response: Response<String>
        withContext(viewModelScope.coroutineContext) {
            response = writeRepository.updateLectureEvaluation(lectureId, info)
        }
        return response.isSuccessful
    }

    init {
        _honeyScore.value = 0f
        _satisfactionScore.value = 0f
        _learningScore.value = 0f
    }
}