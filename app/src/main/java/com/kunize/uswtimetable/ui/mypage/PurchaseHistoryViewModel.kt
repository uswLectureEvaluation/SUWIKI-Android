package com.kunize.uswtimetable.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.PurchaseHistoryDto
import com.kunize.uswtimetable.ui.repository.my_post.MyPostRepository
import kotlinx.coroutines.launch

class PurchaseHistoryViewModel(private val repository: MyPostRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    private val _historyList = MutableLiveData<List<PurchaseHistoryDto>>()
    val historyList: LiveData<List<PurchaseHistoryDto>> get() = _historyList
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        getHistory()
        loading.value = true
    }

    private fun getHistory() {
        viewModelScope.launch {
            loading.postValue(true)
            val response = repository.getPurchaseHistory()
            if (response.isSuccessful) {
                response.body()?.let { _historyList.postValue(response.body()) }
            } else {
                _errorMessage.postValue("구매 이력 로딩 실패")
            }
            loading.postValue(false)
        }
    }
}