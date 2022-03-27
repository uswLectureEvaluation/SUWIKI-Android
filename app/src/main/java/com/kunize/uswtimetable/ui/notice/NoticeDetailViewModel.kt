package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunize.uswtimetable.dataclass.NoticeDetailData
import com.kunize.uswtimetable.ui.repository.notice.NoticeDetailRepository
import kotlinx.coroutines.launch

class NoticeDetailViewModel(private val repository: NoticeDetailRepository) : ViewModel() {
    var notice = MutableLiveData<NoticeDetailData>()
    val loading = MutableLiveData<Boolean>()

    fun getNotice(id: Long) {
        viewModelScope.launch {
            loading.value = true
            val response = repository.getNotice(id)
            if (response.isSuccessful) {
                notice.postValue(response.body()?.data)
                loading.postValue(false)
            }
        }
    }
}