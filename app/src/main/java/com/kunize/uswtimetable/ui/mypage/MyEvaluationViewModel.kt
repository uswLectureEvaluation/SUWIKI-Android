package com.kunize.uswtimetable.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.ui.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.LAST_PAGE
import kotlinx.coroutines.launch

class MyEvaluationViewModel(private val repository: MyPostRepository) : ViewModel() {
    private val _myEvaluationData = MutableLiveData<List<MyEvaluationDto>>()
    val myEvaluationData: LiveData<List<MyEvaluationDto>> get() = _myEvaluationData
    private val page = MutableLiveData<Int>()

    init {
        page.value = 1
        scrollBottomEvent()
    }

    fun getMyEvaluationDetail(id: Long) = myEvaluationData.value?.find { evaluation ->
        evaluation.id == id
    }

    fun scrollBottomEvent() {
        Log.d(TAG, "MyEvaluationViewModel - scrollBottomEvent(${page.value}) called")
        if (page.value == LAST_PAGE) return

        viewModelScope.launch {
            val response = repository.getEvaluations(page.value ?: 1)
            if (response.isSuccessful && response.body() != null) {
                val responseData = response.body()?:return@launch
                if (responseData.data.isEmpty()) return@launch

                val currentData = _myEvaluationData.value?.toMutableList() ?: mutableListOf()
                currentData.addAll(responseData.data)
                _myEvaluationData.postValue(currentData)
                Log.d(TAG, "MyEvaluationViewModel / new data: ${responseData.data} ")
                nextPage()
            }
        }
    }

    private fun nextPage() {
        if (page.value == LAST_PAGE) return
        page.postValue(page.value?.plus(1))
    }
}