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
import com.kunize.uswtimetable.util.LIST_CONFIG.ONCE_REQUEST_SIZE
import kotlinx.coroutines.launch

class MyEvaluationViewModel(private val repository: MyPostRepository) : ViewModel() {
    private val _myEvaluationData = MutableLiveData<List<MyEvaluationDto>>()
    val myEvaluationData: LiveData<List<MyEvaluationDto>> get() = _myEvaluationData
    val loading = MutableLiveData(true)
    private val page = MutableLiveData<Int>()
    private var loadFinished = false

    init {
        page.value = 1
        scrollBottomEvent()
    }

    fun scrollBottomEvent() {
        if (loadFinished || page.value == LAST_PAGE) return
        Log.d(TAG, "MyEvaluationViewModel - scrollBottomEvent(${page.value}) called / loadFinished : $loadFinished")

        viewModelScope.launch {
            loading.postValue(true)
            val response = repository.getEvaluations(page.value ?: 1)
            if (response.isSuccessful) {
                val responseData = response.body() ?: return@launch

                val currentData = _myEvaluationData.value?.toMutableList() ?: mutableListOf()
                currentData.addAll(responseData.data)
                _myEvaluationData.postValue(currentData)

                if (responseData.data.size < ONCE_REQUEST_SIZE) loadFinished = true
                else nextPage()
            }
            loading.postValue(false)
        }
    }

    private fun nextPage() {
        if (page.value == LAST_PAGE) return
        page.postValue(page.value?.plus(1))
    }

    fun deletePost(id: Long) {
        viewModelScope.launch {
            repository.deleteEvaluation(id)
            initList()
        }
    }

    private fun initList() {
        loadFinished = false
        _myEvaluationData.value = emptyList()
        page.value = 1
        scrollBottomEvent()
    }
}