package com.kunize.uswtimetable.ui.mypage.puchase_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.model.PurchaseHistory
import com.suwiki.domain.model.Result
import com.suwiki.domain.repository.MyPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseHistoryViewModel @Inject constructor(
    private val repository: MyPostRepository,
) : ViewModel() {
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
            when (val response = repository.getPurchaseHistory()) {
                is Result.Success -> {
                    if (response.data.isEmpty()) _errorMessage.postValue("구매 이력이 없습니다")
                    _historyList.postValue(response.data)
                }

                is Result.Failure -> _errorMessage.postValue("구매 이력 로딩 실패")
            }

            loading.postValue(false)
        }
    }
}
