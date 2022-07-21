package com.kunize.uswtimetable.ui.mypage.suspend_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.data.local.Suspension
import kotlinx.coroutines.launch

class SuspendHistoryViewModel(private val repository: SuspensionRepository) : ViewModel() {
    private lateinit var _history: LiveData<List<Suspension>>
    val suspendHistory: LiveData<List<Suspension>> get() = _history

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _history = repository.suspensionHistory.asLiveData()
            repository.getSuspensionHistory()
        }
    }
}
