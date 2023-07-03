package com.kunize.uswtimetable.ui.mypage.suspend_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.model.Result
import com.suwiki.domain.model.Suspension
import com.suwiki.domain.repository.SuspensionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuspendHistoryViewModel @Inject constructor(
    private val repository: SuspensionRepository,
) : ViewModel() {
    private lateinit var _history: LiveData<List<Suspension>>
    val suspendHistory: LiveData<List<Suspension>> get() = _history

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _history = repository.getSuspensionHistory().asLiveData().switchMap {
                liveData {
                    when (it) {
                        is Result.Success -> it.data
                        is Result.Failure -> emptyList()
                    }
                }
            }
            repository.getSuspensionHistory()
        }
    }
}
