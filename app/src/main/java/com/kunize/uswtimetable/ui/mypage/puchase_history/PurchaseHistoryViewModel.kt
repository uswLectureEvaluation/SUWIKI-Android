package com.kunize.uswtimetable.ui.mypage.puchase_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.PurchaseHistory
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import kotlinx.coroutines.launch

class PurchaseHistoryViewModel(private val repository: MyPostRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    private val _historyList = MutableLiveData<List<PurchaseHistory>>()
    val historyList: LiveData<List<PurchaseHistory>> get() = _historyList
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
                if (response.body()?.data?.isEmpty() == true) {
                    _errorMessage.postValue("구매 이력이 없습니다")
                }
                response.body()?.data?.let { _historyList.postValue(response.body()?.data!!) }
            } else {
                _errorMessage.postValue("구매 이력 로딩 실패")
            }
            loading.postValue(false)
        }
    }
}