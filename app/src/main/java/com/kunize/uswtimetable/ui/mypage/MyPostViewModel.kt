package com.kunize.uswtimetable.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.dataclass.MyExamInfoDto
import com.kunize.uswtimetable.ui.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.launch

class MyPostViewModel(private val repository: MyPostRepository) : ViewModel() {
    private val _myEvaluationData = MutableLiveData<List<MyEvaluationDto>>()
    val myEvaluationData: LiveData<List<MyEvaluationDto>> get() = _myEvaluationData
    private val _myExamInfoData = MutableLiveData<List<MyExamInfoDto>>()
    val myExamInfoData: LiveData<List<MyExamInfoDto>> get() = _myExamInfoData

    init {
        getMyEvaluations()
        getMyExamInfos()
    }

    fun getMyEvaluationDetail(id: Long) = myEvaluationData.value?.find { evaluation ->
        evaluation.id == id
    }

    fun getMyExamInfoDetail(id: Long) = myExamInfoData.value?.find { examInfo ->
        examInfo.id == id
    }

    private fun getMyEvaluations() {
        viewModelScope.launch {
            val response = repository.getEvaluations()
            if (response.isSuccessful) {
                _myEvaluationData.postValue(response.body()?.data)
            } else {
                Log.d(TAG, "MyPostViewModel - getMyEvaluations() failed : ${response.code()} ${response.message()}")
            }
        }
    }

    private fun getMyExamInfos() {
        viewModelScope.launch {
            val response = repository.getExamInfos()
            if (response.isSuccessful) {
                _myExamInfoData.postValue(response.body()?.data)
            } else {
                Log.d(TAG, "MyPostViewModel - getMyExamInfos() failed : ${response.code()} ${response.message()}")
            }
        }
    }
}