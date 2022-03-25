package com.kunize.uswtimetable.ui.evaluation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureMainDto
import com.kunize.uswtimetable.ui.repository.evaluation.EvaluationRepository
import com.kunize.uswtimetable.util.LectureApiOption.HONEY
import com.kunize.uswtimetable.util.LectureApiOption.MODIFIED
import com.kunize.uswtimetable.util.LectureApiOption.SATISFACTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvaluationViewModel(private val evaluationRepository: EvaluationRepository) : ViewModel() {
    private val _evaluationList = MutableLiveData<ArrayList<EvaluationData?>>()
    val evaluationList: LiveData<ArrayList<EvaluationData?>>
        get() = _evaluationList

    private val _selectedType = MutableLiveData<String>()

    private fun loadEvaluationData() {
        viewModelScope.launch {
            val response = evaluationRepository.getLectureMainList(_selectedType.value.toString())
            if(response.isSuccessful) {
                deleteLoading()
                _evaluationList.value = response.body()?.convertToEvaluationData()
            } else {
                _evaluationList.value = arrayListOf()
            }
        }
    }

    init {
        changeType(MODIFIED)
    }

    private fun loading() {
        _evaluationList.value = arrayListOf(null)
    }

    fun changeType(option: String) {
        _selectedType.value = option
        loading()
        loadEvaluationData()
    }

    private fun deleteLoading() {
        if(_evaluationList.value?.isEmpty() == true)
            return
        if(_evaluationList.value?.get(evaluationList.value?.size!! - 1) == null) {
            _evaluationList.value?.removeAt(evaluationList.value?.size!! - 1)
            _evaluationList.value = _evaluationList.value
        }
    }
}