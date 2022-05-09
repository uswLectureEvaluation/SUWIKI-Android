package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.EvaluationData

class CommonRecyclerViewViewModel: ViewModel() {
    private val _evaluationList = MutableLiveData<MutableList<EvaluationData?>>()
    val evaluationList: LiveData<MutableList<EvaluationData?>>
        get() = _evaluationList

    init {
        loading()
    }

    fun loading() {
        _evaluationList.value = mutableListOf(null)
    }

    fun deleteLoading() {
        if(_evaluationList.value?.isEmpty() == true)
            return
        if(_evaluationList.value?.get(_evaluationList.value?.size!! - 1) == null) {
            _evaluationList.value?.removeAt(_evaluationList.value?.size!! - 1)
            _evaluationList.value = _evaluationList.value
        }
    }

    fun changeRecyclerViewData(data: MutableList<EvaluationData?>) {
        _evaluationList.value = data
    }
}