package com.kunize.uswtimetable.ui.mypage.my_post.evaluation

import com.kunize.uswtimetable.dataclass.MyEvaluationDto

sealed class Event {
    data class EditEvent(val evaluation: MyEvaluationDto) : Event()
    data class DeleteEvent(val evaluation: MyEvaluationDto) : Event()
}
