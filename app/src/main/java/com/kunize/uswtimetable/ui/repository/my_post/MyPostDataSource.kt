package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluation

interface MyPostDataSource {
    fun getMyPostData(): List<MyEvaluation>
}