package com.kunize.uswtimetable.ui.evaluation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.ui.common.CommonRecyclerViewViewModel
import com.kunize.uswtimetable.ui.common.HandlingErrorInterface
import com.kunize.uswtimetable.ui.common.ToastViewModel
import com.kunize.uswtimetable.ui.repository.evaluation.EvaluationRepository
import com.kunize.uswtimetable.util.LectureApiOption.MODIFIED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EvaluationViewModel(private val evaluationRepository: EvaluationRepository) : ViewModel(), HandlingErrorInterface {
    val commonRecyclerViewViewModel = CommonRecyclerViewViewModel()
    val toastViewModel = ToastViewModel()
    private val _selectedType = MutableLiveData<String>()

    private fun loadEvaluationData() {
        viewModelScope.launch {
            val response = evaluationRepository.getLectureMainList(_selectedType.value.toString())
            if(response.isSuccessful) {
                commonRecyclerViewViewModel.deleteLoading()
                commonRecyclerViewViewModel.changeRecyclerViewData(response.body()?.convertToEvaluationData()!!)
            } else {
                handleError(response.code())
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

    override fun handleError(errorCode: Int) {
        toastViewModel.toastMessage = "$errorCode 에러 발생!"
        toastViewModel.showToastMsg()
        commonRecyclerViewViewModel.changeRecyclerViewData(mutableListOf())
    }
}