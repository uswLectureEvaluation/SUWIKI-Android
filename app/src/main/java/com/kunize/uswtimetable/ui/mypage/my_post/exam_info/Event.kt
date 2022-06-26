package com.kunize.uswtimetable.ui.mypage.my_post.exam_info

import com.kunize.uswtimetable.data.remote.LectureExamDto

sealed class Event {
    data class EditEvent(val examInfo: LectureExamDto) : Event()
    data class DeleteEvent(val examInfo: LectureExamDto) : Event()
}
