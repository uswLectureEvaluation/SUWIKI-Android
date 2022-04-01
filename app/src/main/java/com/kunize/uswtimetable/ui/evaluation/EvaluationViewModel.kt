package com.kunize.uswtimetable.ui.evaluation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.common.BaseRecyclerItemViewModel
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
                evaluationList.value = arrayListOf()
            }
        }
    }

    init {
        changeType(MODIFIED)
    }

    fun changeType(option: String) {
        loading()
        _selectedType.value = option
        loadEvaluationData()
    }
}