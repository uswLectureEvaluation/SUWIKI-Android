package com.kunize.uswtimetable.ui.mypage.mypost.evaluation

import com.suwiki.domain.model.MyEvaluation

sealed class Event {
    data class EditEvent(val evaluation: MyEvaluation) : Event()
    data class DeleteEvent(val evaluation: MyEvaluation) : Event()
}
