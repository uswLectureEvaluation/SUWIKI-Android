package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.ui.repository.notice.NoticeRepository

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    private var _noticeList = MutableLiveData<List<NoticeDto>>()
    val noticeList: LiveData<List<NoticeDto>> get() = _noticeList

    init {
        getNotices(1) // TODO 스크롤에 따른 페이지 변화
    }

    private fun getNotices(page: Int?) {
        _noticeList = noticeRepository.getNotices(page) as MutableLiveData<List<NoticeDto>>

    }
}