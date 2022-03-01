package com.kunize.uswtimetable.ui.repository

import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.MyEvaluation
import com.kunize.uswtimetable.ui.more.MyPostDataSource

class MyPostRepository(private val myPostDataSource: MyPostDataSource) {
    fun getEvaluations(): List<MyEvaluation> {
        return myPostDataSource.getMyPostData()
    }
}