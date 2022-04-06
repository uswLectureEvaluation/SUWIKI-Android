package com.kunize.uswtimetable.ui.evaluation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.common.BaseRecyclerItemViewModel
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.ui.repository.evaluation.EvaluationRepository
import com.kunize.uswtimetable.util.LectureApiOption.MODIFIED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EvaluationViewModel(private val evaluationRepository: EvaluationRepository) : BaseRecyclerItemViewModel() {
    private val _selectedType = MutableLiveData<String>()

    private fun loadEvaluationData() {
        viewModelScope.launch {
            val response = evaluationRepository.getLectureMainList(_selectedType.value.toString())
            if(response.isSuccessful) {
                delay(delayTime)
                deleteLoading()
                evaluationList.value = response.body()?.convertToEvaluationData()
            } else {
                toastMessage = "${response.code()} 에러 발생!"
                _makeToast.value = Event(true)
                evaluationList.value = arrayListOf()
            }
        }
    }

    init {
        changeType(MODIFIED)
    }

    fun changeType(option: String) {
        _selectedType.value = option
        loadEvaluationData()
    }
}