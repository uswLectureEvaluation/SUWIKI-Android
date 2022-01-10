package com.kunize.uswtimetable.ui.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.ResponseState
import com.kunize.uswtimetable.util.isJsonArray
import com.kunize.uswtimetable.util.isJsonObject
import java.util.*

class NoticeViewModel : ViewModel() {
    private val retrofitManager = RetrofitManager.instance
    private var _noticeList = MutableLiveData<List<NoticeDto>>()//listOf<NoticeDto>()
    val noticeList: LiveData<List<NoticeDto>> get() = _noticeList
    /*val dumpData = listOf(
        NoticeDto(1, "임시 공지사항 제목1", Date(System.currentTimeMillis()).toString()),
        NoticeDto(2, "임시 공지사항 제목2", Date(System.currentTimeMillis()).toString()),
        NoticeDto(3, "임시 공지사항 제목3", Date(System.currentTimeMillis()).toString()),
        NoticeDto(4, "임시 공지사항 제목4", Date(System.currentTimeMillis()).toString()),
        NoticeDto(5, "임시 공지사항 제목5", Date(System.currentTimeMillis()).toString()),
        NoticeDto(6, "임시 공지사항 제목6", Date(System.currentTimeMillis()).toString()),
        NoticeDto(7, "임시 공지사항 제목7", Date(System.currentTimeMillis()).toString()),
        NoticeDto(8, "임시 공지사항 제목8", Date(System.currentTimeMillis()).toString()),
        NoticeDto(9, "임시 공지사항 제목9", Date(System.currentTimeMillis()).toString()),
        NoticeDto(10, "임시 공지사항 제목10", Date(System.currentTimeMillis()).toString()),
        NoticeDto(11, "임시 공지사항 제목11", Date(System.currentTimeMillis()).toString()),
        NoticeDto(12, "임시 공지사항 제목12", Date(System.currentTimeMillis()).toString()),
        NoticeDto(13, "임시 공지사항 제목13", Date(System.currentTimeMillis()).toString()),
        NoticeDto(14, "임시 공지사항 제목14", Date(System.currentTimeMillis()).toString()),
        NoticeDto(15, "임시 공지사항 제목15", Date(System.currentTimeMillis()).toString()),
        NoticeDto(16, "임시 공지사항 제목16", Date(System.currentTimeMillis()).toString()),
        NoticeDto(17, "임시 공지사항 제목17", Date(System.currentTimeMillis()).toString()),
        NoticeDto(18, "임시 공지사항 제목18", Date(System.currentTimeMillis()).toString()),

    )*/

    init {
        retrofitManager.getNoticeList { state, result ->
            when (state) {
                ResponseState.OK -> {
                    // result에 받아온 리스트 있음
                    _noticeList.value = result ?: listOf()
                }
                ResponseState.FAIL -> {
                    // result에 오류 메시지 있음
                    Log.d(TAG, "NoticeViewModel - 공지 데이터 받아오기 실패")
                    // TODO 리스트를 불러오지 못했을 때 어떻게 처리할지 고민 고민
                }
            }
        }
    }

    fun getNotice(id: Int) {
        retrofitManager.getNotice(id, completion = { state, result ->
            when (state) {
                ResponseState.OK -> {
                    // result에 받아온 공지 있음
                }
                ResponseState.FAIL -> {
                    // result에 오류 메시지 있음
                }
            }
        })
    }
}