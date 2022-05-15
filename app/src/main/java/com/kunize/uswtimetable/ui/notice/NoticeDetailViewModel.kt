package com.kunize.uswtimetable.ui.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDetailData
import com.kunize.uswtimetable.repository.notice.NoticeDetailRepository
import com.kunize.uswtimetable.ui.common.Event
import com.kunize.uswtimetable.util.Constants.TAG
import kotlinx.coroutines.launch

class NoticeDetailViewModel(private val repository: NoticeDetailRepository) : ViewModel() {
    var notice = MutableLiveData<NoticeDetailData>()
    val loading = MutableLiveData<Boolean>()
    private val _eventBack = MutableLiveData<Event<Boolean>>()
    val eventBack: LiveData<Event<Boolean>> get() = _eventBack

    fun getNotice(id: Long) {
        viewModelScope.launch {
            loading.postValue(true)
            val response = repository.getNotice(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d(TAG, "NoticeDetailViewModel - getNotice() called / ${it.data}")
                    notice.postValue(it.data)
                }
                loading.postValue(false)
            }
        }
    }

    fun backToList() {
        _eventBack.value = Event(true)
    }
}