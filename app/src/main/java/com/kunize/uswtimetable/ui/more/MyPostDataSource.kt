package com.kunize.uswtimetable.ui.more

import com.kunize.uswtimetable.dataclass.MyEvaluation

interface MyPostDataSource {
    fun getMyPostData(): List<MyEvaluation>
}