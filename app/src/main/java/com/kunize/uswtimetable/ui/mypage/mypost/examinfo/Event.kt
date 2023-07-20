package com.kunize.uswtimetable.ui.mypage.mypost.examinfo

import com.suwiki.domain.model.LectureExam

sealed class Event {
    data class EditEvent(val examInfo: LectureExam) : Event()
    data class DeleteEvent(val examInfo: LectureExam) : Event()
}
