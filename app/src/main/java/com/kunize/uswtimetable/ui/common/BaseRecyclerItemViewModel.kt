package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.EvaluationData

open class BaseRecyclerItemViewModel: ViewModel() {
    val evaluationList = MutableLiveData<ArrayList<EvaluationData?>>()
    val evaluationListAccessor: LiveData<ArrayList<EvaluationData?>>
        get() = evaluationList
    val delayTime = 200L

    fun loading() {
        evaluationList.value = arrayListOf(null)
    }

    fun deleteLoading() {
        if(evaluationList.value?.isEmpty() == true)
            return
        if(evaluationList.value?.get(evaluationList.value?.size!! - 1) == null) {
            evaluationList.value?.removeAt(evaluationList.value?.size!! - 1)
            evaluationList.value = evaluationList.value
        }
    }
}