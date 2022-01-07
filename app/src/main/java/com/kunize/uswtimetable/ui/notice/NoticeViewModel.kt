package com.kunize.uswtimetable.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.dataclass.NoticeData
import com.kunize.uswtimetable.retrofit.RetrofitManager
import com.kunize.uswtimetable.util.ResponseState
import com.kunize.uswtimetable.util.isJsonArray
import com.kunize.uswtimetable.util.isJsonObject
import java.util.*

class NoticeViewModel: ViewModel() {
    private val retrofitManager = RetrofitManager.instance
    private var _noticeList = mutableListOf<NoticeData>()
    val noticeList:List<NoticeData> get() = _noticeList
    val dumpData = listOf(
        NoticeData(1, "임시 공지사항 제목1", Date(System.currentTimeMillis())),
        NoticeData(2, "임시 공지사항 제목2", Date(System.currentTimeMillis())),
        NoticeData(3, "임시 공지사항 제목3", Date(System.currentTimeMillis())),
        NoticeData(4, "임시 공지사항 제목4", Date(System.currentTimeMillis())),
        NoticeData(5, "임시 공지사항 제목5", Date(System.currentTimeMillis())),
        NoticeData(6, "임시 공지사항 제목6", Date(System.currentTimeMillis())),
        NoticeData(7, "임시 공지사항 제목7", Date(System.currentTimeMillis())),
        NoticeData(8, "임시 공지사항 제목8", Date(System.currentTimeMillis())),
        NoticeData(9, "임시 공지사항 제목9", Date(System.currentTimeMillis())),
        NoticeData(10, "임시 공지사항 제목10", Date(System.currentTimeMillis())),
        NoticeData(11, "임시 공지사항 제목11", Date(System.currentTimeMillis())),
        NoticeData(12, "임시 공지사항 제목12", Date(System.currentTimeMillis())),
        NoticeData(13, "임시 공지사항 제목13", Date(System.currentTimeMillis())),
        NoticeData(14, "임시 공지사항 제목14", Date(System.currentTimeMillis())),
        NoticeData(15, "임시 공지사항 제목15", Date(System.currentTimeMillis())),
        NoticeData(16, "임시 공지사항 제목16", Date(System.currentTimeMillis())),
        NoticeData(17, "임시 공지사항 제목17", Date(System.currentTimeMillis())),
        NoticeData(18, "임시 공지사항 제목18", Date(System.currentTimeMillis())),

    )

    init {
        retrofitManager.getNoticeList { state, result ->
            when(state) {
                ResponseState.OK -> {
                    // result에 받아온 리스트 있음
                    _noticeList = parseNotice(result)
                }
                ResponseState.FAIL -> {
                    // result에 오류 메시지 있음
                    // TODO 리스트를 불러오지 못했을 때 어떻게 처리할지 고민 고민
                }
            }
        }
    }

    fun getNotice(id: Int) {
        retrofitManager.getNotice(id, completion = { state, result ->
            when(state) {
                ResponseState.OK -> {
                    // result에 받아온 공지 있음
                }
                ResponseState.FAIL -> {
                    // result에 오류 메시지 있음
                }
            }
        })
    }

    private fun parseNotice(data: String): MutableList<NoticeData> {
        // TODO 받아온 데이터를 리스트로 파싱하는 과정
        val notices = mutableListOf<NoticeData>()
        when {
            data.isJsonArray() -> {

            }
            data.isJsonObject() -> {

            }
        }
        return notices
    }
}