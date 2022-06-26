package com.kunize.uswtimetable.ui.mypage.suspend_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.SuspensionHistory
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch

class SuspendHistoryViewModel(private val repository: SuspensionRepository) : ViewModel() {
    private val _history = MutableLiveData<List<SuspensionHistory>>()
    val suspendHistory: LiveData<List<SuspensionHistory>> get() = _history

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val response = repository.getSuspensionHistory()
            response.onSuccess {
                _history.postValue(data ?: emptyList())
            }
        }
    }
}
