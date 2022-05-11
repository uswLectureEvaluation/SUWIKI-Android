package com.kunize.uswtimetable.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.data.remote.LectureEvaluationEditDto
import com.kunize.uswtimetable.data.remote.LectureEvaluationPostDto
import com.kunize.uswtimetable.data.remote.LectureExamEditDto
import com.kunize.uswtimetable.data.remote.LectureExamPostDto
import com.kunize.uswtimetable.ui.common.HandlingErrorInterface
import com.kunize.uswtimetable.ui.common.ToastViewModel
import com.kunize.uswtimetable.repository.write.WriteRepository
import kotlinx.coroutines.withContext
import retrofit2.Response

class WriteViewModel(private val writeRepository: WriteRepository) : ViewModel(), HandlingErrorInterface {
    val toastViewModel = ToastViewModel()
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

    suspend fun postLectureEvaluation(lectureId: Long, info: LectureEvaluationPostDto): Response<String> {
        val response: Response<String>
        withContext(viewModelScope.coroutineContext) {
            response = writeRepository.postLectureEvaluation(lectureId, info)
        }
        return response
    }

    suspend fun postLectureExam(lectureId: Long, info: LectureExamPostDto): Response<String> {
        val response: Response<String>
        withContext(viewModelScope.coroutineContext) {
            response = writeRepository.postLectureExam(lectureId, info)
        }
        return response
    }

    suspend fun updateLectureEvaluation(lectureId: Long, info: LectureEvaluationEditDto): Response<String> {
        val response: Response<String>
        withContext(viewModelScope.coroutineContext) {
            response = writeRepository.updateLectureEvaluation(lectureId, info)
        }
        return response
    }

    suspend fun updateLectureExam(lectureId: Long, info: LectureExamEditDto): Response<String> {
        val response: Response<String>
        withContext(viewModelScope.coroutineContext) {
            response = writeRepository.updateLectureExam(lectureId, info)
        }
        return response
    }

    init {
        _honeyScore.value = 0f
        _satisfactionScore.value = 0f
        _learningScore.value = 0f
    }

    override fun handleError(errorCode: Int) {
        toastViewModel.toastMessage = when (errorCode) {
            400 -> "이미 작성한 이력이 있어요!"
            403 -> "권한이 없어요! 이용 제한 내역을 확인하거나 문의해주세요!"
            else -> "$errorCode 에러 발생!"
        }
        toastViewModel.showToastMsg()
    }
}